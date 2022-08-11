import aie.easyAPI.core.serialization.JsonSerialize;
import aie.easyAPI.excepation.SerializeException;
import aie.easyAPI.models.http.Header;
import aie.easyAPI.utils.StopWatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TestSerialize {
    @Test
    public void testJson() throws SerializeException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Header header = new Header();
        header.setName("hi");
        header.setValue("lolo");
        JsonSerialize s = new JsonSerialize();
        ArrayList<Header> a=new ArrayList<>();
        a.add(header);
        String json = s.DeSerialize(a);
        ArrayList<Header> h2 = s.serialize(json, Header.class);
        stopWatch.stop();
System.out.println(stopWatch.getElapsedTime(TimeUnit.MILLISECONDS));
        Assertions.assertEquals(header.name(), h2.get(0).name());
    }
}
