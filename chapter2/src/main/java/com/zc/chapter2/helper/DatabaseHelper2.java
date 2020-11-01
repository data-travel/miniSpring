package com.zc.chapter2.helper;

import com.zc.chapter2.util.PropsUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * 〈〉
 *
 * @author zc
 * @create 2020/11/1
 * @since 1.0.0
 * * DbUtils 提供的QueryRunner对象可以面向实体（Entity）进行查询。
 * 实际上，DBUtil首先执行SQL语句并返回一个ResultSet，随后通过反射去创建并初始化实体对象。
 * 由于我们使用的是List，因此可以使用BeanListHandler。
 */
public class DatabaseHelper2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper2.class);

    private static final String DRIVER ;
    private static final String URL ;
    private static final String USERNAME ;
    private static final String PASSOWRD ;

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

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

    /**
     * @return
     * @since 1.0.0
     * @Author zc
     * @Date 2020/11/1 16:38
     */
    public static <T> List<T> queryEntityList (Class<T> entityClass,Connection conn, String sql, Object... params) {
        List<T> entityList;
        try {
            entityList = QUERY_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass),params);
        } catch ( SQLException e){
            LOGGER.error("query entity list failure",e);
            throw new RuntimeException(e);
        }finally {
            closeConnection(conn);
        }
        return entityList;
    }


}
