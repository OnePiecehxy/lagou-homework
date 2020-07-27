import com.lagou.service.UserServiceImpl;
import com.lagou.service.common.IUserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {
    private IUserService iUserService;

    public static void main(String[] args) throws ClassNotFoundException {
//        Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
//                new Class[]{Class.forName("com.lagou.service.common.IUserService")}, new InvocationHandler() {
//                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                        System.out.println("proxy");
//                        Object obj = Class.forName("com.lagou.service.common.IUserService").newInstance();
////                        Method declaredMethod = obj.getClass().getDeclaredMethod(methodName, parameterTypes);
////                        System.out.println(declaredMethod.getName());
////                        Object result = declaredMethod.invoke(obj, parameters);
//                        Object result = method.invoke(obj, args);
//                        System.out.println("result=" + result);
//
//                        return result;
//                    }
//                });
        UserServiceImpl service = new UserServiceImpl();
        Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("111111");
                return "2222";
            }
        });

    }
}
