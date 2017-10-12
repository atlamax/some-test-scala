package com.mobcrush.ums.service

import java.io.{InputStream, InputStreamReader}
import java.util

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeFlow, GoogleClientSecrets, GoogleCredential}
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.youtube.model.CommentThreadListResponse
import com.google.api.services.youtube.{YouTube, YouTubeScopes}

/**
  * Created by msekerjitsky on 11.10.2017.
  */
object YouTubeDataService {

  import com.google.api.client.json.jackson2.JacksonFactory

  private val JSON_FACTORY: JsonFactory = JacksonFactory.getDefaultInstance
  private val HTTP_TRANSPORT: HttpTransport = GoogleNetHttpTransport.newTrustedTransport
  private val SCOPES = util.Arrays.asList(YouTubeScopes.YOUTUBE_READONLY, YouTubeScopes.YOUTUBEPARTNER, YouTubeScopes.YOUTUBE_FORCE_SSL)
  private val DATA_STORE_FACTORY: FileDataStoreFactory = getDataStoreFactory
  private val CREDENTIAL: Credential = getCredential
  private val YOUTUBE: YouTube = getYoutubeService

  def getData(accessToken: String): Unit = {
    val response: CommentThreadListResponse = YOUTUBE.commentThreads().list("snippet").setVideoId("zMGjyCzAtYE").execute()

    println(response)
  }

  private def getDataStoreFactory: FileDataStoreFactory = {
    new FileDataStoreFactory(new java.io.File(
      sys.props("user.home"), ".credentials/youtube-java-quickstart")
    )
  }

  private def getCredential: Credential = {
    // Build flow and trigger user authorization request.
    val flow: GoogleAuthorizationCodeFlow =
      new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, "483454326705-8b2oqiqese1d81cge7ugo3tp4ragd6hf.apps.googleusercontent.com", null, SCOPES)
        .setDataStoreFactory(DATA_STORE_FACTORY)
//        .setAccessType("offline")
        .build()

    new GoogleCredential.Builder()
      .setClientSecrets("483454326705-8b2oqiqese1d81cge7ugo3tp4ragd6hf.apps.googleusercontent.com", null)
      .setJsonFactory(JSON_FACTORY)
      .setTransport(HTTP_TRANSPORT)
      .build()

//    new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
//      .authorize("user")
  }

  private def getYoutubeService: YouTube = {
    new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, CREDENTIAL)
      .setApplicationName("Mobcrush UMS")
      .build
  }

}
