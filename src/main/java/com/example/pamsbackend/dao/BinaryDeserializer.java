package com.example.pamsbackend.dao;

//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import org.bson.types.Binary;
//import java.io.IOException;
//
//public class BinaryDeserializer extends JsonDeserializer<Binary> {
//
//    @Override
//    public Binary deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
//        ObjectNode node = jsonParser.readValueAsTree();
//        if (node.has("data")) {
//            return new Binary(node.get("data").binaryValue());
//        } else {
//            return null;
//        }
//    }
//}


//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonToken;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import org.bson.types.Binary;
//
//import java.io.IOException;
//
//public class BinaryDeserializer extends JsonDeserializer<Binary> {
//
//    @Override
//    public Binary deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//        JsonToken currentToken = jsonParser.getCurrentToken();
//        if (currentToken == JsonToken.VALUE_NULL) {
//            return null;
//        } else if (currentToken == JsonToken.VALUE_EMBEDDED_OBJECT && jsonParser.getEmbeddedObject() instanceof byte[]) {
//            byte[] data = (byte[]) jsonParser.getEmbeddedObject();
//            return new Binary(data);
//        } else {
//            throw deserializationContext.wrongTokenException(jsonParser, Binary.class, JsonToken.VALUE_EMBEDDED_OBJECT, "Expected embedded byte[]");
//        }
//    }
//}


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.bson.types.Binary;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class BinaryDeserializer extends JsonDeserializer<Binary> {

    @Override
    public Binary deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.readValueAsTree();
        System.out.println("node: " + node);
        if (node != null && node.has("$binary")) {
            String base64Value = node.get("$binary").get("base64").asText();
            System.out.println("base64: "+base64Value);
            byte[] decodedBytes = Base64.getDecoder().decode(base64Value);
            return new Binary(decodedBytes);
        } else {
            return null;
        }
    }
}
