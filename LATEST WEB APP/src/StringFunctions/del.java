package StringFunctions;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;


public class del {

	
//  We have three tables in our database.
//	PHT = Previous all History Table
//	QHT = Query History Table
//	SHT = Suggestion History Table	
//
//	private static final String PHT_TABLE = "create table PHT ( "
//		      + "   id INT PRIMARY KEY, lang1ID VARCHAR(5), lang1TEXT VARCHAR(500), lang2ID VARCHAR(5), lang2TEXT VARCHAR(500), dept VARCHAR(5) "
//		      + ")";
//	
//	private static final String QHT_TABLE = "create table QHT ( "
//		      + "   id INT PRIMARY KEY, langID VARCHAR(5), queryString VARCHAR(500), suggestionIDs VARCHAR(100) "
//		      + ")";
//	
//	private static final String SHT_TABLE = "create table SHT ( "
//		      + "   id INT PRIMARY KEY, langID VARCHAR(5), suggestionText VARCHAR(500), total INT, cws INT, csr INT, cjk INT "
//		      + ")";
	
	
	//DATABASE connection parameters
	Properties info;
	String URL;
	
	//Logic parameters
	HashMap hm = new HashMap();
	String[] stringsToProcess;
	
	//constructor
	public del() {
		
		//initializing database parameters
		Driver myDriver = new oracle.jdbc.driver.OracleDriver();
		try {
			DriverManager.registerDriver( myDriver );
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		   URL = "jdbc:oracle:thin:@192.168.184.91:1521:cp06";
		   info = new Properties( );
		   info.put( "user", "companycom" );
		   info.put( "password", "companycom" );
	}
	
	
	
	public obj[] printCommonStrings()
	{

		int n = stringsToProcess.length;
		for(int i = 0;i<n;i++)
		{
			addIntoHashmap(hm, stringsToProcess[i]);
		}
		
		obj[] a = new obj[hm.size()];
		for(int i=0;i<hm.size();i++)
		{
			a[i] = new obj();
		}
		
		int j=0;
		    Iterator it = hm.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        a[j].str = (String)pairs.getKey();
		        if((int)pairs.getValue()>1)
		        a[j].n = ((int)pairs.getValue());
		        else
		        	a[j].n = 0;
		        j++;
		    }
		    
		    Arrays.sort(a);
		    
		    return a;
		    
	}
	
	public static void addIntoHashmap(HashMap hm, String str)
	{
		
		int window=1;
		
		HashMap notFromSameString = new HashMap();
		
		for(window = 1; window<=str.length(); window++ )
		for(int i = 0; i<= str.length() - window; i++ )
		{
			String sub = str.substring(i,i+window);
			
			if(sub.length()>1)
			{
				if(notFromSameString.get(sub)==null)
				{
					if(hm.get(sub)==null)
					{
						hm.put(sub, 1);
					}
					else
					{
						hm.put(sub, ((int)hm.get(sub))+1);
					}
					notFromSameString.put(sub, 1);
				}
			}
			
				
		}
		
	 }
	
	public void getInputTableInformation(String queryLanguageCode, String query, String suggestionLanguageCode, ArrayList<String> departments)
	{
		Connection conn = null;
		   try {
			conn = DriverManager.getConnection(URL, info);
			Statement stmt=conn.createStatement();
			Statement stmt2=conn.createStatement();  
			
			String GET_HISTORY_DATA = "";
			
			query = query.toLowerCase();
			
			 
			GET_HISTORY_DATA = "(SELECT LANG2TEXT FROM (SELECT LANG1TEXT,LANG2TEXT FROM PHT WHERE LANG1ID='"+queryLanguageCode+"' AND LANG2ID='"+suggestionLanguageCode+"') WHERE LANG1TEXT LIKE '%"+query+"%') UNION ALL "
					+"SELECT LANG1TEXT FROM (SELECT LANG1TEXT,LANG2TEXT FROM PHT WHERE LANG1ID='"+suggestionLanguageCode+"' AND LANG2ID='"+queryLanguageCode+"') WHERE LANG2TEXT LIKE '%"+query+"%'";
			ResultSet rs = stmt.executeQuery(GET_HISTORY_DATA);
			
			
			ArrayList<String> arr = new ArrayList<String>();
			
			while(rs.next())
			{
				arr.add(rs.getString(1));
			}
			
			stringsToProcess = arr.toArray(new String[arr.size()]);
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
	}

	
	//method to add into Query history table
	public static void InsertIntoQHT(Statement stmt, String langID, String queryString, String suggestionIDsString) throws SQLException
	   {
		   ResultSet rs=stmt.executeQuery("select count(*) from QHT");
		   rs.next();
		   int id = Integer.valueOf(rs.getString(1));
		   id++;
		   stmt.executeQuery("INSERT INTO QHT (id, langID, queryString, suggestionIDs) VALUES ("+id+", '"+langID+"', '"+queryString+"', '"+suggestionIDsString+"')");
	   }
	   
		//method to add into Suggestions history table
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
