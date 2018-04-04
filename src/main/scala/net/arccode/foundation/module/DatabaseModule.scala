package net.arccode.foundation.module

import io.getquill.{Literal, MysqlJdbcContext}

/**
  * 数据驱动模块
  *
  * @author http://arccode.net
  * @since 2018-03-31
  */
trait DatabaseModule extends ConfigModule {

  /**
    * 异步数据库驱动
    */
//  lazy val dbContext = new MysqlAsyncContext[Literal]("mysqlAsync")

  /**
    * 同步数据库驱动
    */
  lazy val dbContext = new MysqlJdbcContext(Literal, "mysql")

}
