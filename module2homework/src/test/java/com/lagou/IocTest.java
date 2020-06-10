package com.lagou;

import com.lagou.config.ApplicationConfigXmlBuilder;
import com.lagou.service.TransferService;
import com.lagou.utils.GetAllJavaClass;
import com.lagou.utils.Resources;
import org.dom4j.DocumentException;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class IocTest {
    @Test
    public void test1() throws ClassNotFoundException, InstantiationException, DocumentException, IllegalAccessException, SQLException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

//        MyCount myAccount = (MyCount) applicationContext.getBean("myAccount");
//        myAccount.account();
//
//        Object connectionUtils = applicationContext.getBean("connectionUtils");
//        Object connectionUtils2 = applicationContext.getBean("connectionUtils");
//        System.out.println(connectionUtils);
//        System.out.println(connectionUtils2);
//        AccountDao accountDao = (AccountDao) applicationContext.getBean("accountDao");
//        System.out.println(accountDao);
//        accountDao.queryAccountByCardNo("");
//        Result result = (Result) applicationContext.getBean("result");
//        result.setMessage("hello");
//        System.out.println(result);
        Object companyBean = applicationContext.getBean("&companyBean");
        System.out.println(companyBean);


    }

    @Test
    public void test2() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        TransferService transferService = (TransferService) applicationContext.getBean("transferService");
        transferService.transfer("6029621011000", "6029621011001", 100);


    }

    @Test
    public void test3() throws DocumentException {
        InputStream resourceAsStream = Resources.getResourceAsStream("applicationContext.xml");
        String basePackage = ApplicationConfigXmlBuilder.getBasePackage(resourceAsStream);
        System.out.println(basePackage);
        String path = IocTest.class.getResource("/").getPath();
        System.out.println(path);

    }
    @Test
    public void test4() throws Exception {
        GetAllJavaClass getAllJavaClass = new GetAllJavaClass();
        List<String> allClassName = getAllJavaClass.getAllClassName();
        for (String s : allClassName) {
            System.out.println(s);
        }
//        System.out.println(getAllJavaClass.getPath());
//        String s = GetAllJavaClass.class.getClassLoader().getResource("").toString();
//        System.out.println(s);
//        IocContainer iocContainer = new IocContainer();
//        iocContainer.getIocContainer();


    }
}
