package com.lquan.parseExcel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.lquan.POI.POIException;
import com.lquan.POI.Workbookx;
import com.lquan.util.WriteExcelUtil;



public class CheckStatisticExcelWb extends Workbookx {

	public CheckStatisticExcelWb(FileInputStream workBookStream, int sheet)
			throws IOException, POIException {
		super(workBookStream, sheet);
	}

	@Override
	protected Map<String, Short> validCheck() throws POIException {
		return null;
	}
	
public void  checkStatisticImport(List<Map<String, Object>> list){
		

		//设置列宽
		HSSFCellStyle titleStyel = this.getWookBook().createCellStyle(); //标题样式
		titleStyel.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐 
		// 设置单元格字体 
		HSSFFont titlefont = this.getWookBook().createFont(); 
		titlefont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
		titlefont.setFontName("宋体"); 
		titlefont.setFontHeight((short) 400); 
		titleStyel.setFont(titlefont);
		// 设置单元格背景色 
		titleStyel.setFillForegroundColor(HSSFColor.GREEN.index); 
		titleStyel.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 	
		
       
        HSSFCellStyle bodyCellStyle = this.getWookBook().createCellStyle();      
        bodyCellStyle.setAlignment(bodyCellStyle.ALIGN_CENTER);  
        bodyCellStyle.setWrapText(false); //是否自动换行.  
        

       HSSFWorkbook WookBook = this.getWookBook();
       // 将数据导入excel中
    	   String[] Keys = {"user_name","flowTargetNum" ,"thenUserRouteNum","userCheckSum", "checkFalse_YJ","checkFalse_DH","checkFalse_QT","weightAccuracy","realAccuracy","coefficient"};
    	   int startRowIndex = 2;
    	   WriteExcelUtil.ExportDateOneSheet(WookBook, bodyCellStyle, list, Keys, startRowIndex);
} 

	/**
	 * 评估员的结果统计
	 * @param list
	 */
	public void  AssResultStatisticImport(List<Map<String, Object>> list){
	

	//设置列宽
	HSSFCellStyle titleStyel = this.getWookBook().createCellStyle(); //标题样式
	titleStyel.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐 
	// 设置单元格字体 
	HSSFFont titlefont = this.getWookBook().createFont(); 
	titlefont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
	titlefont.setFontName("宋体"); 
	titlefont.setFontHeight((short) 400); 
	titleStyel.setFont(titlefont);
	// 设置单元格背景色 
	titleStyel.setFillForegroundColor(HSSFColor.GREEN.index); 
	titleStyel.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 	
	
   
    HSSFCellStyle bodyCellStyle = this.getWookBook().createCellStyle();      
    bodyCellStyle.setAlignment(bodyCellStyle.ALIGN_CENTER);  
    bodyCellStyle.setWrapText(false); //是否自动换行.  
    
   HSSFWorkbook WookBook = this.getWookBook();
   // 将数据导入excel中
	   String[] Keys = {"homeAddress","user_name" ,"flowTargetNum","theUserRouteNum", "comDegree_LYLX","integrity_WJTX",
			   "errorNum_WJ","weightAccuracy","realAccuracy","totalScore","coefficient"};
	   int startRowIndex = 2;
	   WriteExcelUtil.ExportDateOneSheet(WookBook, bodyCellStyle, list, Keys, startRowIndex);
} 
    

}
