package myServlet.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.utils.JdbcUtil;

import mybean.data.FindInfo;
import mybean.data.Login;
import mybean.data.PageResult;

/**
 * Servlet implementation class QueryMyInfo
 */
@WebServlet("/QueryMyInfo")
public class QueryMyInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String shareListUrl = "/pages/aboutMe.jsp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QueryMyInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Login loginBean = null;
		HttpSession session = request.getSession(true);

		loginBean = (Login) session.getAttribute("loginBean");
		String name = loginBean.getLogname();

		String currentPage = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		String type = request.getParameter("infoType");

		/* PrintWriter out = response.getWriter(); */
		Integer pageSizeInt = 12;
		Integer currentPageInt = 1;
		if (pageSize != null && pageSize.length() > 0) {
			pageSizeInt = Integer.parseInt(pageSize);
		}
		if (currentPage != null && currentPage.length() > 0) {
			currentPageInt = Integer.parseInt(currentPage);
		}

		PageResult pageResult = queryShareByInfoType(currentPageInt, pageSizeInt, type, name);
		request.setAttribute("ShareByInfoTypeResult", pageResult);

		request.getRequestDispatcher(shareListUrl).forward(request, response);
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

	public PageResult queryShareByInfoType(Integer currentPage, Integer pageSize, String infoType, String userName) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rowSet = null;
		PreparedStatement prepareStatement2 = null;
		ResultSet resultSet2 = null;
		PageResult pageResult = null;
		String imgUrl = "http://localhost:8080/iShare/serviceImages/";
		try {
			connection = JdbcUtil.getConnection();
			String sql = "";
			if (Integer.parseInt(infoType) == 7) {
				sql = "select * from (select * from info where username=? order by info_id desc) AS temp4 limit ?,?";
			} else {
				sql = "select * from (select info.* from info left join focus on info.info_id=focus.info_id where focus.username=? order by focus.info_id desc) AS temp5 limit ?,?";
			}

			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, userName);
			prepareStatement.setInt(2, (currentPage - 1) * pageSize);
			prepareStatement.setInt(3, pageSize);

			rowSet = prepareStatement.executeQuery();

			List<FindInfo> findInfoList = new ArrayList<>();
			while (rowSet.next()) {
				int infoId = rowSet.getInt(1); // 内容ID
				String infoTitle = rowSet.getString(2); // 内容标题
				String infoDescribe = rowSet.getString(3); // 内容简述
				/* infoDescribe = imgUrl + infoDescribe; */
				String infoDetail = rowSet.getString(4); // 内容详情
				int type = rowSet.getInt(5); // 类型：0表示日记，1表示趣事
				int support = rowSet.getInt(6); // 点赞数

				String infoAuthor = rowSet.getString(7); // 作者
				String infoPic = rowSet.getString(8); // 作者

				FindInfo findInfo = new FindInfo(infoId, infoTitle, infoDescribe, infoDetail, type, support, infoAuthor,
						imgUrl + infoPic);
				findInfoList.add(findInfo);
			}
			// 查询表中用户总数
			String sql2 = "select * from info where info_type=" + infoType;
			if (Integer.parseInt(infoType) == 7) {
				sql2 = "select * from info where info_type=" + infoType;
			} else {
				sql2 = "select info.* from info left join focus on info.info_id=focus.info_id where focus.username='"
						+ userName + "' order by focus.info_id desc";
			}

			prepareStatement2 = connection.prepareStatement(sql2);
			resultSet2 = prepareStatement2.executeQuery();
			Long totalRecord = 0L;
			while (resultSet2.next()) {
				totalRecord = totalRecord + 1;
			}

			if (Integer.parseInt(infoType) == 7) {
				pageResult = new PageResult(findInfoList, totalRecord, pageSize, currentPage, 7);
			} else {
				pageResult = new PageResult(findInfoList, totalRecord, pageSize, currentPage, 8);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JdbcUtil.close(connection, prepareStatement, rowSet);
			JdbcUtil.close(connection, prepareStatement2, resultSet2);
		}
		return pageResult;
	}

}
