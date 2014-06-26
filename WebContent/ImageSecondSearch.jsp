<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="image.rankImage" %>
<%@page import="java.util.*" %>
<%@page import="Struts.Image" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <script type="text/javascript"
	src="jquery-1.7.2.min.js"></script>
	<script>
	
	$(document).ready(function(){
		if(	$('table img').css('width')>'600px'){
			
			$('table img').css('width','600px');
		}
	
		
		
		
	});
	</script>
</head>
<body>
<%
String inputURL = request.getParameter("inputURL");
String scopeURL=request.getParameter("scopeURL");
rankImage ri=new rankImage();
ArrayList<Image> images=ri.getRankResult(inputURL,scopeURL);
int length=0;
if(images.size()<10){
	
	length=images.size();
	
}else{
	
	length=10;
}
int count=1;
%>
<div id="similarResult">Similar Image you get:</div>
<table style="width:100%;border-weight:1px;border-color:white;overflow:none;font-family:Tahoma;font-size:13px;">
<%  for(int i=0;i<length;i++){%>

<tr style="text-align:left;font-size:12px"><td><%=count++ %>.<img src='<%=images.get(i).url%>' alt='<%=images.get(i).text %>' style="vertical-align:text-top;width:auto" ><td></tr>
<tr style="text-align:left;"><td><%=images.get(i).text %></td><tr>
	
	
	
	
<% }%>


</table>

</body>
</html>