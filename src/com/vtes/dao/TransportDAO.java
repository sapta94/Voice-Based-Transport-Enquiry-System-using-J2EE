package com.vtes.dao;

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vtes.model.BusInfo;
import com.vtes.model.CabInfo;
import com.vtes.model.TrainInfo;

public class TransportDAO {
	
	public ArrayList<BusInfo> fetch(String s,String d)
	{
		ArrayList<BusInfo> blist=new ArrayList<BusInfo>();
		BusInfo b=null;
	    String sr=s.replaceAll(" ","%20");
	    String des=d.replaceAll(" ","%20");
	    System.out.println(s+" "+d);
		String uri="https://voiceenquiry.herokuapp.com/getdata/"+sr+"/"+des;
		System.out.println(uri);
		
		
		Client client=Client.create();
		WebResource webresource = client.resource(uri);
		ClientResponse resp = webresource.get(ClientResponse.class);
		
		if(resp.getStatus()==200)
		{
			String op=resp.getEntity(String.class);
			Gson g = new Gson();
			JsonElement element = g.fromJson(op, JsonElement.class);
			JsonObject jOb= element.getAsJsonObject();
			
			int i;
			int fares=0;
			double dst=getdistance(s, d);
			int t=gettime(s, d);
			JsonArray jarray = jOb.getAsJsonArray("results");
			for(i=0;i<jarray.size();i++)
			{
				jOb=jarray.get(i).getAsJsonObject();
				b = new BusInfo();
				b.setSource(s);
				b.setDest(d);
				b.setBusno(jOb.get("Busnum").toString());
				b.setDistance(dst);
				b.setTime(t);
				
				String type=jOb.get("Type").toString();
				type=type.substring(1,type.length()-1);
				b.setType(type);
				if(type.equals("Mini") && ( dst > 0 && dst<=3))
					fares=7;
					else if(type.equals("Mini") && ( dst > 3 && dst<=6))
					fares=8;
					else if(type.equals("Mini") && ( dst > 6 && dst<=10))
					fares=9;
					else if(type.equals("Mini") && dst > 10 )
					fares=10;
					else if(type.equals("Private") && ( dst > 0 && dst<=3))
					fares=6;
					else if(type.equals("Private") && ( dst > 3 && dst<=12))
					fares=8;
					else if(type.equals("Private") && dst > 12)
					fares=9;
					else if(type.equals("State") && ( dst > 0 && dst<=3))
					fares=8;
					else if(type.equals("State") && ( dst > 3 && dst<=12))
					fares=10;
					else if(type.equals("State") && dst > 12)
					fares=15;
					else
						System.out.println("None matched with  typt="+type+"and distance ="+dst);
				 
					  
				b.setFare(fares);
				
				blist.add(b);
			}
			
		}
		
		
		return blist;
		
	}
	
	public ArrayList<CabInfo> cabfetch(String s){
		ArrayList<CabInfo> cablist = new ArrayList<CabInfo>();
		CabInfo cab = null;
		Client client=Client.create();
		WebResource webresource = client.resource("https://api.uber.com/v1/products?latitude=22.5834126&longitude=88.3407137");
		ClientResponse resp = webresource.header("Authorization","Token yArQcPIIxaWXJ0-mLVkf1vUpne_RHUS8zoQ51PlF").get(ClientResponse.class);
		if(resp.getStatus()==200)
		{
			String op=resp.getEntity(String.class);
			Gson g = new Gson();
			JsonElement element = g.fromJson(op, JsonElement.class);
			JsonObject jOb= element.getAsJsonObject();
			System.out.println("Data retrived is: "+jOb);
			
			int i;
			JsonArray jarray = jOb.getAsJsonArray("products");
			for(i=0;i<jarray.size();i++)
			{
				jOb = jarray.get(i).getAsJsonObject();
				cab = new CabInfo();
				cab.setCabtype(jOb.get("display_name").toString());
				cab.setDesc(jOb.get("description").toString());
				cab.setCpacity(jOb.get("capacity").toString());
				
				JsonObject job1=jOb.getAsJsonObject("price_details");
				cab.setCpd(job1.get("cost_per_distance").toString());
				cab.setCpm(job1.get("cost_per_minute").toString());
				cab.setCf(job1.get("cancellation_fee").toString());
				
				cablist.add(cab);
			}
			
		}  
		else
			System.out.println("Error in api calling");
		return cablist;
	}
	
