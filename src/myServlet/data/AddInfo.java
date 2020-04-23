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

	// 处理中文字符串
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

		Integer infoId = 0; // 内容ID,默认设置为0
		String infoPic = request.getParameter("infoPic");
		String infoTitle = request.getParameter("infoTitle"); // 内容标题
		String infoDescribe = request.getParameter("infoDescribe"); // 内容简述
		String infoDetail = request.getParameter("infoDetail"); // 内容详情

		String typeStr = request.getParameter("type"); // 类型
		Integer type = 0;

		if (typeStr == null || typeStr == "") {
			typeStr = "0";
		}

		try {
			type = Integer.parseInt(typeStr); // 内容类型
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		Integer support = 0; // 点赞数，默认设置为0
		String infoAuthor = request.getParameter("infoAuthor"); // 作者

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		String backnews = "";
		try {
			connection = JdbcUtil.getConnection();
			// 3.获取statement
			String sql = "INSERT INTO info VALUES(?,?,?,?,?,?,?,?)";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, infoId); // 自增
			prepareStatement.setString(2, infoTitle);
			prepareStatement.setString(3, infoDescribe);
			prepareStatement.setString(4, infoDetail);
			prepareStatement.setInt(5, type);
			prepareStatement.setInt(6, support);
			prepareStatement.setString(7, infoAuthor);
			prepareStatement.setString(8, infoPic);
			// 4.执行sql
			prepareStatement.execute();
			backnews = "发布成功!";
			out.print(backnews);
			backNew(request, response, backnews);

		} catch (Exception e) {
			e.printStackTrace();
			backnews = "发布失败！" + e;
			out.print(backnews);
			backNew(request, response, backnews);

		} finally {
			// 5.释放资源 connection prepareStatement
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
			// 重定向
			response.sendRedirect(request.getContextPath() + "/pages/publishShare.jsp");
		} catch (IOException e) {
			loginBean = (Login) session.getAttribute("loginBean");
			loginBean.setAddInfoBacknews("抛出异常" + e.toString());
		}
	}

}
