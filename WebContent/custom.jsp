<%@page import="Struts.ImageSearchScope"%>
<%@page import="Struts.customGoogleSearchResult"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="GAPI.customGSearch" %>
<%@page import="GAPI.customImageSearch" %>
<%@page import="java.util.*" %>
<%@page import="Struts.Activity" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <link href="css/main.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript"
	src="jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="jquery-ui-1.8.20.custom.min.js"></script>
	 <link rel="stylesheet" type="text/css"
	href="jquery-ui-1.8.20.custom.css" />
	
	<style>
	
	body{




font-family:'Georgia', 'Trebuchet MS', serif;
	
}
	
	</style>
	<script>
	
	$(document).ready(function(){
		
		$('#context').accordion({
			
			autoHeight:false
		});
		$('#context-image').accordion({
			
			autoHeight:false
			
		});
		
		
		$('#c').click(function(){
			$('#context').fadeToggle("slow", function () {
			$('#context').css('display','block');
			$('#context-image').css('display','none');
			
		});
		
		});
		$('#c-image').click(function(){
			$('#context-image').fadeToggle("slow", function () {
			$('#context').css('display','none');
			$('#context-image').css('display','block');
			
		});
		
		});
		
		$('table img').bind('click',function(){
			
			 $.ajax({
	                url: "ImageSecondSearch.jsp",
	                data: { "inputURL":$(this).attr('src')},
	                method: "POST",
	                success:function(data){
	               	$('#wholepage').html(' ');
	               	
	                    $("#wholepage").html(data);
	                    //StepPlus();
	                },
	                error:function(){// StepPlus();}
				 }
	            });
			
		});
	
		$('table img').bind('mouseenter',function(){
			var top=$(this).offset().top;
			top=top-100;
			var inputURL=$(this).attr('src');
			var left=$(this).offset().left;
			$('#input-box').css('display','block');
			$('#input-box').css('top',top);
			$('#input-box').css('left',left);
		//	alert(top);
		$('#submit').bind('click',function(){
		
		 $.ajax({
               url: "ImageSecondSearch.jsp",
               data: { "scopeURL":$('#search-scope').val(),
            	   "inputURL":inputURL
               },
               method: "POST",
               success:function(data){
              	$('#wholepage').html(' ');
              	
                   $("#wholepage").html(data);
                   //StepPlus();
               },
               error:function(){// StepPlus();}
			 }
           });
			$('#input-box').css('display','none');
		});
			});
	$('#cancel').bind('click',function(){
			
			$('#input-box').css('display','none');
			
			
			});
	
		

	});

	</script>
</head>
<body>
<div id="wholepage">
<div id="switch">
<ul>
<li><a id="c" href="#context" style="color:white">Context</a></li>
<li><a id="c-image" href="#context-image" style="color:white">Image</a></li>
</ul>

</div>
<div id="context">

<%
ArrayList<String> ids=new ArrayList<String>();
ids=(ArrayList<String>)session.getAttribute("rel");
String key = request.getParameter("KeyWord");
for(String id:ids){%>

<div class="group" style="color:blue;height:50px;font-size:15px;"><h3><a =href="#">Custom Group search by:<%=id %></a></h3></div>
<div>
<table style="width:100%;border-weight:1px;border-color:white;overflow:none;font-family:Tahoma;font-size:13px;">
	<%

customGSearch cg=new customGSearch();
int count=1;
ArrayList<customGoogleSearchResult> results=cg.getCustomSearch(id);
if(results!=null){
for(customGoogleSearchResult cResult:results){%>

<tr class="title" style="text-align:left;font-size:15px;font-weight:bold;"><td  style="color:blue"><a href='<%=cResult.link%>' target="_blank"><%=count++%>.<%=cResult.title %></a></td></tr>
<tr class="content" style="text-align:left;"><td><%=cResult.link%></td></tr>
<tr class="content" style="text-align:left;"><td><%=cResult.snippet %></td></tr>


	
	
	
	
<%}}else{%>
	
	<tr class="content"  style="text-align:left;font-size:15px;font-weight:bold;"><td>No Similiar Result.</td></tr>
	
<% }%>
</table>
</div>
<% }%>

</div>
<div id="context-image"><% 
for(String id:ids){
int count=1;
%>

<div class="group_image" style="color:blue;height:50px;font-size:15px;"><h3><a href="#">Custom Group search by:<%=id %></a></h3></div>
<div>
<table style="width:100%;border-weight:1px;border-color:white;overflow:none;font-family:Tahoma;font-size:13px;height:auto">
<%
customImageSearch cis=new customImageSearch();
ArrayList<ImageSearchScope> images=cis.getImageResult(id);
if(images!=null){
for(ImageSearchScope image:images){%>

<tr class="title" style="text-align:left;font-size:15px;font-weight:bold;"><td style="color:blue"><%=count++ %>.<img src='<%=image.url%>' style="width:300px;vertical-align:text-top" title="Search similar images,please wait.."/></td></tr>	
<tr class="content" style="text-align:left;font-size:15px;font-weight:bold;"><td ><a href='<%=image.originalURL%>'><%=image.title %></a></td></tr>
	<tr class="content" style="text-align:left;"><td><%=image.content %></td></tr>
	
<% }}else{%>
	
	<tr class="content" style="text-align:left;font-size:15px;font-weight:bold;"><td>No Similiar Result.</td></tr>
<% }%>

</table>
</div>
<% }%>

</div>

<div id="input-box" style="display:none;position:absolute;background:black;width:300px;">
<input type="text" id="search-scope" value="http://pinterest.com/"></input>
<button id="submit">Submit</button>
<button id="cancel">Cancel</button>
</div>
</div>

</body>
</html>