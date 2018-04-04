package net.arccode.api

import akka.http.scaladsl.server.Directives
import net.arccode.foundation.module.ConfigModule

/**
  * 文档接口
  *
  * @author http://arccode.net
  * @since 2018-04-04
  */
trait DocApi extends Directives with ConfigModule {

  val docApi =
    path("swagger") {
      getFromResource(swaggerBaseIndex)
    } ~ getFromResourceDirectory("swagger")

}
