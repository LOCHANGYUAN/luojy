/**
 * 
 */
package luojy.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author luojy
 *
 */
public class HtmlUtils {

	private static Logger logger = Logger.getLogger(HtmlUtils.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

	/**
	 * @param url
	 * @return
	 */
	public static Document getDocument(String url){
		try {
			return Jsoup.connect(url).get();
		} catch (IOException e) {
			//logger.log(Level.WARNING, url, e.getCause());
			logger.fatal(url, e);
			return new Document(url);
		}
	}
	/**
	 * @param doc
	 * @param syntaxString
	 * @return
	 */
	public static Elements getElements(Document doc,String syntaxString){
		return  doc.select(syntaxString);
	}
	/**
	 * @param doc
	 * @param syntaxString
	 * @return
	 */
	public static List<String> getElementListText(Document doc,String syntaxString){
		List<Element> eleList=getElementList(doc,syntaxString);
		List<String> eleListText=new ArrayList<String>();
		for(Element ele:eleList){
			eleListText.add(ele.text());
			//logger.info("elementText-->"+ele.text());
		}		
		return  eleListText;
	}	
	/**
	 * @param doc
	 * @param syntaxString
	 * @return
	 */
	public static List<Element> getElementList(Document doc,String syntaxString){
		Elements eles=doc.select(syntaxString);
		List<Element> eleList=new ArrayList<Element>();
		for(Object obj:eles.toArray()){
			eleList.add((Element)obj);
		}		
		return  eleList;
	}	
}
