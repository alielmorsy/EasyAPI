package aie.easyAPI.utils;

import aie.easyAPI.enums.HttpMethod;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Utils {

    public static ByteBuffer sliceBuffer(ByteBuffer buffer, int startIndex, int stopIndex) {
        if (startIndex == -1 || startIndex > buffer.limit() || stopIndex > buffer.limit() || startIndex > stopIndex) {
            throw new ArrayIndexOutOfBoundsException("Invalid Values for slice buffer");
        }
        byte[] bytes = new byte[stopIndex - startIndex];
        buffer.get(bytes);
        return ByteBuffer.wrap(bytes);
    }

    public static ByteBuffer concatBuffers(ByteBuffer... buffers) {
        final ByteBuffer combined = ByteBuffer.allocate(Arrays.stream(buffers).mapToInt(Buffer::remaining).sum());
        Arrays.stream(buffers).forEach(b -> combined.put(b.duplicate()));
        return combined;
    }

    public static HttpMethod getMethodByName(String method) {
        if (method.equalsIgnoreCase("get")) {
            return HttpMethod.GET;
        } else if (method.equalsIgnoreCase("post")) {
            return HttpMethod.POST;
        } else if (method.equalsIgnoreCase("head")) {
            return HttpMethod.HEAD;
        } else if (method.equalsIgnoreCase("put"))
            return HttpMethod.PUT;
        else if (method.equalsIgnoreCase("delete"))
            return HttpMethod.DELETE;
        return null;
    }
}
