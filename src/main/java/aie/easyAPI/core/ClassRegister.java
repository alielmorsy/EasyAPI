package aie.easyAPI.core;

import aie.easyAPI.context.Controller;
import aie.easyAPI.context.IMiddleware;
import aie.easyAPI.context.IService;
import aie.easyAPI.excepation.ControllerException;
import aie.easyAPI.excepation.ServiceException;
import aie.easyAPI.interfaces.IClassRegister;
import aie.easyAPI.interfaces.IContextWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class ClassRegister implements IClassRegister {
    private static final Logger logger = LoggerFactory.getLogger(ClassRegister.class);
    private static ClassRegister instance;

    private ExecutorService executors;

    public static IClassRegister getInstance() {
        if (instance == null) {
            instance = new ClassRegister();
        }
        return instance;
    }

    private final ClassesLoader classLoader;
    private IContextWrapper context;


    public ClassRegister() {
        classLoader = new ClassesLoader(Thread.currentThread().getContextClassLoader());
        //  executors = Executors.newFixedThreadPool(2);
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
                findClassesV2(file, file);
            }
        }
    }

    private void findClassesV2(File root, File file) {
        if (file.isDirectory()) {
            for (File child : Objects.requireNonNull(file.listFiles())) {
                findClassesV2(root, child);
                //  executors.execute(() -> findClassesV2(root, child));
            }
            return;
        }
        String className = createClassName(root, file);
        //|| className.contains("easyAPI")
        if (!(className.startsWith("jdk") || className.startsWith("java"))) {
            Class<?> clazz;
            try {
                clazz = classLoader.loadClass(className);
            } catch (ClassNotFoundException e) {
                try {
                    clazz = classLoader.loadClassFromBytes(createClassName(root, file), Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                } catch (IOException ex) {
                    logger.error("Failed to Load Class: ".concat(className), ex);
                    return;
                }
            }
            checkClass(clazz);
        }
    }

    private void checkClass(Class<?> clazz) {
        if (Controller.class.isAssignableFrom(clazz)) {
            try {
                context.addController((Class<? extends Controller>) clazz);
            } catch (ControllerException e) {
                e.printStackTrace();
            }
        } else if (IService.class.isAssignableFrom(clazz)) {
            try {
                context.registerService((Class<? extends IService>) clazz);
            } catch (ServiceException e) {
                logger.error("Failed To Register Service", e);
            }
        } else if (IMiddleware.class.isAssignableFrom(clazz)) {
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

    @Override
    public IClassRegister setContext(IContextWrapper context) {
        this.context = context;
        return this;
    }
}
