package com.example.pamsbackend.dao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.bson.types.Binary;

import java.io.IOException;
import java.util.Base64;

public class BinaryDeserializer extends JsonDeserializer<Binary> {

    // Converts Base64 value from image to Binary. For database storage.
    @Override
    public Binary deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();

        if (node.toString().length() > 40 && node.has("$binary")) {
            System.out.println("node2: " + node.toString().substring(0,100));
            String base64Value = node.get("$binary").get("base64").asText();
            byte[] decodedBytes = Base64.getDecoder().decode(base64Value);
            return new Binary(decodedBytes);
        } else {
            byte[] decodedBytes = Base64.getDecoder().decode("noImages");
            return new Binary(decodedBytes);
        }
    }
}
