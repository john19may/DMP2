import java.util.ArrayList;
import java.util.Scanner;


public class FindLCSofManyStrings {

	
	static String whole_string = "";
	
	public static int current;
	
	private static ActivePoint act_pt;
	
	private static Node root;
	
	private static int remainder = 1;
	
	private static ArrayList<String> listOfTheWordsToBeAdded;
	
	public static void main(String args[])
	{
		
		//Inputing all of the strings and initializing
		
		int no_of_strings;
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		no_of_strings = in.nextInt();
		
		String[] arr = new String[no_of_strings];
		
		for(int i = 0;i<no_of_strings;i++)
		{
			arr[i] = in.next();
			arr[i]+='*';
		}
		
		for(int i = 0;i<no_of_strings;i++)
		{
			whole_string+=arr[i];
		}

		root = new Node();
		
		act_pt.initialize(root);
		
		listOfTheWordsToBeAdded = new ArrayList<String>();
		
		//Completed inputing all of the strings
		
		for(int i=0;i<whole_string.length();i++)
		{
			current = i;
			char cur_char = whole_string.charAt(i);
			
			//not same character as the first character on the label of the edge
			if(remainder==1&&act_pt.active_node.ifEdgeExist(cur_char)==null)
			{
				Node n = new Node();
				Edge e = new Edge(i, i, n, true);
				act_pt.active_node.addEdge(cur_char, e);
			}
			else if(remainder==1&&act_pt.active_node.ifEdgeExist(cur_char)!=null)
			{
				act_pt.change(act_pt.active_node, cur_char, act_pt.active_length+1);
				remainder++;
				listOfTheWordsToBeAdded.add(cur_char+"");
			}
			else if(act_pt.getNextChar()==cur_char)
			{
				act_pt.change(act_pt.active_node, act_pt.active_edge, act_pt.active_length+1);
				remainder++;
				
				for(int j=0;j<listOfTheWordsToBeAdded.size();j++)
				{
					listOfTheWordsToBeAdded.set(j, listOfTheWordsToBeAdded.get(j)+cur_char);
				}
				
				listOfTheWordsToBeAdded.add(cur_char+"");
			}
			else
			{
				for(int j=0;j<listOfTheWordsToBeAdded.size();j++)
				{
					if(j==0)
					{
						
					}
				}
			}
		}
	}
	
}

