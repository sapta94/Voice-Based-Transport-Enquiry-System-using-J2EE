package com.vtes.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vtes.model.CabInfo;

import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

/**
 * Servlet implementation class CabFetch
 */
@WebServlet("/CabFetch")
public class CabFetch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CabFetch() {
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
		int i;
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
			
			ArrayList<CabInfo> cablist = new ArrayList<CabInfo>();
			CabInfo cab = null;
			
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
			
			request.setAttribute("clist", cablist);
			
			RequestDispatcher rd = request.getRequestDispatcher("ShowCabs.jsp");
			
			rd.forward(request, response);
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
