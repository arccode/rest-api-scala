package net.arccode.aggregator

import akka.actor.Status.Failure
import akka.actor.{Actor, ActorLogging, Props}
import net.arccode.dao.{InsertUser, SelectUserByMobile}
import net.arccode.dict.SymbolDict
import net.arccode.foundation.throwable.ACException.{
  ResourceConflictException,
  TimeoutException
}
import net.arccode.protocol.service.UserProtocol._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
  * 注册聚合actor, 即用即建, 用完销毁
  *
  * 入口和出口为服务进出点; 其余为分散-聚合中间过程
  *
  * @author http://arccode.net
  * @since 2018-04-04
  */
class SignUpActor extends Actor with ActorLogging {

  // 共享公共可变成员
  var apiSender = sender()
  var mobile = ""
  var password = ""

  override def receive = {

    // 入口  服务进入点
    case cmd @ ExecuteSignUp(mobile, password) ⇒
      log.info("接收到[{}]指令: {}, 发送方 - {}, 接收方 - {}",
               "用户注册",
               cmd,
               sender().path,
               self)

      this.mobile = mobile
      this.password = password
      this.apiSender = sender()

      // 超时检测
      context.system.scheduler.scheduleOnce(2 seconds, self, TimeoutException())

      // 校验手机号是否被注册
      context
        .actorOf(Props[SelectUserByMobile], "selectUserByMobile") ! ExecuteSelectUserByMobile(
        mobile)

    // TODO 增加校验码业务

    // 超时
    case msg: TimeoutException ⇒
      log.info("{}", "调用用户注册Actor超时")

      apiSender ! Failure(TimeoutException())
      context stop self

    case msg @ SelectUserByMobileMsg(userOpt) ⇒ {
      log.info("接收到[{}]指令(响应): {}, 发送方 - {}, 接收方 - {}",
               "查询用户信息",
               msg,
               sender().path,
               self)

      userOpt match {
        case None ⇒
          // 可注册
          context.actorOf(Props[InsertUser], "insertUser") ! ExecuteInsertUser(
            mobile = mobile,
            password = password)

        case _ ⇒
          // 该号码已被注册
          apiSender ! Failure(
            ResourceConflictException(message = "该手机号已被占用, 请更换手机号进行注册"))

          context stop self
      }
    }

    case msg @ InsertUserMsg(id, mobile) ⇒
      log.info("接收到[{}]指令(响应): {}, 发送方 - {}, 接收方 - {}",
               "新增用户",
               msg,
               sender().path,
               self)

      apiSender ! SignUpMsg(id = id, mobile = mobile)

      context stop self
  }
}
