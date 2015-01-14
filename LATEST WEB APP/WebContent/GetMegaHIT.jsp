<%@page import="java.util.ArrayList"%>
<%@page import="StringFunctions.MegaHIT"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<%
	
		ArrayList<MegaHIT.t> arr = null;
		
		MegaHIT hit = new MegaHIT(request.getParameter("lang1ID"), request.getParameter("queryString"), request.getParameter("lang2ID"), arr);
	
		arr = hit.getResult();
		
		System.out.println ("test");
		
		String l1="",l2="";
		
		if(arr.size()==0)
		{
			l1 += "NO MEGA HIT FOUND";
		}
		else
		{
			l1 += "MEGA HIT FOUND\n\n";
			int previous = -1;
			for(int i=0;i<arr.size();i++)
			{
				l1+= (i+1)+". "+arr.get(i).s +" --> "+ arr.get(i).t+"\n";
				
				if(arr.get(i).i>previous+1)
				{
					l2+= "___"+arr.get(i).t;
				}
				else
				{
					l2+=arr.get(i).t;
				}
				previous = arr.get(i).j;
			}
			
			if(previous<request.getParameter("queryString").length()-1)
			{
				l2+="___";
			}
			
		}
	
	%>
	
	<br>
	<pre><%=l1 %></pre>
	<p id="megahitUse" hidden><%=l2 %></p>
	<input type="button" value="Use It" onClick=usePressed()>
	
</body>


</html>