package net.arccode.api

import javax.ws.rs.Path

import akka.actor.Props
import akka.http.scaladsl.server.Directives
import akka.pattern.ask
import io.swagger.annotations._
import net.arccode.aggregator.SignUpActor
import net.arccode.foundation.mapping.JsonSupport
import net.arccode.foundation.module.ApplicationModule
import net.arccode.protocol.api.UserProtocol.{SignUpReq, SignUpResponse}
import net.arccode.protocol.service.UserProtocol.{ExecuteSignUp, SignUpMsg}

/**
  * 用户接口
  *
  * @author http://arccode.net
  * @since 2018-04-04
  */
@Api("用户接口")
@Path("/user")
trait UserApi extends Directives with JsonSupport with ApplicationModule {

  val usersApi = signUp

  @Path("/signUp")
  @ApiOperation(httpMethod = "POST", value = "用户注册")
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(name = "params",
                           value = "业务参数",
                           required = true,
                           dataTypeClass = classOf[SignUpReq],
                           paramType = "body")))
  @ApiResponses(
    Array(
      new ApiResponse(code = 200,
                      message = "业务执行成功",
                      response = classOf[SignUpResponse]),
      new ApiResponse(code = 400, message = "参数错误"),
      new ApiResponse(code = 409, message = "手机号已被注册"),
    ))
  def signUp =
    extractLog { log ⇒
      (path("user" / "signUp") & post) {
        entity(as[SignUpReq]) { req ⇒
          onSuccess(
            system.actorOf(Props[SignUpActor]) ? ExecuteSignUp(mobile =
                                                                 req.mobile,
                                                               password =
                                                                 req.mobile)) {
            response ⇒
              response match {
                case SignUpMsg(id, mobile) ⇒
                  complete(SignUpResponse(id, mobile))
              }
          }
        }
      }
    }
}
