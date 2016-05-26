package gov.coa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import luojy.html.HtmlParsingSample;
import luojy.util.FormatUtils;
import luojy.util.HtmlUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public  class FarmerAcademy extends HtmlParsingSample{
	private static Logger logger = Logger.getLogger(FarmerAcademy.class);
	private final static String URL="http://academy.coa.gov.tw/course.php?grade_id=@gradeId@&WS_id=@wsId@&page=@page@";
	private static Map<String,String> CLASS_MAP=new TreeMap<String,String>();
	static{
		CLASS_MAP.put("B", "22");//農業入門
		CLASS_MAP.put("C", "23");//初階訓練
		CLASS_MAP.put("D", "24");//進階訓練
		CLASS_MAP.put("E", "25");//高階訓練		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		parsingAllClasses();
	}
	/**
	 * 建構子
	 * @param url
	 */
	public FarmerAcademy(String url) {
		super(url);
	}
	/**
	 * 解析所有課程
	 */
	public static void parsingAllClasses() {
		List<Map<String,String>> listMap=new ArrayList<Map<String,String>>();
		for(String gradeId:CLASS_MAP.keySet()){
			listMap.addAll(parsingClass(gradeId));
		}
	}
	/**
	 * 解析指定課程
	 * @param gradeId
	 * @return
	 */
	public static List<Map<String,String>> parsingClass(String gradeId){
		Map<String,Object> paraMap=new HashMap<String,Object>();
		paraMap.put("gradeId", gradeId);
		paraMap.put("wsId", CLASS_MAP.get(gradeId));
		paraMap.put("page", String.valueOf(1));
		FarmerAcademy s=new FarmerAcademy(FormatUtils.replaceByMap(URL, paraMap));
		s.geDateTime();
		return s.parsingMultiPage(URL,paraMap);
	}
	/**
	 * 擷取表頭
	 * @return
	 */
	@Override	
	public final List<String> getHeaderList() {
		//標頭
		List<String> headerList=HtmlUtils.getElementListText(doc,"table tbody tr th");		
		//logger.info(headerList);
		return headerList;
	}	
	/**
	 * 擷取rowData資料
	 * @return
	 */
	@Override	
	public final List<String> getRowDataListMap() {
		//資料表
		List<String> dataList=HtmlUtils.getElementListText(doc,"table tbody tr td[style^=\"padding\"]");
		//logger.info(dataList);
		return dataList;
	}
	/**
	 * 擷取頁數
	 * @return
	 */
	@Override	
	public final int getPageNo(){
		//頁數資訊
		List<String> dateList=HtmlUtils.getElementListText(doc,"span[class=\"page_bar\"]");
		logger.info(dateList);
		String pageStr=dateList.get(0);
	    String totalItem=StringUtils.substringBetween(pageStr, "共", "筆資料");
	   	String itemPerpage=StringUtils.substringBetween(pageStr, "每頁顯示", "筆");
	   	int totalPageNo=1;
	   	if(FormatUtils.isNumericStr(totalItem)&&FormatUtils.isNumericStr(itemPerpage)){
	   		int totalItemNo=Integer.valueOf(totalItem);
	   		int itemPerpageNo=Integer.valueOf(itemPerpage);
	   		totalPageNo=totalItemNo/itemPerpageNo;
	   		if(totalItemNo%itemPerpageNo!=0){
	   			totalPageNo+=1;
	   		}
	   	}
	   	logger.info("頁數："+totalPageNo);
		return totalPageNo;
	}	
}
