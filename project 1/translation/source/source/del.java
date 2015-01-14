import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


public class del {

	public class obj implements Comparable<obj>
	{
		String str;
		int n;
		@Override
		public int compareTo(obj o) {
			return n-o.n;
		}
	}
	
	public static void main(String args[])
	{
		del d = new del();
		HashMap hm = new HashMap();
		
		int n;
		Scanner in = new Scanner(System.in);
		n = in.nextInt();
		String[] arr = new String[n];
		
		in.nextLine();
		
		for(int i = 0;i<n;i++)
		{
			arr[i] = in.nextLine();
			addIntoHashmap(hm, arr[i]);
		}
		
		obj[] a = new obj[hm.size()];
		for(int i=0;i<hm.size();i++)
		{
			a[i] = d.new obj();
		}
		
		int j=0;
		    Iterator it = hm.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        a[j].str = (String)pairs.getKey();
		        if((int)pairs.getValue()>1)
		        a[j].n = ((int)pairs.getValue())*a[j].str.length();
		        else
		        	a[j].n = 0;
		        j++;
		    }
		    
		    Arrays.sort(a);
		    
		    for(int i = 0;i<a.length;i++)
		    {
		    	if(a[i].n!=0)
		    	{
		    		System.out.println(a[i].str+" - "+a[i].n);
		    	}
		    }
	}
	
	public static void addIntoHashmap(HashMap hm, String str)
	{
		
		int window=1;
		
		HashMap notFromSameString = new HashMap();
		
		for(window = 1; window<=str.length(); window++ )
		for(int i = 0; i<= str.length() - window; i++ )
		{
			String sub = str.substring(i,i+window);
			
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
