package com.mobcrush.ums.service

import java.net.URI

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.mobcrush.ums.YoutubeModelConverter
import com.mobcrush.ums.model.ChatMessage
import com.mobcrush.ums.model.provider.youtube.YoutubeCommentsThreadsModel
import org.apache.http.HttpResponse
import org.apache.http.client.ResponseHandler
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.apache.http.message.BasicHeader
import org.apache.http.util.EntityUtils
import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by msekerjitsky on 11.10.2017.
  */
object YoutubeDataService {

  private val LOGGER: Logger = LoggerFactory.getLogger(this.getClass)

  private val HTTP_CLIENT: CloseableHttpClient = getHttpClient
  private val RESPONSE_HANDLER: ResponseHandler[Option[YoutubeCommentsThreadsModel]] = getResponseHandler
  private val URI_BUILDER: URIBuilder = getURIBuilder
  private val MAPPER: ObjectMapper = new ObjectMapper().registerModule(DefaultScalaModule)

  def getData(accessToken: String, videoId: String): Option[ChatMessage] = {
    val httpGet: HttpGet = new HttpGet(buildURI(videoId))
    httpGet.addHeader(new BasicHeader("Authorization", "Bearer " + accessToken))

    HTTP_CLIENT.execute(httpGet, RESPONSE_HANDLER) match {
      case Some(model) => YoutubeModelConverter.convert(model)
      case None => None
    }
  }

  private def getHttpClient: CloseableHttpClient = {
    HttpClientBuilder.create()
      .build()
  }

  private def getResponseHandler: ResponseHandler[Option[YoutubeCommentsThreadsModel]] = {
      (response: HttpResponse) => {
        val entity = EntityUtils.toString(response.getEntity)

        try {
          Some(
            MAPPER.readValue(entity, classOf[YoutubeCommentsThreadsModel])
          )
        } catch {
          case e: Exception => {
            LOGGER.error("Error occurred during parsing response", e)
            None
          }
        }

      }
  }

  private def getURIBuilder: URIBuilder = {
    new URIBuilder()
      .setScheme("https")
      .setHost("www.googleapis.com")
      .setPath("youtube/v3/commentThreads")
  }

  private def buildURI(videoId: String): URI = {
    URI_BUILDER.clearParameters()
      .addParameter("videoId", videoId)
        .addParameter("part", "snippet")
      .build()
  }

}
