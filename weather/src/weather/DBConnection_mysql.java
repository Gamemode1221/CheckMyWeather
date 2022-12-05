package weather;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection_mysql {
    private static final String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/weatherdb?useUnicode=true&serverTimezone=Asia/Seoul";

    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "1234";

    public static Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName(DB_DRIVER_CLASS);
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}
