package aie.easyAPI.context;

import aie.easyAPI.annotation.APIRequest;
import aie.easyAPI.annotation.ControllerRoute;

/**
 * Abstract Class for creating a controllers for your API
 * it should have {@link ControllerRoute} annotation to define the name of the controller in Routes Tree
 * Each Method should be annotated {@link APIRequest}, and returns any object will be converted into json and send to client
 * Usage
 * <pre>
 *     {@code @ControllerRoute("index")
 *     public class IndexController extends Controller{
 *          @APIRequest("/")
 *          public Data mainURI(){
 *          ...
 *              return new Data();
 *          }
 *          @APIRequest("get");
 *          public Data get(){
 *          ...
 *              return new Data();
 *          }
 *     }
 *     }
 * </pre>
 * Note that: each method represent a sub route of the controller.
 */
public abstract class Controller {

}
