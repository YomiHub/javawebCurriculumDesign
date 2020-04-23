package myServlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.utils.JdbcUtil;


/**
 * Servlet implementation class AddSupport
 */
@WebServlet("/AddSupport")
public class AddSupport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddSupport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
       
		String infoId = request.getParameter("infoId").toString().trim();
		
		if(infoId == null) {
			infoId = "";
		}
		
	 	Connection connection = null;
		PreparedStatement prepareStatement = null;
		PreparedStatement prepareStatement2 = null;
		ResultSet rs = null;
		ResultSet rs2 =null;
		
		String backnews="";
		boolean boo = false;
		
		boo = infoId.length()>0;
		
		try {
			connection = JdbcUtil.getConnection();
		
			//3.��ȡstatement
			String sql ="select * from info where info_id=?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, infoId);
			
			if(boo) {
    			//4.ִ��sql
    			rs = prepareStatement.executeQuery();
    			boolean m = rs.next();
    			int support = rs.getInt(6);
    			support = support+1;
    			
    			
    			if(m==true) {
    				//��ѯ�ɹ�
    				String changeSql = "update info set info_support=? where info_id=?";
    				prepareStatement2 = connection.prepareStatement(changeSql);
    				prepareStatement2.setInt(1, support);
    				prepareStatement2.setString(2, infoId);
    				
    			    prepareStatement2.execute();
					out.println("addOk");  
    				
    				
    			}else {
    				
					out.println("addFalse"); 
    			}
			}else {
				
				out.println("addFalse");  //����json
			}
			out.print(backnews.toString());
		} catch (Exception e) {
			backnews="�޸�ʧ��"+e.toString();
			out.print(backnews); 
			e.printStackTrace();
		
		}finally {
			//5.�ͷ���Դ connection prepareStatement
			JdbcUtil.close(connection, prepareStatement, rs);
			JdbcUtil.close(connection, prepareStatement2, rs2);
		}
	     out.flush();
	     out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
