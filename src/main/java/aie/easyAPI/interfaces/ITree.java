package aie.easyAPI.interfaces;

import aie.easyAPI.models.ControllerRoutesMapping;

public interface ITree<T> {

    void add(ControllerRoutesMapping mapping);

    void remove(String route);

    ControllerRoutesMapping search(String route);
}
