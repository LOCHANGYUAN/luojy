package luojy.html;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import luojy.util.FormatUtils;
import luojy.util.HtmlUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

public  abstract class HtmlParsingSample implements IHtmlParsingSample {
	private static Logger logger = Logger.getLogger(HtmlParsingSample.class);
	protected Document doc;
	/**
	 * 建構子
	 * @param url
	 */
	public  HtmlParsingSample(String url){
		setDoc(url);
	}
	/**
	 * @param url
	 */
	private void setDoc(String url){
		this.doc=HtmlUtils.getDocument(url);
	}
	/**
	 * 將表頭和rowdata轉換成ListMap
	 * @param headerList
	 * @param dataList
	 * @return
	 */
	@Override
	public  List<Map<String,String>> toListMap(List<String> headerList, List<String> dataList){
		List<Map<String,String>> listMap=new ArrayList<Map<String,String>>();
		Map<String,String> currMap=new HashMap<String,String>();
		//
		int idx=0;
		int len=headerList.size();
		int headerIdx=0;
		for(String data:dataList){
			//
			headerIdx=idx%len;
			if(headerIdx==0){
				currMap=new HashMap<String,String>();
				listMap.add(currMap);
			}
			currMap.put(headerList.get(headerIdx), data);
			idx++;
		}
		logger.info(listMap);
		return listMap;		
	}
	/**
	 * @return
	 */
	@Override
	public  List<Map<String,String>> parsingSinglePage(){
		return parsingMultiPage(null,null);
	}
	/**
	 * @param urlPattern
	 * @return
	 */
	@Override	
	public  List<Map<String,String>> parsingMultiPage(String urlPattern,Map<String,Object> paraMap){
		List<Map<String,String>> listMap=new ArrayList<Map<String,String>>();
		//第一頁
		String url=doc.baseUri();
		int totalPage=1;
		if(StringUtils.isNotBlank(urlPattern)&&paraMap!=null&&paraMap.size()>=0){
			logger.info("urlPattern:"+urlPattern);
			logger.info("paraMap:"+paraMap);
			totalPage=getPageNo();			
		}else{
			logger.info("url:"+url);
		}
		logger.info("page:1");		
		listMap.addAll(getListMap());	
		
		//多分頁
		for(int page=2;page<=totalPage;page++){
			paraMap.put("page", String.valueOf(page));
			logger.info("page:"+page);
			url=FormatUtils.replaceByMap(urlPattern, paraMap);
			setDoc(url);
			listMap.addAll(getListMap());		
		}
		return listMap;		
	}
	/* 
	 * 取得表頭＋rowData所對應的ListMap
	 */
	private  List<Map<String,String>> getListMap(){
		List<String> headerList = trimHeaderList(getHeaderList());
		List<String> dataList = trimRowDataList(getRowDataListMap());
		return toListMap(headerList,dataList);
	}
	/* 
	 * 預設不處理
	 */
	@Override
	public List<String> trimHeaderList(List<String> headerList){
		return headerList;
	}
	/* 
	 * 預設不處理
	 */
	@Override
	public List<String> trimRowDataList(List<String> rowDataList){
		return rowDataList;
	}	
	/* 
	 * 擷取系統資料時間
	 */
	@Override
	public String geDateTime() {		
		String dateTimeStr= FormatUtils.formatDateStr(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:sss");
		logger.info("[預設主機系統時間]:"+dateTimeStr);
		return dateTimeStr;
	}					
	/* 
	 * 擷取頁資訊
	 * 預設為單一頁面
	 */
	@Override
	public int getPageNo() {
		int totalPageNo=1;
		logger.info("[預設為單一頁面]："+totalPageNo);
		return totalPageNo;
	}
}
