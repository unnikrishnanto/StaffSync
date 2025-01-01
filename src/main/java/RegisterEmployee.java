
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegisterEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Connection conn;
    PreparedStatement pst;
	@Override
		public void init() throws ServletException {
			conn = ConnectDB.getConnection();
			String url = "INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?)";
			try {
				pst = conn.prepareStatement(url);
			} catch (SQLException e) {
				System.out.println("Statement Creation Failed...");
				e.printStackTrace();
			}
		}
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Executing post...on RegisterEmployee ");
		PrintWriter writer =null;
		try {
			   writer = response.getWriter();
			   String id = request.getParameter("id");
			   String name = request.getParameter("name");
			   String salary = request.getParameter("salary");
			   String email = request.getParameter("email");
			   String phone = request.getParameter("phone");
			   String address = request.getParameter("address");
			   
			   pst.setString(1, id);
			   pst.setString(2, name);
			   pst.setString(3, salary);
			   pst.setString(4, email);
			   pst.setString(5, phone);
			   pst.setString(6, address);
			   response.setContentType("text/html");
			   int nora = pst.executeUpdate();
			   if (nora == 1) {
		            writer.println("""
		                <script>
		                    alert('Employee registered successfully!');
		                    window.location.href = 'registration.html'; // Redirect to the form page
		                </script>
		            """);
		        } else {
		            writer.println("""
		                <script>
		                    alert('Failed to register employee. Please try again.');
		                    window.location.href = 'registration.html'; // Redirect to the form page
		                </script>
		            """);
		        }
		}catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Integrity Vioaltion");
			writer.println("""
		                <script>
		                    alert('Employee With ID Already Exists.');
		                    window.location.href = 'registration.html'; // Redirect to the form page
		                </script>
		            """);
		} catch (Exception e) {
			e.printStackTrace();
			writer.println("""
		                <script>
		                    alert('Failed to register employee.');
		                    window.location.href = 'registration.html'; // Redirect to the form page
		                </script>
		            """);
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
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
