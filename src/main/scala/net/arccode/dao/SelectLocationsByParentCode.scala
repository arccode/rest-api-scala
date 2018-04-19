package net.arccode.dao

import akka.actor.{Actor, ActorLogging}
import net.arccode.dict.SymbolDict
import net.arccode.protocol.service.LocationProtocol.{
  ExecuteSelectLocationsByParentCode,
  SelectLocationsByParentCodeMsg
}

/**
  * 查询区域信息 -- 对远程actor提供服务
  *
  * @author http://arccode.net
  * @since 2018-04-11
  */
class SelectLocationsByParentCode extends Actor with ActorLogging {

  import net.arccode.domain.ACSchema._
  import dbContext._
  override def receive = {
    case cmd: String ⇒
      log.info("{}, {}", SymbolDict.DEBUG_DELIMITER, "接收到message")

    case cmd @ ExecuteSelectLocationsByParentCode(parentCode) ⇒ {
      log.info("接收到[{}]指令: {}, 发送方 - {}, 接收方 - {}",
               "根据区域code查询其下一级别的区域",
               cmd,
               sender().path,
               self)

      val q = quote {
        locations.filter(p ⇒ p.parentCode == lift(parentCode))
      }

      val result = dbContext.run(q)

      log.info("根据区域code查询其下一级别的区域结果 - {}", result)

      sender() ! SelectLocationsByParentCodeMsg(result)
    }

    // 远程调用
    case cmd @ net.arccode.protocol.remote.LocationProtocol
          .ExecuteSelectLocationsByParentCode(parentCode) ⇒
      log.info("接收到[{}]指令: {}, 发送方 - {}, 接收方 - {}",
               "根据区域code查询其下一级别的区域(远程)",
               cmd,
               sender().path,
               self)

      val q = quote {
        locations.filter(p ⇒ p.parentCode == lift(parentCode))
      }

      val result = dbContext.run(q)

      log.info("根据区域code查询其下一级别的区域结果(远程) - {}", result)

      sender() ! net.arccode.protocol.remote.LocationProtocol
        .SelectLocationsByParentCodeMsg(
          result.map(
            p ⇒
              net.arccode.protocol.remote.LocationProtocol.Location(
                code = p.code,
                name = p.name,
                parentCode = p.parentCode,
                level = p.level)))

  }
}
