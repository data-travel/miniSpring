package com.zc.chapter2;

import com.zc.chapter2.model.Customer;
import com.zc.chapter2.service.CustomerService;
import com.zc.chapter2.service.CustomerService2;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 〈〉
 *
 * @author zc
 * @create 2020/11/1
 * @since 1.0.0
 */
public class CustomerServiceTest {

    private final CustomerService customerService;
    private final CustomerService2 customerService2;

    public CustomerServiceTest(){
            customerService = new CustomerService();
            customerService2 = new CustomerService2();
    }

    @Test
    public void getCustomerListTest(){
        List<Customer> customerList = customerService.getCustomerList();
//        System.out.println(customerList.get(0).toString());
        Assert.assertEquals(2,customerList.size());
    }

     @Test
    public void getCustomerListTest2(){
        List<Customer> customerList = customerService2.getCustomerList();
        Assert.assertEquals(2,customerList.size());
    }
    @Test
    public void getCustomerListTest3(){
        List<Customer> customerList = customerService2.getCustomerList2();
        Assert.assertEquals(2,customerList.size());
    }
}
