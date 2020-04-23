package myServlet.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * Servlet implementation class AddComment
 */
@WebServlet("/AddComment")
public class AddComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComment() {
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
        
        Integer commentId =0;    //内容ID,默认设置为0
        String infoIdStr = request.getParameter("infoId");   //内容标题
        String commentUser =request.getParameter("username");   //内容简述
        String commentDetail = request.getParameter("commentDetail");   //内容详情

        if(infoIdStr == null||infoIdStr == "") {
        	infoIdStr = "";
		}
        
        if(commentUser == null||commentUser == "") {
        	commentUser = "";
		}
        
        if(commentDetail == null||commentDetail == "") {
        	commentDetail = "";
		}
      
        
        Boolean m = infoIdStr.length()>0&&commentUser.length()>0&&commentDetail.length()>0;
        
        int infoId = 0;
        try {
        	infoId = Integer.parseInt(infoIdStr);    //内容类型
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }       
		 
	 	Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = JdbcUtil.getConnection();
			//3.获取statement
			String sql ="INSERT INTO comment VALUES(?,?,?,?)";
			prepareStatement = connection.prepareStatement(sql);
			
			if(m) {
				prepareStatement.setInt(1, commentId);  //自增
				prepareStatement.setString(2, commentUser);
				prepareStatement.setInt(3, infoId);
				prepareStatement.setString(4, commentDetail);
				
				//4.执行sql
				prepareStatement.execute();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("addOk", true);
		
				JSONObject json = new JSONObject();
				for(Map.Entry<String, Object> entry:map.entrySet()) {
					json.put(entry.getKey(), entry.getValue());
				}
		        out.print(json);
				
			}else {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("addOk", false);
		
				JSONObject json = new JSONObject();
				for(Map.Entry<String, Object> entry:map.entrySet()) {
					json.put(entry.getKey(), entry.getValue());
				}
				
		        out.print(json);
			}
			
  
		} catch (Exception e) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("addOk", false);
	
			JSONObject json = new JSONObject();
			for(Map.Entry<String, Object> entry:map.entrySet()) {
				json.put(entry.getKey(), entry.getValue());
			}
			
	        out.print(json);
			
		}finally {
			//5.释放资源 connection prepareStatement
			JdbcUtil.close(connection, prepareStatement, null);
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
