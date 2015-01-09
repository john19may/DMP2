package StringFunctions;


public class obj implements Comparable<obj>
	{
		public String str;
		public int no[] = {};
		
		
		@Override
		public int compareTo(obj o) {
			return o.no[0]-no[0];
		}
		
		public int compareStringLength(obj o)
		{
			return o.str.length()-str.length();
		}
	}