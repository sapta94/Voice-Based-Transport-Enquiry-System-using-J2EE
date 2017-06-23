<%@page import="com.vtes.model.TrainInfo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Trains available are:</title>
<style>
 body {
  background-image: url(aas.png);

  background-size: cover;
  background-repeat: no-repeat;
 }
</style>
</head>
<body>
	<% ArrayList<TrainInfo> train =(ArrayList<TrainInfo>) request.getAttribute("tlist"); %>
	<h2>Available trains are:</h2>
	
	<table style="position:absolute; right:40%; opacity:1.0;" id="bus" border="2" cellspacing="10px">
  	<tr>
  	  <td>Train No.</td>
  	  <td>Train Name</td>
  	  <td>Arrival</td>
  	  <td>Departure</td>
  	  <td>Fare</td>
  	</tr>
  	<% for(TrainInfo ob: train)  {%>
  	<tr>
  		<td><%=ob.getTrainno() %></td>
  		<td><%=ob.getTrainName() %></td>
  		<td><%=ob.getArrival() %></td>
  		<td><%=ob.getDept() %></td>
  		<td><%=ob.getFare() %>
  	</tr>
  	<% } %>
  </table>
  <script>
  window.onload=function () {
	  	var tab=document.getElementById("bus");
	  	var i=1;
	  	var sp="";
	  	for(i=1;i<tab.rows.length;i++) {
	  	sp="Train number is "+tab.rows[i].cells[0].innerHTML+" . Train is "+tab.rows[i].cells[1].innerHTML+" arrival is at "+tab.rows[i].cells[2].innerHTML+",departs at "+tab.rows[i].cells[3].innerHTML+" and train fare is rupees"+tab.rows[i].cells[4].innerHTML;
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