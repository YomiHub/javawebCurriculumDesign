package myServlet.data;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import myServlet.databaseImp.InfoDatabaseImpl;
import mybean.data.Login;
import mybean.data.PageResult;

/**
 * Servlet implementation class QueryInfo
 */
@WebServlet("/QueryInfo")
public class QueryInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String indexUrl = "/index.jsp";
	String detailUrl = "/pages/showShare.jsp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QueryInfo() {
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
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String method = request.getParameter("method").trim();
		if (method == null || method.length() == 0) {
			method = "";
		}
		switch (method) {
		case "listAll":
			listAll(request, response);
			break;
		case "listDetail":
			listDetail(request, response);
			break;
		default:
			listAll(request, response);
			break;
		}
	}

	/**
	 * 设置首页所有信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void listAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String currentPage = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		String keyWord = request.getParameter("keyWord");
		String logname;

		Login loginBean = null;
		HttpSession session = request.getSession(true);
		loginBean = (Login) session.getAttribute("loginBean");

		/* PrintWriter out = response.getWriter(); */
		Integer pageSizeInt = 12;
		Integer currentPageInt = 1;
		if (pageSize != null && pageSize.length() > 0) {
			pageSizeInt = Integer.parseInt(pageSize);
		}
		if (currentPage != null && currentPage.length() > 0) {
			currentPageInt = Integer.parseInt(currentPage);
		}

		InfoDatabaseImpl infoService = new InfoDatabaseImpl();
		PageResult bannerResult = infoService.queryBanner(1, 3);
		request.setAttribute("bannerResult", bannerResult); /* 查询轮播内容（ 查询最近20条记录中点赞数排前三的内容 ） */

		PageResult pageResult = infoService.queryShare(currentPageInt, pageSizeInt, keyWord);
		request.setAttribute("pageResult", pageResult);

		PageResult hotFocusResult = infoService.queryByHotFocus(1, 12);
		request.setAttribute("hotFocusResult", hotFocusResult);

		PageResult hotSupportResult = infoService.queryByHotSupport(1, 10);
		request.setAttribute("hotSupportResult", hotSupportResult);

		// 最近收藏
		if (loginBean != null && loginBean.getLogname().length() > 0) {
			logname = loginBean.getLogname();
			PageResult myFocusResult = infoService.queryByMyFocus(1, 10, logname);
			request.setAttribute("myFocusResult", myFocusResult);
		}

		/* out.print(bannerResult); */
		request.getRequestDispatcher(indexUrl).forward(request, response);

	}

	/**
	 * 获取内容详情
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void listDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String currentPage = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		String infoId = request.getParameter("infoId");

		/* PrintWriter out = response.getWriter(); */
		Integer pageSizeInt = 1;
		Integer currentPageInt = 1;
		if (pageSize != null && pageSize.length() > 0) {
			pageSizeInt = Integer.parseInt(pageSize);
		}
		if (currentPage != null && currentPage.length() > 0) {
			currentPageInt = Integer.parseInt(currentPage);
		}

		InfoDatabaseImpl infoService = new InfoDatabaseImpl();
		PageResult myFocusResult = infoService.queryByInfoId(currentPageInt, pageSizeInt, infoId);

		request.setAttribute("infoDetailResult", myFocusResult);

		PageResult commentResult = infoService.queryCommentByInfoId(currentPageInt, pageSizeInt, infoId);
		request.setAttribute("commentResult", commentResult);

		/* out.print(bannerResult); */
		request.getRequestDispatcher(detailUrl).forward(request, response);
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

}
