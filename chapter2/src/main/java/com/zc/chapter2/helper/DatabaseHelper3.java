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
 * 为了使connection对于开发人员完全透明（即隐藏掉创建和关闭连接）
 * 使用ThreadLocal存放本地线程变量
 * 每次获取Connection时，首先在ThreadLocal中寻找，
 * 若不存在，则创建新的Connection，并将其放入ThreadLocal；当使用Connection完毕后，需要移除。
 */
public class DatabaseHelper3 {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper3.class);

    private static final String DRIVER ;
    private static final String URL ;
    private static final String USERNAME ;
    private static final String PASSOWRD ;

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();

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
        Connection conn = CONNECTION_HOLDER.get();
       if (conn == null) {
           try {
               conn = DriverManager.getConnection(URL,USERNAME,PASSOWRD);
           }catch (SQLException e){
               LOGGER.error("get connection failure",e);
           }
       }
        return conn;
    }

    public static void closeConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn != null){
            try {
                conn.close();
            }catch (SQLException e){
                LOGGER.error("close connection failure", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * @return
     * @since 1.0.0
     * @Author zc
     * @Date 2020/11/1 16:38
     */
    public static <T> List<T> queryEntityList (Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try {
            entityList = QUERY_RUNNER.query(getConnection(),sql,new BeanListHandler<T>(entityClass),params);
        } catch ( SQLException e){
            LOGGER.error("query entity list failure",e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return entityList;
    }


}
