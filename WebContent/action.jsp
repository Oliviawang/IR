
<%@page import="Struts.TermResult"%>
<%@page import="com.sun.xml.internal.fastinfoset.util.StringArray"%>
<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import"%>
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
<script type="text/javascript"
	src="jquery-1.7.2.min.js"></script>
<script>
$(document).ready(function(){
	
//var post=	$.unique($('.post_name').get());
	
	//$('#filter').html(post);
});


</script>
<style>


body{




font-family:'Georgia', 'Trebuchet MS', serif;
	
}

</style>
</head>
<body>
<!-- <h1>Google+ By Action</h1> -->

<% 





String key = request.getParameter("KeyWord");
categoryGroup cg=new categoryGroup();
ArrayList<Activity> a=cg.groupByAction(key);
int count=1;
int num=1;
for(int i=0;i<a.size();i++){
String action=a.get(i).verb;
	if(action.equals("post")){%>

		<div class="post_name"><table><tr style="font-size:15px;"><td id="action-<%=a.get(i).id%>" style="color:blue" class="title"><b><%=count++%>.<a href='<%=a.get(i).url%>' target="_blank"><%=a.get(i).name %></a></b></td><td><a href='<%=a.get(i).link%>'><img style="vertical-align:text-bottom" src='<%=a.get(i).actorPic%>' title='<%=a.get(i).actorName %>'/></a></td></tr></table>
		<div class="content"></br><%=a.get(i).actorName %>&nbsp;&nbsp;||&nbsp;Type:&nbsp;<%=a.get(i).type %>&nbsp;||&nbsp;<div style="background:yellow;width:80px;display:inline">Action:Post</div> </div>
		</br> <%=a.get(i).text %>
		</div>


<% 	}else if(action.equals("share")){%>
	
	
		<div class="share_name"><table><tr style="font-size:15px;"><td style="color:blue" class="title" id="action-<%=a.get(i).id%>"><b><a href='<%=a.get(i).url%>' target="_blank"><%=count++%>.<%=a.get(i).name %></a></b></td><td><a href='<%=a.get(i).link%>'><img style="vertical-align:text-bottom"  src='<%=a.get(i).actorPic%>' title='<%=a.get(i).actorName %>'/></a></td></tr></table>
		<div class="content"></br><%=a.get(i).actorName %>&nbsp;&nbsp;||&nbsp;Type:&nbsp;<%=a.get(i).type %>&nbsp;||&nbsp;<div style="background:white;width:80px;display:inline">Action:Share</div></div>
	</br> <%=a.get(i).text %></div>	
	
	

 <%	}
	
}%>





	





</body>
</html>