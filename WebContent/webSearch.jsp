<%@page import="Struts.webSearchResult"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="GAPI.webGSearch" %>
<%@page import="JSON.JSONException"%>
<%@page import="java.util.*" %>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <link href="css/main.css" rel="stylesheet" type="text/css" />
</head>
<style>
body{




font-family:'Georgia', 'Trebuchet MS', serif;
	
}


</style>

<script>


$(document).ready(function(){
	
	
	$('#web-search').accordion({
		autoHeight:false
	});
	
});


</script>
<body>
<div id="web-search">
<%
ArrayList<String> ids=new ArrayList<String>();
ids=(ArrayList<String>)session.getAttribute("rel");
String key = request.getParameter("KeyWord");
for(String id:ids){%>

<div class="webgroup" style="color:blue;height:50px;font-size:15px;"><h3><a href="#">Web Group search by:<%=id %></a></h3></div>
<div class="web-table">
<table style="width:100%;border-weight:1px;border-color:white;overflow:none;font-family:Tahoma;font-size:13px;">
	<%
webGSearch wg=new webGSearch();
int count=1;
ArrayList<webSearchResult> webList=wg.getWebSearch(id);
if(webList!=null){
for(webSearchResult l:webList){%>
<tr class="title" style="text-align:left;font-size:15px;font-weight:bold;"><td style="font-size:15px;color:blue;"><a href='<%=l.url%>'><%=count++%>.<%=l.title %></td></a></tr>
<tr class="content"style="text-align:left;"><td><%=l.visibleURL %></td></tr>
<tr class="content" style="text-align:left;"><td><%=l.content %></td></tr>
	
	
	
	
<% }}else{%>
	
	<tr class="content"><td>There are no Similiar results.</td></tr>
	
<% }%>
</table></div>
<% }%>

</div>


</body>
</html>