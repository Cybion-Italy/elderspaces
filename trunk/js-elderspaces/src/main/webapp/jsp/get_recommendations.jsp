<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../style/primary.css" type="text/css">
        <link rel="stylesheet" href="../style/ui.jqgrid.css" type="text/css">
       
        <title>Twitter Monitor</title>
       
        <script type="text/javascript" src="../js/jquery-1.8.3.min.js"></script>  
        <script src="../js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="../js/grid.locale-en.js" type="text/javascript"></script>
        <script src="http://www.google.com/jsapi" type="text/javascript"></script>
    </head>
    <body>
    	<h1>Monitoring activities</h1>
    	<div style="height:20px;"></div>
       	<div id="activities">
       		   		
       		<div style="height:40px;"></div>
		
			<table id="activity_list"><tr><td/></tr></table>
			<div id="pager"></div>
			
			<script type="text/javascript">
			   
			   jQuery("#activity_list").jqGrid({
				
				   	url:'../../twitter-monitor/rest/getactivities-grid',
					datatype: "json",
				   	colNames:['Id', 'Description', 'Tags', 'Active', 'Type', 'Username'],
				   	colModel:[
				   		{name:'id',index:'id', width:55, search:true, stype:'select'},
				   		
				   		{name:'description',index:'description', width:300, search:true, stype:'text', editable:true},
			
				   		{name:'tags',index:'tags', width:400, resizable: true, search:true, stype:'text', editable:true},
			
				   		{name:'active',index:'active', width:120, search:true, stype:'text', editable:true, edittype: "select", editoptions: { value: {'1':'true','0':'false'}}},
				   		
				   		{name:'type', index:'type', width:120, search:true, stype:'text', editable:true, edittype: "select", editoptions: { value: {'1':'STREAM','0':'CONTENT'}}},
				   		
				   		{name:'username',index:'username', width:120, search:true, stype:'text'}		
				   	],
				   	onSelectRow: function(id){
				    	if(id && id!==lastsel){
				        	jQuery("#activity_list").restoreRow(lastsel);
				        	jQuery("#activity_list").editRow(id,true);
				        	lastsel=id;
				      }
				    },
				    rowNum:10,
				   	rowList:[10,20,30],
				   	pager: '#pager',
				   	sortname: 'id',
				    viewrecords: true,
				    sortorder: "asc",
				    caption:"",
				    loadonce: true, 
				    sortable: true,
				    cellEdit:true, 
				    cellSubmit:"remote", 
				    cellurl:"../../twitter-monitor/rest/edit"
				    
				});
				
				var lastsel;
				
				jQuery("#activity_list").jqGrid('navGrid','#pager',{edit:true,add:true,del:true});

			 
			</script>
       	</div>
  	</body>
</html>
