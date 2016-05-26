package luojy.html;

import java.util.List;
import java.util.Map;

public interface IHtmlParsingSample {
	/**
	 * 擷取表頭
	 * @return
	 */
	public abstract List<String> getHeaderList();
	/**
	 * 擷取rowData資料
	 * @return
	 */
	public abstract List<String> getRowDataListMap(); 
	/**
	 * 將表頭和rowdata轉換成ListMap
	 * @param headerList
	 * @param dataList
	 * @return
	 */
	public abstract List<Map<String,String>> toListMap(List<String> headerList, List<String> dataList);
	/**
	 * 解析單一頁面
	 * @return
	 */
	public abstract List<Map<String,String>> parsingSinglePage();
	/**
	 * 解析多頁面
	 */
	public abstract List<Map<String,String>> parsingMultiPage(String urlPattern,Map<String,Object> paraMap);
	/**
	 * headerList處理
	 * @param headerList
	 * @return
	 */
	public List<String> trimHeaderList(List<String> headerList);
	/**
	 * rowDataList處理
	 * @param rowDataList
	 * @return
	 */
	public List<String> trimRowDataList(List<String> rowDataList);	
	/**
	 * 擷取系統資料時間
	 * @return
	 */
	public abstract  String geDateTime();
	/**
	 * 擷取頁數
	 * @return
	 */
	public abstract  int getPageNo();
	
}