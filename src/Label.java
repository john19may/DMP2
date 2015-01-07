
public class Label {

	int s,e;
	boolean isCurrentSysmbol;
	
	public Label(int n1, int n2, boolean isEqualToCurrent) {
		s = n1;
		e = n2;
		isCurrentSysmbol = isEqualToCurrent;
	}
	
	public Label getUpdated()
	{
		if(isCurrentSysmbol)
		{
			e = FindLCSofManyStrings.current;
			return this;
		}
		else
			return this;
	}
}
