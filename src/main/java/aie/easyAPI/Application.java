package aie.easyAPI;

import aie.easyAPI.context.impelements.ApplicationContext;
import aie.easyAPI.core.ClassRegister;
import aie.easyAPI.core.structure.Node;
import aie.easyAPI.excepation.ServerException;
import aie.easyAPI.interfaces.IContextWrapper;
import aie.easyAPI.server.core.StandardServer;
import aie.easyAPI.utils.AssertChecks;
import aie.easyAPI.utils.ContextUtils;

/**
 * The Main Application Class It the Start Point of the application.
 * Usage:
 * <pre>
 *     {@code
 *      public static void main(String[] args){
 *          Application.build(args).start();
 *      }
 *     }
 * </pre>
 */
public class Application {
    /**
     * Main Context being used in the application
     */
    private ApplicationContextFactory context;
    /**
     * if set true it will search for all controllers and services in the project
     */
    private boolean searchForClasses = false;

    /**
     * @param args The Main program arguments
     * @return Return the created application
     */
    public static Application build(String[] args) {
        return new Application(args);
    }

    /**
     * Return The Main Application Context
     *
     * @return The main application context that currently running
     * @throws IllegalAccessException If The Application didn't create Yet
     */
    public IContextWrapper getApplicationContext() throws IllegalAccessException {
        return context;
    }

    /**
     * if searchForClasses set to true it will start searching over all classes to find controllers and services
     *
     * @return default Application instance
     */
    public Application searchForClasses(boolean searchForClasses) {
        this.searchForClasses = searchForClasses;
        return this;
    }

    /**
     * Set Server port
     *
     * @param port server port
     * @return default Application instance
     * @throws ServerException if its wrong port
     */
    public Application setPort(int port) throws ServerException {
        if (port > 65325 || port < 1024) {
            throw new ServerException("Port can be only from 1024 to 65325");
        }
        context.setPort(port);
        return this;
    }

    /**
     * to start server mapping and run Server on provided port
     *
     * @throws ServerException if failed to run server
     */
    public void start() throws ServerException {
        context.getDefaultStopWatch().start();
        if (searchForClasses) {
            var classRegister = ClassRegister.getInstance();
            classRegister.setContext(context);
            classRegister.findClasses();
        }
        runServer();

    }

    private Application(String[] args) {
        init(args);
    }


    private void init(String[] args) {
        context = new ApplicationContext();
        if (args.length != 0) {
            handleArgs(args);
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
                    break;
                }
                case "-s":
                    searchForClasses = true;
            }
        }
    }

    private void runServer() throws ServerException {
        StandardServer standardServer = new StandardServer(context, context.getPort());
        standardServer.start();
    }

}
