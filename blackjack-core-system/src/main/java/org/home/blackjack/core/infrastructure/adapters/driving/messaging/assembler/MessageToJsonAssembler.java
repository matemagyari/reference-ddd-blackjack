package org.home.blackjack.core.infrastructure.adapters.driving.messaging.assembler;

import javax.inject.Named;

import org.home.blackjack.messaging.common.Message;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Part of the ACL between application and the client, responsible for
 * transforming DTOs to JSON.
 * 
 */
@Named
public class MessageToJsonAssembler {

    public String convert(Message message) {
        JsonObject jsonObject = new Gson().toJsonTree(message).getAsJsonObject();
        jsonObject.addProperty("type", message.getClass().getSimpleName());
        return jsonObject.toString();
    }
}
