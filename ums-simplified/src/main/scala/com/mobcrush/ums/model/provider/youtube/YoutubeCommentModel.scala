package com.mobcrush.ums.model.provider.youtube

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

/**
  * Created by msekerjitsky on 13.10.2017.
  */
@JsonIgnoreProperties(ignoreUnknown = true)
case class YoutubeCommentModel(@JsonProperty("snippet") snippet: YoutubeCommentSnippetModel) {

}
