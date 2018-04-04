package net.arccode.foundation.mapping

import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.AutoDerivation

/**
  * 自动序列化或反序列化case class或其它数据
  *
  * @author http://arccode.net
  * @since 2018-03-31
  */
trait JsonSupport extends FailFastCirceSupport with AutoDerivation
