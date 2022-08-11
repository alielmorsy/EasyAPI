package aie.easyAPI.server.core;

import aie.easyAPI.interfaces.Parser;
import aie.easyAPI.models.http.Header;
import aie.easyAPI.models.http.UserAgent;

import java.nio.charset.Charset;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;


public class HeaderParser implements Parser<String[], List<Header>> {

    private static final ZoneId gmt = ZoneId.systemDefault();
    private static final DateTimeFormatter[] DATE_PARSERS = {
            DateTimeFormatter.RFC_1123_DATE_TIME,
            DateTimeFormatter.ofPattern("EEEE, dd-MMM-yy HH:mm:ss zzz", Locale.ROOT),
            DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy", Locale.ROOT).withZone(gmt)
    };

    /*
     * Basic Pattern to match headers and extract data
     */
    public static final Pattern BASIC_HEADER_PATTERN = Pattern.compile("([A-Za-z1-9/-]+)(: |:)(.+)");
    /*
    Pattern to match etag header
     */
    private static final Pattern ETAG_HEADER_PATTERN = Pattern.compile("\\*|\\s*((W/)?(\"[^\"]*\"))\\s*,?");

    public static final Pattern ACCEPT_HEADER_PATTERN = Pattern.compile("(\\*|[A-Za-z/\\-]+/\\*|[A-Za-z/\\-]+)(; charset=(.+))?");

    private static final Header BASIC_HEADER = new Header();


    private UserAgent userAgent;

    @Override
    public List<Header> parse(String[] headers) {
        var list = new ArrayList<Header>();
        for (String s : headers) {

            var header = parseHeader(s);
            if (header == BASIC_HEADER || header == null) continue;
            list.add(header);
        }
        return list;
    }

    private Header parseHeader(String headerString) {
        var match = BASIC_HEADER_PATTERN.matcher(headerString);
        if (!match.matches()) return null;
        var headerName = match.group(1);
        var headerValue = match.group(3);
        var header = new Header();
        header.setName(headerName);
        if (headerName.equalsIgnoreCase("Content-Coding")) {
            if (!headerValue.equals("gzip") && !headerValue.equals("compress")) {
                return null;
            }
        } else if (headerName.equalsIgnoreCase("Content-Length")) {
            if (!headerValue.matches("[0-9]+")) {
                return null;
            }

        } else if (headerName.equalsIgnoreCase("User-Agent")) {
            userAgent = new UserAgent();
            return BASIC_HEADER;
        } else if (headerName.equalsIgnoreCase("Accept") || headerName.equals("Content-Type")) {

            var matcher = ACCEPT_HEADER_PATTERN.matcher(headerValue);
            if (!matcher.matches()) return null;
            headerValue = matcher.group(1);
            try {
                header.setCharset(Charset.forName(matcher.group(3)));
            } catch (Exception e) {
                return null;
            }
        }
        header.setValue(headerValue);
        return header;
    }

    public UserAgent userAgent() {
        return userAgent;
    }
}
