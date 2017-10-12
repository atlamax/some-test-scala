package com.mobcrush.ums

import scala.collection.mutable


/**
  * Component that contains all global configuration parameters
  */
object ConfigurationHolder {

  private val YOUTUBE_CREDENTIALS_FILE_PARAM_NAME: String = "youtube.credentials.file"

  private val configsMap: mutable.Map[String, String] = new mutable.HashMap[String, String]

  def addConfiguration(key: String, value: String): Unit = {
    configsMap.put(key, value)
  }

  def getConfiguration(key: String): Option[String] = {
    configsMap.get(key)
  }

}
