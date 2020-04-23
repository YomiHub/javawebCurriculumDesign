package myServlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.utils.JdbcUtil;

import mybean.data.Login;

/**
 * Servlet implementation class AddInfo
 */
@WebServlet("/AddInfo")
public class AddInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	// ���������ַ���
	public String handleString(String s) {
		try {
			byte bb[] = s.getBytes("ISO8859-1");
			s = new String(bb, "UTF-8");
		} catch (Exception e) {
		}

		return s;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		Integer infoId = 0; // ����ID,Ĭ������Ϊ0
		String infoPic = request.getParameter("infoPic");
		String infoTitle = request.getParameter("infoTitle"); // ���ݱ���
		String infoDescribe = request.getParameter("infoDescribe"); // ���ݼ���
		String infoDetail = request.getParameter("infoDetail"); // ��������

		String typeStr = request.getParameter("type"); // ����
		Integer type = 0;

		if (typeStr == null || typeStr == "") {
			typeStr = "0";
		}

		try {
			type = Integer.parseInt(typeStr); // ��������
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		Integer support = 0; // ��������Ĭ������Ϊ0
		String infoAuthor = request.getParameter("infoAuthor"); // ����

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		String backnews = "";
		try {
			connection = JdbcUtil.getConnection();
			// 3.��ȡstatement
			String sql = "INSERT INTO info VALUES(?,?,?,?,?,?,?,?)";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, infoId); // ����
			prepareStatement.setString(2, infoTitle);
			prepareStatement.setString(3, infoDescribe);
			prepareStatement.setString(4, infoDetail);
			prepareStatement.setInt(5, type);
			prepareStatement.setInt(6, support);
			prepareStatement.setString(7, infoAuthor);
			prepareStatement.setString(8, infoPic);
			// 4.ִ��sql
			prepareStatement.execute();
			backnews = "�����ɹ�!";
			out.print(backnews);
			backNew(request, response, backnews);

		} catch (Exception e) {
			e.printStackTrace();
			backnews = "����ʧ�ܣ�" + e;
			out.print(backnews);
			backNew(request, response, backnews);

		} finally {
			// 5.�ͷ���Դ connection prepareStatement
			JdbcUtil.close(connection, prepareStatement, null);
		}
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void backNew(HttpServletRequest request, HttpServletResponse response, String backnews) {
		response.setContentType("text/html;charset=utf-8");
		Login loginBean = null;
		HttpSession session = request.getSession(true);
		try {
			loginBean = (Login) session.getAttribute("loginBean");
			loginBean.setAddInfoBacknews(backnews);
			// �ض���
			response.sendRedirect(request.getContextPath() + "/pages/publishShare.jsp");
		} catch (IOException e) {
			loginBean = (Login) session.getAttribute("loginBean");
			loginBean.setAddInfoBacknews("�׳��쳣" + e.toString());
		}
	}

}
