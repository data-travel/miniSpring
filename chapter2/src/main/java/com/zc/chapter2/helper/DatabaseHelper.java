package com.zc.chapter2.helper;

import com.zc.chapter2.service.CustomerService;
import com.zc.chapter2.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 〈〉
 *
 * @author zc
 * @create 2020/11/1
 * @since 1.0.0
 */
public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

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

    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL,USERNAME,PASSOWRD);
        }catch (SQLException e){
            LOGGER.error("get connection failure",e);
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e){
                LOGGER.error("close coonection failure",e);
            }
        }
    }

}
