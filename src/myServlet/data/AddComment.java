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
        
        Integer commentId =0;    //����ID,Ĭ������Ϊ0
        String infoIdStr = request.getParameter("infoId");   //���ݱ���
        String commentUser =request.getParameter("username");   //���ݼ���
        String commentDetail = request.getParameter("commentDetail");   //��������

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
        	infoId = Integer.parseInt(infoIdStr);    //��������
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }       
		 
	 	Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = JdbcUtil.getConnection();
			//3.��ȡstatement
			String sql ="INSERT INTO comment VALUES(?,?,?,?)";
			prepareStatement = connection.prepareStatement(sql);
			
			if(m) {
				prepareStatement.setInt(1, commentId);  //����
				prepareStatement.setString(2, commentUser);
				prepareStatement.setInt(3, infoId);
				prepareStatement.setString(4, commentDetail);
				
				//4.ִ��sql
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
			//5.�ͷ���Դ connection prepareStatement
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
