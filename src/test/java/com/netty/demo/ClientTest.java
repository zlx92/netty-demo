package com.netty.demo;


import com.netty.demo.client.JacksonTransferClient;

import java.util.HashMap;
import java.util.Map;

public class ClientTest {
    public static void main(String[] args) {
        Map<String, Object> message = new HashMap<>();
        for(int i=0;i<100000;i++)
        {
            message.put("name"+i,"nsdfsssssssssssssssdsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
                    "ssssssdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
                    "sddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
                    "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssdf" +
                    "sdffffffffffffffffffffffffffffffffffffffsdffffffffffffffffff");
        }
        JacksonTransferClient<Map<String, Object>> client = new JacksonTransferClient<>("127.0.0.1", 8888,
                message);
        try {
            client.send();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("hello world");
        }
    }
}
