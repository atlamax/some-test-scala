package com.mobcrush.ums.service

import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by msekerjitsky on 11.10.2017.
  */
object SocialProviderService {

  private val LOGGER: Logger = LoggerFactory.getLogger(this.getClass)

  private val YOUTUBE_PROVIDER_NAME: String = "youtube"
  private val FACEBOOK_PROVIDER_NAME: String = "facebook"
  private val PERISCOPE_PROVIDER_NAME: String = "periscope"

  def getMessages(provider: String, accessToken: String): Unit = {

    provider match {
      case YOUTUBE_PROVIDER_NAME => YouTubeDataService.getData(accessToken)
      case FACEBOOK_PROVIDER_NAME => LOGGER.info("Going to receive messages from Facebook")
      case PERISCOPE_PROVIDER_NAME => LOGGER.info("Going to receive messages from Periscope")
      case _ => throw new IllegalArgumentException("Unknown social provider name: " + provider)
    }
  }

}
