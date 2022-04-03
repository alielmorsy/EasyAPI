package aie.easyAPI.context;

import aie.easyAPI.annotation.ControllerRoute;
import aie.easyAPI.models.DataPack;


public abstract class Controller {
    protected DataPack Request;
    protected DataPack Response;
}
