<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery-latest.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function(){  
    var tds =$("td");  
    tds.click(tdclick);  
});  
  
function tdclick(){  
      var td =$(this);  
      var tdtext =td.text();  
      td.html("");  
      var inputtext =$("<input>");  
      inputtext.attr("value",tdtext);  
  
          inputtext.keyup(function(event){  
             var keycode = event || window.event;  
              var code =keycode.keyCode;  
              if(code == 13){  
                  var inputtext =$(this);  
                  var tdtext =inputtext.val();  
                  var ts =inputtext.parent();  
                  ts.html(tdtext);  
                  ts.click(tdclick);  
              }  
  
          });  
  
      td.append(inputtext);  
      var aa =inputtext.get(0);  
      aa.select();  
      td.unbind();  
  
}; 
	</script>
  </head>
  
  <body>
    <table border="1">  
      <tbody>  
       <tr>  
           <td>123123</td>  
           <td>456456</td>  
       </tr>  
      </tbody>  
  
  </table>  
  </body>
</html>