	public ArrayList<TrainInfo> trainfetch(String s,String d)
	{
		ArrayList<TrainInfo> tlist = new ArrayList<TrainInfo>();
		TrainInfo td=null;
		String sr=s.replaceAll(" ","%20");
	    String des=d.replaceAll(" ","%20");
		String uri="http://aquaman.herokuapp.com/gettrains/"+sr+"/"+des;
		
		Client client=Client.create();
		WebResource webresource = client.resource(uri);
		ClientResponse resp = webresource.get(ClientResponse.class);
		if(resp.getStatus()==200)
		{
			String op=resp.getEntity(String.class);
			Gson g = new Gson();
			JsonElement element = g.fromJson(op, JsonElement.class);
			JsonObject jOb= element.getAsJsonObject();
			System.out.println("Data retrived is: "+jOb);
			
			int i,fare;
			double dist;
			JsonArray jarray = jOb.getAsJsonArray("railgaris");
			for(i=0;i<jarray.size();i++)
			{
				jOb = jarray.get(i).getAsJsonObject();
				td= new TrainInfo();
				td.setArrival(jOb.get("Arrival").toString());
				td.setDept(jOb.get("Departure").toString());
				td.setTrainno(jOb.get("TrainNo").toString());
				td.setTrainName(jOb.get("TrainName").toString());
				
				dist=getdistance(s,d);
				fare=(int) ((dist/20)+1)*5;
				
				td.setFare(fare);
				
				tlist.add(td);
			}
	}
		return tlist;
 }
	public double getdistance(String s, String d)
	{
		s=s+",India";
		 d=d+",India";
		String uri="https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+s+"&destinations="+d+"&key=AIzaSyADXLArBImn2497G6GPpuH34wFGXDBQJww";
		
		Client client=Client.create();
		WebResource webresource = client.resource(uri);
		ClientResponse resp = webresource.get(ClientResponse.class);
		String dist = null;
		double f = 0;
		if(resp.getStatus()== 200)
		{
			String op=resp.getEntity(String.class);
			Gson g = new Gson();
			JsonElement element = g.fromJson(op, JsonElement.class);
			JsonObject jOb= element.getAsJsonObject();
			
			JsonArray jarray = jOb.getAsJsonArray("rows");
			JsonObject job1 = jarray.get(0).getAsJsonObject();
			JsonArray jarr = job1.getAsJsonArray("elements");
			
			job1 = jarr.get(0).getAsJsonObject();
			job1=job1.getAsJsonObject("distance");
			dist = job1.get("text").toString();
			dist = dist.substring(1,4);
			f = Double.parseDouble(dist);
			f=f/0.6214;
		}
		return f;
	}
	
	public int gettime(String s , String d)
	{
		s=s+",India";
		 d=d+",India";
		String uri="https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+s+"&destinations="+d+"&key=AIzaSyADXLArBImn2497G6GPpuH34wFGXDBQJww";
		
		Client client=Client.create();
		WebResource webresource = client.resource(uri);
		ClientResponse resp = webresource.get(ClientResponse.class);
		String dist = null;
		int f = 0;
		if(resp.getStatus()== 200)
		{
			String op=resp.getEntity(String.class);
			Gson g = new Gson();
			JsonElement element = g.fromJson(op, JsonElement.class);
			JsonObject jOb= element.getAsJsonObject();
			
			JsonArray jarray = jOb.getAsJsonArray("rows");
			JsonObject job1 = jarray.get(0).getAsJsonObject();
			JsonArray jarr = job1.getAsJsonArray("elements");
			
			job1 = jarr.get(0).getAsJsonObject();
			job1=job1.getAsJsonObject("duration");
			dist = job1.get("text").toString();
			dist = dist.substring(1,3);
			f = Integer.parseInt(dist);
			
		}
		return f;
	}
	
}

