package aie.easyAPI.context.impelements;

import aie.easyAPI.ApplicationContextFactory;
import jdk.jfr.ContentType;

public class ApplicationContext extends ApplicationContextFactory {
    public ApplicationContext() {
        super();
    }

    @Override
    public void setPort(int port) {
        if (port < 1024) {
            throw new IllegalArgumentException("Port Number Must be >=1024");
        }
        super.setPort(port);
    }
}
