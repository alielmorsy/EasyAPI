package aie.easyAPI.utils;

public class AssertChecks {
    public static void assertIndex(int index, int arrayLength,String message){
            if(index>=arrayLength){
                throw new IllegalStateException(message);
            }

    }
}
