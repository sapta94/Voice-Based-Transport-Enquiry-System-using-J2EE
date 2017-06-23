<%@page import="com.vtes.model.BusInfo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Here are the details</title>
<style>
#map {
        height: 50%;
      }
 body {
  background-image: url(bg1.jpg);
  
  background-size: cover;
  background-repeat: no-repeat;
 }
</style>
</head>
<body>
<% ArrayList<BusInfo> blist=(ArrayList<BusInfo>)request.getAttribute("clist"); %>
   <% BusInfo b=blist.get(0); %>
  <h2>Bus Details From <%=b.getSource() %> to <%=b.getDest()%> :</h2>
  <input type="text" id="src" hidden value=<%=b.getSource() %>>
   <input type="text" id="dest" hidden value=<%=b.getDest() %>>
  <table style="position:absolute; right:40%;" id="bus" border="2" cellspacing="10px">
  	<tr>
  	  <td>Bus No.</td>
  	  <td>From</td>
  	  <td>To</td>
  	  <td>Distance(in kms)</td>
  	  <td>Time(in mins)</td>
  	  <td>Fare</td>
  	  <td>Type</td>
  	</tr>
  	<% for(BusInfo ob: blist)  {%>
  	<tr>
  		<td><%=ob.getBusno() %></td>
  		<td><%=ob.getSource() %></td>
  		<td><%=ob.getDest() %></td>
  		<td><%=ob.getDistance() %></td>
  		<td><%=ob.getTime() %></td>
  		<td><%=ob.getFare() %></td>
  	    <td><%=ob.getType() %></td>
  	</tr>
  	<% } %>
  </table>
  <div id="map"></div>
  <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD6DVVsOQbq6nL9S2aFHy_lptCX4xsa5jg&callback">
    </script>
  <script type="text/javascript">
  
    window.onload=function () {
  	var tab=document.getElementById("bus");
  	var i=1;
  	var sp="";
  	for(i=1;i<tab.rows.length;i++) {
  	sp="Bus number is "+tab.rows[i].cells[0].innerHTML+" from "+tab.rows[i].cells[1].innerHTML+" to "+tab.rows[i].cells[2].innerHTML+",time taken is "+tab.rows[i].cells[4].innerHTML+"minutes and fare is rupees "+tab.rows[i].cells[5].innerHTML;
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
  	
  	function initMap() {
        var directionsService = new google.maps.DirectionsService;
        var directionsDisplay = new google.maps.DirectionsRenderer;
        var myLatLng = {lat: 22.5851477, lng: 88.3446166};
        alert('plot success');
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 14,
          center: myLatLng
        });
            directionsDisplay.setMap(map);
             
        /*var marker = new google.maps.Marker({
          position: myLatLng,
          map: map,
          title: 'Hello World!'
        });*/
		   calculateAndDisplayRoute(directionsService, directionsDisplay);
      }

      function calculateAndDisplayRoute(directionsService, directionsDisplay) {
        directionsService.route({
          origin:  '22.6197799,88.3454714',
          destination: 'howrah',
          travelMode: 'DRIVING'
        }, function(response, status) {
          if (status === 'OK') {
        	  
            directionsDisplay.setDirections(response);
            alert('ok');
          } else {
            window.alert('Directions request failed due to ' + status);
          }
        });
      }
  	
  </script>
    
</body>
</html>