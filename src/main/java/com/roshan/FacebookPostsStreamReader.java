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

import co.cask.cdap.api.annotation.ProcessInput;
import co.cask.cdap.api.common.Bytes;
import co.cask.cdap.api.flow.flowlet.AbstractFlowlet;
import co.cask.cdap.api.flow.flowlet.FlowletContext;
import co.cask.cdap.api.flow.flowlet.OutputEmitter;
import co.cask.cdap.api.flow.flowlet.StreamEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacebookPostsStreamReader extends AbstractFlowlet {

    private OutputEmitter<FacebookPost> out;

    private Afinn afinn;

    @Override
    public void initialize(FlowletContext context) throws Exception {
        super.initialize(context);
        afinn = new Afinn();
    }

    private static final Logger LOG = LoggerFactory.getLogger(FacebookPostsStreamReader.class);

    @ProcessInput
    public void process(StreamEvent event) {
        String body = Bytes.toString(event.getBody());
        int splitIndex = body.indexOf(":");
        if (splitIndex < 0) {
            LOG.error("Error parsing the post : {}", body);
            return;
        }
        String facebookId = body.substring(0, splitIndex);
        String postText = body.substring(splitIndex + 1);
        LOG.info("Parsed facebook post - Facebook Id : {}, Post : {}", facebookId, postText);
        int sentimentScore = getPostSentiment(postText);
        LOG.info("Sentiment Analysis - Facebook Id : {}, Post : {}, Sentiment : {}", facebookId, postText, sentimentScore);
        FacebookPost facebookPost = new FacebookPost(facebookId, postText, sentimentScore);
        out.emit(facebookPost);
    }

    public int getPostSentiment(String postText) {
        return afinn.getAffinSentimentRate(postText);
    }

}
