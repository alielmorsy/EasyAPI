package aie.easyAPI.core;

import aie.easyAPI.context.Controller;
import aie.easyAPI.context.IMiddleware;
import aie.easyAPI.context.IService;
import aie.easyAPI.excepation.ControllerException;
import aie.easyAPI.excepation.ServiceException;
import aie.easyAPI.interfaces.IClassRegister;
import aie.easyAPI.interfaces.IContextWrapper;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassRegister implements IClassRegister {

    private static ClassRegister instance;

    public static ClassRegister getInstance() {
        if (instance == null) {
            instance = new ClassRegister();
        }
        return instance;
    }

    private ClassesLoader classLoader;
    public IContextWrapper context;

    public ClassRegister setContext(IContextWrapper context) {
        this.context = context;
        return this;
    }

    public ClassRegister() {
        classLoader = new ClassesLoader(Thread.currentThread().getContextClassLoader());
    }

    @Override
    public void findClasses() {
        String classpath = System.getProperty("java.class.path");

        String[] paths = classpath.split(System.getProperty("path.separator"));
        for (String path : paths) {
            File file = new File(path);
            if (file.exists()) {
                if (!file.isDirectory() && !file.getName().endsWith(".class"))
                    continue;

                try {

                    findClasses(file, file, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void handleClass(Class<?> clazz) {

    }

    private void findClasses(File root, File file, boolean includeJars) throws Exception {
        if (file.isDirectory()) {
            for (File child : Objects.requireNonNull(file.listFiles())) {
                findClasses(root, child, includeJars);
            }
        } else {
            if (file.getName().toLowerCase().endsWith(".jar") && includeJars) {
                JarFile jar = null;
                try {
                    jar = new JarFile(file);
                } catch (Exception ignored) {
                }
                if (jar != null) {

                    URL[] urls = {new URL("jar:file:" + file.getAbsolutePath() + "!/")};
                    URLClassLoader cl = URLClassLoader.newInstance(urls);
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        int extIndex = name.lastIndexOf(".class");
                        if (extIndex > 0) {
                            String className = name.substring(0, extIndex).replace("/", ".");
                            Class<?> clazz = cl.loadClass(className);
                            if (clazz != null) {
                                checkClass(clazz);
                            }
                        }
                    }
                }
            } else  {
                String className = createClassName(root, file);
                if (!className.startsWith("java") || !className.startsWith("jdk")) {
                    Class<?> clazz = null;
                    try {
                        clazz = classLoader.loadClass(className);
                    } catch (ClassNotFoundException e) {
                        try {
                            clazz = classLoader.loadClassFromBytes(createClassName(root, file), Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (clazz != null) {

                        checkClass(clazz);
                    }
                }

            }
        }
    }

    private void checkClass(Class<?> clazz) {
        if (Controller.class.equals(clazz.getSuperclass())) {
            try {
                context.addController((Class<? extends Controller>) clazz);
            } catch (ControllerException e) {
                e.printStackTrace();
            }
        } else if (clazz.getInterfaces().length > 0 && IService.class.equals(clazz.getInterfaces()[0])) {
            try {
                context.registerService((Class<? extends IService>) clazz);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        } else if (clazz.getInterfaces().length > 0 && IMiddleware.class.equals(clazz.getInterfaces()[0])) {
            context.registerMiddleware((Class<? extends IMiddleware>) clazz);
        }
    }

    private String createClassName(File root, File file) {
        StringBuilder sb = new StringBuilder();
        String fileName = file.getName();
        sb.append(fileName, 0, fileName.lastIndexOf(".class"));
        file = file.getParentFile();
        while (file != null && !file.equals(root)) {
            sb.insert(0, '.').insert(0, file.getName());
            file = file.getParentFile();
        }
        return sb.toString();
    }
}
