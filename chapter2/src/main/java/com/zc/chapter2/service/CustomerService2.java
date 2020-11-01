package com.zc.chapter2.service;

import com.zc.chapter2.helper.DatabaseHelper;
import com.zc.chapter2.helper.DatabaseHelper2;
import com.zc.chapter2.helper.DatabaseHelper3;
import com.zc.chapter2.model.Customer;
import com.zc.chapter2.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 〈〉
 *
 * @author zc
 * @create 2020/10/31
 * @since 1.0.0
 *  使用DatabaseHelper简化版service，简化掉 1、读取配置文件；2、获取数据库连接，关闭连接。
 *  数据库连接部分得到重用。
 *  service类更加专注业务实现。
 */
public class CustomerService2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService2.class);

    public List<Customer> getCustomerList() {
        Connection conn = null;
        try{
            List<Customer> customerList = new ArrayList<>();
            String sql = "select * from customer";
            conn = DatabaseHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Customer cus = new Customer();
                cus.setId(rs.getLong("id"));
                cus.setName(rs.getString("name"));
                cus.setContact(rs.getString("contact"));
                cus.setTelephone(rs.getString("telephone"));
                cus.setEmail(rs.getString("email"));
                cus.setRemark(rs.getString("remark"));
                customerList.add(cus);
            }
            return customerList;

        }catch (SQLException e){
            LOGGER.error("execute sql failure",e);
        }finally {
            DatabaseHelper.closeConnection(conn);
        }
        return null;
    }

    public List<Customer> getCustomerList2(){
        Connection conn = DatabaseHelper2.getConnection();
        String sql = "select * from customer";
        try {
            return DatabaseHelper2.queryEntityList(Customer.class, conn,sql,null);
        } finally {
            DatabaseHelper2.closeConnection(conn);
        }
    }

    public List<Customer> getCustomerList3(){
        String sql = "select * from customer";
        try {
            return DatabaseHelper3.queryEntityList(Customer.class, sql,null);
        } finally {
            DatabaseHelper3.closeConnection();
        }
    }

    public Customer getCustomer(long id) {
        return null;
    }

}
