package net.arccode.domain

/**
  * 领域模型, 用户
  *
  * @author http://arccode.net
  * @since 2018-04-04
  */
object ACUser {

  case class User(id: Long = 0L, mobile: String, password: String)

}
