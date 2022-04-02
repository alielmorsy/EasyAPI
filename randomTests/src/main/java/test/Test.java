package test;

public class Test {
    public static void main(String[] args) throws Throwable {

        ClassFinder.findClasses(new ClassFinder.Visitor<String>() {
            @Override
            public void visit(String s) {
                System.out.println(s);

            }
        });

    }
}
