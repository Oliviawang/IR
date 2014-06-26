<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="GAPI.activityList"  %>
<%@page import="java.io.IOException" %>
<%@page import="Struts.Activity" %>
<%@page import="JSON.JSONException"%>
<%@page import="java.util.*" %>
<%@page import="GAPI.categoryGroup" %>
<%@page import="java.util.ArrayList" %>

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
<body>
<!-- <h1>Google+ By Relevance</h1> -->
<table id="rel" style="border-weight:1px;border-color:white;overflow:none;">
<% 
String key = request.getParameter("KeyWord");
categoryGroup cg=new categoryGroup();
ArrayList<Activity> finalresult=new ArrayList<Activity>();
ArrayList<Activity>  tr=cg.groupByRelevance(key);
ArrayList<String> ids=new ArrayList<String>();

boolean flag=true;
for(Activity rel:tr){
	for(Activity activity:finalresult){
	 flag=true;
		if(activity.name.equals(rel.name)){
			flag=false;
			break;
			
		}}
	if(rel.text==null){
		
	rel.text="There is no content";
	}
		if(rel.name==null){
			if(rel.title==null){
			rel.name="No title";}
			else{
				
				rel.name=rel.title;
			}
		}
		if(rel.verb==null){
			
			rel.verb="Unkown";
		}
	
	if(flag){
		finalresult.add(rel);
		
	}
	}
	
	int count=1;

for(Activity r:finalresult){
ids.add(r.name);
%>
	
	<tr class="title">
	<td style="text-align:left;font-size:15px;font-weight:bold;"><a id="rel-<%=r.id %>" class="rel" href='<%=r.url%>' target="_blank" rel="<%=r.name %>"><%=count++%>.<%=r.name %></a></td><td><a href='<%=r.link%>'><img style="vertical-align:text-bottom" src='<%=r.actorPic%>' title='<%=r.actorName%>'/></a></td></tr>
	<tr class="content"><td style="text-align:left;width:100%"><%=r.actorName %> &nbsp; &nbsp;||&nbsp;Type:&nbsp;<%=r.type %></td>
	
	</tr>
	<tr class="content">

	<td style="text-align:left;width:100%"><%=r.text %></td>
	
	</tr>
	
	
	
	<% 
	
}
session.setAttribute("rel",ids);

%>



</table>
</body>
</html>