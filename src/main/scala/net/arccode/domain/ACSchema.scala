package net.arccode.domain

import net.arccode.domain.ACUser.User
import net.arccode.foundation.module.DatabaseModule

/**
  *
  *
  * @author http://arccode.net
  * @since 2018-04-04
  */
object ACSchema extends DatabaseModule {

  import dbContext._

  val users = quote {
    querySchema[User]("users")
  }

}
