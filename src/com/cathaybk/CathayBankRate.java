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
	 * �غc�l
	 */
	public CathayBankRate() {
		super(URL);
	}		
	/**
	 * �^�����Y
	 * @return
	 */
	@Override	
	public final List<String> getHeaderList() {
		//���Y
		List<String> headerList=HtmlUtils.getElementListText(doc,"td[class=\"td1\"]");		
		//logger.info(headerList);
		return headerList;
	}	
	/**
	 * �^��rowData���
	 * @return
	 */
	@Override	
	public final List<String> getRowDataListMap() {
		//��ƪ�
		List<String> dataList=HtmlUtils.getElementListText(doc,"td[class=\"td2\"],td[align=\"right\"][bgcolor=\"#FFFFFF\"]");
		//logger.info(dataList);
		return dataList;
	}
	/* 
	 * 1.��������
	 */
	@Override
	public List<String> trimHeaderList(List<String> headerList){
		headerList=FormatUtils.removeChinesePart(headerList);//��������
		return headerList;
	}
	/* 
	 * 1.���p�A��()�����l�r��
	 * 2.��������
	 * 3.���H���e���l�r��
	 * 4.--�}�Y==>-- 
	 */
	@Override
	public List<String> trimRowDataList(List<String> rowDataList){
		rowDataList=FormatUtils.subStringBetweenParenthesis(rowDataList);//���p�A��()�����l�r��
		rowDataList=FormatUtils.removeChinesePart(rowDataList);//��������
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
	 * �^���t�θ�Ʈɶ�
	 * @return
	 */
	@Override	
	public final String geDateTime(){
		//�ײv����ɶ�
		List<String> dateList=HtmlUtils.getElementListText(doc,"span[class=\"s12\"]");
		String dateTimeStr=dateList.get(0);
		dateTimeStr=StringUtils.replace(dateTimeStr, "�~", "-");
		dateTimeStr=StringUtils.replace(dateTimeStr, "��", "-");
		dateTimeStr=StringUtils.replace(dateTimeStr, "��", " ");
		dateTimeStr=StringUtils.substringAfter(dateTimeStr, ")");
		dateTimeStr=StringUtils.substringAfter(dateTimeStr, "�G");
		dateTimeStr=StringUtils.substringBefore(dateTimeStr, "(");
		
		logger.info("�����W���ɶ�:"+dateTimeStr);
		return dateTimeStr;
	}	
}
