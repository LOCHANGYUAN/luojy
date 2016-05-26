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
	 * �غc�l
	 */
	public CathayBankCurrency() {
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
		return dataList;
	}	
	/* 
	 * 1.���p�A��()�����l�r��
	 * 2.��������
	 */
	@Override
	public List<String> trimHeaderList(List<String> headerList){
		headerList=FormatUtils.subStringBetweenParenthesis(headerList);//���p�A��()�����l�r��
		headerList=FormatUtils.removeChinesePart(headerList);//��������
		return headerList;
	}
	/* 
	 * 1.���p�A��()�����l�r��
	 * 2.��������
	 */
	@Override
	public List<String> trimRowDataList(List<String> rowDataList){
		rowDataList=FormatUtils.subStringBetweenParenthesis(rowDataList);//���p�A��()�����l�r��
		rowDataList=FormatUtils.removeChinesePart(rowDataList);//��������
		return rowDataList;
	}	
	/**
	 * �^���t�θ�Ʈɶ�
	 * @return
	 */
	@Override	
	public final String geDateTime(){
		//�ײv����ɶ�
		List<String> dateList=HtmlUtils.getElementListText(doc,"div center table[class=\"paragraph\"] tbody tr td[align=\"center\"]");
		String dateTimeStr=dateList.get(0);
		dateTimeStr=StringUtils.replace(dateTimeStr, "�~", "-");
		dateTimeStr=StringUtils.replace(dateTimeStr, "��", "-");
		dateTimeStr=StringUtils.replace(dateTimeStr, "��", " ");
		dateTimeStr=StringUtils.replace(dateTimeStr, "��", "�G");
		dateTimeStr=StringUtils.replace(dateTimeStr, "��", "�G00.000");
		dateTimeStr=StringUtils.substringAfter(dateTimeStr, ")");
		dateTimeStr=StringUtils.substringAfter(dateTimeStr, "�G");
		
		logger.info("�����W���ɶ�:"+dateTimeStr);
		return dateTimeStr;
	}	
}
