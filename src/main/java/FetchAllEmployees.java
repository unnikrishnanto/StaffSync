

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FetchAllEmployees
 */
@WebServlet("/FetchAllEmployees")
public class FetchAllEmployees extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection conn = null;
	@Override
	public void init() throws ServletException {
		conn = ConnectDB.getConnection();
	}					
   
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		String query = "SELECT * FROM employee"; 
		try(
			Statement stm = conn.createStatement();
			ResultSet res = stm.executeQuery(query);				
			PrintWriter write = response.getWriter(); 
			){
			StringBuilder data = new StringBuilder();
			while(res.next()) {
				data.append("<tr>");
				for(int i = 1; i <= 6; i++) {
					data.append("<td>").
					append(res.getString(i)).
					append("</td>");
				}
				data.append("</tr>");
			}
			if(data.isEmpty()) {
				data.append("<tr colspan='6'>").append("<td>No Data Found</td></tr>");
			}
			
			String html = """
				<!DOCTYPE html>
				<html lang="en">
				<head>
				    <meta charset="UTF-8">
				    <meta name="viewport" content="width=device-width, initial-scale=1.0">
				    <title>All Employees</title>
				</head>
				<body style="display: flex; flex-direction: column;">
				    <table border="1">
				        <tr>
				            <th>Employee Id</th>
				            <th>Name</th>
				            <th>Salary</th>
				            <th>Email</th>
				            <th>Phone</th>
				            <th>Address</th>
				        </tr>"""
				        +data+
				    """	
				    </table>
				</body>
				</html>
					""";
			write.println(html);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		ConnectDB.closeConnection();
	}

}
