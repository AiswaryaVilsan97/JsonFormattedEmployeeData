import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
public class ConvertEmployees extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		//PrintWriter pw = response.getWriter();
		Connection c = null;
		Statement s = null;
		ResultSet r = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "Current-Root-Password");
			s = c.createStatement();
			r = s.executeQuery("select * from employee"); 
			
			ArrayList<EmployeeData>ar = new ArrayList<EmployeeData>();
			
			while (r.next()) {
				//pw.print("<br>" + r.getLong("empl_id") + "," + r.getLong("id") + ": :" + r.getString("empl_name")+ ": :" + r.getLong("phone_number") + ": :" + r.getString("place") + ": :"+ r.getString("role"));
				long empl_id=r.getLong("empl_id");
				long id=r.getLong("id") ;
				String empl_name =r.getString("empl_name");
				long phone_number=r.getLong("phone_number");
				String place=r.getString("place");
				String role=r.getString("role");
				
				EmployeeData ed = new EmployeeData();
				
				ed.setEmpl_id(empl_id);
				ed.setId(id);
				ed.setEmpl_name(empl_name);
				ed.setPhone_number(phone_number);
				ed.setPlace(place);
				ed.setRole(role);
				
				ar.add(ed);
			}
			for(int i=0;i<ar.size();i++) {
					File jsonfile = new File ("C:\\Users\\91965\\eclipse-workspace\\EmployeeAccesss\\employeeinformation"+i+".json");
				    ObjectMapper om =new ObjectMapper();
				    om.writeValue(jsonfile,ar.get(i));
				   }
			 	c.close();
			    System.out.println("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}