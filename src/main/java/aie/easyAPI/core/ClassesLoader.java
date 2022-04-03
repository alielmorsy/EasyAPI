package aie.easyAPI.core;

public class ClassesLoader extends ClassLoader {


    public ClassesLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        return super.findClass(name);
    }

    public Class<?> loadClassFromBytes(String className, byte[] clazz) {
        return defineClass(className, clazz, 0, clazz.length);
    }

}
