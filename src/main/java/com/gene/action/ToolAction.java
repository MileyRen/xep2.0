package com.gene.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.gene.utils.*;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import developtool.Reg;

//wo tian jia

//鏂囦欢鍖�
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
//宸ュ叿鍖�
import java.util.Iterator;
import java.util.List;
//dom4j鍖�
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

//wo tian jia

public class ToolAction extends ActionSupport implements ModelDriven<Tool> {
	private Tool tool = new Tool();
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();

	public Tool getTool() {
		return tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}
	
//
	
	private String username;
    
	//娉ㄦ剰锛宖ile骞朵笉鏄寚鍓嶇jsp涓婁紶杩囨潵鐨勬枃浠舵湰韬紝鑰屾槸鏂囦欢涓婁紶杩囨潵瀛樻斁鍦ㄤ复鏃舵枃浠跺す涓嬮潰鐨勬枃浠�
	    private File file;
	    
	    //鎻愪氦杩囨潵鐨刦ile鐨勫悕瀛�
	    private String fileFileName;
	    
	    //鎻愪氦杩囨潵鐨刦ile鐨凪IME绫诲瀷
	    private String fileContentType;

	    public String getUsername()
	    {
	        return username;
	    }

	    public void setUsername(String username)
	    {
	        this.username = username;
	    }

	    public File getFile()
	    {
	        return file;
	    }

	    public void setFile(File file)
	    {
	        this.file = file;
	    }

	    public String getFileFileName()
	    {
	        return fileFileName;
	    }

	    public void setFileFileName(String fileFileName)
	    {
	        this.fileFileName = fileFileName;
	    }

	    public String getFileContentType()
	    {
	        return fileContentType;
	    }

	    public void setFileContentType(String fileContentType)
	    {
	        this.fileContentType = fileContentType;
	    }
	    
	    public String execute() throws Exception
	    {
	        String root = ServletActionContext.getServletContext().getRealPath("/upload");
	        
	        InputStream is = new FileInputStream(file);
	        
	        OutputStream os = new FileOutputStream(new File(root, fileFileName));
	        
	        System.out.println("fileFileName: " + fileFileName);

	// 鍥犱负file鏄瓨鏀惧湪涓存椂鏂囦欢澶圭殑鏂囦欢锛屾垜浠彲浠ュ皢鍏舵枃浠跺悕鍜屾枃浠惰矾寰勬墦鍗板嚭鏉ワ紝鐪嬪拰涔嬪墠鐨刦ileFileName鏄惁鐩稿悓
	        System.out.println("file: " + file.getName());
	        System.out.println("file: " + file.getPath());
	        
	        byte[] buffer = new byte[500];
	        int length = 0;
	        
	        while(-1 != (length = is.read(buffer, 0, buffer.length)))
	        {
	            os.write(buffer);
	        }
	        
	        os.close();
	        is.close();
	        
	        return SUCCESS;
	    }


	
	
	
	//
	
	
	

	public void viewbytypeadded() throws ServletException, IOException {
		HttpSession wsession = request.getSession();
		Tooltype tp = (Tooltype) wsession.getAttribute("viewtype");
		Session session = HibernateSessionFactory.getSessionFactory()
				.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(Tool.class);
		crit.add(Restrictions.eq("tooltype", tp));
		List<Tool> tools = crit.list();
		tx.commit();
		request.setAttribute("ctools", tools);
		request.getRequestDispatcher("/tool/index.jsp").forward(request,
				response);

	}
	
