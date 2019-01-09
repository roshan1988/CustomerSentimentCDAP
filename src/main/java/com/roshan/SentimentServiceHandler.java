/*
 * Copyright © 2014 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.roshan;

import co.cask.cdap.api.annotation.UseDataSet;
import co.cask.cdap.api.common.Bytes;
import co.cask.cdap.api.dataset.lib.KeyValueTable;
import co.cask.cdap.api.dataset.lib.ObjectMappedTable;
import co.cask.cdap.api.service.http.AbstractHttpServiceHandler;
import co.cask.cdap.api.service.http.HttpServiceRequest;
import co.cask.cdap.api.service.http.HttpServiceResponder;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Handler for user sentiment requests
 */
public final class SentimentServiceHandler extends AbstractHttpServiceHandler {

    public static final String SERVICE_NAME = "SentimentService";
    public static final String SENTIMENT_ENDPOINT = "sentiment";

    private static final Gson GSON = new Gson();

    @UseDataSet("sentiment")
    private ObjectMappedTable<FacebookPost> sentimentStore;

    @UseDataSet("userProfiles")
    private KeyValueTable userProfiles;

    @Path(SENTIMENT_ENDPOINT + "/{id}")
    @GET
    public void getUserSentiment(HttpServiceRequest request, HttpServiceResponder responder, @PathParam("id") String id) {
        byte[] encodedUserProfile = userProfiles.read(id);
        if (encodedUserProfile == null) {
            responder.sendString(HttpURLConnection.HTTP_NO_CONTENT,
                    String.format("No  user : %s", id), StandardCharsets.UTF_8);
            return;
        }
        UserProfile userProfile = GSON.fromJson(Bytes.toString(encodedUserProfile), UserProfile.class);
        String sentiment = "NEUTRAL";
        FacebookPost facebookPost = sentimentStore.read(userProfile.getFacebookId());
        if (facebookPost != null) {
            if (facebookPost.getSentimentScore() < -3) {
                sentiment = "VERY_UNHAPPY";
            } else if (facebookPost.getSentimentScore() < 0) {
                sentiment = "SOMEWHAT_UNHAPPY";
            } else if (facebookPost.getSentimentScore() > 0) {
                sentiment = "SOMEWHAT_HAPPY";
            } else if (facebookPost.getSentimentScore() < -3) {
                sentiment = "VERY_HAPPY";
            }
        }
        UserSentiment userSentiment = new UserSentiment(userProfile, facebookPost, null, sentiment);
        responder.sendJson(userSentiment);
    }

}
