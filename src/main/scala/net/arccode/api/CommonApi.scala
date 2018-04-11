package net.arccode.api

import javax.ws.rs.Path

import akka.http.scaladsl.server.Directives
import akka.pattern.ask
import io.swagger.annotations._
import net.arccode.foundation.mapping.JsonSupport
import net.arccode.foundation.module.{ApplicationModule, AssertModule}
import net.arccode.foundation.throwable.ACException.ParamException
import net.arccode.protocol.api.comm.LocationProtocol.LocationResponse
import net.arccode.protocol.service.LocationProtocol.{ExecuteSelectLocationsByParentCode, SelectLocationsByParentCodeMsg}

/**
  * 通用接口
  *
  * @author http://arccode.net
  * @since 2018-04-11
  */
@Api("通用接口")
@Path("/comm")
trait CommonApi
    extends Directives
    with JsonSupport
    with ApplicationModule
    with AssertModule {

  val commonApi = queryLocations

  @Path("/locations")
  @ApiOperation(httpMethod = "GET",
                value = "获取指定区域码下的所有区域信息, 0 - 获取所有省级区域信息(包括直辖市)")
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(name = "code",
                           value = "区域码",
                           required = true,
                           dataType = "string",
                           paramType = "query")))
  @ApiResponses(
    Array(
      new ApiResponse(code = 200,
                      message = "[]",
                      response = classOf[Array[LocationResponse]])))
  def queryLocations =
    extractLog { log ⇒
      (path("comm" / "locations") & get) {
        parameters('code.?) { code ⇒
          required(!code.isEmpty, ParamException())

          onSuccess(
            system.actorSelection("/user/selectLocationsByParentCode") ? ExecuteSelectLocationsByParentCode(
              code.get)) { p ⇒
            p match {
              case SelectLocationsByParentCodeMsg(subLocations) ⇒ {
                complete(subLocations.map(p ⇒
                  LocationResponse(p.code, p.name, p.level)))
              }
            }
          }
        }
      }
    }
}
