package net.arccode.dao

import akka.actor.{Actor, ActorLogging}
import net.arccode.dict.SymbolDict
import net.arccode.domain.ACUser.User
import net.arccode.protocol.service.UserProtocol.{ExecuteInsertUser, InsertUserMsg}

/**
  * 新增用户
  *
  * @author http://arccode.net
  * @since 2018-04-04
  */
class InsertUser extends Actor with ActorLogging {

  import net.arccode.domain.ACSchema._
  import dbContext._
  override def receive = {

    case cmd @ ExecuteInsertUser(mobile, password) ⇒

      log.info("接收到[{}]指令: {}, 发送方 - {}, 接收方 - {}",
        "插入用户",
        cmd,
        sender().path,
        self)

      // 插入
      val q = quote {
        users
          .insert(lift(User(mobile = mobile, password = password))).returning(_.id)
      }

      val result = dbContext.run(q)

      sender() ! InsertUserMsg(id = result, mobile = mobile)
  }
}
