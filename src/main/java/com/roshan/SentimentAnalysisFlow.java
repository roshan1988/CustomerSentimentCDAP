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

import co.cask.cdap.api.flow.AbstractFlow;

/**
 * This is a simple Flow that consumes facebook post events from a Stream and stores corresponding sentiment in datastore.
 */
public class SentimentAnalysisFlow extends AbstractFlow {

  @Override
  protected void configure() {
    setName("SentimentFlow");
    setDescription("Analyze facebook posts for sentiment and stores in dataset");
    addFlowlet("postStreamReader", new FacebookPostsStreamReader());
    addFlowlet("sentimentCollector", new SentimentStore());
    connectStream("facebookPostsStream", "postStreamReader");
    connect("postStreamReader", "sentimentCollector");
  }
}
