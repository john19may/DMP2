
public class Edge {

	Label label;
	Node pointer;
	
	public Edge(int n1, int n2, Node node, boolean isEqualToCurrent) {
		
		label = new Label(n1,n2,isEqualToCurrent);
		pointer = node;
	}
	
	public void changeLabel(int n1, int n2, boolean isEqualToCurrent)
	{
		label.s = n1;
		label.e = n2;
		label.isCurrentSysmbol = isEqualToCurrent;
	}
	
	public char getFirstChar()
	{
		return FindLCSofManyStrings.whole_string.charAt(0);
	}
}
