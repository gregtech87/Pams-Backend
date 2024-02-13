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
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.bson.types.Binary;

import java.io.IOException;
import java.util.Base64;

public class BinaryDeserializer extends JsonDeserializer<Binary> {

    @Override
    public Binary deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();
        if (node != null) {
            System.out.println("node1: " + node);
            System.out.println("node1: " + node.toString().length());
        }


//        if (node != null && node.has("$binary")) {
        //noinspection DataFlowIssue
        if (node.toString().length() >20  && node.has("$binary")) {
            System.out.println("node2: " + node);
            String base64Value = node.get("$binary").get("base64").asText();
            byte[] decodedBytes = Base64.getDecoder().decode(base64Value);
            return new Binary(decodedBytes);
        } else {
//            return null;
            byte[] decodedBytes = Base64.getDecoder().decode("noImages");
            return new Binary(decodedBytes);
        }
    }

//
//    @Override
//    public Picture deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//        JsonNode node = p.getCodec().readTree(p);
//
//        if (node != null) {
//            System.out.println("node1: " + node);
//        }
//        if (node != null && node.has("$binary")) {
//            String base64Value = node.get("$binary").get("base64").asText();
//            byte[] decodedBytes = Base64.getDecoder().decode(base64Value);
//
//            Picture picture = new Picture(decodedBytes);
//            System.out.println("Pic1: "+picture);
////            picture.setId(node.get("id").asText());
//            picture.setName(node.get("name").asText());
//            picture.setTypeData(node.get("type").asText());
//            picture.setSize(node.get("size").asInt());
//            picture.setLastModified(node.get("lastModified").asLong());
//            picture.setLastModifiedDate(node.get("lastModifiedDate").asText());
//
//            Binary binary = new Binary(decodedBytes);
//            picture.setBinary(binary);
//            System.out.println("Pic2: "+picture);
//
//            return picture;
//        } else {
//            return null;
//        }
//    }
}
