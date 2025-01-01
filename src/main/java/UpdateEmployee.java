

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateEmployee
 */
public class UpdateEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection conn;
	PreparedStatement pst;
	@Override
	public void init() throws ServletException {
		conn = ConnectDB.getConnection();
		String query = "UPDATE employee  SET name=?,salary=?, email=?, phone=?, address=? where id=?";
		try {
			pst = conn.prepareStatement(query);
		} catch (SQLException e) {
			System.out.println("Statement Creation failed");
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter writer = response.getWriter()) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String salary = request.getParameter("salary");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			
			pst.setString(6, id);
			pst.setString(1, name);
			pst.setString(2, salary);
			pst.setString(3, email);
			pst.setString(4, phone);
			pst.setString(5, address);
			
			int nora = pst.executeUpdate();
			if(nora == 1) {
				writer.println("""
						<!DOCTYPE html>
							<html lang="en">
							<head>
							    <meta charset="UTF-8">
							    <title>Updation Status</title>
							</head>
							<body style="display: flex; flex-direction: column; align-items: center;">
							   <div id="main-div" style="display: flex; flex-direction: column; align-items: center;">
							        <h1>Employee Data Updated</h1>
							        <p>To go back <a href="update.html"> Click here</a></p> 
							   </div>  
							</body>
							</html>
						""");
			} else {
				writer.println("""
						<!DOCTYPE html>
							<html lang="en">
							<head>
							    <meta charset="UTF-8">
							    <title>Updation Status</title>
							</head>
							<body style="display: flex; flex-direction: column; align-items: center;">
							   <div id="main-div" style="display: flex; flex-direction: column; align-items: center;">
							        <h1>Updation Failed..</h1>
							        <p>To go back <a href="update.html"> Click here</a></p> 
							   </div>  
							</body>
							</html>
						""");
			}
			
		} catch (Exception e) {
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
		ConnectDB.closeConnection();
	}
}
