package net.arccode.foundation.module

import com.typesafe.config.ConfigFactory

/**
  * 配置模块, 对application.conf进行反序列化
  *
  * @author http://arccode.net
  * @since 2018-03-31
  */
trait ConfigModule {

  private val config = ConfigFactory.load()

  // 环境
  val env = config.getString("env")

  // http 监听
  private val httpConfig = config.getConfig("http")
  val httpInterface = httpConfig.getString("interface")
  val httpPort = httpConfig.getInt("port")
  val path = httpConfig.getString("path")

  // flyway
  private val flywayConfig = config.getConfig("flyway")
  val flywayUrl = flywayConfig.getString("url")
  val flywayUsername = flywayConfig.getString("username")
  val flywayPassword = flywayConfig.getString("password")

  // swagger
  private val swaggerConfig = config.getConfig("swagger")
  val swaggerHost = swaggerConfig.getString("host")
  val swaggerBasePath = swaggerConfig.getString("basePath")
  val swaggerApiDocsPath = swaggerConfig.getString("apiDocsPath")
  val swaggerBaseIndex = swaggerConfig.getString("baseIndex")

}
