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
 * This class represents a purchase made by a customer. It is a very simple class and only contains
 * the name of the customer, the name of the product, and the purchase time.
 */
public class Purchase implements Writable {

    private String customer, product;
    private long purchaseTime;

    public Purchase() {
    }

    public Purchase(Purchase other) {
        this(other.getCustomer(), other.getProduct(),
                other.getPurchaseTime());
    }

    public Purchase(String customer, String product, long purchaseTime
    ) {
        this.customer = customer;
        this.product = product;
        this.purchaseTime = purchaseTime;
    }

    public String getCustomer() {
        return customer;
    }

    public String getProduct() {
        return product;
    }

    public long getPurchaseTime() {
        return purchaseTime;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        WritableUtils.writeString(out, customer);
        WritableUtils.writeString(out, product);
        WritableUtils.writeVLong(out, purchaseTime);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        customer = WritableUtils.readString(in);
        product = WritableUtils.readString(in);
        purchaseTime = WritableUtils.readVLong(in);
    }
}
