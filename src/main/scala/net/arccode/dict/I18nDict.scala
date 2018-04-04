package net.arccode.dict

/**
  * 国际化字典, 时区和语言
  *
  * @author http://arccode.net
  * @since 2018-03-31
  */
object I18nDict {

  object ACTimeZone extends Enumeration {
    type ACTimeZone = Value

    val TIME_ZONE_ID_DEFAULT = ("Aisa/Shanghai")
  }

  object ACLocale extends Enumeration {
    type ACLocale = Value

    val ZH_CN = Value("zh-CN")
  }
}
