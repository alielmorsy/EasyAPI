package aie.easyAPI.core.serialization;

import aie.easyAPI.excepation.SerializeException;

import java.io.Serializable;

public interface ISerializable {
    <T extends Serializable> String DeSerialize(T object);

    <T extends Serializable> T serialize(String s, Class<?> tClass) throws SerializeException;
}
