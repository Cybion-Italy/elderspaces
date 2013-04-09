<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../style/primary.css" type="text/css">
        <title>Elderspaces</title>
        <script type="text/javascript" src="../js/jquery-1.8.3.min.js"></script>  
    </head>
    <body>
    	<h1>Store activity</h1>
    	<div style="height:20px;"></div>
       	<div id="activity">
       		
       		<div style="height:10px;"></div>
       			
       		<div>
       			Description:
       			<div style="height:10px;"></div>
       			
       			<textarea id="description" rows="3" cols="80">Example monitoring activity</textarea>
       			
       			<div style="height:40px;"></div>
       			
       			<select id='type'>
					<option value = 'STREAM'>tag</option>
					<option value = 'CONTENT'>user</option>
				</select>
       			
       			<div style="height:10px;"></div>
       			
       			<textarea id="tags" rows="10" cols="100">Justin Bieber, #SingleBecause, #FemaleLies</textarea>
       			
       			<div style="height:20px;"></div>
       			
	       		<button id="add">add</button>
			</div>
    
       		<div style="height:40px;"></div>
			
			<div id='loading_message' style='display:none'>
			  <h3>Loading data...</h3>
			</div>
			<div id="add_result"></div>
		
			<script type="text/javascript">
			   
				$('#add').click(function() { 
			   		$('#loading_message').show(); 
			   		var description = $('#description').val();
			   		var tagsString = $('#tags').val();
			   		var tagsArray = tagsString.split(", ");
			   		var length = tagsArray.length,
					element = null;
					for (var i = 0; i < length; i++) {
					  tagsArray[i] = '"' + tagsArray[i] + '"';
					}
			   		var tagsParam = tagsArray.join();

			   		var typeString = $('#type').val();
			   		var postData = '{"description":"' + description + '", "tags":[' + tagsParam + '],"tweets":[],"active":false, "type":"' + typeString + '","username":""}';

			   		$.post("../../elderspaces-services/rest/activities/store", postData,
				         function(data) { 
				            $('#loading_message').hide();
				            $('#add_result').empty().append("<h3>Service response: <code>" + JSON.stringify(data) + "</code></h3>");
			             }, "json"
			         ).error(function() { alert('Internal Server Error'); });   
			   	});
			   
			</script>
       	</div>
  	</body>
</html>
