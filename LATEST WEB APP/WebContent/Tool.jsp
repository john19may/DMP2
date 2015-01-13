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
<link rel="stylesheet" href="css/spinner.css" >

<script type="text/javascript" src="scripts/jquery-latest.js"></script> 
<script type="text/javascript" src="scripts/jquery.tablesorter.js"></script> 
<script type="text/javascript" src="scripts/GetSuggestions.js"></script> 


</head>
<body bgcolor="#E6E6FA">

<div id="table" style= " height: 100%; width: 100%; ">

		<div class="container">
		<section><button id="tab_but" onclick="$('#table').fadeOut('slow'); $('#tool').fadeIn('slow');">&#60; Tool</button></section>
	    <section><centre123><p id="heading">Translation Tool</p></centre123></section>
	    
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
	    <section ><right_numbers><p id="currentEntry"></p></right_numbers></section>
	    
	</div>
    <hr>
	<!-- Heading part ends -->
	
	<!-- The textarea section starts -->
	
	
	<form name="myform">
		<table border="0" cellspacing="0" cellpadding="5" align="center" width="100%"><tr>
		<td align="center" ><textarea name="inputtext" id="Editor"  readonly></textarea></td>
		<td align="center">
		<input id="suggestion_but" type="button" value="Get suggestions" onClick="getSuggestions()">
		</td>
		<td align="center"><textarea name="outputtext" id="Editor_result"></textarea></td>
		</tr></table>
	</form>
	
	
	<hr>
	<div class="spinner" align="center">
		<img id="img-spinner" src="css/spinner.gif" alt="Loading" height="70px" width="70px" alt="Loading" />
	</div>
		    
	<!-- The textarea section ends -->
	<div id="getResponse">
		
		
	</div>
	<hr>
	<div class="spinner" align="center">
		<img id="img-spinner2" src="css/spinner.gif" alt="Loading" height="70px" width="70px" alt="Loading" />
	</div>
	
	<div id="googleSuggestion">
	
	</div>
	<hr>
	
	<input type="button" value="Next" onClick="nextPressed()">
	
</div>
	
</body>

<script>

	$("#img-spinner").hide();
	$("#img-spinner2").hide();
	
	var arr = eval("<%=request.getParameter("tableString")%>");
	
	var arr_result = new Array(arr.length);
	
	for ( var i=0; i <arr.length; i++)
		arr_result[i]=new Array(arr[0].length);
	//window.alert(arr);
	
	
	var html = '';
	
	var currentRow = 0;
	
	var previousRow = -1;
	
	var text_columns = parseInt("<%=request.getParameter("column")%>", 10)-1;
	
	
	for(var row in arr) {
        
		html += '<tr bgcolor="#dcddc0" id="r'+row+'">\r\n';
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
	
	for(var g1 in arr)
	{
		for(var g2 in arr[g1])
			{
				arr[g1][g2] = document.getElementById("input_table").rows[g1].cells[g2].innerHTML;
				if(g2==text_columns&&g1!=0)
				arr_result[g1][g2] = "";
				else
				arr_result[g1][g2] = document.getElementById("input_table").rows[g1].cells[g2].innerHTML;
			}
	}
	
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
		document.getElementById("getResponse").innerHTML="";
		
		document.getElementById("currentEntry").innerHTML = currentRow+"/"+(arr.length-1);
		
		//alert(arr[ro][text_columns]);
		document.getElementById("Editor").value =arr[ro][text_columns];
		document.getElementById("Editor_result").value =arr_result[ro][text_columns];
		
	}
	
	function getSuggestions()
	{
		var v1 = "<%=request.getParameter("output_language")%>";
		var v2 = "<%=request.getParameter("input_language")%>";
		ShowSelection(v2,v1);
	}
	
	function nextPressed()
	{
		arr_result[currentRow][text_columns] = document.getElementById("Editor_result").value;
		
		
		if(currentRow==(arr.length-1))
			{
				var csvContent = "data:text/csv;charset=utf-8,";
				for(var g1 in arr_result)
				{
					for(var g2 in arr_result[g1])
						{
							var ele = arr_result[g1][g2];
							ele = "\""+ele+"\"";
							csvContent +=ele;
							if(g2==arr_result[g1].length-1)
								csvContent+="\n";
							else
								csvContent+=",";
						}
				}
				
				
				var encodedUri = encodeURI(csvContent);
				var link = document.createElement("a");
				document.body.appendChild(link);
				link.setAttribute("href", encodedUri);
				link.setAttribute("download", "my_data.csv");
				
				if (confirm("Congratulations your file is translated! \n Do you want to download it? ") == true) {
					link.click();    
			    } 
				
			}
		else
			{
				changeRow(currentRow+1);
			}
	}
	
</script>



</html>