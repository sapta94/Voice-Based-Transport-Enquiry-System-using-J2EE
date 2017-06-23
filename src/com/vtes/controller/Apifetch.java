package com.vtes.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.dmr.JSONParser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Servlet implementation class Apifetch
 */
@WebServlet("/Apifetch")
public class Apifetch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Apifetch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s=request.getParameter("source");
		String d=request.getParameter("dest");
		String t=request.getParameter("mode");
		
		Client client=Client.create();
		String uri ="https://voiceenquiry.herokuapp.com/getdata/"+s+"/"+d;
		WebResource webresource = client.resource(uri);
		ClientResponse resp = webresource.get(ClientResponse.class);
		if(resp.getStatus()==200)
		{
			String op=resp.getEntity(String.class);
			Gson g = new Gson();
			JsonElement element = g.fromJson(op, JsonElement.class);
			JsonObject jOb= element.getAsJsonObject();
			System.out.println("Data retrived is: "+jOb);
			
			JsonArray jarray = jOb.getAsJsonArray("results");
			jOb = jarray.get(0).getAsJsonObject();
			String res = jOb.get("Busnum").toString()+" "+jOb.get("Type").toString();
			
			System.out.println(res);
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			out.println("<html><p></p></html>");
		}
		else
			System.out.println("Error in api calling");
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
