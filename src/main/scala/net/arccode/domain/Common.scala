package net.arccode.domain

/**
  * 领域模型, 通用
  *
  * @author http://arccode.net
  * @since 2018-04-11
  */
object Common {

  case class Location(code: String,
                      name: String,
                      parentCode: String,
                      level: Int = 0)
}
