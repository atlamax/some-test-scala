package com.mobcrush.ums

import org.slf4j.{Logger, LoggerFactory}

/**
  * Main class to start application
  */
object UMSServerMain {

  private val LOGGER: Logger = LoggerFactory.getLogger(this.getClass)

  private val STREAM_ID_PARAM_NAME: String = "streamId"
  private val PROVIDER_PARAM_NAME: String = "provider"
  private val TOKEN_PARAM_NAME: String = "token"

  private var streamId: Option[String] = None
  private var providerName: Option[String] = None
  private var accessToken: Option[String] = None

  def main(args: Array[String]): Unit = {
    if(!parseParameters(args)) {
      LOGGER.error("Not all parameters provided. Going to exit")
      LOGGER.info("List of required parameters: {}, {}, {}", STREAM_ID_PARAM_NAME, PROVIDER_PARAM_NAME, TOKEN_PARAM_NAME)
      return
    }

    args.foreach(println(_))
  }

  def parseParameters(args: Array[String]): Boolean = {

    args.foreach(arg => {
      val parametersArray = arg.split("=")

      if (parametersArray.size.equals(2)) {
        parametersArray(0) match {
          case STREAM_ID_PARAM_NAME => streamId = Some(parametersArray(1))
          case PROVIDER_PARAM_NAME => providerName = Some(parametersArray(1))
          case TOKEN_PARAM_NAME => accessToken = Some(parametersArray(1))
          case _ => LOGGER.warn("Unknown parameter: {}", parametersArray(0))
        }
      } else {
        LOGGER.warn("Parameters key and value must be delimited by symbol '=': {}", arg)
      }
    })

    streamId.isDefined && providerName.isDefined && accessToken.isDefined
  }
}
