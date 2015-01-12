<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="sun.org.mozilla.javascript.internal.json.JsonParser"%>
<%@page import="StringFunctions.MakeGetRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
		<%
			
			String query = request.getParameter("query");
		
			MakeGetRequest make = new MakeGetRequest();
			
			String resp = make.sendGet('t',query);
			
			String final_result="NOUN --->  ";
			
			int ind = resp.indexOf("\"noun\",[");
			
    		int i = ind+8;
    		
    		if(ind==-1)
	    	{
    			resp = make.sendGet('p',query);
    			
				JSONObject jo = new JSONObject(resp);
				
				JSONArray ja = jo.getJSONArray("sentences");
				
				final_result = (String)ja.getJSONObject(0).get("trans");
				
	    	}
	    	else
	    	{
	    		final_result="NOUN --->  ";
	    		int i2 = ind+8;
	    		while(resp.charAt(i2)!=']')
	    		{
	    			final_result+= resp.charAt(i2);
	    			i2++;
	    		}
	    		final_result+="\n\n";
	    		
	    		if(resp.indexOf("\"verb\",[")!=-1)
	    		{
	    			final_result+="VERB --->  ";
	    			i2 = resp.indexOf("\"verb\",[")+8;
	    			while(resp.charAt(i2)!=']')
		    		{
		    			final_result+= resp.charAt(i2);
		    			i2++;
		    		}
		    		
		    			
	    		}
	    		
	    		final_result = final_result.replaceAll("\",\"", "\"    \"");
	    		
	    	}
    		
    		
    		
		%>
		
		<p>Google suggestion for "<%= query %>"</p>
		<pre><%= final_result %></pre>
</body>
</html>