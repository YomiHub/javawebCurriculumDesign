package myServlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.utils.JdbcUtil;

import mybean.data.FindPeople;


public class HandleRegister extends HttpServlet{

	/**
	 * ע��
	 */
	private static final long serialVersionUID = 1L;
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        
        //����user��JavaBeanģ��
        FindPeople userBean = new FindPeople();  
        request.setAttribute("userBean", userBean);
	          
        String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
	    String again_password = request.getParameter("again_password").trim();
		String signature =request.getParameter("signature");
		String userlogimage = "userImg.png";  /* Ĭ��ͷ��*/     
		
		if(username == null) {
			username = "";
		}
		
		if(password == null) {
			password = "";
		}
		
		if(!password.equals(again_password)) {
			userBean.setBacknews("�����������벻һ�£�ע��ʧ��");
			/*ת�����ı��ַ���ҿ��Խ����ݱ��浽request*/
			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/register.jsp");
			dispatcher.forward(request, response);
			return;
		}
		 
	 	Connection connection = null;
		PreparedStatement prepareStatement = null;
		String backnews="";
		try {
			connection = JdbcUtil.getConnection();
			//3.��ȡstatement
			String sql ="INSERT INTO user VALUES(?,?,?,?)";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, username);
			prepareStatement.setString(2, password);
			prepareStatement.setString(3, signature);
			prepareStatement.setString(4, userlogimage);
			//4.ִ��sql
			int m = prepareStatement.executeUpdate();
			
			if(m!=0) {
				backnews = "register succeeded!";
				userBean.setBacknews(backnews);
				userBean.setUserName(username);
				userBean.setSignature(signature);
				userBean.setUserLogImage(userlogimage);
				userBean.setBacknews(backnews);
			}else {
				backnews="ע��ʧ��";
				userBean.setBacknews(backnews);
			}
  
		} catch (Exception e) {
			backnews="�û�Ա�����ѱ�ʹ�ã������������";
			userBean.setBacknews(backnews);
			out.print(e.toString()); 
			e.printStackTrace();
			
		}finally {
			//5.�ͷ���Դ connection prepareStatement
			JdbcUtil.close(connection, prepareStatement, null);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/register.jsp");
			dispatcher.forward(request, response);
		}
	     out.flush();
	     out.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
	  doGet(request, response);
	}
}
