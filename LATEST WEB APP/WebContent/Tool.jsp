<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="jquery-2.1.3.min.js"></script>
<title>Translation Tool</title>

<link rel="stylesheet" href="css/style.css" >
<link rel="stylesheet" href="css/style_table.css" >
<link rel="stylesheet" href="css/input_table.css" >

<script type="text/javascript" src="scripts/jquery-latest.js"></script> 
<script type="text/javascript" src="scripts/jquery.tablesorter.js"></script> 
<script type="text/javascript" src="scripts/GetSuggestions.js"></script> 


</head>
<body bgcolor="#E6E6FA">

<div id="table" style= " height: 100%; width: 100%; ">

		<div class="container">
		<section><button id="tab_but" onclick="$('#table').fadeOut('slow'); $('#tool').fadeIn('slow');">&#60; Tool</button></section>
	    <section><centre123><p id="heading">Translation Tool</p></centre123></section>
	    <section ><right_numbers><p id="currentEntry"></p></right_numbers></section>
	    
		</div>
		<br>
		<table id="input_table" align="center" class="imagetable">
		
		
		</table>
		
	</div>

<div id="tool" >
	<!-- Here is the Heading part of the page -->
	<div class="container">
		<section><button id="too_but" onclick="$('#table').fadeIn('slow'); $('#tool').fadeOut('slow');">&#60; Table</button></section>
	    <section><centre123><p id="heading">Translation Tool</p></centre123></section>
	    <section ><right_numbers><p id="currentEntry">10/100</p></right_numbers></section>
	    
	</div>
    <hr>
	<!-- Heading part ends -->
	
	<!-- The textarea section starts -->
	
	
	<form name="myform">
		<table border="0" cellspacing="0" cellpadding="5" align="center" width="100%"><tr>
		<td align="center" ><textarea name="inputtext" id="Editor"  readonly></textarea></td>
		<td align="center">
		<input type="button" value="Get suggestions" onClick="ShowSelection()">
		</td>
		<td align="center"><textarea name="outputtext" ></textarea></td>
		</tr></table>
	</form>
	
	
	<hr>
	<!-- The textarea section ends -->
	<p id="historySuggestions"></p>
	<div id="getResponse">
		
	</div>
	<hr>
	<div id="googleSuggestion">
	
	</div>
	<hr>
	
	</div>
	
</body>

<script type="text/javascript">
	
	var arr = eval("<%=request.getParameter("tableString")%>");
	
	window.alert(arr);
	
	
	var html = '';
	
	var currentRow = 0;
	
	var previousRow = -1;
	
	
	
	for(var row in arr) {
        
		html += '<tr bgcolor="#dcddc0">\r\n';
        for(var item in arr[row]) {
        	if(row==0)
          html += '<th>' + arr[row][item] + '</th>\r\n';
          else
        	  html += '<td>' + arr[row][item] + '</td>\r\n';
          if(item==(arr[row].length-1)&&row!=0)
       	  {		
        	  	
       	  		html+='<td ><input type="button" id="'+row+'" value="Translate" onClick="changeRow(this.id)"></td>\r\n';
       	  }
          else if(item==(arr[row].length-1))
        	  {
        	  		html+='<th></th>\r\n';
        	  }
          
        }
        html += '</tr>\r\n';
      }
	document.getElementById("input_table").innerHTML=html;
	
	changeRow(1);
	
	function changeRow(button_id)
	{
		
		var ro = parseInt(button_id, 10);
		currentRow = ro; 
		
		if(previousRow==-1)
			{
				var tbl = document.getElementById("input_table");
				tbl.rows[ro].style.background  = '#7FFF00';
				previousRow = ro;
				$('#table').hide();
			}
		else
			{
				var tbl = document.getElementById("input_table");
				tbl.rows[previousRow].style.background  = '#dcddc0';
				tbl.rows[ro].style.background  = '#7FFF00';
				previousRow = ro;
				$('#table').fadeOut('slow'); $('#tool').fadeIn('slow');
			}
		
		document.getElementById("googleSuggestion").innerHTML="";
		document.getElementById("historySuggestions").innerHTML="";
		document.getElementById("getResponse").innerHTML="";
		document.getElementById("Editor").value = arr[ro][parseInt("<%=request.getParameter("column")%>", 10)-1];
		
		
	}
	
</script>



</html>