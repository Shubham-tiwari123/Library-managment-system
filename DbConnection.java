
package librarymanagment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DbConnection {
    protected final String dbClassName = "com.mysql.jdbc.Driver";
    protected final String CONNECTION ="jdbc:mysql://127.0.0.1/Library";
    protected Connection c = null;
    protected Statement st = null;
    protected ResultSet rs = null;
    
    protected void initializeDbConnection() throws ClassNotFoundException, SQLException {
        Class.forName(dbClassName);
        Properties p = new Properties();
        p.put("user", "root");
        p.put("password", "");
        c = DriverManager.getConnection(CONNECTION, p);
        st = c.createStatement();
    }
    
    protected void closeDbConnection(Connection connection) {
        if (connection != null) {
            try {
                c.close();
            } catch (SQLException e) {
               System.out.println("Connection can not be ade free: " + e);
            }
        }
    }
}
