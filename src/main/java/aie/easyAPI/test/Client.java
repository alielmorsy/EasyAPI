package aie.easyAPI.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Objects;

public class Client {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Socket socket = new Socket("127.0.0.1", 5555);
        var in = socket.getInputStream();
        var out = socket.getOutputStream();
        var map = new HashMap<String, Object>();
        map.put("request", "hello");
        var request = new SimpleRequest();
        request.message = "Hello Message";
        map.put("data", request);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mapper.writeValue(baos, map);
        out.write(ByteBuffer.allocate(4).putInt(baos.size()).array());
        out.write(baos.toByteArray());
        byte[] bytes = new byte[256];
        int read = in.read(bytes);
        System.out.println(new String(bytes, 0, read));
        map.put("request", "hello/world");
        baos = new ByteArrayOutputStream();
        new ObjectMapper().writeValue(baos, map);
        out.write(ByteBuffer.allocate(4).putInt(baos.size()).array());
        out.write(baos.toByteArray());
        read = in.read(bytes);
        System.out.println(new String(bytes, 0, read));
    }

    public static class SimpleRequest {
        @JsonProperty
        public String message;
    }
}
