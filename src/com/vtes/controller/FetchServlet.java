package com.vtes.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.mail.Transport;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vtes.dao.TransportDAO;
import com.vtes.model.BusInfo;
import com.vtes.model.CabInfo;
import com.vtes.model.TrainInfo;

/**
 * Servlet implementation class FetchServlet
 */
@WebServlet("/FetchServlet")
public class FetchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchServlet() {
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
		
		if(t.equals("bus"))
		{
			TransportDAO td=new TransportDAO();
			ArrayList<BusInfo> blist=td.fetch(s, d);
			if(blist!=null)
			{
				request.setAttribute("clist", blist);
				
				RequestDispatcher rd = request.getRequestDispatcher("ShowDetails.jsp");
				
				rd.forward(request, response);
			}
			else
			{
				response.setContentType("text/html");
				PrintWriter out=response.getWriter();
				out.println("<html><script>alert('No such Transport available');</script></html>");
				out.println("<html><script>window.location='newTest.html'</script></html>");
			}
		}
		
		if(t.equals("train"))
		{
			TransportDAO td = new TransportDAO();
			ArrayList<TrainInfo> tlist = td.trainfetch(s, d);
			if(tlist!=null)
			{
				request.setAttribute("tlist", tlist);
				RequestDispatcher rd = request.getRequestDispatcher("ShowTrains.jsp");
				rd.forward(request, response);
			}
			else
			{
				response.setContentType("text/html");
				PrintWriter out=response.getWriter();
				out.println("<html><script>alert('No such Transport available');</script></html>");
				out.println("<html><script>window.location='newTest.html'</script></html>");
			}
		}
		
		if(t.equals("cab"))
		{
			TransportDAO td=new TransportDAO();
			ArrayList<CabInfo> cab = td.cabfetch(s);
			double dis=td.getdistance(s, d);
			System.out.println("distance is "+dis);
			if(cab!=null)
			{
				request.setAttribute("clist", cab);
				RequestDispatcher rd = request.getRequestDispatcher("ShowCabs.jsp");
				rd.forward(request, response);
			}
			else
			{
				response.setContentType("text/html");
				PrintWriter out=response.getWriter();
				out.println("<html><script>alert('No such Transport available');</script></html>");
				out.println("<html><script>window.location='newTest.html'</script></html>");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
