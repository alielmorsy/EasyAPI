package aie.easyAPI.interfaces;

import aie.easyAPI.excepation.ServerException;

/**
 * General Interface for specific jobs that require handling
 * @author Ali Elmorsy
 */
public interface IHandler {
    /**
     * Main Class of interface should be called for each handler
     */
    void handle() throws ServerException;
}
