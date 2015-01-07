package StringFunctions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class del {

	
	
	HashMap hm = new HashMap();
	
	public obj[] printCommonStrings()
	{

		String[] arr = {"shubham","bhamwer","werwerham","shub"};
		
		int n = arr.length;
		for(int i = 0;i<n;i++)
		{
			addIntoHashmap(hm, arr[i]);
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
		        a[j].n = ((int)pairs.getValue())*a[j].str.length();
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
