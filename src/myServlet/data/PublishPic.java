package myServlet.data;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class PublishPic
 */
@WebServlet("/PublishPic")
@MultipartConfig
public class PublishPic extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String imageUrl = "/serviceImages";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublishPic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		request.getParameter("formData");
		
		String mobilePic = up(request,response);  //选择了图片
		
		if(mobilePic!=null&&mobilePic.length()>0) {
			out.print(mobilePic);
		}else {
			out.print("null");
		}
		
	/*	doPost(request,response);*/
	}
	
	public String up(HttpServletRequest req,HttpServletResponse response) throws ServletException, IOException{
		 DiskFileItemFactory factory = new DiskFileItemFactory();  
		 ServletFileUpload upload = new ServletFileUpload(factory);
		 
		/* StringBuilder b = new StringBuilder();
	     try(BufferedReader reader = req.getReader();) {
	          char[]buff = new char[1024];
	          int len;
	          while((len = reader.read(buff)) != -1) {
	              b.append(buff,0, len);
	          }
	     }catch (IOException e) {
	          e.printStackTrace();
	     }
	     b.toString();*/
	     
	      //把文件的中文乱码解决了
	     upload.setHeaderEncoding("utf-8");
		 upload.setFileSizeMax(4 * 1024 * 1024);// 4M
		 upload.setSizeMax(4 * 1024 * 1024);// 6M
	     //设置文件的大小
	     //upload.setSizeMax(20*1024);
	     String  fileName="";
	     try {
	      //[3]获得前台传递过来的所有信息对象
	    	List<FileItem> list = upload.parseRequest(req);
		   
			for (FileItem item : list) {
			//System.out.println(item.getFieldName()+"---"+item.getName()+"---"+item.getSize()+"----"+item.getContentType()+"----"+item.isFormField());	
			if(!item.isFormField()){
			//证明是文件对象
		    //f.l.jpg  文件的后缀
			String sub = item.getName().substring(item.getName().lastIndexOf("."));
				/****[2]保证上传的文件名称不重复***********/
				//利用UUID产生文件的 名称
				String uuid = UUID.randomUUID().toString();
				
				//拼接文件的名称
			    fileName=uuid+sub;
				
				/****[2]上传到指定的服务器目录中***********/ 
				 String realPath = req.getSession().getServletContext().getRealPath(imageUrl);
				 
			   /*****[1]上传的目录是否存在的解决*******/
				File  file=new File(realPath);
				
				if (!file.exists() && !file.isDirectory()) {
					// 鍒涘缓鐩綍
					file.mkdir();
				}
				
				//实现文件的上传 把item中的文件上传到指定的目录中
				item.write(new File(file,fileName));	
				return fileName;
			  }			
		  }
		
		} catch (Exception e) {			
			e.printStackTrace();
		} 
	    return fileName;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Configure a repository (to ensure a secure temp location is used)
		
		PrintWriter out = response.getWriter();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        factory.setRepository(repository);
        
        ServletFileUpload upload = new ServletFileUpload(factory);

        String  fileName="";
	     try {
	      //[3]获得前台传递过来的所有信息对象
	    	List<FileItem> list = upload.parseRequest(request);
		   
			for (FileItem item : list) {
			//System.out.println(item.getFieldName()+"---"+item.getName()+"---"+item.getSize()+"----"+item.getContentType()+"----"+item.isFormField());	
			if(!item.isFormField()){
			//证明是文件对象
		    //f.l.jpg  文件的后缀
			String sub = item.getName().substring(item.getName().lastIndexOf("."));
				/****[2]保证上传的文件名称不重复***********/
				//利用UUID产生文件的 名称
				String uuid = UUID.randomUUID().toString();
				
				//拼接文件的名称
			    fileName=uuid+sub;
				
				/****[2]上传到指定的服务器目录中***********/ 
				 String realPath = request.getSession().getServletContext().getRealPath(imageUrl);
				 
			   /*****[1]上传的目录是否存在的解决*******/
				File  file=new File(realPath);
				
				if (!file.exists() && !file.isDirectory()) {
					// 鍒涘缓鐩綍
					file.mkdir();
				}
				
				//实现文件的上传 把item中的文件上传到指定的目录中
				item.write(new File(file,fileName));	
				out.print(fileName);;
			  }			
		  }
		
		} catch (Exception e) {			
			e.printStackTrace();
			out.print("null");
		} 
	  
	}

}
