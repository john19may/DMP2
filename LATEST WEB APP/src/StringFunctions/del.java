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
	Connection conn;
	
	//Logic parameters
	HashMap hm = new HashMap();
	String[] stringsToProcess ;
	String[] deptOfStringToProcess;
	obj top[] = new obj[5];
	String deptUserWantsToSee[];
	String queryLanguageCode, query, suggestionLanguageCode;
	
	
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
		   try {
			conn = DriverManager.getConnection(URL, info);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	public obj[] printCommonStrings(String sortByDept)
	{

		int n = stringsToProcess.length;
		for(int i = 0;i<n;i++)
		{
			addIntoHashmap(hm, stringsToProcess[i], deptOfStringToProcess[i]);
		}
		
		ArrayList<obj> a = new ArrayList<obj>();
		
		
		int j=0;
		    Iterator it = hm.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        obj oo = new obj();
		        oo.str = (String)pairs.getKey();
		        int no[] = (int[])pairs.getValue();
		        if(no[0]>1)
		        {
		        	oo.no = no;
		        	a.add(oo);
		        }
		        
		        j++;
		    }
		    
		    //save memory
		    hm = null;
		    
		    	if(a.size()<=5)
		    	{
		    		top = a.toArray(new obj[a.size()]); 
		    		Arrays.sort(top);
		    	}
		    	
		    	//get top 5 results from the array in best complexity
		    	else
		    	{
		    		for(int i=0;i<5;i++)
		    			top[i] = a.get(i);
		    		
		    		Arrays.sort(top);
		    		
		    		for(int i=0;i<4;i++)
		    		{
		    			for(j=i+1;j<5;j++)
		    			{
		    				if(top[i].compareTo(top[j])==0&&top[i].str.length()<top[j].str.length())
		    				{
		    					swap(i,j);
		    				}
		    			}
		    		}
		    		
		    		for(int i=5;i<a.size();i++)
		    		{
		    			
		    			if((a.get(i).compareTo(top[0])==0&&a.get(i).compareStringLength(top[0])<0)||a.get(i).compareTo(top[0])<0)
		    			{
		    				top[4] = top[3];
		    				top[3] = top[2];
		    				top[2] = top[1];
		    				top[1] = top[0];
		    				top[0] = a.get(i);
		    			}
		    			else if((a.get(i).compareTo(top[1])==0&&a.get(i).compareStringLength(top[1])<0)||a.get(i).compareTo(top[1])<0)
		    			{
		    				top[4] = top[3];
		    				top[3] = top[2];
		    				top[2] = top[1];
		    				top[1] = a.get(i);
		    				
		    			}
		    			else if((a.get(i).compareTo(top[2])==0&&a.get(i).compareStringLength(top[2])<0)||a.get(i).compareTo(top[2])<0)
		    			{
		    				top[4] = top[3];
		    				top[3] = top[2];
		    				top[2] = a.get(i);
		    			}
		    			else if((a.get(i).compareTo(top[3])==0&&a.get(i).compareStringLength(top[3])<0)||a.get(i).compareTo(top[3])<0)
		    			{
		    				top[4] = top[3];
		    				top[3] = a.get(i);
		    			}
		    			else if((a.get(i).compareTo(top[4])==0&&a.get(i).compareStringLength(top[4])<0)||a.get(i).compareTo(top[4])<0)
		    			{
		    				top[4] = a.get(i);
		    			}
		    		}
		    	}
		    
		   
		    for(int i=0;i<5;i++)
		    {
		    	System.out.print(top[i].str+" ");
		    	System.out.print(top[i].no[0]+" ");
		    	for(int l = 0; l<deptUserWantsToSee.length;l++)
		    	{
		    			System.out.print(deptUserWantsToSee[l]+" "+top[i].no[DepartmentIndex.getInstance().dept.get(deptUserWantsToSee[l])]+" ");
		    	}
		    	System.out.println();
		    }
		    
		    try {
				InsertIntoSHT(conn.createStatement());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return top;
		    
	}
	
	public static void addIntoHashmap(HashMap hm, String str, String Dept)
	{
		
		int window=1;
		
		HashMap notFromSameString = new HashMap();
		
		DepartmentIndex di = DepartmentIndex.getInstance();
		
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
						int d[] = new int[di.dept.size()];
						Arrays.fill( d, (int) 0 );
						d[0] = 1;
						d[di.dept.get(Dept)] = 1;
						hm.put(sub, d);
					}
					else
					{
						int d[];
						
						d = (int[]) hm.get(sub);
						
						d[0]++;
						d[di.dept.get(Dept)]++;
						hm.put(sub, d);
					}
					notFromSameString.put(sub, 1);
				}
			}
							
		}
		
	 }
	
	//method to retrieve data from previous history table database
	public void getInputTableInformation(String queryLanguageCode, String query, String suggestionLanguageCode, ArrayList<String> departments)
	{
		this.queryLanguageCode = queryLanguageCode;
		this.query = query;
		this.suggestionLanguageCode = suggestionLanguageCode;
		
		Statement stmt2;
		ResultSet result = null;
		try {
			stmt2 = conn.createStatement();
			 result = stmt2.executeQuery("SELECT TOP 1 FROM QHT WHERE QUERYSTRING='"+query.toLowerCase()+"'");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
		}
		
		
		try {
			if(result.next())
			{
				System.out.println("true");
			}	
			
			else
			{
				try {
					
					deptUserWantsToSee = departments.toArray(new String[departments.size()]);
					
					Statement stmt=conn.createStatement();
					
					String GET_HISTORY_DATA = "";
					
					query = query.toLowerCase();
					
					 
					GET_HISTORY_DATA = "(SELECT LANG2TEXT,DEPT FROM (SELECT LANG1TEXT,LANG2TEXT,DEPT FROM PHT WHERE LANG1ID='"+queryLanguageCode+"' AND LANG2ID='"+suggestionLanguageCode+"') WHERE LANG1TEXT LIKE '%"+query+"%') UNION ALL "
							+"SELECT LANG1TEXT,DEPT FROM (SELECT LANG1TEXT,LANG2TEXT,DEPT FROM PHT WHERE LANG1ID='"+suggestionLanguageCode+"' AND LANG2ID='"+queryLanguageCode+"') WHERE LANG2TEXT LIKE '%"+query+"%'";
					ResultSet rs = stmt.executeQuery(GET_HISTORY_DATA);
					
					
					ArrayList<String> arr_str = new ArrayList<String>();
					ArrayList<String> arr_dept = new ArrayList<String>();
					
					while(rs.next())
					{
						arr_str.add(rs.getString(1));
						arr_dept.add(rs.getString(2));
					}
					
					stringsToProcess = arr_str.toArray(new String[arr_str.size()]);
					deptOfStringToProcess = arr_dept.toArray(new String[arr_dept.size()]);
					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	}

	
	//method to add into Query history table
	public void InsertIntoQHT(Statement stmt, String suggestionIDsString) throws SQLException
	   {
		   
		   ResultSet rs=stmt.executeQuery("select count(*) from QHT");
		   rs.next();
		   int id = Integer.valueOf(rs.getString(1));
		   id++;
		   
		   
		   stmt.executeQuery("INSERT INTO QHT (id, langID, queryString, suggestionIDs) VALUES ("+id+", '"+queryLanguageCode+"', '"+query+"', '"+suggestionIDsString+"')");
		   stmt.close();
	   }
	   
		//method to add into Suggestions history table
	   public void InsertIntoSHT(Statement stmt) throws SQLException
	   {
		   ResultSet rs=stmt.executeQuery("select count(*) from SHT");
		   rs.next();
		   int id = Integer.valueOf(rs.getString(1));
		   id++;
		   
		   int dd = id;
		   String suggestionIDsString = "";
		   for(int i=0;i<top.length;i++)
		   {
			   suggestionIDsString += dd+"$";
			   dd++;
		   }
		   
		   for(int i=0;i<top.length;i++)
		   {
			   String str = "";
			   
			   for(int t=0;t<top[i].no.length;t++)
				   str+=", "+top[i].no[t];
				
				String addEntry="INSERT INTO SHT (id, langID, suggestionText, total, CWS, CSR, CJK) VALUES ("+id+", '"+suggestionLanguageCode+"', '"+top[i].str+"'"+str+")";
			   
			   stmt.executeQuery(addEntry);
			   id++;
		   }
		   
		   InsertIntoQHT(conn.createStatement(),suggestionIDsString);
		   stmt.close();
	   }
	   
	   public void swap(int i,int j)
	   {
		   obj temp22 = top[i];
		   top[i] = top[j];
		   top[j] = temp22;
	   }
}
