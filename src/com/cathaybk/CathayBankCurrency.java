package com.cathaybk;

import java.util.List;

import luojy.html.HtmlParsingSample;
import luojy.util.FormatUtils;
import luojy.util.HtmlUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public  class CathayBankCurrency extends HtmlParsingSample{
	private static Logger logger = Logger.getLogger(CathayBankCurrency.class);
	private final static String URL="https://www.cathaybk.com.tw/cathaybk/personal_info07.asp";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CathayBankCurrency s=new CathayBankCurrency();
		s.geDateTime();
		s.parsingSinglePage();		
	}
	/**
	 * 建構子
	 */
	public CathayBankCurrency() {
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
		return dataList;
	}	
	/* 
	 * 1.取小括號()內的子字串
	 * 2.移除中文
	 */
	@Override
	public List<String> trimHeaderList(List<String> headerList){
		headerList=FormatUtils.subStringBetweenParenthesis(headerList);//取小括號()內的子字串
		headerList=FormatUtils.removeChinesePart(headerList);//移除中文
		return headerList;
	}
	/* 
	 * 1.取小括號()內的子字串
	 * 2.移除中文
	 */
	@Override
	public List<String> trimRowDataList(List<String> rowDataList){
		rowDataList=FormatUtils.subStringBetweenParenthesis(rowDataList);//取小括號()內的子字串
		rowDataList=FormatUtils.removeChinesePart(rowDataList);//移除中文
		return rowDataList;
	}	
	/**
	 * 擷取系統資料時間
	 * @return
	 */
	@Override	
	public final String geDateTime(){
		//匯率日期時間
		List<String> dateList=HtmlUtils.getElementListText(doc,"div center table[class=\"paragraph\"] tbody tr td[align=\"center\"]");
		String dateTimeStr=dateList.get(0);
		dateTimeStr=StringUtils.replace(dateTimeStr, "年", "-");
		dateTimeStr=StringUtils.replace(dateTimeStr, "月", "-");
		dateTimeStr=StringUtils.replace(dateTimeStr, "日", " ");
		dateTimeStr=StringUtils.replace(dateTimeStr, "時", "：");
		dateTimeStr=StringUtils.replace(dateTimeStr, "分", "：00.000");
		dateTimeStr=StringUtils.substringAfter(dateTimeStr, ")");
		dateTimeStr=StringUtils.substringAfter(dateTimeStr, "：");
		
		logger.info("網頁上的時間:"+dateTimeStr);
		return dateTimeStr;
	}	
}
