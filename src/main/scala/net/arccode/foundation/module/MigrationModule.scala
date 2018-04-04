package net.arccode.foundation.module

import com.typesafe.scalalogging.Logger
import org.flywaydb.core.Flyway

/**
  * 数据迁移模块
  *
  * 1. 管理sql脚本
  * 2. 自定义jdbc操作
  *
  * @author http://arccode.net
  * @since 2018-03-31
  */
trait MigrationModule extends ConfigModule {

  private val log = Logger("MigrationModule")

  private val flyway = new Flyway()
  flyway.setDataSource(flywayUrl, flywayUsername, flywayPassword)
  flyway.setLocations("db/migration")

  def migrate() = {
    flyway.setBaselineOnMigrate(true)
    flyway.setValidateOnMigrate(false)
    flyway.migrate()
  }

}
