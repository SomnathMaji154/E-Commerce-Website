/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class OrderDetails extends HttpServlet
{
    public void service(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException
    {
        res.setContentType("text/html");
        PrintWriter pw=res.getWriter();
        String size=req.getParameter("size");
        int quantity=Integer.parseInt(req.getParameter("quantity"));
        HttpSession nam=req.getSession(false);
        String pid=(String)nam.getAttribute("P");
        Connection con=null;
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //registering type4 driver of oracle
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","manager");
            Statement smt=con.createStatement();
            String query="select * from product where pid='"+pid+"';";
            ResultSet rs=smt.executeQuery(query);
            String pname=null,pprice=null;
            while(rs.next())
            {
                pname=rs.getString(2);
                pprice=rs.getString(3);
            }
            int price=Integer.parseInt(pprice);
            int amt=price*quantity;
            pw.println("name:"+pname+", price: "+price+", size: "+size+", quantity: "+quantity+", amount: "+amt);
            nam.setAttribute("pr",price);
            
            nam.setAttribute("sz",size);
            nam.setAttribute("qn",quantity);
            nam.setAttribute("amt",amt);
            nam.setAttribute("name",pname);
            res.sendRedirect("booking.jsp");
            con.close();
        }
        catch(Exception e)
        {
            pw.println(e);
        }
    }
}
