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

public class GetProcessedData {
	
	//DATABASE connection parameters
		Properties info;
		String URL;
		Connection conn;
		
		//logical parameters
		HashMap hm = new HashMap();
		String[] stringsToProcess ;
		String[] deptOfStringToProcess;
		
		String queryLanguageCode, query, suggestionLanguageCode;
		
		public GetProcessedData() {
			
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
	
	//method to retrieve data from previous history table database
		public void setInputInformation(String queryLanguageCode, String query, String suggestionLanguageCode)
		{
			this.queryLanguageCode = queryLanguageCode;
			this.query = query;
			this.suggestionLanguageCode = suggestionLanguageCode;
			
					try {
						
						Statement stmt=conn.createStatement();
						
						String GET_HISTORY_DATA = "";
						
						 
						GET_HISTORY_DATA = "SELECT LANG2TEXT,DEPT FROM (SELECT LANG1TEXT,LANG2TEXT,DEPT FROM PHT WHERE LANG1ID='"+queryLanguageCode+"' AND LANG2ID='"+suggestionLanguageCode+"') WHERE LANG1TEXT LIKE '%"+query.trim().toLowerCase()+"%' UNION ALL "
								+"SELECT LANG1TEXT,DEPT FROM (SELECT LANG1TEXT,LANG2TEXT,DEPT FROM PHT WHERE LANG1ID='"+suggestionLanguageCode+"' AND LANG2ID='"+queryLanguageCode+"') WHERE LANG2TEXT LIKE '%"+query.trim().toLowerCase()+"%'";
						
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
						
						conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				
		}
		
		public ArrayList<obj> getProcessedObjArray()
		{
			int n = stringsToProcess.length;
			for(int i = 0;i<n;i++)
			{
				if(i%3==0)
				addIntoHashmap(hm, stringsToProcess[i], "CWS");
				if(i%3==1)
					addIntoHashmap(hm, stringsToProcess[i], "CSR");
				if(i%3==2)
					addIntoHashmap(hm, stringsToProcess[i], "CJK");
			}
			
			ArrayList<obj> a = new ArrayList<obj>();
			
			
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
			        
			        
			    }
			    
			    //save memory
			    hm = null;
			    
			    return a;
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
}
