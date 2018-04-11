package net.arccode.dao

import akka.actor.{Actor, ActorLogging}
import net.arccode.protocol.service.LocationProtocol.{ExecuteSelectLocationsByParentCode, SelectLocationsByParentCodeMsg}

/**
  * 查询区域信息
  *
  * @author http://arccode.net
  * @since 2018-04-11
  */
class SelectLocationsByParentCode extends Actor with ActorLogging {

  import net.arccode.domain.ACSchema._
  import dbContext._
  override def receive = {
    case cmd @ ExecuteSelectLocationsByParentCode(parentCode) ⇒ {
      log.info("接收到[{}]指令: {}, 发送方 - {}, 接收方 - {}",
               "根据位置code查询其下一级别的位置",
               cmd,
               sender().path,
               self)

      val q = quote {
        locations.filter(p ⇒ p.parentCode == lift(parentCode))
      }

      val result = dbContext.run(q)

      log.info("根据位置code查询其下一级别的位置结果 - {}", result)

      sender() ! SelectLocationsByParentCodeMsg(result)
    }
  }
}
