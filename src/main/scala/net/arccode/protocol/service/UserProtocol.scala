package net.arccode.protocol.service

import net.arccode.domain.ACUser.User

/**
  * 用户协议
  *
  * @author http://arccode.net
  * @since 2018-04-04
  */
object UserProtocol {

  case class ExecuteSignUp(mobile: String, password: String)
  case class SignUpMsg(id: Long, mobile: String)

  case class ExecuteInsertUser(mobile: String, password: String)
  case class InsertUserMsg(id: Long, mobile: String)

  case class ExecuteSelectUserByMobile(mobile: String)
  case class SelectUserByMobileMsg(user: Option[User] = None)
}
