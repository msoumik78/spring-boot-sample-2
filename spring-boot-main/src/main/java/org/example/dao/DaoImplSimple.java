package org.example.dao;

import org.example.BankCustomer;
import org.springframework.stereotype.Component;

@Component(value="SimpleDao")
public class DaoImplSimple implements IDao {
    @Override
    public void createCustomer(BankCustomer bankCustomer) {
        System.out.println("In create dao of simple");
        DataHolder.bankCustomerSimpleHashMap.put(bankCustomer.name(), bankCustomer);
    }

    @Override
    public BankCustomer getCustomerDetail(String bankCustomerName) {
        return DataHolder.bankCustomerSimpleHashMap.get(bankCustomerName);
    }

    @Override
    public void removeCustomerDetail(String bankCustomerName) {
        DataHolder.bankCustomerSimpleHashMap.remove(bankCustomerName);
    }

    @Override
    public void updateCustomerDetail(String bankCustomerName, BankCustomer bankCustomer) {
        DataHolder.bankCustomerSimpleHashMap.replace(bankCustomerName,bankCustomer);
    }
}
