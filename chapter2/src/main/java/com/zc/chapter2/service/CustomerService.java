package com.zc.chapter2.service;

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
 */
public class CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private static final String DRIVER ;
    private static final String URL ;
    private static final String USERNAME ;
    private static final String PASSOWRD ;

    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSOWRD = conf.getProperty("jdbc.password");
        try {
            Class.forName(DRIVER);
        }catch ( ClassNotFoundException e){
            LOGGER.error("can not load jdbc driver!",e);
        }
    }
    public List<Customer> getCustomerList() {
        Connection conn = null;
        try{
            List<Customer> customerList = new ArrayList<>();
            String sql = "select * from customer";
            conn = DriverManager.getConnection(URL,USERNAME,PASSOWRD);
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
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e){
                    LOGGER.error("close connection failure",e);
                }
            }
        }
        return null;
    }

    public Customer getCustomer(long id) {
        return null;
    }

}
