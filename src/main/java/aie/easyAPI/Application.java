package aie.easyAPI;

import aie.easyAPI.context.impelements.ApplicationContext;
import aie.easyAPI.core.ClassRegister;
import aie.easyAPI.core.structure.Node;
import aie.easyAPI.utils.AssertChecks;
import aie.easyAPI.utils.ContextUtils;

/**
 * The Main Application Class It the Start Point of the application
 * Usage:
 * <pre>
 *     {@code
 *      public static void main(String[] args){
 *          Application.run(args);
 *      }
 *     }
 * </pre>
 *
 *
 */
public class Application {
    private static ApplicationContextFactory context;

    /**
     * @param args The Main program arguments
     * @return Return the created application
     * @throws IllegalAccessException if the application already created before
     */
    public static Application run(String[] args) throws IllegalAccessException {
        if (context != null) {
            throw new IllegalAccessException("Application Already Initialized Before");
        }
        return new Application(args);
    }

    /**
     * Return The Main Application Context
     *
     * @return The main application context that currently running
     * @throws IllegalAccessException If The Application didn't create Yet
     */
    public ApplicationContextFactory getApplicationContext() throws IllegalAccessException {
        if (context == null) {
            throw new IllegalAccessException("Application Not Initialized yet");
        }
        return context;
    }


    private Application(String[] args) {
        init(args);
    }


    private void init(String[] args) {
        context = new ApplicationContext();
        if (args.length != 0) {
            handleArgs(args);
        }
        ClassRegister classRegister = new ClassRegister();
        ContextUtils.addContextToObject(classRegister, context);
        classRegister.findClasses();
        //   print();
    }

    /***
     * Print The Routes Tree
     */
    public void print() {
        print(context.getControllerTree().root, "");
    }

    private void print(Node<String> root, String space) {
        System.out.println(space + root.getValue());
        for (Node<String> node : root.getNodes()) {
            print(node, space + "\t");
        }
    }

    private void handleArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "-p": {
                    AssertChecks.assertIndex(i + 1, args.length, "Missing Port Number");
                    int port = Integer.parseInt(args[i + 1]);
                    context.setPort(port);
                }
                break;
                case "-t":
                    context.setOnSameThread(true);
            }
        }

    }

}
