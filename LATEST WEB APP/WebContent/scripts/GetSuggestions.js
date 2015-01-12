/**
 *  This file actually fetches suggestions from the previous history and google
 */

function ShowSelection()
{
  var textComponent = document.getElementById('Editor');
  var selectedText;
  // IE version
  if (document.selection != undefined)
  {
    textComponent.focus();
    var sel = document.selection.createRange();
    selectedText = sel.text;
  }
  // Mozilla version
  else if (textComponent.selectionStart != undefined)
  {
    var startPos = textComponent.selectionStart;
    var endPos = textComponent.selectionEnd;
    selectedText = textComponent.value.substring(startPos, endPos)
  }
  
  if(selectedText.length<=1)
  alert("Please select the string length greater that 1");
  else
  {
  	 var depts = ["CWS", "CSR", "CJK"];
  	 makeAjaxRequest("EN", selectedText,"JP",depts);
  	 getGoogleWords(selectedText);
  }
  
}

//here depts is the object with names of all departments that user wants to see
function makeAjaxRequest(lang1ID, query, lan2ID, depts)
{
	var i=0;
	var str="";
	for(i=0;i<depts.length;i++)
    {
    	str+=(depts[i]+"$");
    }
    
    
	var xmlhttp;
	if (window.XMLHttpRequest)
	  {// code for IE7+, Firefox, Chrome, Opera, Safari
	  xmlhttp=new XMLHttpRequest();
	  }
	else
	  {// code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	xmlhttp.onreadystatechange=function()
	  {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
	    	document.getElementById("historySuggestions").innerHTML="> History based suggestions for \""+query+"\"";
	    	document.getElementById("getResponse").innerHTML=xmlhttp.responseText;
	    	$(document).ready(function() 
			    { 
		    		$("#myTable").tablesorter({
							sortList: [[1,1]] 
		    		});
				}); 
	    }
	    else{
	    document.getElementById("historySuggestions").innerHTML="Problem while retrieving History based suggestions";
	    }
	  }
	xmlhttp.open("GET","GetTable.jsp?lang1ID=" + lang1ID+"&queryString="+query+"&lang2ID="+lan2ID+"&depts="+str,true);
	xmlhttp.send(null);
}

function getGoogleWords(query)
{
	var xmlhttp;
	if (window.XMLHttpRequest)
	  {// code for IE7+, Firefox, Chrome, Opera, Safari
	  xmlhttp=new XMLHttpRequest();
	  }
	else
	  {// code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
		xmlhttp.onreadystatechange=function()
	  {
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
	    	
	    		document.getElementById("googleSuggestion").innerHTML=xmlhttp.responseText;
	    }
	    else{
	    
	    }
	  }
	xmlhttp.open("GET","GetGoogleSuggestions.jsp?query="+query,true);
	xmlhttp.send(null);
}