	// abandoned function
    public void selecttool() throws ServletException, IOException{  
    	String adderid=request.getParameter("adder");
    	String typeid=request.getParameter("type");
    	Session session = HibernateSessionFactory.getSessionFactory()
				.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria crit=session.createCriteria(Tool.class);
				if (!"0".equals(adderid)){
					Criteria addercri=session.createCriteria(User.class);
					addercri.add(Restrictions.eq("id",Integer.parseInt(adderid)));
					User user=(User) addercri.uniqueResult();
					crit.add(Restrictions.eq("user",user));
					
				}
				    
				if (!"0".equals(typeid)){
					Criteria typecri=session.createCriteria(Tooltype.class);
					typecri.add(Restrictions.eq("id",Integer.parseInt(typeid)));
					Tooltype type=(Tooltype) typecri.uniqueResult();
					crit.add(Restrictions.eq("tooltype",type));			
				}
		List<Tool> tools=crit.list();
		crit=session.createCriteria(User.class);
		crit.add(Restrictions.eq("roleId", 1));
		List<User> users=crit.list();
		crit=session.createCriteria(Tooltype.class);
		List<Tooltype> types=crit.list();
		tx.commit();
		request.setAttribute("ctools", tools);
		request.setAttribute("adders", users);
		request.setAttribute("types", types);
		request.getRequestDispatcher("/tool/listall.jsp").forward(request,response);
		}
    
    
	public void getalltool() throws ServletException, IOException{
		Session session = HibernateSessionFactory.getSessionFactory()
				.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria crit=session.createCriteria(Tool.class);
		List<Tool> tools=crit.list();
		crit=session.createCriteria(User.class);
		crit.add(Restrictions.eq("roleId", 1));
		List<User> users=crit.list();
		crit=session.createCriteria(Tooltype.class);
		List<Tooltype> types=crit.list();
		tx.commit();
		HttpSession wsession=request.getSession();
		wsession.setAttribute("ctypes", types);
		request.setAttribute("ctools", tools);
		request.setAttribute("adders", users);
		request.setAttribute("types", types);
		request.getRequestDispatcher("/tool/listall.jsp").forward(request,
				response);
		
	}
	public void viewbytype() throws ServletException, IOException {
		Tooltype tp = new Tooltype();
		int id = Integer.parseInt(request.getParameter("id"));
		Session session = HibernateSessionFactory.getSessionFactory()
				.getCurrentSession();
		Transaction tx = session.beginTransaction();
		tp = (Tooltype) session.get(Tooltype.class, id);
		Criteria crit = session.createCriteria(Tool.class);
		crit.add(Restrictions.eq("tooltype", tp));
		List<Tool> tools = crit.list();
		tx.commit();
		HttpSession wsession = request.getSession();

		wsession.setAttribute("viewtype", tp);
		request.setAttribute("ctools", tools);
		request.getRequestDispatcher("/tool/index.jsp").forward(request,
				response);
	}

	public boolean checkexist(String toolname) {
		Session session = HibernateSessionFactory.getSessionFactory()
				.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria cri = session.createCriteria(Tool.class);
		cri.setMaxResults(1);
		cri.add(Restrictions.eq("toolName", toolname));
		Tool l = (Tool) cri.uniqueResult();
		tx.commit();
		if (l != null)
			return true;
		else
			return false;

	}

	//
	 public static Document load(String filename) 
     { 
        Document document = null; 
       try 
        { 
            SAXReader saxReader = new SAXReader(); 
            document = saxReader.read(new File(filename)); 
        } 
       catch (Exception ex){ 
            ex.printStackTrace(); 
        }   
       return document; 
     }
	 
	 public static String doc2String(Document document) 
     { 
       String s = ""; 
       try 
        { 
            //浣跨敤杈撳嚭娴佹潵杩涜杞寲 
            ByteArrayOutputStream out = new ByteArrayOutputStream(); 
            //浣跨敤GB2312缂栫爜 
            OutputFormat format = new OutputFormat("   ", true, "utf-8"); 
             XMLWriter writer = new XMLWriter(out, format); 
             writer.write(document); 
             s = out.toString("utf-8"); 
        }catch(Exception ex) 
        {             
             ex.printStackTrace(); 
        }       
       return s; 
     }

