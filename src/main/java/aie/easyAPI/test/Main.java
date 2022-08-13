package aie.easyAPI.test;


import aie.easyAPI.Application;

public class Main {
    public static void main(String[] args) throws Exception {
        Application.build(args)
                .setPort(5555)
                .searchForClasses(true)
                .start();
    }
}
