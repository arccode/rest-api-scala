package net.arccode.foundation.throwable

/**
  * 自定义异常
  *
  * @author http://arccode.net
  * @since 2018-03-31
  */
object ACException {

  case class TimeoutException(message: String = "处理超时, 请重试",
                              throwable: Throwable = null)
      extends RuntimeException(message, throwable)

  case class ResourceNotFoundException(message: String = "资源没有找到",
                                       throwable: Throwable = null)
      extends RuntimeException(message, throwable)

  case class BadCommandException(message: String = "未知的指令",
                                 throwable: Throwable = null)
      extends RuntimeException(message, throwable)

  case class ResourceConflictException(message: String = "资源已被使用, 请使用其它资源",
                                       throwable: Throwable = null)
      extends RuntimeException(message, throwable)

  case class ParamException(message: String = "输入参数错误, 请重试",
                            throwable: Throwable = null)
      extends RuntimeException(message, throwable)

  case class InvalidFileException(message: String = "无效的文件",
                                  throwable: Throwable = null)
      extends RuntimeException(message, throwable)

  case class MatchException(message: String = "模式匹配异常, 无法匹配输入的数据类型",
                            throwable: Throwable = null)
      extends RuntimeException(message, throwable)

  case class CheckCodeExpiredException(message: String = "验证码已经过期",
                                       throwable: Throwable = null)
      extends RuntimeException(message, throwable)

  case class BizExpiredException(message: String = "业务已经过期",
                                 throwable: Throwable = null)
      extends RuntimeException(message, throwable)

  case class AuthenticationException(message: String = "认证失败, 用户名或密码错误",
                                     throwable: Throwable = null)
      extends RuntimeException(message, throwable)

  case class AuthorityException(message: String = "授权拒绝, 权限不足",
                                throwable: Throwable = null)
      extends RuntimeException(message, throwable)

  case class BeyondAbilityException(message: String = "当前服务无能力处理调用方的请求",
                                    throwable: Throwable = null)
      extends RuntimeException(message, throwable)

  case class TooManyRequestsException(message: String = "调用太频繁",
                                      throwable: Throwable = null)
      extends RuntimeException(message, throwable)

  case class BizException(message: String = "业务异常", throwable: Throwable = null)
      extends RuntimeException(message, throwable)

  case class NotImplementException(message: String = "不提供该服务",
                                   throwable: Throwable = null)
      extends RuntimeException(message, throwable)
}
