<%@page import="StringFunctions.obj"%>
<%@page import="StringFunctions.del"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Created my first Web site</title>
</head>
<body>

<p>fhadkfhakhdfakjd</p>

<%
	del d = new del();
	obj a[] = d.printCommonStrings();
	for(int i = 0;i<a.length;i++)
    {
    	if(a[i].n!=0)
    	{
    		out.println(a[i].str+" - "+a[i].n);
    	}
    }
%>
</body>
</html>