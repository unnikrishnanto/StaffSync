

import java.io.IOException;
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
 * Servlet implementation class FetchForUpdate
 */
public class FetchForUpdate extends HttpServlet {
private static final long serialVersionUID = 1L;
	
	Connection conn;
	PreparedStatement pst;
	ResultSet resSet;
	@Override
	public void init() throws ServletException {
		conn = ConnectDB.getConnection();
		String query = "SELECT * FROM employee WHERE id =? ";
		try {
			pst = conn.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Statement Creation failed");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(PrintWriter writer = resp.getWriter()) {
			String id = req.getParameter("id");
			pst.setString(1, id);
			resSet = pst.executeQuery();
			if(resSet.next()) {
				writer.println("""
						<!DOCTYPE html>
							<html lang="en">
							<head>
							    <meta charset="UTF-8">
							    <meta name="viewport" content="width=device-width, initial-scale=1.0">
							    <link rel="stylesheet" href="registration.css">
							    <title>Registration</title>
							</head>
							<body>
							    <div id="main-div">
							        <h1>Edit Data and Click Update</h2>
							        <div id="form-div">
							            <form action="UpdateEmployee" method="post" >
							                <label for="id">Employee ID:</label>
							                <input type="text" name="id" """+
							                "value='"+ id +"' readonly><br> "+
							                "<label for='name'>Name:</label>"+
							                "<input type='text' name='name' value='"+
							                resSet.getString("name") +"'><br>"+
							                "<label for='salary'>Salary:</label>"+
							                "<input type='number' name='salary' value='"+
							                resSet.getString("salary") +"'><br>"+
							                "<label for='email'>Email :</label>"+
							                "<input type='email' name='email' value='"+
							                resSet.getString("email") +"'><br>"+
							                "<label for='phone'>Phone :</label>"+
							                "<input type='tel' name='phone' value='"+
							                resSet.getString("phone") +"'><br> "+
							                "<input type='text' name='address' value='"+
							                resSet.getString("address") +"'><br> "+
							                """
							                <br>
							                <input type="submit" value="Update">
							            </form>        
							        </div>
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
							        <h1>No Employee Data Found</h1>
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
