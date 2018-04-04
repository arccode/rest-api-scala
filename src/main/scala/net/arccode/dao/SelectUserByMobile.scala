package net.arccode.dao

import akka.actor.{Actor, ActorLogging}
import net.arccode.domain.ACUser.User
import net.arccode.protocol.service.UserProtocol.{
  ExecuteInsertUser,
  ExecuteSelectUserByMobile,
  InsertUserMsg,
  SelectUserByMobileMsg
}

/**
  * 查询用户
  *
  * @author http://arccode.net
  * @since 2018-04-04
  */
class SelectUserByMobile extends Actor with ActorLogging {

  import net.arccode.domain.ACSchema._
  import dbContext._
  override def receive = {

    case cmd @ ExecuteSelectUserByMobile(mobile) ⇒
      log.info("接收到[{}]指令: {}, 发送方 - {}, 接收方 - {}",
        "根据手机号查询用户",
        cmd,
        sender().path,
        self)

      // 插入
      val q = quote {
        users.filter(_.mobile == lift(mobile))
      }

      val result = dbContext.run(q).headOption

      sender() ! SelectUserByMobileMsg(result)
  }
}
