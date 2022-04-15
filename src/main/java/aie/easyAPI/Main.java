package aie.easyAPI;

import aie.easyAPI.context.impelements.ApplicationContext;
import aie.easyAPI.core.serialization.JsonSerialize;
import aie.easyAPI.models.Header;
import aie.easyAPI.server.intenral.InternalServer;
import aie.easyAPI.utils.StopWatch;
import com.mysql.cj.jdbc.Driver;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws SQLException {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ApplicationContext context = new ApplicationContext();
        context.setPort(5555);
        InternalServer internalServer = new InternalServer(context);
        internalServer.bind();
        stopWatch.stop();

    }
}
