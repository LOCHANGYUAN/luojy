package com.cathaybk;

import java.util.ArrayList;
import java.util.List;

import luojy.html.HtmlParsingSample;
import luojy.util.FormatUtils;
import luojy.util.HtmlUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public  class CathayBankRate extends HtmlParsingSample{
	private static Logger logger = Logger.getLogger(CathayBankRate.class);
	private final static String URL="https://www.cathaybk.com.tw/cathaybk/personal_info06.asp";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CathayBankRate s=new CathayBankRate();
		s.geDateTime();
		s.parsingSinglePage();		
	}
	/**
	 * 建構子
	 */
	public CathayBankRate() {
		super(URL);
	}		
	/**
	 * 擷取表頭
	 * @return
	 */
	@Override	
	public final List<String> getHeaderList() {
		//標頭
		List<String> headerList=HtmlUtils.getElementListText(doc,"td[class=\"td1\"]");		
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
		List<String> dataList=HtmlUtils.getElementListText(doc,"td[class=\"td2\"],td[align=\"right\"][bgcolor=\"#FFFFFF\"]");
		//logger.info(dataList);
		return dataList;
	}
	/* 
	 * 1.移除中文
	 */
	@Override
	public List<String> trimHeaderList(List<String> headerList){
		headerList=FormatUtils.removeChinesePart(headerList);//移除中文
		return headerList;
	}
	/* 
	 * 1.取小括號()內的子字串
	 * 2.移除中文
	 * 3.取％之前的子字串
	 * 4.--開頭==>-- 
	 */
	@Override
	public List<String> trimRowDataList(List<String> rowDataList){
		rowDataList=FormatUtils.subStringBetweenParenthesis(rowDataList);//取小括號()內的子字串
		rowDataList=FormatUtils.removeChinesePart(rowDataList);//移除中文
		List<String> resultList=new ArrayList<String>();
		for(String str:rowDataList){
			if(str.startsWith("--")){
				resultList.add("--");
			}else{
				resultList.add(StringUtils.substringBefore(str, "%"));
			}
			
		}
		return resultList;
	}	
	/**
	 * 擷取系統資料時間
	 * @return
	 */
	@Override	
	public final String geDateTime(){
		//匯率日期時間
		List<String> dateList=HtmlUtils.getElementListText(doc,"span[class=\"s12\"]");
		String dateTimeStr=dateList.get(0);
		dateTimeStr=StringUtils.replace(dateTimeStr, "年", "-");
		dateTimeStr=StringUtils.replace(dateTimeStr, "月", "-");
		dateTimeStr=StringUtils.replace(dateTimeStr, "日", " ");
		dateTimeStr=StringUtils.substringAfter(dateTimeStr, ")");
		dateTimeStr=StringUtils.substringAfter(dateTimeStr, "：");
		dateTimeStr=StringUtils.substringBefore(dateTimeStr, "(");
		
		logger.info("網頁上的時間:"+dateTimeStr);
		return dateTimeStr;
	}	
}
