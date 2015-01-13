<%@page import="StringFunctions.MakeGetRequest"%>
<%@page import="StringFunctions.SingleQuery"%>
<%@page import="StringFunctions.DepartmentIndex"%>
<%@page import="java.util.ArrayList"%>
<%@page import="StringFunctions.obj"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body >

	
<%

//departments that user wants to see data for

	String depts = request.getParameter("depts");
	String arr2[] = depts.split("\\$");
	ArrayList arr = new ArrayList();
	for(int i=0;i<arr2.length;i++)
	{
		
		arr.add(arr2[i].trim());
	}
	
	SingleQuery d = new SingleQuery();

	d.setInputInformation(request.getParameter("lang1ID"), request.getParameter("queryString"), request.getParameter("lang2ID"));
	
	obj a[] = d.getTop5Results();
	%>
	
	<!-- showing table using custom library table.sorter -->
	
	<p>History based suggestions for "<%=request.getParameter("queryString") %>"</p>
	
	<%if(a.length>0){ %>
	<table id="myTable" class="tablesorter">
	<thead>
	 <tr>
	<%
	int no_of_columns = 2+arr.size();
	for(int i = 0;i<no_of_columns;i++)
	{
		
		if(i==0){ %>
		<th>History Suggestions</th>
		<%}
			else if(i==1){ %>
		<th>Total</th>
		<%}
			else{ 
		%>
		<th><%= arr2[i-2] %></th>
		
	<%}
	}%>
	</tr>
	</thead> 
	
	
	<tbody> 
	<%
	for(int i = 0;i<a.length;i++)
	{
		%><tr><%
		for(int j=0;j<no_of_columns;j++)
		{
		
			if(j>1)
			{%>
				<th><%= a[i].no[DepartmentIndex.getInstance().getIndex(arr2[j-2])] %></th> 
			<% }
			else if(j==1)
			{%>
				<th><%= a[i].no[0] %></th>
			<%}
			else
				{%>
					<th><%= a[i].str %></th>
				<%}
		}
		%></tr><%
	}%>
	
	</tbody> 
	</table>
<%} %>
</body>



</html>