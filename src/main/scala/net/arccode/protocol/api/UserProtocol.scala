package net.arccode.protocol.api

import io.swagger.annotations.{ApiModel, ApiModelProperty}

import scala.annotation.meta.field

/**
  * 用户协议
  *
  * @author http://arccode.net
  * @since 2018-04-04
  */
object UserProtocol {

  @ApiModel(description = "用户注册Req")
  case class SignUpReq(
      @(ApiModelProperty @field)(value = "手机号", required = true)
      mobile: String,
      @(ApiModelProperty @field)(value = "密码", required = true)
      password: String)

  @ApiModel(description = "用户注册Response")
  case class SignUpResponse(
      @(ApiModelProperty @field)(value = "用户id", required = true)
      id: Long,
      @(ApiModelProperty @field)(value = "手机号", required = true)
      mobile: String)

}
