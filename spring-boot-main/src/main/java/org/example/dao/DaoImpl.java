package org.example.dao;

import org.example.BankCustomer;
import org.springframework.stereotype.Component;

@Component(value="ComplexDao")
public class DaoImpl implements IDao{
    @Override
    public void createCustomer(BankCustomer bankCustomer) {
        System.out.println("In create dao of complex");
        DataHolder.bankCustomerConcurrentHashMap.put(bankCustomer.name(), bankCustomer);
    }

    @Override
    public BankCustomer getCustomerDetail(String bankCustomerName) {
        return DataHolder.bankCustomerConcurrentHashMap.get(bankCustomerName);
    }

    @Override
    public void removeCustomerDetail(String bankCustomerName) {
        DataHolder.bankCustomerConcurrentHashMap.remove(bankCustomerName);
    }

    @Override
    public void updateCustomerDetail(String bankCustomerName, BankCustomer bankCustomer) {
        DataHolder.bankCustomerConcurrentHashMap.replace(bankCustomerName,bankCustomer);
    }
}
