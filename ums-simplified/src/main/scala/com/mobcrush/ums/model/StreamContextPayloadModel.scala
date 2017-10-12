package com.mobcrush.ums.model

/**
  * Model that represents structure of stream's context payload data
  */
case class StreamContextPayloadModel(streamId: String,
                                     provider: String,
                                     accessToken: String) {

}
