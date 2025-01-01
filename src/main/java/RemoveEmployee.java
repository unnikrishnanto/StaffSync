
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RemoveEmployee
 */
public class RemoveEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Connection conn;
    PreparedStatement pst;
    ResultSet resSet;
    
	public void  init() {
		conn = ConnectDB.getConnection();	
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			String query = "SELECT * FROM employee WHERE id =?"; // for fetching data for display
			pst = conn.prepareStatement(query);
			writer = response.getWriter();
			String id = request.getParameter("id");
			System.out.println("ID: " + id);
			pst.setString(1, id);
			resSet = pst.executeQuery();
			if(resSet.next()) {
				String name = resSet.getString("name");
				query = "DELETE FROM employee WHERE id= ?";
				pst = conn.prepareStatement(query);
				pst.setString(1, id);
				int nora = pst.executeUpdate();
				if(nora == 1) {
					writer.println("""
							<!DOCTYPE html>
								<html lang="en">
								<head>
								    <meta charset="UTF-8">
								    <title>Deletion Status</title>
								</head>
								<body style="display: flex; flex-direction: column; align-items: center;">
								   <div id="main-div" style="display: flex; flex-direction: column; align-items: center;">
								        <h1>Employee Deleted</h1>
								        <p>Employee ID: """+
								        id+
								        "</p> <p>Name :"+
								        name+
								        """
								       </p><p>To go back <a href="remove.html"> Click here</a></p>
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
								    <title>Deletion Status</title>
								</head>
								<body style="display: flex; flex-direction: column; align-items: center;">
								   <div id="main-div" style="display: flex; flex-direction: column; align-items: center;">
								        <h1>Deletion Failed</h1>
								        <p>To go back <a href="remove.html"> Click here</a></p>
								   </div>  
								</body>
								</html>
							""");
				}
			} else {
				writer.println("""
						<!DOCTYPE html>
							<html lang="en">
							<head>
							    <meta charset="UTF-8">
							    <title>Deletion Status</title>
							</head>
							<body style="display: flex; flex-direction: column; align-items: center;">
							   <div id="main-div" style="display: flex; flex-direction: column; align-items: center;">
							        <h1>No Employee Data Found</h1>
							        <p>To go back <a href="remove.html"> Click here</a></p> 
							   </div>  
							</body>
							</html>
						""");
			}
			
			
		}catch (Exception e) {
			System.out.println("DELETION FAILED...");
			writer.println("""
					<!DOCTYPE html>
						<html lang="en">
						<head>
						    <meta charset="UTF-8">
						    <title>Deletion Status</title>
						</head>
						<body style="display: flex; flex-direction: column; align-items: center;">
						   <div id="main-div" style="display: flex; flex-direction: column; align-items: center;">
						        <h1>No Employee Data Found</h1>
						        <p>To go back <a href="remove.html"> Click here</a></p> 
						   </div>  
						</body>
						</html>
					""");
			e.printStackTrace();
			
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	public void destroy() {
		if(pst != null) {
			try {
				pst.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(resSet !=null) {
			try {
				resSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ConnectDB.closeConnection();
	}

}
