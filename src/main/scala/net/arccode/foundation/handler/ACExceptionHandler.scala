package net.arccode.foundation.handler

import java.net.UnknownHostException

import akka.http.scaladsl.model.{HttpResponse, StatusCode, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, extractUri}
import akka.http.scaladsl.server.{ExceptionHandler, StandardRoute}
import akka.stream.StreamTcpException
import com.typesafe.scalalogging.Logger
import net.arccode.dict.SymbolDict
import net.arccode.foundation.throwable.ACException._

import scala.util.control.NonFatal

/**
  * 全局异常处理 并返回标准的http status和message
  *
  * @author http://arccode.net
  * @since 2018-03-31
  */
trait ACExceptionHandler {

  private val log = Logger("ACExceptionHandler")

  // 全局
  implicit def globalExceptionHandler: ExceptionHandler = ExceptionHandler {

    case e: NoSuchElementException ⇒
      extractUri { uri ⇒
        commHandle(StatusCodes.NotFound, e)
      }
    case e: IllegalArgumentException ⇒
      commHandle(StatusCodes.BadRequest, e)
    case e: UnknownHostException ⇒
      commHandle(StatusCodes.InternalServerError, e, "系统内部异常, 请联系管理员")
    case e: StreamTcpException ⇒
      commHandle(StatusCodes.InternalServerError, e, "系统内部异常, 请联系管理员")
    case e: ResourceNotFoundException ⇒
      commHandle(StatusCodes.NotFound, e)
    case e: ResourceConflictException ⇒
      commHandle(StatusCodes.Conflict, e)
    case e: TimeoutException ⇒
      commHandle(StatusCodes.GatewayTimeout, e)
    case e: CheckCodeExpiredException ⇒
      commHandle(StatusCodes.Gone, e)
    case e: BizExpiredException ⇒
      commHandle(StatusCodes.Gone, e)
    case e: ParamException ⇒
      commHandle(StatusCodes.BadRequest, e)
    case e: AuthenticationException ⇒
      commHandle(StatusCodes.Unauthorized, e)
    case e: AuthorityException ⇒
      commHandle(StatusCodes.Forbidden, e)
    case e: TooManyRequestsException ⇒
      commHandle(StatusCodes.TooManyRequests, e)
    case e: BizException ⇒
      commHandle(StatusCodes.UnavailableForLegalReasons, e)
    case e: ClassCastException ⇒
      commHandle(StatusCodes.UnprocessableEntity, e)
    case e: NotImplementException ⇒
      commHandle(StatusCodes.NotImplemented, e)

    case NonFatal(e) ⇒
      commHandle(StatusCodes.InternalServerError, e)
  }

  // 私有方法, 仅供handler使用
  private def commHandle(status: StatusCode,
                         t: Throwable,
                         bizMsg: String = ""): StandardRoute = {
    log.info("捕获到全局异常: 异常类名 - [{}], 异常消息 - [{}]",
             t.getClass.getName,
             t.getMessage)
    log.error("捕获到全局异常(栈帧): {}",
              t.getStackTrace.mkString(SymbolDict.HEAP_STACK_EX_SPLIT))

    complete(HttpResponse(status, entity = bizMsg match {
      case str: String if str.isEmpty ⇒ t.getMessage
      case _ ⇒ bizMsg
    }))
  }
}
