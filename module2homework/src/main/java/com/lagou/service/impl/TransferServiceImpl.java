package com.lagou.service.impl;

import com.lagou.annotation.Autowired;
import com.lagou.annotation.Service;
import com.lagou.annotation.Transactional;
import com.lagou.bean.Account;
import com.lagou.dao.AccountDao;
import com.lagou.service.TransferService;

@Service("transferService")
@Transactional
public class TransferServiceImpl implements TransferService {
    @Autowired
    private AccountDao accountDao;


    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
//        AccountDao accountDao = new AccountDaoImpl();
        Account fromAccount = accountDao.queryAccountByCardNo(fromCardNo);
        Account toAccount = accountDao.queryAccountByCardNo(toCardNo);


        if (fromAccount.getMoney() > money) {



//            ConnectionUtils.getConnection().setAutoCommit(false);
            int fromAccountMoney = fromAccount.getMoney() - money;
            fromAccount.setMoney(fromAccountMoney);
            accountDao.updateAccount(fromAccount);
            System.out.println(fromAccount.toString());
            int i = 3 / 0;
            int toAccountMoney = toAccount.getMoney() + money;
            toAccount.setMoney(toAccountMoney);
            accountDao.updateAccount(toAccount);
            System.out.println(toAccount.toString());
            System.out.println("----------------iocxml-------------------");
//            ConnectionUtils.getConnection().commit();


        }

    }
}
