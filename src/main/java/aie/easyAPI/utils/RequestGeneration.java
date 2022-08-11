package aie.easyAPI.utils;

import aie.easyAPI.enums.ContentCoding;
import aie.easyAPI.enums.ContentType;
import aie.easyAPI.models.HttpRequest;
import aie.easyAPI.models.http.Header;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class RequestGeneration {
    public static final String BASIC_HEADER_PATTERN = "([A-Za-z1-9/-]+)(: |:)(.+)";
    public static final Pattern CONTENT_TYPE_HEADER_PATTERN = Pattern.compile("([A-Za-z\\-]+[/][A-Za-z\\-]+)(; charset=(.+))?");

    private StopWatch stopWatch;
    private String delimiter = "\r\n";
    private char[] CRLF = {'\r', '\n'};
    private boolean isHeadersDone = false;

    private HttpRequest request;

    public RequestGeneration() {
        stopWatch = new StopWatch();
        request = new HttpRequest();
        stopWatch.start();
    }

    public HttpRequest createRequest(ByteBuffer fullBody) throws IOException {
        readHeaders(fullBody);
        return request;
    }

    private boolean readHeaders(ByteBuffer buffer) {
        String[] headersString = fitHeaders(buffer);
        if (headersString == null) return false;

        String requestData = headersString[0];

        String[] data = requestData.split(" ");
        if (data.length != 3) return false;
        if (!generateRequestBasedData(data)) return false;

        List<Header> headers = new ArrayList<>();
        for (int i = 1; i < headersString.length; i++) {
            String s = headersString[i];
            if (!handleHeader(s, headers)) return false;
        }
        request.setHeaders(headers);
        return false;
    }

    private InputStream readBody() {

        return null;
    }

    @Deprecated
    private String[] readHeadersLines(ByteBuffer buffer) {
        StopWatch watch = new StopWatch();
        watch.start();
        boolean newLine = false;
        boolean foundStartOfDelimiter = false;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < buffer.limit(); i++) {
            char c = (char) buffer.get();
            if (foundStartOfDelimiter) {
                if (c == delimiter.charAt(1)) {
                    if (newLine) {
                        System.out.println("Headers Done");
                        isHeadersDone = true;

                        break;
                    } else {
                        newLine = true;
                        strings.add(baos.toString());
                        baos = new ByteArrayOutputStream();
                        foundStartOfDelimiter = false;
                        continue;
                    }

                } else {
                    foundStartOfDelimiter = false;

                }

            } else if (c == delimiter.charAt(0)) {

                foundStartOfDelimiter = true;
                continue;
            }

            newLine = isHeadersDone;
            baos.write(c);
        }

        stopWatch.stop();
        strings.forEach(System.out::println);
        System.out.println(stopWatch.getElapsedTime(TimeUnit.NANOSECONDS));
        System.out.println();
        return strings.toArray(new String[0]);
    }

    private String[] fitHeaders(ByteBuffer buffer) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        boolean newLine = false;
        int dIndex = 0;
        StringBuilder line = new StringBuilder();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < buffer.limit(); i++) {
            char c = (char) buffer.get();
            if (CRLF[dIndex] == c) {
                dIndex++;
                if (dIndex == CRLF.length) {
                    if (newLine) {
                        break;
                    }
                    newLine = true;
                    dIndex = 0;
                    strings.add(line.toString());
                    line = new StringBuilder();
                }


            } else if (dIndex != 0) {
                return null;
            } else {
                newLine = false;
                line.append(c);
            }
        }
        stopWatch.stop();
        strings.forEach(System.out::println);
        System.out.println(stopWatch.getElapsedTime(TimeUnit.NANOSECONDS));
        return strings.toArray(new String[0]);
    }

    /**
     * @param data
     * @return false if it's not HTTP/1.1
     */
    private boolean generateRequestBasedData(String[] data) {
        request.setMethod(Utils.getMethodByName(data[0]));

        String path = data[1];
        if (path.indexOf('?') != -1) {
            String[] full = path.split("\\?");
            request.setPath(full[0]);
            request.setQueryString(full[1]);
        } else
            request.setPath(path);

        return data[2].equals("HTTP/1.1");

    }

    private boolean handleHeader(String h, List<Header> headers) {
        if (Pattern.matches(BASIC_HEADER_PATTERN, h)) return false;
        String[] splits = h.split(": |:");
        String name = splits[0];
        String value = splits[1];
        if (name.equalsIgnoreCase("Content-Length")) {
            if (!value.matches("[0-9]+")) return false;
            request.setContentLength(Integer.parseInt(value));
            return true;
        } else if (name.equalsIgnoreCase("Content-Coding")) {
            if (value.equals("gzip")) {
                request.setContentCoding(ContentCoding.GZIP);
            } else if (value.equals("compress")) {
                request.setContentCoding(ContentCoding.COMPRESS);
            }
            return true;
        } else if (name.equalsIgnoreCase("Content-Type")) {

            return handleContentType(value);
        }

        Header header = new Header();
        header.setName(name);
        header.setValue(value);
        headers.add(header);
        return true;
    }

    private boolean handleContentType(String value) {
        var matcher = CONTENT_TYPE_HEADER_PATTERN.matcher(value);
        if (!matcher.matches()) return false;
        String group1 = matcher.group(1);
        switch (group1) {
            case "application/json":
                request.setContentType(ContentType.JSON);
                break;
            case "text/plain":
                request.setContentType(ContentType.TEXT);
                break;
            case "application/octet-stream":
                request.setContentType(ContentType.STREAM);
                break;
            case "application/xml":
                request.setContentType(ContentType.XML);
                break;
            default:
                return false;
        }
        return true;
    }
}
