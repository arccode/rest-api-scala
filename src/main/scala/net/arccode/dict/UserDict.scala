package net.arccode.dict

/**
  * 用户字典
  *
  * @author http://arccode.net
  * @since 2018-03-31
  */
object UserDict {

  object Sex extends Enumeration {
    type Sex = Value

    val MALE = Value(1, "男")
    val FEMALE = Value(2, "女")
    val UNKNOWN = Value(3, "未知")
  }
}
