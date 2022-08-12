package aie.easyAPI.context.impelements;

import aie.easyAPI.ApplicationContextFactory;
import aie.easyAPI.context.IService;
import aie.easyAPI.excepation.ServiceException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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

    @Override
    public <T extends IService> T getServiceInstance(Class<T> serviceClass) throws ServiceException {
        if (!_services.contains(serviceClass))
            throw new ServiceException("Service " + serviceClass.getSimpleName() + " Not Registered");

        return createObjectForService(serviceClass);
    }

    private <T extends IService> T createObjectForService(Class<T> serviceClass) throws ServiceException {
        Constructor<?> con = serviceClass.getConstructors()[0];
        Object[] parameters = new Object[con.getParameterTypes().length];
        int index = 0;
        for (Class<?> p : con.getParameterTypes()) {
            if (IService.class.isAssignableFrom(p)) {
                parameters[index++] = getServiceInstance((Class<T>) p);
            } else {
                throw new ServiceException("Service " + serviceClass.getSimpleName() + "Has Parameter: " + p.getSimpleName() + ", State Unknown");
            }
        }
        try {
            return (T) con.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ServiceException("Can't Create Service: " + serviceClass, e);

        }
    }
}
