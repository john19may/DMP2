//STEP 1. Import required packages
import java.sql.*;
import java.util.*;

public class FirstExample {
   
	
	private static final String PHT_TABLE = "create table PHT ( "
		      + "   ID INT PRIMARY KEY, lang1ID VARCHAR(5), lang1TEXT VARCHAR(500), lang2ID VARCHAR(5), lang2TEXT VARCHAR(500), dept VARCHAR(5) "
		      + ")";
	
	private static final String QHT_TABLE = "create table QHT ( "
		      + "   id INT PRIMARY KEY, langID VARCHAR(5), queryString VARCHAR(500), suggestionIDs VARCHAR(100) "
		      + ")";
	
	private static final String SHT_TABLE = "create table SHT ( "
		      + "   id INT PRIMARY KEY, langID VARCHAR(5), suggestionText VARCHAR(500), total INT, CWS INT, CSR INT, CJK INT "
		      + ")";
	
   public static void main(String[] args) {

	   Driver myDriver = new oracle.jdbc.driver.OracleDriver();
	   try {
		DriverManager.registerDriver( myDriver );
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	   String URL = "jdbc:oracle:thin:@192.168.184.91:1521:cp06";
	   Properties info = new Properties( );
	   info.put( "user", "companycom" );
	   info.put( "password", "companycom" );

	   Connection conn;
	   try {
		conn = DriverManager.getConnection(URL, info);
		Statement stmt=conn.createStatement();  
		
		//stmt.executeQuery(SHT_TABLE);
		
		
		
		 //ResultSet rs=stmt.executeQuery("SELECT owner, table_name FROM dba_tables");
		 
//		 while(rs.next())  
//			 System.out.println(rs.getString(2));
		
		
		
//		InsertIntoPHT(stmt,  "EN", "Give the list of names", "JP", "名前のリストを与える", "CWS");
		InsertIntoPHT(stmt,  "JP", "お名前は何ですか？","EN", "What is your name?", "CWS");
//		stmt.executeQuery(QHT_TABLE);
//		InsertIntoQHT(stmt, "EN", "name", "1$2$3$");
		
//		HashMap hh = new HashMap();
//		hh.put("TOTAL", 12);
//		hh.put("CWS", 10);
//		hh.put("CSR", 8);
//		hh.put("CJK", 6);
//		
//		InsertIntoSHT(stmt, "EN", "name", hh);
		 conn.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
   public static void InsertIntoPHT(Statement stmt, String lang1ID, String lang1TEXT, String lang2ID, String lang2TEXT, String DEPT ) throws SQLException
   {
	   ResultSet rs=stmt.executeQuery("select count(*) from PHT");
	   rs.next();
	   int id = Integer.valueOf(rs.getString(1));
	   id++;
	   stmt.executeQuery("INSERT INTO PHT (id, lang1ID, lang1TEXT, lang2ID, lang2TEXT, dept) VALUES ("+id+", '"+lang1ID+"', '"+lang1TEXT+"', '"+lang2ID+"', '"+lang2TEXT+"', '"+DEPT+"')");
   }
   
   public static void InsertIntoQHT(Statement stmt, String langID, String queryString, String suggestionIDsString) throws SQLException
   {
	   ResultSet rs=stmt.executeQuery("select count(*) from QHT");
	   rs.next();
	   int id = Integer.valueOf(rs.getString(1));
	   id++;
	   stmt.executeQuery("INSERT INTO QHT (id, langID, queryString, suggestionIDs) VALUES ("+id+", '"+langID+"', '"+queryString+"', '"+suggestionIDsString+"')");
   }
   
   public static void InsertIntoSHT(Statement stmt, String langID, String suggestionString, HashMap deptWiseCount) throws SQLException
   {
	   ResultSet rs=stmt.executeQuery("select count(*) from SHT");
	   rs.next();
	   int id = Integer.valueOf(rs.getString(1));
	   id++;
	   
	   
	   rs= stmt.executeQuery("select column_name from user_tab_columns where table_name = 'SHT'");
		
	   int temp = 1;
	   String str = "";
	   
		while(rs.next())  
			 {
				
				if(temp>3)
				{
					if(deptWiseCount.get(rs.getString(1))!=null)
					{
						str+=", "+deptWiseCount.get(rs.getString(1));
					}
					else
					{
						str+=", 0";
					}
					
				}
				temp++;
			 }
		
		String addEntry="INSERT INTO SHT (id, langID, suggestionText, total, CWS, CSR, CJK) VALUES ("+id+", '"+langID+"', '"+suggestionString+"'"+str+")";
		
		//System.out.println(addEntry);
	   
	   stmt.executeQuery(addEntry);
   }
}