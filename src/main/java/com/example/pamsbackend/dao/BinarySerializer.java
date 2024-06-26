/*******************************************************************************
 * Copyright (c) 2024. Tobias Gregorsson.
 * Github: Gregtech87
 ******************************************************************************/

package com.example.pamsbackend.dao;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.Binary;

import java.io.IOException;

public class BinarySerializer extends JsonSerializer<Binary> {

    // Converts Binary from database storage to image Base64 value.
    @Override
    public void serialize(Binary binary, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (binary != null) {
            jsonGenerator.writeBinary(binary.getData());
        } else {
            jsonGenerator.writeNull();
        }
    }
}
