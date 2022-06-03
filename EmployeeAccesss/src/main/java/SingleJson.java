import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import java.io.File;
import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
public class SingleJson extends HttpServlet {
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
			JSONArray jsonr = new JSONArray();
			for(int i=0;i<ar.size();i++) {
				Gson g = new Gson();				String jsonString=g.toJson(ar.get(i));
				jsonr.add(jsonString);
					}
			JSONObject jo = new JSONObject();
			jo.put("data",jsonr);
			
			//System.out.println(jo.toJSONString());
			String formatedS=jo.toJSONString().replace("\\\"", "\"");
			//System.out.println(formatedS);
			String finalS = formatedS.replace("\"{", "{").replace("}\"", "}");
			System.out.println(finalS);
			c.close();
			
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}