package io.github.antonio.backnotfront.ratelimiter.utility;


import java.util.HashMap;
import java.util.Map;

public class ErrorBodyFactory {

    public static Map<String, Object> generateBody(Object error, Integer status){
        Map<String, Object> body = new HashMap<>();
        body.put("status", status);
        body.put("error", error);
        body.put("timestamp", System.currentTimeMillis());
        return body;
    }

}
