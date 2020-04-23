package myServlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.utils.JdbcUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class DeleteComment
 */
@WebServlet("/DeleteComment")
public class DeleteComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String infoId = request.getParameter("infoId").toString().trim();
		String commentId = request.getParameter("commentId").toString().trim();
		
		if(commentId == null||commentId == "") {
			return;
		}
	
		
	 	Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rs = null;

		boolean boo = false;
		
		boo = commentId.length()>0;
		
		try {
			connection = JdbcUtil.getConnection();
		
			//3.获取statement
			String sql ="delete from comment where comment_id=?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, commentId);
			
			if(boo) {
    			//4.执行sql
    			prepareStatement.execute();
    			   
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("removeOk", true);
		
				JSONObject json = new JSONObject();
				for(Map.Entry<String, Object> entry:map.entrySet()) {
					json.put(entry.getKey(), entry.getValue());
				}
				
		        out.print(json);
		        //重定向到首页
	    		response.sendRedirect(request.getContextPath()+"/servlet/QueryInfo?method=listDetail&infoId="+infoId);
    		
			}else {
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("removeOk", false);
				JSONObject json = new JSONObject();
				for(Map.Entry<String, Object> entry:map.entrySet()) {
					json.put(entry.getKey(), entry.getValue());
				}
				
		        out.print(json);	
			}
			
		} catch (Exception e) {

		
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("removeOk", false);
	
			JSONObject json = new JSONObject();
			for(Map.Entry<String, Object> entry:map.entrySet()) {
				json.put(entry.getKey(), entry.getValue());
			}
			
	        out.print(json);	
		
		}finally {
			//5.释放资源 connection prepareStatement
			JdbcUtil.close(connection, prepareStatement, rs);
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
