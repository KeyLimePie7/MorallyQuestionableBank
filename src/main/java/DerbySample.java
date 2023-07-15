import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DerbySample {

	public static void main(String[] args) {
		String databaseURL = "jdbc:derby://localhost:1527/bankDB;create=true";
		
        try {
        	Connection conn = DriverManager.getConnection(databaseURL);
            deleteTables(conn);
            createDB(conn);
            conn.close();
 
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("XJ015")) {
                System.out.println("Derby shutdown normally");
            } else {
                ex.printStackTrace();
                ex.getMessage();
            }
        }
    }
 
    private static boolean doesTableExists(String tableName, Connection conn)
            throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet result = meta.getTables(null, null, tableName.toUpperCase(), null);
 
        return result.next();
    }
    
    private static void deleteTables(Connection conn) {
    	try {
    		Statement statement = conn.createStatement();
            String dropAccount = "DROP TABLE account";
            String dropCustomer = "DROP TABLE customer";
            String dropTransactions = "DROP TABLE transactions";
            
            if(doesTableExists("transactions", conn)) {
			    try {
			    	statement.execute(dropTransactions);
				    System.out.println("transactions Deleted");
			    } catch (Exception e) {
			    	System.out.println(e.getMessage());
				}
			} else {
				System.out.println("transactions dont exist");
			}
            
            if(doesTableExists("account", conn)) {
			    try {
			    	statement.execute(dropAccount);
				    System.out.println("account Deleted");
			    } catch (Exception e) {
			    	System.out.println(e.getMessage());
				}
			} else {
				System.out.println("account dont exist");
			}
            
            if(doesTableExists("customer", conn)) {
			    try {
			    	statement.execute(dropCustomer);
				    System.out.println("customer Deleted");
			    } catch (Exception e) {
			    	System.out.println(e.getMessage());
				}
			} else {
				System.out.println("customer dont exist");
			}
            
            statement.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
    
    public static void createDB(Connection conn) {
		try {
			Statement statement = conn.createStatement();

		    if (!doesTableExists("customer", conn)) {
		        String sql = "CREATE TABLE customer ("
		        		+ " ws_cust_id int primary key NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 100000000, INCREMENT BY 1),"
		        		+ " ws_ssn int,"
		        		+ " name varchar(255),"
		        		+ " ws_adrs varchar(255),"
		        		+ " age int"
		        		+ ")";
		        statement.execute(sql);
		        statement.executeUpdate("INSERT INTO customer(ws_ssn, name, ws_adrs, age) VALUES (123456789, 'Wei Hong','Hougang Avenue 5',25)"); 
		        statement.executeUpdate("INSERT INTO customer(ws_ssn, name, ws_adrs, age) VALUES (234567890, 'Bryan','Ang Mo Kio Avenue 7',25)");
		        statement.executeUpdate("INSERT INTO customer(ws_ssn, name, ws_adrs, age) VALUES (345678901, 'Oleta','Serangoon Central Avenue 2',31)"); 
		        System.out.println("Created table customer.");
		    } else {
				System.out.println("customer table already created");
			}
			
		    if (!doesTableExists("account", conn)) {
		    	String sql = "CREATE TABLE account ("
		    			+ " ws_acct_id int primary key GENERATED ALWAYS AS IDENTITY (START WITH 200000000, INCREMENT BY 1),"
		    			+ " ws_cust_id int,"
		    			+ " ws_acct_type varchar(255),"
		    			+ " ws_acct_balance int,"
		    			+ " ws_acct_crdate date,"
		    			+ " ws_acct_lasttrdate date"
		    			+ ")";
		        statement.execute(sql);
		        statement.executeUpdate("INSERT INTO account(ws_cust_id,ws_acct_type,ws_acct_balance,ws_acct_crdate,ws_acct_lasttrdate) VALUES (100000000,'Savings',29050,'2022-10-23','2022-10-25')");
                statement.executeUpdate("INSERT INTO account(ws_cust_id,ws_acct_type,ws_acct_balance,ws_acct_crdate,ws_acct_lasttrdate) VALUES (100000001,'Current',210,'2022-10-20','2022-10-24')");
                statement.executeUpdate("INSERT INTO account(ws_cust_id,ws_acct_type,ws_acct_balance,ws_acct_crdate,ws_acct_lasttrdate) VALUES (100000002,'DEGEN',29050203,'2022-10-21','2022-10-22')");
		        System.out.println("Created table account.");
		    } else {
				System.out.println("account table already created");
			}
		    
		    if (!doesTableExists("transactions", conn)) {
		        String sql = "CREATE TABLE transactions ("
		        		+ " ws_trxn_id int primary key GENERATED ALWAYS AS IDENTITY (START WITH 300000000, INCREMENT BY 1),"
		    			+ " ws_acct_id int,"
		    			+ " ws_trxn_amount int,"
		    			+ " ws_trxn_type varchar(255),"
		    			+ " ws_trxn_date DATE"
		    			+ ")";
		        statement.execute(sql);
		        statement.executeUpdate("INSERT INTO transactions(ws_acct_id, ws_trxn_amount, ws_trxn_type, ws_trxn_date) VALUES (200000000, 1000, 'Deposit', '2022-10-25')");
		        statement.executeUpdate("INSERT INTO transactions(ws_acct_id, ws_trxn_amount, ws_trxn_type, ws_trxn_date) VALUES (200000000, 500, 'Withdraw', '2022-10-25')");
		        statement.executeUpdate("INSERT INTO transactions(ws_acct_id, ws_trxn_amount, ws_trxn_type, ws_trxn_date) VALUES (200000000, 100, 'Deposit', '2022-10-25')");
		        System.out.println("Created table transactions.");
		    } else {
				System.out.println("transactions table already created");
			}
		    
		    statement.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		
    }
}
