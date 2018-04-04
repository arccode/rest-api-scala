package net.arccode.foundation.module

import java.util.Date

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.scalalogging.Logger
import io.circe.{Decoder, Encoder, Printer}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
  * 主服务模块
  *
  * @author http://arccode.net
  * @since 2018-03-31
  */
trait ApplicationModule extends Directives {

  private val log = Logger("ApplicationModule")

  implicit val system = ActorSystem("rest-api-scala")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  implicit val timeout = Timeout(3 seconds)

  // date json序列化
  implicit val dateTimeEncoder: Encoder[Date] =
    Encoder.instance(d ⇒ Encoder.encodeLong(d.getTime))

  // date json反序列化
  implicit val dateTimeDecoder: Decoder[Date] =
    Decoder.instance(ms ⇒ ms.as[Long].map(new Date(_)))

  // json序列化配置
  implicit val printer = Printer.noSpaces.copy(dropNullValues = true)

  // http Authorization token解析
}
