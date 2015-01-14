package StringFunctions;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class MegaHIT {

	
	
	int thread_pos = 0;
	
	ArrayList<MegaHIT.t> allString = new ArrayList<MegaHIT.t>();
	
	ArrayList<MegaHIT.t> selected = new ArrayList<MegaHIT.t>();

	static int threshold = 500;
	
	 String queryLanguageCode;
	String query;
	 String suggestionLanguageCode;
	
	MyThread m[] = new MyThread[10];
	
	ArrayList<MegaHIT.t> returnList ;
	
	SingleQuery d = new SingleQuery();
	
	public class t implements Comparable<t>{
		
		public String s;
		public String t;
		public int i,j;
		public t(String s,int n1,int n2) {
			i=n1;j=n2;
			this.s = s;
		}
		@Override
		public int compareTo(t o) {
			return i-o.i;
		}
		
		
	}
	
	
	
	
	
	public MegaHIT(String queryLanguageCode1, String query, String suggestionLanguageCode1, ArrayList<MegaHIT.t> arrL) {
		
		returnList = arrL;
		queryLanguageCode = queryLanguageCode1;
		this.query = query.toLowerCase();
		suggestionLanguageCode = suggestionLanguageCode1;
		
	}
	
	
	
	public class MyThread extends Thread{

		int index ;
		//DATABASE connection parameters
		Properties info;
		String URL;
		Connection conn;
		
		public MyThread(int i) {
			
			index = i;
		}
		
	    public void run(){
	    	
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
			   
	    	try {
	    		t temp = getString();
		    	while(temp!=null)
		    	{
		    		String no = getStringSuggestion(temp.s);
		    		if(!no.equals("NOTFOUND"))
		    		{
		    			//System.out.println(temp.s + " "+no);
		    			temp.t = no; 
		    			addString(temp);
		    		}
		    		temp = getString();
		    	}
		    	
		    	conn.close();
		    	allThreadsComplete();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    }
	    
	    
	    
	    public String getStringSuggestion(String str)
		{
			if(str.length()<2)
				return "NOTFOUND";
			
			
			

			obj a[] = d.getTop5Results(conn,queryLanguageCode, str, suggestionLanguageCode);
			
			
			
			obj max;
			if(a.length>0)
				max = a[0];
			else
				return "NOTFOUND";
			
			if(max.no[0]>=threshold)
			{
				return max.str;
			}
			else
				return "NOTFOUND";
		}
	}
	
	
	
	public synchronized MegaHIT.t getString(){
    	if(thread_pos>=allString.size())
    	{
    		return null;
    	}
    	else
    	{
    		t temp = allString.get(thread_pos);
    		thread_pos++;
    		return temp;
    	}
    }

    public synchronized void addString(MegaHIT.t temp)
    {
    	selected.add(temp);
    }
    
    
    public synchronized void allThreadsComplete()
    {
    	completed++;
    	if(completed==10)
    	{
    		t sa[] = selected.toArray(new t[selected.size()]);
        	
        	Arrays.sort(sa);
        	
        	for(int i=0;i<sa.length;i++)
        	{
        		whereToGO(sa, i);
        	}
        	
        	for(int i=0;i<final_ar.size();i++)
        	{
        		System.out.println(final_ar.get(i).s+" "+final_ar.get(i).t);
        	}
        	flag_finished=1;
        	returnList = final_ar;
        	System.out.println("done");
    	}
    }
    
    
    
    
    
    public void whereToGO(t[] sa, int pi)
    {
    	temp_ar.add(sa[pi]);
    	cur_len+=sa[pi].s.length();
    	if(cur_len>final_len)
    	{
    		final_ar = (ArrayList<t>) temp_ar.clone();
			final_len = cur_len;
    		
    	
    	}
    	else if(cur_len==final_len)
    	{
    		if(temp_ar.size()<final_ar.size())
    		{
    			final_ar = (ArrayList<t>) temp_ar.clone();
    			final_len = cur_len;
    		}
    	}
    	for(int h=pi+1;h<sa.length-1;h++)
    	{
    		if(sa[h].i>sa[pi].j)
    		{
    			whereToGO(sa, h);
    		}
    	}
    	temp_ar.remove(temp_ar.size()-1);
    	cur_len-=sa[pi].s.length();
    }
    
    int completed=0;
    public int flag_finished = 0;
    public ArrayList<t> final_ar = new ArrayList<MegaHIT.t>();
    int final_len = 0;
    ArrayList<t> temp_ar = new ArrayList<MegaHIT.t>();
    int cur_len = 0;
    
    public ArrayList<MegaHIT.t> getResult()
    {
    	for(int window = query.length()/3; window<=query.length(); window++ )
			for(int i = 0; i<= query.length() - window; i++ )
			{
				String sub = query.substring(i,i+window);
				allString.add(new t(sub, i, i+window-1));
			}
		
		//initializing database parameters
		Driver myDriver = new oracle.jdbc.driver.OracleDriver();
		try {
			DriverManager.registerDriver( myDriver );
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		for(int i=0;i<10;i++)
		{
			m[i] = new MyThread(i);
			m[i].start();
			
		}
		for(int i=0;i<10;i++)
		{
			try {
				m[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		
		return final_ar;
    }
}
