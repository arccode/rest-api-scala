package net.arccode

import akka.http.scaladsl.model.HttpMethods
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings
import net.arccode.api.{CommonApi, DocApi, UserApi}
import net.arccode.foundation.handler.{ACExceptionHandler, ACRejectionHandler}
import net.arccode.foundation.module.ConfigModule
import net.arccode.service.SwaggerDocService

/**
  * 路由
  *
  * @author http://arccode.net
  * @since 2018-04-04
  */
trait Routes
    extends ACExceptionHandler
    with ACRejectionHandler
    with ConfigModule
    with DocApi
    with UserApi
    with CommonApi {

  // CORS 设置
  val settings = CorsSettings.defaultSettings.copy(
    allowedMethods = scala.collection.immutable.Seq(HttpMethods.GET,
                                                    HttpMethods.POST,
                                                    HttpMethods.PUT,
                                                    HttpMethods.DELETE,
                                                    HttpMethods.HEAD,
                                                    HttpMethods.OPTIONS))

  // CORS错误处理
  val handleErrors = handleRejections(globalRejectionHandler) & handleExceptions(
    globalExceptionHandler)

  val routes = handleErrors {
    cors(settings) {
      handleErrors {
        (pathPrefix(path) {
          docApi ~
            usersApi ~
            commonApi
        } ~ new SwaggerDocService(system).routes)
      }
    }
  }
}
