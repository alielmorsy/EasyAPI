package aie.easyAPI.interfaces;

import aie.easyAPI.context.Controller;
import aie.easyAPI.core.structure.Node;
import aie.easyAPI.excepation.RouteException;

import java.lang.reflect.Method;

/**
 * interface to route Mapping Tree contains all Routes specs to be easily find them see {@link  aie.easyAPI.context.impelements.ControllerMapper}
 */
public interface IRouteTree {
    /**
     * Add Controller To the tree see {@link Controller}
     *
     * @param uri   uri used in controller to map it
     * @param clazz the controller class
     * @return {@link  Node} class of the current controller data (may use in future works)
     */
    Node<String> addController(String uri, Class<? extends Controller> clazz);

    /**
     * In case of mapping uri of the controller as a route add the method of that route to controller node
     *
     * @param controllerNode Controller's Node
     * @param method
     * @return
     */
    void addMethodToStartupController(Node<String> controllerNode, Method method);

    /**
     * add sub URI to the control mapping
     *
     * @param controllerNode
     * @param uri
     * @param method
     * @return
     */
    Node<String> addSubURI(Node<String> controllerNode, String uri, Method method);

    /**
     * Remove specific route from Tree
     *
     * @param route route to be deleted
     */
    void remove(String route);

    /**
     * Search for a route to get node contains method required to run this route
     *
     * @param route route used sent to check it
     * @return node of the current route contains all required data
     * @throws RouteException throw if the route doesn't match the route pattern(URI/subURI)
     */
    Node<String> search(String route) throws RouteException;
}
