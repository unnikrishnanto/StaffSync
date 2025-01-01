

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FetchEmployeeById
 */
public class FetchEmployeeById extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	Connection conn;
	PreparedStatement pst = null;
	ResultSet resSet = null;
	
	@Override
	public void init() throws ServletException {
		conn = ConnectDB.getConnection();
		String query = "SELECT * FROM employee WHERE id =?";
		try {
			pst = conn.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Statement creation failed...");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Executing doPost");
		try(PrintWriter writer = response.getWriter()){
			String empId = request.getParameter("id");
			pst.setString(1, empId);
			resSet = pst.executeQuery();
			StringBuilder data = new StringBuilder();
			
			if(resSet.next()) {
				data.append("<p>Employee ID: ").
					 append(resSet.getString(1)).
					 append("</p>"). 
					 append("<p>Name: ").
					 append(resSet.getString(2)).
					 append("</p>"). 
					 append("<p>Salary: ").
					 append(resSet.getString(3)).
					 append("</p>"). 
					 append("<p>Email: ").
					 append(resSet.getString(4)).
					 append("</p>"). 
					 append("<p>Address: ").
					 append(resSet.getString(5)).
					 append("</p>");
			} else {
				data.append("<p>No Such Employee..</p>");			}
			String html ="""
				<!DOCTYPE html>
				<html lang="en">
				<head>
				    <meta charset="UTF-8">
				    <meta name="viewport" content="width=device-width, initial-scale=1.0">
				    <title>All Employees</title>
				</head>
				<body style="display: flex; flex-direction: column; align-items: center;">
				   <div id="main-div">
				        <h1>Employee Details</h1>
				        <div id="details-div" style="display: flex; flex-direction: column; align-items: center;">
				        
					"""+
					data +
					"""
				        </div>
				   </div>  
				</body>
				</html>
					""";
			writer.print(html);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy() {
		if(pst != null) {
			try {
				pst.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(resSet != null) {
			try {
				resSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ConnectDB.closeConnection();
	} 

}
