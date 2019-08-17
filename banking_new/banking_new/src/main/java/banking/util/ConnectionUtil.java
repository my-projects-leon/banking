package banking.util;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static ConnectionUtil connectionUtil = new ConnectionUtil();

    private ConnectionUtil() {
        super();
    }

    public static ConnectionUtil getConnectionUtil() {
        return connectionUtil;
    }

    public Connection getConnection() {
        try {
            Properties prop = new Properties();
            prop.load(new FileReader("src/main/resources/database.properties"));
            return DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
