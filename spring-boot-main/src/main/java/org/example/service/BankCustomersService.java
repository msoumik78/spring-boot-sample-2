package org.example.service;

import org.example.BankCustomer;
import org.example.dao.IDao;
import org.example.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BankCustomersService {

    @Autowired
    @Qualifier("SimpleDao")
    IDao iDao;

    public void createCustomer(BankCustomer bankCustomer) {
        iDao.createCustomer(bankCustomer);
    }

    public BankCustomer getCustomerDetail(String bankCustomerName) {
        BankCustomer bankCustomer=  iDao.getCustomerDetail(bankCustomerName);
        if (bankCustomer == null) {
            throw new BusinessException("Customer does not exist");
        } else {
            return bankCustomer;
        }
    }

    public void removeCustomer(String bankCustomerName) {
        iDao.removeCustomerDetail(bankCustomerName);
    }

    public void updateCustomer(String bankCustomerName, BankCustomer bankCustomerUpdated) {
        iDao.updateCustomerDetail(bankCustomerName, bankCustomerUpdated);
    }

}
