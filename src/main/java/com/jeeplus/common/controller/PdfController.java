package com.jeeplus.common.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.modules.cyl.bean.Bill_info;
@Controller
@RequestMapping(value="${adminPath}")
public class PdfController {

	@Autowired
	private PdfServer pdfserver;
    /**
     * 导出pdf单据
     * @param response
     * @return
     * @throws IOException 
     */
    @RequestMapping(value={"/exportpdf"})
    public String exportPdf(Bill_info bill_info,HttpServletResponse response,HttpServletRequest  request) throws IOException { 
    	String type = request.getParameter("type");
    	String realPath = Global.USERFILES_BASE_URL
        		+ "" + "/download/";
    	String pdfname=type+"templet.pdf";
    	//原来得模板文件
    	String temname=type+".doc";
    	
    	//新生成得文件
    	String newname="new";
    	
    	String time=Calendar.getInstance().getTimeInMillis()+"";
    	//先替换模板文字
        Map<String, Object> map =null;
           
        if(type.indexOf("rongzi") != -1) {
        	map=pdfserver.getRzMap(bill_info);
        }else {
        	map= pdfserver.getMap(bill_info);
        }
   	PdfUitl.readwriteWord(Global.getUserfilesBaseDir() + realPath+temname,map,Global.getUserfilesBaseDir() + realPath +newname+time+".doc");
    	FileUtils.createDirectory(Global.getUserfilesBaseDir() + realPath);
    	
    	String billpath=Global.getUserfilesBaseDir() + realPath + pdfname;
    	PdfUitl.WordToPDF(Global.getUserfilesBaseDir() + realPath+newname+time+".doc", billpath);
    	 
    	PdfUitl.download(response,billpath,pdfname);
		PdfUitl.delAllFile(billpath);

    	return null;
    }
    
}