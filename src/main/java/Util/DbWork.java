package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DbWork{
    private static DbWork dbWork;
    private Connection connection;

    public static DbWork getInstance(){
        if (dbWork==null){
            dbWork = new DbWork();
        }
        return dbWork;
    }

    public void setConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()){
            String fullUrl="jdbc:";
            Properties p = getProperties();
            fullUrl+=p.getProperty("db.app")+"://";
            fullUrl+=p.getProperty("db.host")+":";
            fullUrl+=p.getProperty("db.port")+"/";
            fullUrl+=p.getProperty("db.name");
            String user = p.getProperty("db.username");
            String password = p.getProperty("db.password");
            this.connection = DriverManager.getConnection(fullUrl, user, password);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    private Properties getProperties(){
        Properties prop = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            prop.load(classLoader.getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Long generateId(String sequenceName){

        Long id = null;
        String sql = "select nextval( ? ) as id";

        try (PreparedStatement st = getConnection().prepareStatement(sql)){

            st.setString(1, sequenceName);

            ResultSet rs = st.executeQuery();

            if (rs.next()){
                id = rs.getLong("id");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}