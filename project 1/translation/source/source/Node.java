import java.util.HashMap;


public class Node {
	
	int no_of_times;
	String complete_str_upto_this;
	HashMap<Character, Edge> hm = new HashMap<Character, Edge>();
	
	public Edge ifEdgeExist(char c)
	{
		if(hm.get(c)==null)
		{
			return null; 
		}
		else
			return hm.get(c);
	}
	
	public void addEdge(char c, Edge e)
	{
		hm.put(c, e);
	}
	
}
