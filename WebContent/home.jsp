<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Custom Google+</title>
  <link href="css/main.css" rel="stylesheet" type="text/css" />
  <link href='http://fonts.googleapis.com/css?family=Metal+Mania' rel='stylesheet' type='text/css'>
 <link rel="stylesheet" type="text/css"
	href="jquery-ui-1.8.20.custom.css" />
  <link href='http://fonts.googleapis.com/css?family=Mystery+Quest&subset=latin,latin-ext' rel='stylesheet' type='text/css'>
<script type="text/javascript" src="jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="jquery-ui-1.8.20.custom.min.js"></script>
<!-- <script type="text/javascript" src="PIE.js"></script> -->
<style>
.button{
	border: 1px solid #696;
	padding: 3px 3px;
	text-align:center;
	font-family:"lucida grande",lucida,tahoma;
	font-size:1.0em;
	
	width: 100%;
	height:100%;
	color:black;
	-webkit-border-radius: 8px;
	-moz-border-radius: 8px;
	border-radius: 8px;
	-webkit-box-shadow: #666 0px 2px 3px;
	-moz-box-shadow: #666 0px 2px 3px;
	box-shadow: #666 0px 2px 3px;
	background: #828679;
	background: -webkit-gradient(linear, 0 0, 0 bottom, from(#828679),
		to(#E7E7E9) );
	background: -webkit-linear-gradient(#828679, #E7E7E9);
	background: -moz-linear-gradient(#828679, #E7E7E9);
	background: -ms-linear-gradient(#828679, #E7E7E9);
	background: -o-linear-gradient(#828679, #E7E7E9);
	background: linear-gradient(#828679, #E7E7E9);
	-pie-background: linear-gradient(#828679, #E7E7E9);
	1behavior: url(PIE.htc);
}

.input{
   font-family:"lucida grande",lucida,tahoma;
	font-size:0.8em;
    color: #ACAEB9;
    text-align: inherit;
    outline: none;
    cursor: text;
    background-color:white;
    -webkit-border-radius: 8px;
	-moz-border-radius: 8px;
	border-radius: 8px;
	-webkit-box-shadow: #666 1px 1px 1px;-moz-box-shadow: #666 1px 1px 1px;box-shadow: #666 1px 1px 1px;
	1behavior: url(PIE.htc);
}

</style>
	<script>
	
	$(document).ready(function(){
		$('#search, #category_search, #web_search')
	    .button().addClass('#keyword');
   $('#search').button();
		
		$('#category_search').button();
		$('#web_search').button();
		$("#tabs").tabs({
			event : "mouseover"
		}
	 
	);
		$('#search').click(function(){
		    $("#result1").html(" ");
			 $.ajax({
                 url: "action.jsp",
                 data: { "KeyWord":$("#keyword").val()},
                 method: "POST",
                 success:function(data){
                	 $('#tabs').css('display','block');
                	
                     $("#result1").html(data);
                     //StepPlus();
                 },
                 error:function(){// StepPlus();}
			 }
             });
			 $("#result2").html(" ");
			 $.ajax({
                 url: "rel.jsp",
                 data: { "KeyWord":$("#keyword").val()},
                 method: "POST",
                 success:function(data){
                     $("#result2").html(data);
                     $('#category_search').css('display','block');
                     $('#search').css('margin-left','80px');
                     $('#category_search').css('margin-left','200px');
                     $('#web_search').css('margin-left','320px');
                     $('#web_search').css('display','block');
                     $('.button').css('font-size','0.8em');
                     $('.button').css('width','80px');
                     
                     
                 },
                 error:function(){// StepPlus();}
			 }
             });
			 $('#custom_search').css('display','none');
			 $('#web').css('display','none');
		});
		$('#category_search').click(function(){
			 $("#custom_search").html(" ");
			 $.ajax({
                 url: "custom.jsp",
                 data: { "KeyWord":$('.rel').attr('rel'),                	                 	                            
                 },
                 method: "POST",
                 success:function(data){
                	 $('#tabs').css('display','none');
                	 $('#web').css('display','none')
                	 $('#custom_search').css('display','block');
                	 $('.button').css('font-size','0.8em');
                	   $('#search').css('margin-left','80px');
                       $('#category_search').css('margin-left','200px');
                       $('#web_search').css('margin-left','320px');
                       $('.button').css('width','70px');
                     $("#custom_search").html(data);
                     //StepPlus();
                 },
                 error:function(){// StepPlus();}
			 }
             });
			
		
			
		});
		
		
		
		$('#web_search').click(function(){
			  $("#web").html(" ");
			 $.ajax({
                 url: "webSearch.jsp",
                 data: { "KeyWord":$("#keyword").val()},
                 method: "POST",
                 success:function(data){
                	 $('#tabs').css('display','none');
                	 $('#custom_search').css('display','none');
                	 $('#web').css('display','block');
                	 $('.button').css('font-size','0.8em');
              	   $('#search').css('margin-left','80px');
                     $('#category_search').css('margin-left','200px');
                     $('#web_search').css('margin-left','320px');
                     $('.button').css('width','70px');
                     $("#web").html(data);
                     //StepPlus();
                 },
                 error:function(){// StepPlus();}
			 }
             });
			
			
		});
		$('#theme').bind('mouseenter',function(){
			$('#hide').slideDown('1500');
			
		});
		
		$('#theme').bind('mouseleave',function(){
			$('#hide').slideUp('1500');
			
		});	
		$('#dad').click(function(){
			$('body').css('background','url("./image/life.jpg")  center center fixed');
			
		});
		$('#normal').click(function(){
			$('body').css('background','url("./image/body.png")  center center fixed');
			
		});
		$('#wood').click(function(){
			$('body').css('background','url("./image/woodpaper.jpg")  center center fixed');
			
		});
		$('#flower').click(function(){
			$('body').css('background','url("./image/flower.jpg")  center center fixed');
			
		});
	});
	
	
	</script>
</head>
<body>
<div id="header">Google+  Search</div>
<div id="theme">Change Theme
<ul id="hide" style="margin-left:-25px;display:none;width:60px;font-color:white">
<li style="display:block"><div id="dad">Life-style</div></li>
<li style="display:block"><div id="normal">Normal</div></li>
<li style="display:block"><div id="wood">Wood</div></li>
<li style="display:block"><div id="flower">Flower</div></li>
</ul>
</div>
<div id="input">
<input id="keyword" type="text" value="Android Oatmeal" size="70" style="height:30px"></input>
<button id="search" class="button" style="width:80px;height:30px" title="search related people activities">Search</button>
<button id="category_search" class="button"style="width:80px;height:30px;display:none" title="Expand result from your custom search engine">CustSearch</button>
<button id="web_search" class="button" style="width:80px;height:30px;display:none" title="search in google web page">WebSearch</button>
</div>
<!-- <table id="result">
<tr>
<td id="result1"></td>
<td id="result2" style="vertical-align:text-top"></td>
</tr>
</table> -->
<div id="tabs" style="width: 90%; margin-left:60px;display: none;">
			<ul>
				<li>
					<a href="#tabs-1"><u>Search By Action</u> </a>
				</li>
				<li>
					<a href="#tabs-2"><u>Search By Relevance</u> </a>
				</li>

			</ul>
			<div id="tabs-1">
				<div id="result1"
					style="margin-left: -20px; margin-top: -20px; margin-right: -20px;">

				</div>
			</div>
			<div id="tabs-2">
				<div id="result2"
					style="vertical-align:text-top;margin-left: -20px; margin-top: -20px; margin-right: -15px;">

				</div>
			</div>


		</div>
<div id="custom_search">



</div>
<div id="web"></div>
</body>
</html>