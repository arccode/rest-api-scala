package net.arccode.foundation.handler

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.{MethodNotAllowed, Unauthorized, UnprocessableEntity}
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server._
import com.typesafe.scalalogging.Logger
import net.arccode.dict.SymbolDict

/**
  * 全局DSL出错处理 并返回标准的http status和message
  *
  * @author http://arccode.net
  * @since 2018-03-31
  */
trait ACRejectionHandler {

  private val log = Logger("ACRejectionHandler")

  // 全局
  implicit def globalRejectionHandler =
    RejectionHandler
      .newBuilder()
      .handle {
        case MalformedRequestContentRejection(reason, t) ⇒
          log.error("{}", t.getClass.getName)
          log.error(reason)
          log.error("{}",
                    t.getStackTrace.mkString(SymbolDict.HEAP_STACK_EX_SPLIT))

          complete(HttpResponse(UnprocessableEntity, entity = reason))
      }
      .handle {
        case MissingFormFieldRejection(fieldName) ⇒
          val reason = "Request is missing required form field '" + fieldName + '\''
          log.error(reason)
          complete(HttpResponse(UnprocessableEntity, entity = reason))
      }
      .handle {
        case r: AuthenticationFailedRejection ⇒
          complete(HttpResponse(Unauthorized, entity = "请重新登陆."))
      }
      .handle {
        case MethodRejection(supported) ⇒
          complete(HttpResponse(MethodNotAllowed, entity = "url或method错误"))
      }
      .handleNotFound {
        complete(HttpResponse(MethodNotAllowed, entity = "url或method错误"))
      }
      .result()
}
