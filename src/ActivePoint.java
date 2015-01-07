
public class ActivePoint {

	Node active_node;
	char active_edge;
	int active_length;
	
	public void change(Node node,char edge_char, int len)
	{
		active_node = node;
		active_edge = edge_char;
		active_length = len;
	}
	
	public void initialize(Node root)
	{
		active_node = root;
		active_length = 0;
	}
	
	public char getNextChar()
	{
		Label l = active_node.hm.get(active_edge).label.getUpdated();
		String str = FindLCSofManyStrings.whole_string.substring(l.s, l.e+1);
		return str.charAt(active_length);
	}
	
}
