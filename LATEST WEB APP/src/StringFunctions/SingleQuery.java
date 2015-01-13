package StringFunctions;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class SingleQuery {

	
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
	
	obj top[] = new obj[5];
	String queryLanguageCode, query, suggestionLanguageCode;
	
	
	//constructor
	public SingleQuery() {
		
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
	
	
	
	public obj[] getTop5Results()
	{
				
		Statement stmt2;
		ResultSet result = null;
		try {
			stmt2 = conn.createStatement();
			result = stmt2.executeQuery("SELECT * FROM (SELECT * FROM QHT WHERE QUERYSTRING='"+query.toLowerCase()+"' AND LANG2ID='"+suggestionLanguageCode+"') where rownum <= 1");
			if(result.next())
			{
				System.out.println("true");
				String sugg = result.getString(5);
				
				String arrr[] = sugg.split("\\$");
				
				if(arrr.length==0)
				{
					top = new obj[0];
				}
				else
				{
					
					int temm[] = new int[arrr.length];
					for(int i=0;i<arrr.length;i++)
					{
						System.out.println(arrr[i]);
						temm[i] = Integer.parseInt(arrr[i]);
					}
					Arrays.sort(temm);
					String qq = "SELECT * FROM SHT WHERE id>="+temm[0]+" AND id<="+temm[temm.length-1];
					ResultSet uu = stmt2.executeQuery(qq);
					
					
					obj yu[] = new obj[temm.length];
					int k2=0;
					while(uu.next())
					{
							System.out.println(uu.getString(3));
							yu[k2].str = uu.getString(3);
							for(int ki=0;ki<DepartmentIndex.getInstance().dept.size();ki++)
							{
								yu[k2].no[ki] = uu.getInt(ki+4);
							}
							k2++;
					}
					top = yu;
					
				}
				
			}
			else
			{
				GetProcessedData gpd = new GetProcessedData();
				gpd.setInputInformation(queryLanguageCode, query, suggestionLanguageCode);
				
				
				ArrayList<obj> a = gpd.getProcessedObjArray();
				
				//save memory
				gpd = null;
				
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
		    			for(int j=i+1;j<5;j++)
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
		    	
		    	try {
					InsertIntoSHT();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
		}
			
		    
		    return top;
		    
	}
	
	
	
	public void setInputInformation(String queryLanguageCode, String query, String suggestionLanguageCode)
	{
		this.queryLanguageCode = queryLanguageCode;
		this.query = query;
		this.suggestionLanguageCode = suggestionLanguageCode;
		   
	}

	
	//method to add into Query history table
	public void InsertIntoQHT(Statement stmt, String suggestionIDsString) throws SQLException
	   {
		   
		   ResultSet rs=stmt.executeQuery("select count(*) from QHT");
		   rs.next();
		   int id = Integer.valueOf(rs.getString(1));
		   id++;
		   
		   
		   stmt.executeQuery("INSERT INTO QHT (id, lang1ID, queryString, lang2ID, suggestionIDs) VALUES ("+id+", '"+queryLanguageCode+"', '"+query.toLowerCase()+"', '"+ suggestionLanguageCode +"', '"+suggestionIDsString+"')");
		   stmt.close();
	   }
	   
		//method to add into Suggestions history table
	   public void InsertIntoSHT() throws SQLException
	   {
		   Statement st = conn.createStatement();
		   ResultSet rs=st.executeQuery("select count(*) from SHT");
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
			   
			   st.executeQuery(addEntry);
			   id++;
		   }
		   
		   InsertIntoQHT(conn.createStatement(),suggestionIDsString);
		   st.close();
	   }
	   
	   public void swap(int i,int j)
	   {
		   obj temp22 = top[i];
		   top[i] = top[j];
		   top[j] = temp22;
	   }
}
