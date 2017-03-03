<%@page import="com.vtes.model.BusInfo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Here are the details</title>
</head>
<body>
<% ArrayList<BusInfo> blist=(ArrayList<BusInfo>)request.getAttribute("clist"); %>
  <% BusInfo b=blist.get(1); %>
  <h2>Bus Details From <%=b.getSource() %> to <%=b.getDest()%> :</h2>
  <table style="position:absolute; right:40%;" id="bus" border="2" cellspacing="10px">
  	<tr>
  	  <td>Bus No.</td>
  	  <td>From</td>
  	  <td>To</td>
  	  <td>Distance(in kms)</td>
  	  <td>Time(in mins)</td>
  	  <td>Fare</td>
  	</tr>
  	<% for(BusInfo ob: blist)  {%>
  	<tr>
  		<td><%=ob.getBusno() %></td>
  		<td><%=ob.getSource() %></td>
  		<td><%=ob.getDest() %></td>
  		<td><%=ob.getDistance() %></td>
  		<td><%=ob.getTime() %></td>
  		<td><%=ob.getFare() %></td>
  	
  	</tr>
  	<% } %>
  </table>
  
  <script type="text/javascript">
  
    window.onload=function () {
  	var tab=document.getElementById("bus");
  	var i=1;
  	var sp="";
  	for(i=1;i<tab.rows.length;i++) {
  	sp="Bus number is "+tab.rows[i].cells[0].innerHTML+" from "+tab.rows[i].cells[1].innerHTML+" to "+tab.rows[i].cells[2].innerHTML+",time taken is "+tab.rows[i].cells[4].innerHTML+"minutes and fare is rupees "+tab.rows[i].cells[5].innerHTML;
  	speak(sp);
  	}
  	
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