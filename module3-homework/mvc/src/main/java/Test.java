import com.lagou.mvcframeword.servlet.LGDispacherServlet;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.channels.ClosedSelectorException;

public class Test {

    public static void main(String[] args) {
//        String rootPath = Test.class.getClassLoader().getResource("").getPath();
//        System.out.println(rootPath);
//        try {
//            Class<?> aClass = Class.forName("com.lagou.demo.controller.DemoController");
//            Field[] declaredFields = aClass.getDeclaredFields();
//            for (Field declaredField : declaredFields) {
//                String simpleName = declaredField.getClass().getSimpleName();
//                System.out.println(simpleName);
//                String simpleName1 = declaredField.getType().getSimpleName();
//                System.out.println(simpleName1);
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        File file = new File("E:\\拉勾训练营\\第一阶段\\模块三\\SpringMVC\\代码\\mvc\\src\\main\\java\\com\\lagou\\edu");
//        String name = file.getName();
//        String path = file.getPath();
//        System.out.println(name);
//        System.out.println(path);
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "com.lagou".replaceAll(".", "/");
        System.out.println(path);
        System.out.println("com.lagou".replace(".", "/"));

    }
}
