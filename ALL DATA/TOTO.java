package yoyo;

import java.util.ArrayList;

public class TOTO {

	
	
	int thread_pos = 0;
	
	ArrayList<TOTO.t> allString = new ArrayList<TOTO.t>();
	
	ArrayList<TOTO.t> selected = new ArrayList<TOTO.t>();

	static int threshold = 5;
	
	 String queryLanguageCode;
	String query;
	 String suggestionLanguageCode;
	
	MyThread m[] = new MyThread[10];
	
	class t{
		
		String s;
		String t;
		int i,j;
		public t(String s,int n1,int n2) {
			i=n1;j=n2;
			this.s = s;
		}
		
	}
	
	
	
	
	
	public TOTO(String queryLanguageCode1, String query, String suggestionLanguageCode1) {
		
		queryLanguageCode = queryLanguageCode1;
		this.query = query;
		suggestionLanguageCode = suggestionLanguageCode1;
		
		for(int window = query.length()/3; window<=query.length(); window++ )
			for(int i = 0; i<= query.length() - window; i++ )
			{
				String sub = query.substring(i,i+window);
				allString.add(new t(sub, i, i+window-1));
			}
		
		for(int i=0;i<10;i++)
		{
			m[i] = new MyThread(i);
			m[i].start();
		}
	}
	
	
	
	public class MyThread extends Thread{

		int index ;
		
		public MyThread(int i) {
			
			index = i;
		}
		
	    public void run(){
	    	
	    	try {
	    		t temp = getString();
		    	while(temp!=null)
		    	{
		    		String no = getStringSuggestion(temp.s);
		    		//System.out.println(temp.s+" "+no);
		    		if(!no.equals("NOTFOUND"))
		    		{
		    			temp.t = no; 
		    			addString(temp);
		    		}
		    		temp = getString();
		    	}
		    	allThreadsComplete(index);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    }
	    
	    public String getStringSuggestion(String str)
		{
			if(str.length()<=2)
				return "NOTFOUND";
			
			GetProcessedData gp = new GetProcessedData();
			gp.setInputInformation(queryLanguageCode, str, suggestionLanguageCode);
			ArrayList<obj> a = gp.getProcessedObjArray();
			
			obj max;
			if(a.size()>0)
				max = a.get(0);
			else
				return "NOTFOUND";
			
			
			for(int i=0;i<a.size();i++)
			{
				if(max.compareTo(a.get(i))<0)
				{
					max = a.get(i);
				}
			}
			
			
			if(max.no[0]>=threshold)
			{
				return max.str;
			}
			else
				return "NOTFOUND";
		}
	}
	
	
	
	public synchronized TOTO.t getString(){
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

    public synchronized void addString(TOTO.t temp)
    {
    	selected.add(temp);
    }
    
    int completed=0;
    public synchronized void allThreadsComplete(int ind)
    {
    	completed++;
    	if(completed==10)
    	{
    		System.out.println("done");
    	}
    }
}
