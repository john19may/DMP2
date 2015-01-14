<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Demo - CSV-to-Table</title>
<link rel="stylesheet" href="css/form_style.css" >
<script src="scripts/jquery-2.1.3.min.js"></script>
<script src="scripts/jquery.csv-0.71.js"></script>
<script>
  $(document).ready(function() {
    if(isAPIAvailable()) {
      $('#files').bind('change', handleFileSelect);
    }
  });

  function isAPIAvailable() {
    // Check for the various File API support.
    if (window.File && window.FileReader && window.FileList && window.Blob) {
      // Great success! All the File APIs are supported.
      return true;
    } else {
      // source: File API availability - http://caniuse.com/#feat=fileapi
      // source: <output> availability - http://html5doctor.com/the-output-element/
      document.writeln('The HTML5 APIs used in this form are only available in the following browsers:<br />');
      // 6.0 File API & 13.0 <output>
      document.writeln(' - Google Chrome: 13.0 or later<br />');
      // 3.6 File API & 6.0 <output>
      document.writeln(' - Mozilla Firefox: 6.0 or later<br />');
      // 10.0 File API & 10.0 <output>
      document.writeln(' - Internet Explorer: Not supported (partial support expected in 10.0)<br />');
      // ? File API & 5.1 <output>
      document.writeln(' - Safari: Not supported<br />');
      // ? File API & 9.2 <output>
      document.writeln(' - Opera: Not supported');
      return false;
    }
  }

  function handleFileSelect(evt) {
    var files = evt.target.files; // FileList object
    var file = files[0];

    // read the file metadata
    var output = ''
        output += '<span style="font-weight:bold;">' + escape(file.name) + '</span><br />\n';
        output += ' - FileType: ' + (file.type || 'n/a') + '<br />\n';
        output += ' - FileSize: ' + file.size + ' bytes<br />\n';
        output += ' - LastModified: ' + (file.lastModifiedDate ? file.lastModifiedDate.toLocaleDateString() : 'n/a') + '<br />\n';

    // read the file contents
    printTable(file);

    // post the results
    $('#list').append(output);
  }

  function printTable(file) {
    var reader = new FileReader();
    reader.readAsText(file);
    reader.onload = function(event){
      var csv = event.target.result;
      var data = $.csv.toArrays(csv);

		
		var pre = "Columns in File:\n";
		for(var row in data) {
	        
			if(row==0)
	        for(var item in data[row]) {
	        	var tt= parseInt("1", 10);
	        	var ttt = parseInt(item, 10);
	        	pre+=tt+ttt;
	        	pre+=" ";
	            pre+=data[0][item];
	            pre+="\n";
	        }
	      }
		
		
		$('#columns').html(pre);
		 
		var completeTableString = "[";
		
		for(var row in data) {
	        completeTableString+="[";
	        for(var item in data[row]) {
	          if(item==(data[row].length-1))
	          completeTableString+="'"+data[row][item]+"'";
	          else
	          completeTableString+="'"+data[row][item]+"',";  
	        }
	        if(row==(data.length-1))
	        completeTableString+=']';
	        else
	        	completeTableString+='],';
	      }
		completeTableString +=']';
		
		console.log(completeTableString);
		
		$('#hiddenTAble').val(completeTableString);
		
    };
    reader.onerror = function(){ alert('Unable to read ' + file.fileName); };
  }
</script>
</head>

<body background="css/background.png">

<h1 align="center" >Translation Tool</h1>
<hr>
<br />

<form action="Tool.jsp" method="POST" accept-charset="ISO-8859-1" >


<p><b>Choose Table Format:</b></p>
Format 1 - One table per language<input type="radio" name="format" value="format1" checked>
<br>
Format 2 - One table all languages<input type="radio" name="format" value="format2">

<br />

<p><b>Choose Table File:</b></p> <input type=file id=files name=files[] multiple />
<pre id="columns"></pre>
<p><b>Text Column Number:</b></p> <input type="text" name="column">
<br />
<p><b>Input Language Code:</b></p> <a href="https://cloud.google.com/translate/v2/using_rest#language-params" target="_blank"> (Codes Page)</a>    <input type="text" name="input_language" />
<br />
<p><b>Output Language Code:</b></p> <input type="text" name="output_language" />
<br />

<input type="hidden" type="text" name="tableString" id="hiddenTAble" />
<br />
<input type="submit" value="Submit" />
</form>
<br>


</body>
</html>