package org.example.dao;

import org.example.BankCustomer;

public interface IDao {

    void createCustomer(BankCustomer bankCustomer);

    BankCustomer getCustomerDetail(String bankCustomerName);

    void removeCustomerDetail(String bankCustomerName);

    void updateCustomerDetail(String bankCustomerName, BankCustomer bankCustomer);


}
