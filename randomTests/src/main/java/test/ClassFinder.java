package test;

import java.io.File;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class ClassFinder {
    public interface Visitor<T> {
        /**
         * @return {@code true} if the algorithm should visit more results,
         * {@code false} if it should terminate now.
         */
        public void visit(T t);
    }

    public static void findClasses(Visitor<String> visitor) {
        String classpath = System.getProperty("java.class.path");
        String[] paths = classpath.split(System.getProperty("path.separator"));
        System.out.println(classpath);

        for (String path : paths) {
            File file = new File(path);
            if (file.exists()) {
                findClasses(file, file, false, visitor);
            }
        }
    }

    private static boolean findClasses(File root, File file, boolean includeJars, Visitor<String> visitor) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                if (!findClasses(root, child, includeJars, visitor)) {
                    return false;
                }
            }
        } else {
            if (file.getName().toLowerCase().endsWith(".jar") && includeJars) {
                JarFile jar = null;
                try {
                    jar = new JarFile(file);
                } catch (Exception ex) {

                }
                if (jar != null) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        int extIndex = name.lastIndexOf(".class");
                        if (extIndex > 0) {
                            visitor.visit(name.substring(0, extIndex).replace("/", "."));
                        }
                    }
                }
            } else if (file.getName().toLowerCase().endsWith(".class")) {
                visitor.visit(createClassName(root, file));

            }
        }

        return true;
    }

    private static String createClassName(File root, File file) {
        StringBuilder sb = new StringBuilder();
        String fileName = file.getName();
        sb.append(fileName.substring(0, fileName.lastIndexOf(".class")));
        file = file.getParentFile();
        while (file != null && !file.equals(root)) {
            sb.insert(0, '.').insert(0, file.getName());
            file = file.getParentFile();
        }
        return sb.toString();
    }
}