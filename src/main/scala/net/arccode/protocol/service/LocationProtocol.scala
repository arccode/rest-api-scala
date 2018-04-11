package net.arccode.protocol.service

import net.arccode.domain.Common.Location

/**
  * 区域信息协议
  *
  * @author http://arccode.net
  * @since 2018-04-11
  */
object LocationProtocol {

  case class ExecuteSelectLocationsByParentCode(parentCode: String)
  case class SelectLocationsByParentCodeMsg(subLocations: List[Location])

}
