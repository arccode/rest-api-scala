package net.arccode.protocol.api.comm

import io.swagger.annotations.ApiModelProperty

import scala.annotation.meta.field

/**
  * 区域信息协议
  *
  * @author http://arccode.net
  * @since 2018-04-11
  */
object LocationProtocol {

  case class LocationResponse(
      @(ApiModelProperty @field)(value = "区域码", required = true)
      code: String,
      @(ApiModelProperty @field)(value = "区域名", required = true)
      name: String,
      @(ApiModelProperty @field)(value = "级别, 1-省/直辖市, 2-市/自治州, 3-区",
                                 required = true)
      level: Int)
}
