
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
    protected Connection connect = null;
    protected Statement statement,statement1 = null;
    protected ResultSet resultSet,rs1 = null;
    
    protected void initializeDbConnection() throws ClassNotFoundException, SQLException {
        Class.forName(dbClassName);
        Properties property = new Properties();
        property.put("user", "root");
        property.put("password", "");
        connect = DriverManager.getConnection(CONNECTION, property);
        statement = connect.createStatement();
        statement1=connect.createStatement();
    }
    
    protected void closeDbConnection(Connection connection) {
        if (connection != null) {
            try {
                connect.close();
            } catch (SQLException e) {
               System.out.println("Connection can not be ade free: " + e);
            }
        }
    }
}
