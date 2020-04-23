package myServlet.data;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UploadEssayPicController
 */
@WebServlet("/UploadEssayPic")
public class UploadEssayPic extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String serverPath = "http://localhost:8080/iShare/essayImages/";
	String imageUrl = "/essayImages";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadEssayPic() {
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
		PrintWriter out = response.getWriter();

		Map<String, Object> result = up(request, response); // ѡ����ͼƬ
		JSONObject json = new JSONObject();
		for (Map.Entry<String, Object> entry : result.entrySet()) {
			json.put(entry.getKey(), entry.getValue());
		}

		out.print(json);

		out.flush();
		out.close();
	}

	public Map<String, Object> up(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		Map<String, Object> result = new HashMap<String, Object>();
		// ���ļ���������������
		upload.setHeaderEncoding("utf-8");
		upload.setFileSizeMax(4 * 1024 * 1024);// 4M
		upload.setSizeMax(4 * 1024 * 1024);// 6M
		// �����ļ��Ĵ�С
		// upload.setSizeMax(20*1024);
		String fileName = "";
		try {
			// [3]���ǰ̨���ݹ�����������Ϣ����
			List<FileItem> list = upload.parseRequest(req);

			for (FileItem item : list) {
				// System.out.println(item.getFieldName()+"---"+item.getName()+"---"+item.getSize()+"----"+item.getContentType()+"----"+item.isFormField());
				if (!item.isFormField()) {
					// ֤�����ļ�����
					// f.l.jpg �ļ��ĺ�׺
					String sub = item.getName().substring(item.getName().lastIndexOf("."));
					/**** [2]��֤�ϴ����ļ����Ʋ��ظ� ***********/
					// ����UUID�����ļ��� ����
					String uuid = UUID.randomUUID().toString();

					// ƴ���ļ�������
					fileName = uuid + sub;

					/**** [2]�ϴ���ָ���ķ�����Ŀ¼�� ***********/
					String realPath = req.getSession().getServletContext().getRealPath(imageUrl);

					/***** [1]�ϴ���Ŀ¼�Ƿ���ڵĽ�� *******/
					File file = new File(realPath);

					if (!file.exists() && !file.isDirectory()) {

						file.mkdir();
					}

					// ʵ���ļ����ϴ� ��item�е��ļ��ϴ���ָ����Ŀ¼��
					item.write(new File(file, fileName));

					String[] str = new String[] { serverPath + fileName };

					result.put("errno", 0);
					result.put("data", str);

					return result;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
