package com.lagou.servlet;

import com.lagou.bean.Result;
import com.lagou.service.TransferService;
import com.lagou.utils.IocContainer;
import com.lagou.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="transferServlet",urlPatterns = "/transferServlet")
public class TransferServlet extends HttpServlet {
//    private TransferService transferService = (TransferService) BeanFactory.getBean("transferService");

//    private ProxyFactory proxyFactory = (ProxyFactory) BeanFactory.getBean("proxyFactory");
//    private TransferService transferService = (TransferService) proxyFactory.getProxy(BeanFactory.getBean("transferService"));

    private TransferService transferService;

    @Override
    public void init() throws ServletException {
//        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
//        ProxyFactory proxyFactory = (ProxyFactory) webApplicationContext.getBean("proxyFactory");
//        transferService = (TransferService) proxyFactory.getCGlibProxy(webApplicationContext.getBean("transferService"));
        transferService = (TransferService) IocContainer.objectMap.get("transferServiceImpl");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
//        TransferService transferService = new TransferServiceImpl();
        Result result = new Result();
        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        String money = req.getParameter("money");


        try {
            transferService.transfer(fromCardNo, toCardNo, Integer.parseInt(money));
            result.setStatus("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus("201");
            result.setMessage(e.toString());
        }


        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(JsonUtils.object2Json(result));


    }
}
