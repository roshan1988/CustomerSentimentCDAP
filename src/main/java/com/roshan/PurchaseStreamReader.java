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
import co.cask.cdap.api.flow.flowlet.OutputEmitter;
import co.cask.cdap.api.flow.flowlet.StreamEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PurchaseStreamReader extends AbstractFlowlet {

  private OutputEmitter<Purchase> out;

  private static final Logger LOG = LoggerFactory.getLogger(PurchaseStreamReader.class);

  @ProcessInput
  public void process(StreamEvent event) {
    String body = Bytes.toString(event.getBody());
    // <name> bought<item>
    String[] tokens =  body.split(" ");
    if (tokens.length != 3) {
      LOG.error("The purchase stream event cant be parsed. Event : {}", body);
      return;
    }
    String customer = tokens[0];
    if (!"bought".equals(tokens[1])) {
      LOG.error("The purchase stream event cant be parsed. Event : {}", body);
      return;
    }
    String item = tokens[2];
    LOG.info("Purchase stream event - Customer : {}, Item : {}", customer, item);
    Purchase purchase = new Purchase(customer, item, System.currentTimeMillis());
    out.emit(purchase);
  }
}
