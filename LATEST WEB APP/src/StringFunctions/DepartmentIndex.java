package StringFunctions;

import java.util.HashMap;


//singleton class to get index of the department from all departments
public class DepartmentIndex {

		
		   public static HashMap< String, Integer> dept;
		   private static DepartmentIndex instance = null;
		   protected DepartmentIndex() {
		      // Exists only to defeat instantiation.
			   
		   }
		   public static DepartmentIndex getInstance() {
		      
			   
			   if(instance == null) {
		         instance = new DepartmentIndex();
		         dept = new HashMap<String, Integer>();
		         dept.put("TOTAL", 0);
		         dept.put("CWS", 1);
		         dept.put("CSR", 2);
		         dept.put("CJK", 3);
		      }
		      return instance;
		   }
		   
		   public int getIndex(String str)
		   {
			   return dept.get(str);
		   }
}
