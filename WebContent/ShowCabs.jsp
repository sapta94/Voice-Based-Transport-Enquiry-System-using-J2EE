<%@page import="com.vtes.model.CabInfo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cabs Found</title>
<style>
 body {
  background-image: url(ub.png);

  background-size: cover;
  background-repeat: no-repeat;
 }
</style>
</head>
<body>
  <%ArrayList<CabInfo> cabs=(ArrayList<CabInfo>)request.getAttribute("clist"); %>
  <h2>Cabs near you:</h2>
   <table style="position:absolute; right:40%;" id="bus" border="2" cellspacing="10px">
  	<tr>
  	  <td>Cab Type</td>
  	  <td>Capacity</td>
  	  <td>Description</td>
  	  <td>Per minute Cost</td>
  	  <td>Per km cost</td>
  	  <td>Cancellation Fare</td>
  	</tr>
  	<% for(CabInfo ob: cabs)  {%>
  	<tr>
  		<td><%=ob.getCabtype() %></td>
  		<td><%=ob.getCpacity() %></td>
  		<td><%=ob.getDesc() %></td>
  		<td><%=ob.getCpm() %></td>
  		<td><%=ob.getCpd() %></td>
  		<td><%=ob.getCf()%></td>
  	
  	</tr>
  	<% } %>
  </table>
  
   <script type="text/javascript">
  
    window.onload=function () {
  	var tab=document.getElementById("bus");
  	var i=1;
  	var sp="";
  	for(i=1;i<tab.rows.length;i++) {
  	sp="Cab is "+tab.rows[i].cells[0].innerHTML+" with capacity "+tab.rows[i].cells[1].innerHTML+" it is the "+tab.rows[i].cells[2].innerHTML+",with cost per minute rupees "+tab.rows[i].cells[3].innerHTML+"and cost per kilometer rupees "+tab.rows[i].cells[4].innerHTML;
  	speak(sp);
  	}
  	initMap();
    };
  	
  	function speak(text, callback) {
  	    var u1 = new SpeechSynthesisUtterance();
  	    u1.text = text;
  	    u1.lang = 'en-US';
  	 
  	    u1.onend = function () {
  	        if (callback) {
  	            callback();
  	        }
  	    };
  	 
  	    u1.onerror = function (e) {
  	        if (callback) {
  	            callback(e);
  	        }
  	    };
  	 
  	    speechSynthesis.speak(u1);
  	} 
  	</script>
</body>
</html>