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

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * This class represents a facebook post made by a customer.
 */
public class FacebookPost implements Writable {

    private String facebookId, postText;

    private int sentimentScore;

    public FacebookPost() {
    }

    public FacebookPost(FacebookPost other) {
        this(other.getFacebookId(), other.getPostText(),
                other.getSentimentScore());
    }

    public FacebookPost(String facebookId, String postText, int sentimentScore
    ) {
        this.facebookId = facebookId;
        this.postText = postText;
        this.sentimentScore = sentimentScore;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getPostText() {
        return postText;
    }

    public int getSentimentScore() {
        return sentimentScore;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        WritableUtils.writeString(out, facebookId);
        WritableUtils.writeString(out, postText);
        WritableUtils.writeVLong(out, sentimentScore);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        facebookId = WritableUtils.readString(in);
        postText = WritableUtils.readString(in);
        sentimentScore = WritableUtils.readVInt(in);
    }
}
