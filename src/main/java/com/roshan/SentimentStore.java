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

import co.cask.cdap.api.Resources;
import co.cask.cdap.api.annotation.ProcessInput;
import co.cask.cdap.api.annotation.UseDataSet;
import co.cask.cdap.api.common.Bytes;
import co.cask.cdap.api.dataset.lib.ObjectMappedTable;
import co.cask.cdap.api.flow.flowlet.AbstractFlowlet;
import co.cask.cdap.api.flow.flowlet.FlowletConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Store the incoming Sentiment objects in the sentiment dataset.
 */
public class SentimentStore extends AbstractFlowlet {

    @UseDataSet("sentiment")
    private ObjectMappedTable<FacebookPost> store;

    private static final Logger LOG = LoggerFactory.getLogger(SentimentStore.class);

    @ProcessInput
    public void process(FacebookPost facebookPost) {
        LOG.info("Sentiment info: Facebook Id {}, Post {}, Sentiment {}",
                facebookPost.getFacebookId(), facebookPost.getPostText(), facebookPost.getSentimentScore());
        store.write(Bytes.toBytes(facebookPost.getFacebookId()), facebookPost);
    }

    @Override
    public void configure(FlowletConfigurer configurer) {
        super.configure(configurer);
        setDescription("Store the facebook post objects in the sentiments dataset");
        setResources(new Resources(1024));
    }

}
