import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
 


public class ConnectionFactory {
         
    public Connection getConnection(){
        Connection conn = null;
         
        try {
             
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("jdbc driver �ε� ����");
            String url = "jdbc:oracle:thin:@112.175.145.76:1522:CADENZA";
              
            String user = "bxifuser";
            String password = "pwd!!bxif";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("����Ŭ ���� ����");
            
        } catch (SQLException e) {
			System.out.println(e.toString());
		}catch (Exception e) {
        	System.out.println("����Ŭ ���� ����");
        }
         
        return conn;
    }
     
    public void close(Connection conn, PreparedStatement pstmt ){
        if (conn != null){
             
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
         
         
}       