package aie.easyAPI.context;

import aie.easyAPI.annotation.ControllerRoute;
import aie.easyAPI.annotation.HttpPost;
import aie.easyAPI.annotation.HttpGet;
import aie.easyAPI.annotation.HttpHead;
import aie.easyAPI.annotation.HttpDelete;
import aie.easyAPI.models.DataPack;

/**
 * Abstract Class for creating a controllers for your API or website
 * it should have {@link ControllerRoute} annotation to define the name of the controller in Routes Tree
 * Each Method should be annotated {@link HttpGet}, {@link HttpPost}, {@link HttpHead}, or {@link HttpDelete}, and returns {@link HttpBaseRequest} class
 * Usage
 * <pre>
 *     {@code @ControllerRoute("index")
 *     public class IndexController extends Controller{
 *          @HttpGet("/")
 *          public HttpBaseResponse mainPage(){
 *              return HttpBaseResponse.okay("Hello Guys To My Website");
 *
 *          }
 *          @HttpGet("/{name}")
 *            public HttpBaseResponse mainPage(String name){
 *                return HttpBaseResponse.okay("Hello "+name);
 *
 *            }
 *
 *     }
 *     }
 * </pre>
 * Note that: each method represent a sub route of the controller and that sub route can contain a variables and pas it to the method
 * U can also see the Requested data from cookies and headers to the request stream itself by accessing {@link #Request}
 * and also can send cookies and send specific Headers by accessing the {@link #Response}
 */
public abstract class Controller {
    protected DataPack Request;
    protected DataPack Response;
}
