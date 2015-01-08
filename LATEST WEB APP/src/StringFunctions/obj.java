package StringFunctions;


public class obj implements Comparable<obj>
	{
		public String str;
		public int n;
		@Override
		public int compareTo(obj o) {
			return o.n-n;
		}
	}