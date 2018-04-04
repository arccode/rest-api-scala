import java.time.LocalDate
import java.time.format.DateTimeFormatter

import sbt.Keys.mainClass

// --------------------------------------
// -------- 通用配置 ---------------------
// --------------------------------------
lazy val commonSettings = Seq(
  version := "0.0.1",
  scalaVersion := "2.12.2",
  javaOptions in Universal := Seq(
    "-Dconfig.file=conf/application.conf"
  ),
  javacOptions ++= Seq("-encoding", "UTF-8")
)

lazy val restApi =
  project
    .in(file("."))
    .settings(
      commonSettings,
      name := "rest-api-scala",
      libraryDependencies ++= {
        sys.props += "package.type" → "jar"
        Seq(
          // akka生态相关
          "com.typesafe.akka" %% "akka-actor" % "2.5.3",
          "com.typesafe.akka" %% "akka-stream" % "2.5.3",
          "com.typesafe.akka" %% "akka-http" % "10.0.11",
          // 日志相关
          "com.typesafe.akka" %% "akka-slf4j" % "2.5.3",
          "ch.qos.logback" % "logback-classic" % "1.2.3",
          // db相关
          "mysql" % "mysql-connector-java" % "5.1.39",
          "io.getquill" %% "quill-jdbc" % "2.3.2",
          // 数据迁移相关
          "org.flywaydb" % "flyway-core" % "3.2.1",
          // 文档相关
          "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.10.0",
          // 跨域相关
          "ch.megard" %% "akka-http-cors" % "0.2.1",
          // json序列化和反序列化
          "de.heikoseeberger" %% "akka-http-circe" % "1.17.0",
          "io.circe" %% "circe-generic" % "0.9.1",
          "io.circe" %% "circe-parser" % "0.9.1",
          // jwt相关
          "com.pauldijou" %% "jwt-core" % "0.14.0",
          // 分布式文件相关
          "net.arccode" % "fastdfs-client-java" % "1.27.0",
          // 测试相关
          "org.scalatest" %% "scalatest" % "3.0.3" % "test",
          "com.typesafe.akka" %% "akka-testkit" % "2.5.3" % "test",
          "com.typesafe.akka" %% "akka-http-testkit" % "10.0.11" % "test"
        )
      }
    )

// --------------------------------------
// -------- 使用Submodules对多环境打包 ----
// --------------------------------------
lazy val testPackage = project
  .in(file("build/test"))
  .enablePlugins(JavaAppPackaging)
  .settings(
    commonSettings,
    mainClass in Compile := Some("net.arccode.Main"),
    resourceDirectory in Compile := (resourceDirectory in (restApi, Compile)).value,
    mappings in Universal ++= Seq(
      ((resourceDirectory in Compile).value / "test.conf") → "conf/application.conf"
    ),
    packageName in Universal := "api-rest" + "_test_" + version.value + "_build" + LocalDate
      .now()
      .format(DateTimeFormatter.ofPattern("yyyyMMdd"))
  )
  .dependsOn(restApi)
