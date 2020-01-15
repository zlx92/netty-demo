package com.netty.demo;


import com.netty.demo.callback.IReceiveMsgCallBack;
import com.netty.demo.server.JacksonTransferServer;
import com.netty.demo.utils.JsonUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ServerTest {
    public static void main(String[] args) {
        JacksonTransferServer<Map<String, Object>> server = new JacksonTransferServer<>(8888, new HashMap<String, Object>(),
                new IReceiveMsgCallBack() {
                    @Override
                    public void receiveMsg(Object msg) {
                        try {
                            File file = new File("D:" + File.separator + "test.txt");
                            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
                            outputStream.write(JsonUtils.toJson(msg).getBytes());
                            outputStream.flush();
                            outputStream.close();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                });
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
