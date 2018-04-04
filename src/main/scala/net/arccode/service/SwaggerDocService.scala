package net.arccode.service

import akka.actor.ActorSystem
import com.github.swagger.akka.SwaggerHttpService
import com.github.swagger.akka.model.Info
import io.swagger.models.auth.{
  ApiKeyAuthDefinition,
  In,
  SecuritySchemeDefinition
}
import net.arccode.api.UserApi
import net.arccode.foundation.module.ConfigModule

/**
  * 生成swagger json
  *
  * @author http://arccode.net
  * @since 2018-04-04
  */
class SwaggerDocService(system: ActorSystem)
    extends SwaggerHttpService
    with ConfigModule {
  override def apiClasses: Set[Class[_]] =
    Set(classOf[UserApi])

  override val host = swaggerHost
  override val basePath = swaggerBasePath
  override val apiDocsPath = swaggerApiDocsPath
  override val info = Info()
  // http header Authorization
  override def securitySchemeDefinitions
    : Map[String, SecuritySchemeDefinition] =
    Map("apiKey" → new ApiKeyAuthDefinition("Authorization", In.HEADER))
}
