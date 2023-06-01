package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.BankCustomer;
import org.example.dao.IDao;
import org.example.exception.BusinessException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankCustomersService {

    private final IDao iDao;

    private final CacheManager cacheManager;

    public void createCustomer(BankCustomer bankCustomer) {
        iDao.createCustomer(bankCustomer);
    }

    public BankCustomer getCustomerDetail(String bankCustomerName) {
        // First check if it is in cache
        BankCustomer bankCustomerFromCache = (BankCustomer) getFromCache(bankCustomerName);
        if (bankCustomerFromCache != null)
            return bankCustomerFromCache;

        BankCustomer bankCustomer=  iDao.getCustomerDetail(bankCustomerName);
        if (bankCustomer == null) {
            throw new BusinessException("Customer does not exist");
        } else {
            // Put in cache
            putInCache(bankCustomerName, bankCustomer);
            return bankCustomer;
        }
    }

    public void removeCustomer(String bankCustomerName) {
        iDao.removeCustomerDetail(bankCustomerName);
    }

    public void updateCustomer(String bankCustomerName, BankCustomer bankCustomerUpdated) {
        iDao.updateCustomerDetail(bankCustomerName, bankCustomerUpdated);
    }

    private Object getFromCache(String bankEmpName) {
        Cache cache = cacheManager.getCache("employee-cache");
        if (cache.get(bankEmpName) != null) {
            log.info("Emp exists in cache, empname = {} ",bankEmpName);
            return cache.get(bankEmpName).get();
        }
        else {
            log.info("Emp NOT exists in cache, empname = {} " ,bankEmpName);
            return null;
        }
    }

    private void putInCache(String bankEmpName, BankCustomer bankCustomer) {
        Cache cache = cacheManager.getCache("employee-cache");
        cache.put(bankEmpName,bankCustomer);
        log.info("Emp put in cache, empname = {} " ,bankEmpName);
    }

}
