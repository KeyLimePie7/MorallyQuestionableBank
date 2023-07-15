package createLoginTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginTableScript {
	public static void main(String[] args) {
		String databaseURL = "jdbc:derby://localhost:1527/bankDB;create=true";
		try {
        	Connection con = DriverManager.getConnection(databaseURL);
        	Statement sta = con.createStatement();
//        	sta.execute("DROP TABLE loginTable") ;
        	
        	sta.execute("CREATE TABLE loginTable (uid varchar(255), pw varchar(255))");
        	sta.execute("INSERT INTO loginTable VALUES('teller','teller')");
        	sta.execute("INSERT INTO loginTable VALUES('exec','exec')");
        	System.out.println("Added both accounts into loginTable");
        	ResultSet rs = sta.executeQuery("SELECT * FROM loginTable");
        	while (rs.next()) {
        		String uid = rs.getString("uid");
        		String pw = rs.getString("pw");
        		System.out.println("UID: "+uid+" PW: "+pw);
        	}
        	rs.close();
        	sta.close();
        	con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage()) ;
		}
	}
}
