package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.BankCustomer;
import org.example.dao.IDao;
import org.example.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankCustomersService {

    private final IDao iDao;

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
