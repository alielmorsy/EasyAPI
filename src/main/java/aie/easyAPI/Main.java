package aie.easyAPI;


import aie.easyAPI.server.core.StandardServer;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.regex.Pattern;

import static aie.easyAPI.server.core.HeaderParser.ACCEPT_HEADER_PATTERN;
import static aie.easyAPI.server.core.HeaderParser.BASIC_HEADER_PATTERN;

public class Main {
    public static void main(String[] args) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap("gsdgsds sdfLMEOASD".getBytes());
        long start = System.nanoTime();
        String request = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println(System.nanoTime() - start);
        start = System.nanoTime();
      String  re1quest = new String(buffer.array());
        System.out.println(System.nanoTime() - start);
    }
}
