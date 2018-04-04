package net.arccode.foundation.module

/**
  * 断言
  *
  * @author http://arccode.net
  * @since 2018-03-31
  */
trait AssertModule {

  /**
    * 条件运算结果为false时, 抛出传入的异常
    *
    * @param requirement 条件
    * @param throwable 待抛出的异常
    */
  def required(requirement: Boolean, throwable: Throwable): Unit = {
    if (!requirement) {
      throw throwable;
    }
  }
}
