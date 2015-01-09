<%@page import="java.util.ArrayList"%>
<%@page import="StringFunctions.obj"%>
<%@page import="StringFunctions.del"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Created my first Web site</title>
</head>
<body>


<%
//departments that user wants to see data for
ArrayList arr = new ArrayList();
arr.add("CWS");
arr.add("CSR");
arr.add("CJK");

del d = new del();
d.getInputTableInformation("EN", "name", "JP", arr);

// obj a[] = d.printCommonStrings("TOTAL");
// for(int i = 0;i<a.length;i++)
// {
// 		out.println(a[i].str+" - "+a[i].no[0]+"\n");
// }


%>
</body>
</html>