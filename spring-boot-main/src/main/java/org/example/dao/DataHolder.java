package org.example.dao;

import org.example.BankCustomer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataHolder {

    public static Map<String, BankCustomer> bankCustomerConcurrentHashMap = new ConcurrentHashMap<>();
    public static Map<String, BankCustomer> bankCustomerSimpleHashMap = new HashMap<>();

}
