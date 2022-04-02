package aie.easyAPI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String s="{asdw5asd}";
        Pattern p=Pattern.compile("\\{([A-Za-z_][A-Za-z1-9]*)}");
        Matcher m=p.matcher(s);
      System.out.println(m.find());
System.out.println(m.group(1));

    }
}
