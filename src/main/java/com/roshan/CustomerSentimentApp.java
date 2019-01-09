/*
 * Copyright © 2014-2016 Cask Data, Inc.
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

import co.cask.cdap.api.app.AbstractApplication;
import co.cask.cdap.api.data.schema.UnsupportedTypeException;
import co.cask.cdap.api.data.stream.Stream;
import co.cask.cdap.api.dataset.DatasetProperties;
import co.cask.cdap.api.dataset.lib.KeyValueTable;
import co.cask.cdap.api.dataset.lib.ObjectMappedTable;
import co.cask.cdap.api.dataset.lib.ObjectMappedTableProperties;

public class CustomerSentimentApp extends AbstractApplication {

    public static final String APP_NAME = "CustomerSentimentApp";

    @Override
    public void configure() {
        setName(APP_NAME);
        setDescription("Customer Sentiment application");

        // Ingest data into the Application via a Stream
        addStream(new Stream("purchaseStream"));

        // Ingest data into the Application via a Stream
        addStream(new Stream("facebookPostsStream"));

        // Process events in realtime using a Flow
        addFlow(new PurchaseFlow());

        // Process events in realtime using a Flow
        addFlow(new SentimentAnalysisFlow());

        // Store and retrieve user profile data using a Service
        addService(UserProfileServiceHandler.SERVICE_NAME, new UserProfileServiceHandler());

        // Store and retrieve user profile data using a Service
        addService(SentimentServiceHandler.SERVICE_NAME, new SentimentServiceHandler());

        // Store user profiles in a Dataset
        createDataset("userProfiles", KeyValueTable.class,
                DatasetProperties.builder().setDescription("Store user profiles").build());
        try {
            createDataset("purchases", ObjectMappedTable.class, ObjectMappedTableProperties.builder().setType(Purchase.class)
                    .setDescription("Store purchases").build());
            createDataset("sentiment", ObjectMappedTable.class, ObjectMappedTableProperties.builder().setType(FacebookPost.class)
                    .setDescription("Store Facebook Post Sentiments").build());
        } catch (UnsupportedTypeException e) {
            throw new RuntimeException(e);
        }
    }
}
