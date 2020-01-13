package com.jeeplus.common.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class PdfUitl {
	/**
	  * 将word转换成pdf
	 * @param startFile
	 * @param overFile
	 * @throws IOException
	 */
	public static void WordToPDF(String startFile, String overFile) throws IOException {
	        // 源文件目录
	        File inputFile = new File(startFile);
	        if (!inputFile.exists()) {
	            System.out.println("源文件不存在！");
	            return;
	        }
	        // 输出文件目录
	        File outputFile = new File(overFile);
	        if (!outputFile.getParentFile().exists()) {
	            outputFile.getParentFile().exists();
	        }
	        // 调用openoffice服务线程
	        /** 我把openOffice下载到了 C:/Program Files (x86)/下  ,下面的写法自己修改编辑就可以**/
	        String command = "C:\\Program Files (x86)\\OpenOffice 4\\program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
	        Process p = Runtime.getRuntime().exec(command);
	        // 连接openoffice服务
	        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
	        connection.connect();
	        // 转换
	        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
	        converter.convert(inputFile, outputFile);
	        // 关闭连接
	        connection.disconnect();
	 
	        // 关闭进程
	        p.destroy();
	    }
	

	
	/**
	 * 删除文件和文件夹公共
	 * @param path
	 * @return
	 */
	public static boolean delAllFile(String path) {
	      boolean flag = false;
	      File file = new File(path);
	      file.delete();
	      if (!file.exists()) {
	        return flag;
	      }
	      if (!file.isDirectory()) {
	        return flag;
	      }
	      String[] tempList = file.list();
	      File temp = null;
	      for (int i = 0; i < tempList.length; i++) {
	         if (path.endsWith(File.separator)) {
	            temp = new File(path + tempList[i]);
	         } else {
	             temp = new File(path + File.separator + tempList[i]);
	         }
	         if (temp.isFile()) {
	            temp.delete();
	         }
	         if (temp.isDirectory()) {
	            delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	            flag = true;
	         }
	      }
	      return flag;
	    }
	
	/**
	 * 下载代码
	 * @param response
	 * @param downpath
	 * @param filename
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String download(HttpServletResponse response,String downpath,String filename) throws UnsupportedEncodingException {
		
		response.setContentType("application/x-download");  
		String filedownload = downpath; 
		String filedisplay = filename;
		filedisplay = URLEncoder.encode(filedisplay,"UTF-8");
		response.addHeader("Content-Disposition","attachment;filename=" + filedisplay);  
		OutputStream outp = null;  
		FileInputStream in = null;  
		  try{  
		       outp = response.getOutputStream();  
		       in = new FileInputStream(filedownload);  
		        byte[] b = new byte[1024];  
		           int i = 0;  
		         while((i = in.read(b)) > 0)  {  
		              outp.write(b, 0, i);  
		        }  
		          outp.flush();  
		     } catch(Exception e){  
		        System.out.println("Error!");  
		         e.printStackTrace();  
		      }finally{  
		           if(in != null)  
		            {  
		              try {
		in.close();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}  
		             in = null;  
		           }  
		          if(outp != null)  
		           {  
		               try {
		outp.close();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}  
		              outp = null;  
		          }  
		       } 
		  return null;

		}

	/**
	 * 将word转换成html
	 * @param wordpath
	 * @param htmlpath
	 */
	public static void WordToHtml(String wordpath,String htmlpath) {
		File inputFile = new File(wordpath);
        File outputFile = new File(htmlpath);
        OpenOfficeConnection con = new SocketOpenOfficeConnection(8100);
        try {
            con.connect();
        } catch (ConnectException e) {
            System.err.println("文件转换出错，请检查OpenOffice服务是否启动。");
            e.printStackTrace();
        }
        DocumentConverter converter = new OpenOfficeDocumentConverter(con);
        converter.convert(inputFile, outputFile);
        con.disconnect();
	}
	
	
	
//	public static void main(String[] args) {
//		//获取连接
//Map<String,Object> map=new HashMap<String,Object>();
//map.put("{ID}", "3232");
//map.put("{NAME}", "333");
//map.put("{GENERATIONDATE}", "333");//创建时间
//map.put("{AMOUNT}", "333");//单据金额
//map.put("{NUM}", "333");//单据编号
//map.put("{TOTALAMOUNT}", "333");//融资金额
//map.put("{LOANAMOUNT}", "333");//放款金额
//
//
//
//PdfUitl.readwriteWord("c://rongzi.doc",map,"c://aaa.doc");
//		
//		}
	/**
	    * 实现对word读取和修改操作
	    * 
	    * @param filePath
	    *            word模板路径和名称
	    * @param map
	    *            待填充的数据，从数据库读取
	    */
	    public static void readwriteWord(String filePath, Map<String, Object> map,String url)
	    {
	    	File outputFile = new File(url);
	        if (!outputFile.getParentFile().exists()) {
	            outputFile.getParentFile().exists();
	        }
	        HWPFDocument hdt = null;
	        try
	        {
	      	  InputStream is = new FileInputStream(filePath);  
	      	  
	      	  hdt = new HWPFDocument(is);  
	        }
	        catch (IOException e1)
	        {
	            e1.printStackTrace();
	        }
	        Fields fields = hdt.getFields();
	        Iterator<Field> it = fields.getFields(FieldsDocumentPart.MAIN)
	                .iterator();
	        while (it.hasNext())
	        {
	            System.out.println(it.next().getType());
	        }
	        //读取word文本内容
	        Range range = hdt.getRange();
	        TableIterator tableIt = new TableIterator(range); 
	        //迭代文档中的表格
	        int ii = 0;
	        while (tableIt.hasNext()) {  
	            Table tb = (Table) tableIt.next();  
	            ii++;
	            System.out.println("第"+ii+"个表格数据...................");
	            //迭代行，默认从0开始
	            for (int i = 0; i < tb.numRows(); i++) {  
	                TableRow tr = tb.getRow(i);  
	                //迭代列，默认从0开始
	                for (int j = 0; j < tr.numCells(); j++) {  
	                    TableCell td = tr.getCell(j);//取得单元格
	                    //取得单元格的内容
	                    for(int k=0;k<td.numParagraphs();k++){  
	                        Paragraph para =td.getParagraph(k);  
	                        String s = para.text();  
	                        System.out.println(s);
	                    } //end for   
	                }   //end for
	            }   //end for
	        } //end while
	        //System.out.println(range.text());
	        
	        // 替换文本内容
	        
	        for (Map.Entry<String, Object> entry : map.entrySet())
	        {
	      	  String value="";
	      	  String key="";
	      	  if(null!=entry.getValue()){
	      		  value=entry.getValue().toString();
	      	  }
	      	  if(null!=entry.getKey()){
	      		  key=entry.getKey().toString();
	      	  }
	      	  range.replaceText(key, value);
	        }
	        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
	        FileOutputStream out = null;
	        try
	        {
	            out = new FileOutputStream(url, true);
	        }
	        catch (FileNotFoundException e)
	        {
	            e.printStackTrace();
	        }
	        try
	        {
	            hdt.write(ostream);
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	        // 输出字节流
	        try
	        {
	            out.write(ostream.toByteArray());
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	        try
	        {
	            out.close();
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	        try
	        {
	            ostream.close();
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	    }
}