	public static boolean doc2XmlFile(Document document, String filename) {
		boolean flag = true;
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			XMLWriter writer = new XMLWriter(
					new FileWriter(new File(filename)), format);
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	public static boolean string2XmlFile(String str, String filename) {
		boolean flag = true;
		try {
			Document doc = DocumentHelper.parseText(str);
			flag = doc2XmlFile(doc, filename);
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}
	
	

	

	

	public String add() throws IOException {
		

		tool.setToolName(tool.getToolName().trim());
		if (Reg.IsNullOrEmpty(tool.getToolName())) {
			response.getWriter()
					.print("<script languge='javascript'>history.go(-1);alert('please input toolname :)')</script>");
			return null;
		}

		if (checkexist(tool.getToolName())) {
			response.getWriter()
					.print("<script languge='javascript'>alert('This toolname is existed,please change your toolname :)');history.go(-1)</script>");
			return null;
		}
		String webtooltype=request.getParameter("webtooltype");
		//String toolshare=request.getParameter("share");
		Session session = HibernateSessionFactory.getSessionFactory()
				.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria crit=session.createCriteria(Tooltype.class);
		crit.add(Restrictions.eq("id", Integer.parseInt(webtooltype)));
		Tooltype tooltype=(Tooltype) crit.uniqueResult();

		HttpSession wsession = request.getSession();
		User user = (User) wsession.getAttribute("user");
		tool.setUser(user);

		//tool.setIsShared(Integer.parseInt(toolshare));
		tool.setTooltype(tooltype);

		Date date = new Date();
		Timestamp nowdate = new Timestamp(date.getTime());
		tool.setAddedTime(nowdate);

		int toolid=(Integer) session.save(tool);
		if(toolid>0)
		{
			tooltype.setToolNum(tooltype.getToolNum()+1);
			session.update(tooltype);
		}
		
		
		String toolpath = ServletActionContext.getServletContext().getRealPath("/uploadtools");		
		toolpath+="\\"+fileFileName;
		String interpreter=request.getParameter("interpreter");
		
		
		
		//---------------upload files
				System.out.println("-------------");
				

				String xmlstring = request.getParameter("xmlsection");
				xmlstring=xmlstring.replace("toolid", Integer.toString(toolid));
				xmlstring=xmlstring.replace("xmlpath", toolpath);
				xmlstring=xmlstring.replace("interpretertext", interpreter);
				System.out.println(xmlstring);
				String path=ServletActionContext.getServletContext().getRealPath("/uploadtools");
				
			    String xin=fileFileName;
				String xinname = xin.substring(0,xin.lastIndexOf("."));
				path+="\\"+xinname+".xml";
				tool.setParameters(path);
				tx.commit();
				boolean me = string2XmlFile(xmlstring, path);
				System.out.println(me);

				System.out.println("------------");
				
				//---------------upload files
				System.out.println("-----start upload files-------");
				
				String root = ServletActionContext.getServletContext().getRealPath("/uploadtools");
		        
		        InputStream is = new FileInputStream(file);
		        
		        OutputStream os = new FileOutputStream(new File(root, fileFileName));
		        
		        System.out.println("fileFileName: " + fileFileName);

		// 鍥犱负file鏄瓨鏀惧湪涓存椂鏂囦欢澶圭殑鏂囦欢锛屾垜浠彲浠ュ皢鍏舵枃浠跺悕鍜屾枃浠惰矾寰勬墦鍗板嚭鏉ワ紝鐪嬪拰涔嬪墠鐨刦ileFileName鏄惁鐩稿悓
		        System.out.println("file: " + file.getName());
		        System.out.println("file: " + file.getPath());
		        
		        byte[] buffer = new byte[500];
		        int length = 0;
		        
		        while(-1 != (length = is.read(buffer, 0, buffer.length)))
		        {
		            os.write(buffer);
		        }
		        
		        os.close();
		        is.close();
			
		        System.out.println("-----end upload files-------");
				//--------------end upload files

		return SUCCESS;
	}

	public String edit_step1() {
		int id = Integer.parseInt(request.getParameter("id"));
		Session session = HibernateSessionFactory.getSessionFactory()
				.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria cri = session.createCriteria(Tool.class);
		cri.setMaxResults(1);
		cri.add(Restrictions.eq("id", id));
		Tool c = (Tool) cri.uniqueResult();
		tx.commit();
		HttpSession wsession = request.getSession();
		wsession.setAttribute("ctool", c);
		
		

		//String xmlstring = doc2String(load("a.xml"));
		System.out.println("-----meme--------");	
		System.out.println(c.getParameters());
		String xmlstring = load(c.getParameters()).asXML().replaceAll("[\\t\\n\\r]", "");
		System.out.println("-----session--------");		
		System.out.println(xmlstring);		
		System.out.println("----session--------");
		String cpath=c.getParameters();
		
		int i=cpath.lastIndexOf("xml");
		cpath=cpath.substring(0, i);
		
		
		
		int begin=xmlstring.lastIndexOf(cpath);
		int end=xmlstring.indexOf("\"", begin);
		cpath=xmlstring.substring(begin, end);
		wsession.setAttribute("cpath", cpath);
		xmlstring=xmlstring.replace(cpath, "");
		wsession.setAttribute("xmlstring", xmlstring);
		System.out.println("-----session--------");		
		System.out.println(xmlstring);		
		System.out.println("----session--------");
		

		return SUCCESS;
	}

	public String edit_step2() throws IOException {
		HttpSession wsession = request.getSession();
		Tool c = (Tool) wsession.getAttribute("ctool");
		String cpath= (String) wsession.getAttribute("cpath");
		String xmlstring = (String) wsession.getAttribute("xmlstring");
		
		String interpreter=request.getParameter("interpreter");
		
		System.out.println("-----xiugai--------");

		String xxmlstring = request.getParameter("xmlsection");
		xxmlstring=xxmlstring.replace("toolid", Integer.toString(c.getId()));
		xxmlstring=xxmlstring.replace("xmlpath", cpath);
		xxmlstring=xxmlstring.replace("interpretertext", interpreter);
		
		System.out.println(xxmlstring);
		boolean me = string2XmlFile(xxmlstring,c.getParameters());
		System.out.println(me);

		System.out.println("------xiugai------");
		
		
		
		
		
		
		

		if (checkexist(tool.getToolName())
				&& !(tool.getToolName().equals(c.getToolName()))) {
			response.getWriter()
					.print("<script languge='javascript'>alert('This toolname is existed,please change your toolname :)');history.go(-1)</script>");
			return null;
		}
		Session session = HibernateSessionFactory.getSessionFactory()
				.getCurrentSession();
		Transaction tx = session.beginTransaction();
		int id = Integer.parseInt(request.getParameter("type"));

		Tooltype ctype = (Tooltype) session.get(Tooltype.class, id);

		c.setToolName(tool.getToolName());
		c.setSavedResults(tool.getSavedResults());
		c.setIsShared(tool.getIsShared());
		c.setTooltype(ctype);
		c.setDescribtion(tool.getDescribtion());
		session.update(c);
		tx.commit();
		wsession.removeAttribute("ctool");
		wsession.removeAttribute("cpath");
		wsession.removeAttribute("xmlstring");
		return SUCCESS;
	}

	public String del() {
		int id = Integer.parseInt(request.getParameter("id"));
		Session session = HibernateSessionFactory.getSessionFactory()
				.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria cri = session.createCriteria(Tool.class);
		cri.setMaxResults(1);
		cri.add(Restrictions.eq("id", id));
		Tool c = (Tool) cri.uniqueResult();
		
		
		// get file and xml path
		String xmlstring = load(c.getParameters()).asXML().replaceAll("[\\t\\n\\r]", "");
		String cpath=c.getParameters();
		String xmlpath=cpath;		
		int i=cpath.lastIndexOf("xml");
		cpath=cpath.substring(0, i);
		int begin=xmlstring.lastIndexOf(cpath);
		int end=xmlstring.indexOf("\"", begin);
		cpath=xmlstring.substring(begin, end);
		// get file and xml path end
		
		
		Tooltype type=c.getTooltype();
		type.setToolNum(type.getToolNum()-1);
		session.delete(c);		
		tx.commit();
		deleteFile(xmlpath);
		deleteFile(cpath);
		return SUCCESS;

	}
	
	public boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  

	@Override
	public Tool getModel() {

		return tool;
	}

}
