package com.cognizant.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Flux;

public class Mapper {

    public static String mapToJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String mapStreamToJsonString(Flux object) {
        try {
            return new ObjectMapper().writeValueAsString(object);

        }catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
