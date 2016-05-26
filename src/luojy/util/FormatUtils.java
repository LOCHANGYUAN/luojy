/**
 * 
 */
package luojy.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.base.CaseFormat;

/**
 * @author luojy
 *
 */
public class FormatUtils {

	private static Logger log = Logger.getLogger(FormatUtils.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		getChinesePart("美元現鈔US Dollar Cash");
		
	}
	public static final String DATE_FORMATE = "yyyyMMdd";

	public static final String DATE_TIME_FORMATE = "yyyyMMddHHmmss";

	public static final String DATE_TIMES_FORMATE = "yyyyMMddHHmmssSSS";

	public static final String DIGITAL_0_STR = "0";

	public static final String DIGITAL_1_STR = "1";

	public static final String DIGITAL_2_STR = "2";

	public static final String DIGITAL_3_STR = "3";

	public static final String DIGITAL_4_STR = "4";

	public static final String DIGITAL_5_STR = "5";

	public static final String DIGITAL_6_STR = "6";

	public static final String DIGITAL_7_STR = "7";

	public static final String DIGITAL_8_STR = "8";

	public static final String DIGITAL_9_STR = "9";

	public static final String Y_STR = "Y";

	public static final String N_STR = "N";

	public static final String B_STR = "B";

	public static final String Default_STR = "Default";

	public static final String UTF8_STR = "UTF-8";

	public static final String xml_extension = ".xml";

	public static final String pdf_extension = ".pdf";

	public static final String txt_extension = ".txt";

	public static final String xlsx_extension = ".xlsx";

	public static final String json_extension = ".json";

	public static final String java_extension = ".java";

	public static final String sql_extension = ".sql";

	public static final String BLANK_STR = "";

	public static final String ONE_BLANK_STR = " ";

	public static final String UNDERLINE_STR = "_";

	public static final String DASH_STR = "-";

	public static final String EQUAL_STR = "=";

	public static final String POUND_STR = "#";

	public static final String THREE_POUND_STR = "###";

	public static final String STAR_STR = "*";

	public static final String SEMICOLON_STR = ";";

	public static final String COLON_STR = ":";

	public static final String AMPERSAND_STR = "&";

	public static final String SLASH_STR = "/";

	public static final String BACK_SLASH_STR = "\\";

	public static final String QUESTION_SIGN_STR = "?";

	public static final String COMMA_STR = ",";

	public static final String DOT_STR = ".";

	public static final String AT_SIGN_STR = "@";

	public static final String TILDE_STR = "~";

	public static final String SINGLE_QUOTE_STR = "'";

	public static final String DOUBLE_QUOTE_STR = "\"";

	public static final String NULL_STR = "NULL";

	public static final String LEFT_BRACKET1_STR = "(";

	public static final String RIGHT_BRACKET1_STR = ")";

	public static final String LEFT_BRACKET2_STR = "[";

	public static final String RIGHT_BRACKET2_STR = "]";

	public static final String LEFT_BRACKET3_STR = "{";

	public static final String RIGHT_BRACKET3_STR = "}";

	public static final String TAB_STR = "\t";

	public static final String NEW_LINE_STR = "\r\n";

	public static final String TWO_NEW_LINE_STR = "\r\n\r\n";

	public static final String BR_STR = "<BR>";

	public static final String FOR_TAG_STR = "<FOR>";

	public static final String FOR_TAG_END_STR = "</FOR>";

	public static final String TABLE_TAG_PREFIX_STR = "<TABLE";

	public static final String TABLE_TAG_END_PREFIX_STR = "</TABLE";

	public static final String TABLE_CNT_KEY = "@TABLE%d_CNT@";

	public static final String TABLE_TAG_STR = "<TABLE%d>";

	public static final String TABLE_TAG_END_STR = "</TABLE%d>";

	public static final String IMPORT_TAG_STR = "<IMPORT>";

	public static final String IMPORT_TAG_END_STR = "</IMPORT>";

	public static final String CNT_STR = "CNT";

	public static final String EVEN_ODD_STR = "EVEN_ODD";

	public static final String LIST_CNT_TAG = "#ListCnt#";

	public final static String[] DEFAULT_NEW_LINE_AFTER_ARY = { COMMA_STR, LEFT_BRACKET3_STR };

	public final static String[] DEFAULT_NEW_LINE_BEFORE_ARY = { RIGHT_BRACKET3_STR };

	private static String DB_INSTANCE;

	private static String ENV;

	private static Map<String, String> HEADER_MAP;

	private static Map<String, String> SEND_TYPE_MAP;

	private static Map<String, String> MSG_SEND_TYPE_MAP;

	/** 愛發科技有限公司 簡稱 */
	public final static String IFTEK_STR = "IFTEK";

	/** IFTEK 統編 */
	public final static String U_53121171_STR = "53121171";

	/** 伊頓數位科技有限公司 簡稱 */
	public final static String ETON_STR = "ETON";

	/** ETON 統編 */
	public final static String U_53743179_STR = "53743179";

	public final static String[] EMPTY_STRING_ARRAY = {};

	private final static String TEMPLATE_CLS_ROOT = "com/iftek/sys/template/";

	private final static String FILE_NOT_FOUND_STR = "FILE NOT FOUND!";

	private static String TemplateClassRoot;

	static {
		TemplateClassRoot = FormatUtils.class.getClassLoader().getResource( TEMPLATE_CLS_ROOT ).getFile();
		if ( TemplateClassRoot.indexOf( COLON_STR ) >= 0 ) {
			TemplateClassRoot = TemplateClassRoot.substring( 1 );
		}
	}

	/**
	 * @param templateFilePath
	 * @param rpStrMap
	 * @param dataList
	 * @return
	 */
	public static String formatConent( String templateFilePath, Map<String, Object> rpStrMap, List<List<Map<String, Object>>> dataList ) {
		String mailContent = templateFilePath;
		if ( CollectionUtils.isNotEmpty( dataList ) ) {
			mailContent = formatConent( mailContent, dataList );
		} else {
			mailContent = getTemplateContent( templateFilePath );
		}
		if ( rpStrMap != null ) {
			mailContent = replaceByMap( mailContent, rpStrMap, true );
		}
		return mailContent;
	}

	/**
	 * @param templeteFilePath
	 * @param dataList
	 * @return
	 */
	public static String formatConent( String templeteFilePath, List<List<Map<String, Object>>> dataList ) {
		return formatConent( templeteFilePath, dataList, false );
	}

	/**
	 * @param templeteFilePath
	 * @param dataList
	 * @param recordMap
	 * @param removeUnusedPara
	 * @return
	 */
	public static String formatConent( String templeteFilePath, List<List<Map<String, Object>>> dataList, boolean removeUnusedPara ) {
		File templeteFile = getTemplateFile( templeteFilePath );
		String templeteConent = expandForScript( templeteFile, null, null, null, null, dataList );
		if ( removeUnusedPara ) {
			templeteConent = removeUnusedPara( templeteConent, AT_SIGN_STR );
		}
		return templeteConent;
	}

	/**
	 * @param templeteScript
	 * @param listCntTag
	 * @param dataList
	 * @return
	 */
	public static String formatConent( String templeteFilePath, String listCntTag, List dataList ) {
		return formatConent( templeteFilePath, listCntTag, dataList.toArray() );
	}

	/**
	 * @param templeteScript
	 * @param objAry
	 * @return
	 */
	public static String formatConent( String templeteFilePath, Object[] objAry ) {
		String str = formatConent( templeteFilePath, null, objAry );
		// log.info( "formatConent by Array str:"+str );
		return str;
	}

	/**
	 * @param templeteScript
	 * @param listCntTag
	 * @param objAry
	 * @return
	 */
	public static String formatConent( String templeteFilePath, String listCntTag, Object[] objAry ) {
		int expandNo = objAry.length;
		listCntTag = StringUtils.isNotBlank( listCntTag ) ? listCntTag : LIST_CNT_TAG;
		String templeteConent = getTemplateContent( templeteFilePath );
		String templeteScript = expandForScript( templeteConent, null, null, listCntTag, null, expandNo );
		// log.info( "templeteScript:"+templeteScript );
		templeteScript = replaceBySequenceArray( templeteScript, objAry );

		return templeteScript;
	}

//-------------------------------------------------------------------------------------------------------------------

	/**
	 * @param templeteFile
	 * @param forStTag
	 * @param forEdTag
	 * @param cntTag
	 * @param evenOddTag
	 * @param dataList
	 * @return
	 */
	private static String expandForScript( File templeteFile, String forStTag, String forEdTag, String cntTag, String evenOddTag, List<List<Map<String, Object>>> dataList ) {
		String templeteConent = getTemplateContent( templeteFile.getAbsolutePath() );
		if ( templeteConent.indexOf( FILE_NOT_FOUND_STR ) < 0 ) {
			templeteConent = expandForScript( templeteConent, forStTag, forEdTag, cntTag, evenOddTag, dataList );
			// log.info( templeteConent );
		}
		return templeteConent;
	}

	public static String getTemplateContent( String templeteFileName ) {
		File TemplateFile = getTemplateFile( templeteFileName );
		String content = BLANK_STR;
		try {
			log.info( "Start read Template:" + TemplateFile );
			content = FileUtils.readFileToString( TemplateFile, UTF8_STR );
			content = importFileContent( content );
		} catch ( IOException e ) {
			content = concat( TemplateFile, FILE_NOT_FOUND_STR );
		}
		return content;
	}

	private static File getTemplateFile( String templeteFileName ) {
		File TemplateFile = new File( TemplateClassRoot, templeteFileName );
		log.info( "TemplateFile:" + TemplateFile );
		if ( !TemplateFile.exists() || !TemplateFile.isFile() ) {
			TemplateFile = new File( templeteFileName );
		}
		return TemplateFile;
	}

	/**
	 * @param templeteScript
	 * @param dataList
	 * @return
	 */
	public static String expandForScript( String templeteScript, List<List<Map<String, Object>>> dataList ) {
		return expandForScript( templeteScript, null, null, null, null, dataList );
	}

	/**
	 * @param templeteScript
	 * @param forStTag
	 * @param forEdTag
	 * @param dataList
	 * @return
	 */
	public static String expandForScript( String templeteScript, String forStTag, String forEdTag, List<List<Map<String, Object>>> dataList ) {
		return expandForScript( templeteScript, forStTag, forEdTag, null, null, dataList );
	}

	/**
	 * @param templeteScript
	 * @param forStTag
	 * @param forEdTag
	 * @param cntTag
	 * @param evenOddTag
	 * @param expandNo
	 * @return
	 */
	public static String expandForScript( String templeteScript, String forStTag, String forEdTag, String cntTag, String evenOddTag, int expandNo ) {
		return expandForScript( templeteScript, forStTag, forEdTag, cntTag, evenOddTag, null, expandNo );
	}

	/**
	 * @param templeteScript
	 * @param forStTag
	 * @param forEdTag
	 * @param cntTag
	 * @param evenOddTag
	 * @param dataList
	 * @return
	 */
	public static String expandForScript( String templeteScript, String forStTag, String forEdTag, String cntTag, String evenOddTag, List<List<Map<String, Object>>> dataList ) {
		int idx = 1;
		String tableTagStart = null;
		String tableTagEnd = null;
		String prefixStr = null;
		String suffixStr = null;
		String tableStr = null;
		String replaceTableStr = null;
		List<Map<String, Object>> dataListMap = null;
		int size = dataList.size();
		while ( templeteScript.indexOf( TABLE_TAG_PREFIX_STR ) >= 0 && templeteScript.indexOf( TABLE_TAG_END_PREFIX_STR ) >= 0 ) {
			dataListMap = size >= idx ? dataList.get( idx - 1 ) : new ArrayList<Map<String, Object>>();
			tableTagStart = String.format( TABLE_TAG_STR, idx );
			tableTagEnd = String.format( TABLE_TAG_END_STR, idx );
			// log.info( "tableTag:" + tableTagStart + "," + tableTagEnd );
			prefixStr = StringUtils.substringBefore( templeteScript, tableTagStart );
			suffixStr = StringUtils.substringAfter( templeteScript, tableTagEnd );
			tableStr = StringUtils.substringBetween( templeteScript, tableTagStart, tableTagEnd );
			if ( CollectionUtils.isNotEmpty( dataListMap ) ) {
				replaceTableStr = expandForScript( tableStr, forStTag, forEdTag, cntTag, evenOddTag, dataListMap, 0 );
			} else {
				replaceTableStr = BLANK_STR;
			}
			templeteScript = concat( prefixStr, replaceTableStr, suffixStr );
			String tableCntKey = String.format( TABLE_CNT_KEY, idx );
			// log.info( "tableCntKey:"+tableCntKey );
			templeteScript = StringUtils.replace( templeteScript, tableCntKey, dataListMap == null ? FormatUtils.DIGITAL_0_STR : String.valueOf( dataListMap.size() ) );
			idx++;
		}
		return templeteScript;
	}

	/**
	 * @param templeteScript
	 * @param forStTag
	 * @param forEdTag
	 * @param cntTag
	 * @param evenOddTag
	 * @param dataListMap
	 * @param expandNo
	 * @return
	 */
	public static String expandForScript( String templeteScript, String forStTag, String forEdTag, String cntTag, String evenOddTag, List<Map<String, Object>> dataListMap, int expandNo ) {
		// default:<FOR>
		forStTag = StringUtils.isNotBlank( forStTag ) ? forStTag : FOR_TAG_STR;
		// default:</FOR>
		forEdTag = StringUtils.isNotBlank( forEdTag ) ? forEdTag : FOR_TAG_END_STR;
		// default:#CNT#
		cntTag = StringUtils.isNotBlank( cntTag ) ? cntTag : concatWithDel( POUND_STR, CNT_STR );
		// log.info( "cntTag:"+cntTag );
		// default:#EVEN_ODD#
		evenOddTag = StringUtils.isNotBlank( evenOddTag ) ? evenOddTag : concatWithDel( POUND_STR, EVEN_ODD_STR );

		String forTempleateStr = null;
		// String oriTempStr = null;
		String replacedStr = null;
		String prefixStr = null;
		String suffixStr = null;
		if ( dataListMap != null && dataListMap.size() > 0 ) {
			expandNo = dataListMap.size();
		}
		// 逐一處理<FOR> </FOR>間的文字
		while ( templeteScript.indexOf( forStTag ) >= 0 ) {
			// 取得<FOR> </FOR>間的文字
			forTempleateStr = StringUtils.substringBetween( templeteScript, forStTag, forEdTag );
			prefixStr = StringUtils.substringBefore( templeteScript, forStTag );
			suffixStr = StringUtils.substringAfter( templeteScript, forEdTag );
			// oriTempStr = concat( forStTag, forTempleateStr, forEdTag );
			// 分批展開
			replacedStr = expandByBatch( forTempleateStr, cntTag, evenOddTag, dataListMap, expandNo );
			templeteScript = concat( prefixStr, replacedStr, suffixStr );
			// templeteScript = StringUtils.replace( templeteScript, oriTempStr, replacedStr );
		}

		return templeteScript;
	}

	/**
	 * @param templeteScript
	 * @return
	 */
	private static String importFileContent( String templeteScript ) {
		// 逐一處理<IMPORT> </IMPORT>間的文字
		while ( templeteScript.indexOf( IMPORT_TAG_STR ) >= 0 && templeteScript.indexOf( IMPORT_TAG_END_STR ) >= 0 ) {
			// 取得<IMPORT> </IMPORT>間的檔案路徑
			String fileName = StringUtils.substringBetween( templeteScript, IMPORT_TAG_STR, IMPORT_TAG_END_STR );
			String prefixStr = StringUtils.substringBefore( templeteScript, IMPORT_TAG_STR );
			String suffixStr = StringUtils.substringAfter( templeteScript, IMPORT_TAG_END_STR );
			// 讀取檔案
			File file = new File( TemplateClassRoot, fileName );
			String replacedStr;
			try {
				replacedStr = FileUtils.readFileToString( file, UTF8_STR );
				templeteScript = concat( prefixStr, replacedStr, suffixStr );
			} catch ( IOException e ) {
				templeteScript = concat( prefixStr, BLANK_STR, suffixStr );
				log.error( e.getMessage(), e );
				break;
			}
		}
		return templeteScript;
	}

	private static String expandByBatch( String forTempleateStr, String cntTag, String evenOddTag, List<Map<String, Object>> dataListMap, int expandNO ) {
		String resultStr = BLANK_STR;
		if ( dataListMap != null && dataListMap.size() > 0 ) {
			// 資料筆數
			int dataSize = dataListMap.size();
			// 1000件一批
			int NumberPerBatchCnt = 1000;
			// 最後一批剩餘件數
			int remainCnt = dataSize % NumberPerBatchCnt;
			// 批次數
			int bacthCnt = ( dataSize - remainCnt ) / NumberPerBatchCnt;
			for ( int batchNO = 1; batchNO <= bacthCnt; batchNO++ ) {
				resultStr = concat( resultStr, expandByBatch( forTempleateStr, cntTag, evenOddTag, ( batchNO - 1 ) * NumberPerBatchCnt, batchNO * NumberPerBatchCnt - 1, dataListMap ) );
			}
			// 最後一批
			resultStr = concat( resultStr, expandByBatch( forTempleateStr, cntTag, evenOddTag, bacthCnt * NumberPerBatchCnt, dataSize - 1, dataListMap ) );
		} else {
			resultStr = expandByBatch( forTempleateStr, cntTag, evenOddTag, 0, expandNO - 1, null );
		}
		return resultStr;
	}

	/**
	 * @param forTempleateStr
	 * @param cntTag
	 * @param evenOddTag
	 * @param startIdx
	 * @param endIdx
	 * @param dataListMap
	 * @return
	 */
	private static String expandByBatch( String forTempleateStr, String cntTag, String evenOddTag, int startIdx, int endIdx, List<Map<String, Object>> dataListMap ) {
		String tempStr = null;
		String replacedStr = BLANK_STR;
		// 展開
		for ( int idx = startIdx + 1; idx <= endIdx + 1; idx++ ) {
			// 將#CNT#以數字i取代
			tempStr = StringUtils.replace( forTempleateStr, cntTag, String.valueOf( idx ) );
			// 額外加一個屬性EVEN_ODD=0:偶數列/1:奇數列
			// 將#EVEN_ODD#以數字i%2取代
			tempStr = StringUtils.replace( tempStr, evenOddTag, idx % 2 == 0 ? DIGITAL_0_STR : DIGITAL_1_STR );
			replacedStr = concat( replacedStr, tempStr, NEW_LINE_STR );
		}
		if ( dataListMap != null && dataListMap.size() > 0 ) {
			Map<String, Object> dataMap = listMap2SingleMap( dataListMap, startIdx, endIdx );
			replacedStr = replaceByMap( replacedStr, dataMap );
			dataMap.clear();
		}
		return replacedStr;
	}
//-------------------------------------------------------------------------------------------------------------------

	/**
	 * @param str
	 * @return
	 */
	public static String concat( Object... str ) {
		return concatWithDel( BLANK_STR, str );
	}

	/**
	 * @param delimeter
	 * @param str
	 * @return
	 */
	public static String concatWithDel( String delimeter, Object... str ) {
		StringBuilder sb = new StringBuilder( delimeter );
		for ( Object s : str ) {
			sb.append( s );
			sb.append( delimeter );
		}
		return sb.toString();
	}

	/**
	 * @param partern_str
	 * @param rpStrMap
	 * @return
	 */
	public static String replaceByMap( String partern_str, Map<String, Object> rpStrMap ) {
		return replaceByMap( partern_str, rpStrMap, AT_SIGN_STR );
	}

	/**
	 * @param partern_str
	 * @param rpStrMap
	 * @param removeUnusedPara
	 * @return
	 */
	public static String replaceByMap( String partern_str, Map<String, Object> rpStrMap, boolean removeUnusedPara ) {
		if ( removeUnusedPara ) {
			String result = replaceByMap( partern_str, rpStrMap, AT_SIGN_STR, removeUnusedPara );
			result = removeUnusedPara( result, AT_SIGN_STR );
			result = result.replaceAll( THREE_POUND_STR, AT_SIGN_STR );
			return result;
		} else {
			return replaceByMap( partern_str, rpStrMap, AT_SIGN_STR );
		}
	}

	/**
	 * @param partern_str
	 * @param rpStrMap
	 * @param pre_suffix_sign
	 * @return
	 */
	public static String replaceByMap( String partern_str, Map<String, Object> rpStrMap, String pre_suffix_sign ) {
		return replaceByMap( partern_str, rpStrMap, pre_suffix_sign, true );
	}

	/**
	 * @param partern_str
	 * @param rpStrMap
	 * @param pre_suffix_sign
	 * @param removeUnusedPara
	 * @return
	 */
	private static String replaceByMap( String partern_str, Map<String, Object> rpStrMap, String pre_suffix_sign, boolean removeUnusedPara ) {
		String result = partern_str;
		String value = null;
		for ( String key : rpStrMap.keySet() ) {
			value = MapUtils.getString( rpStrMap, key, NULL_STR );
			if ( removeUnusedPara && value.indexOf( pre_suffix_sign ) >= 0 ) {
				value = value.replaceAll( pre_suffix_sign, THREE_POUND_STR );
			}
			result = StringUtils.replace( result, concat( pre_suffix_sign, key, pre_suffix_sign ), value );
		}
		return result;
	}

	/**
	 * @param partern_str
	 * @param rp_obj_ary
	 * @return
	 */
	public static String replaceBySequenceArray( String partern_str, Object... rp_obj_ary ) {
		String result = partern_str;
		if ( rp_obj_ary != null ) {
			int idx = 1;
			for ( Object obj : rp_obj_ary ) {
				result = result.replace( concat( AT_SIGN_STR, Integer.valueOf( idx ), AT_SIGN_STR ), obj != null ? obj.toString() : NULL_STR );
				++idx;
			}
		}
		return result;
	}

	/**
	 * @param result
	 * @param pre_suffix_sign
	 * @return
	 */
	private static String removeUnusedPara( String result, String pre_suffix_sign ) {
		// 取得[]內的內容,不含前後的中括弧
		String contentWithoutBracket = StringUtils.substringBetween( result, LEFT_BRACKET2_STR, RIGHT_BRACKET2_STR );
		// 取得[]內的內容,含前後的中括弧
		String option_clause = concat( LEFT_BRACKET2_STR, contentWithoutBracket, RIGHT_BRACKET2_STR );
		do {
			if ( contentWithoutBracket == null ) {
				break;
			}
			// 如果option_clauseWithoutBracket有指定的字元
			if ( contentWithoutBracket.indexOf( pre_suffix_sign ) >= 0 ) {
				// 連前後中括號都一併移除
				result = StringUtils.replace( result, option_clause, BLANK_STR );
			} else {
				// 去除前後中括號,只留下內容
				result = StringUtils.replace( result, option_clause, contentWithoutBracket );
			}
			contentWithoutBracket = StringUtils.substringBetween( result, LEFT_BRACKET2_STR, RIGHT_BRACKET2_STR );
			option_clause = concat( LEFT_BRACKET2_STR, contentWithoutBracket, RIGHT_BRACKET2_STR );

		} while ( contentWithoutBracket != null && contentWithoutBracket.length() > 0 );

		while ( result.indexOf( TWO_NEW_LINE_STR ) >= 0 ) {
			result = StringUtils.replace( result, TWO_NEW_LINE_STR, NEW_LINE_STR );
		}

		return result;
	}

	/*
	 * public static String newString( String str ) { if ( StringUtils.isBlank( str ) ) { return null; } else { return new String( str ); } } private static Map<String, Object> listMap2SingleMap(
	 * List<Map<String, Object>> dataListMap ) { return listMap2SingleMap( dataListMap, 0, dataListMap.size() - 1 ); } /**
	 * @param dataListMap
	 * @param startIdx
	 * @param endIdx
	 * @return
	 */
	private static Map<String, Object> listMap2SingleMap( List<Map<String, Object>> dataListMap, int startIdx, int endIdx ) {
		Map<String, Object> result_map = new HashMap<String, Object>();
		String newKey = null;
		Map<String, Object> temp_map = null;
		for ( int idx = startIdx; idx <= endIdx; idx++ ) {
			temp_map = dataListMap.get( idx );
			for ( String key : temp_map.keySet() ) {
				newKey = concat( key, idx + 1 );
				result_map.put( newKey, MapUtils.getString( temp_map, key ) );
			}
		}
		// log.info( result_map );
		return result_map;
	}

	/**
	 * 回傳Date,並將HH:MM:SS.SSS reset為hour:00:00.000
	 * 
	 * @return
	 */
	public static Date getWorkHour( int hour ) {
		return getWorkDate( hour, 0, 0, 0 );
	}

	/**
	 * 回傳Date,並將HH:MM:SS.SSS reset為hour:min:00.000
	 * 
	 * @param hour
	 * @param min
	 * @return
	 */
	public static Date getWorkHourMin( int hour, int min ) {
		return getWorkDate( hour, min, 0, 0 );
	}

	/**
	 * 回傳Date,並將SS.SSS reset為00.000
	 * 
	 * @return
	 */
	public static Date getWorkDateHHMM() {
		return getWorkDate( null, null, 0, 0 );
	}

	/**
	 * 回傳Date,並將HH:MM:SS.SSS reset為00:00:00.000
	 * 
	 * @return
	 */
	public static Date getWorkDate() {
		return getWorkDate( 0, 0, 0, 0 );
	}

	/**
	 * 回傳Date,並將HH:MM:SS.SSS reset為hour:min:sec.milsec
	 * 
	 * @param hour
	 * @param min
	 * @param sec
	 * @param milsec
	 * @return
	 */
	public static Date getWorkDate( Integer hour, Integer min, Integer sec, Integer milsec ) {
		Calendar cal = Calendar.getInstance();
		if ( hour != null ) {
			cal.set( Calendar.HOUR_OF_DAY, hour );
		}
		if ( min != null ) {
			cal.set( Calendar.MINUTE, min );
		}
		if ( sec != null ) {
			cal.set( Calendar.SECOND, sec );
		}
		if ( milsec != null ) {
			cal.set( Calendar.MILLISECOND, milsec );
		}
		return cal.getTime();
	}

	/**
	 * @param objPrefix
	 * @param obj
	 */
	public static void logDebugDataObject( String objPrefix, Object obj ) {
		if ( log.isDebugEnabled() ) {
			log.debug( formatDataObject( objPrefix, obj, null, null ) );
		}
	}

	/**
	 * @param objPrefix
	 * @param obj
	 */
	public static void logInfoDataObject( String objPrefix, Object obj ) {
		if ( log.isInfoEnabled() ) {
			log.info( formatDataObject( objPrefix, obj, null, null ) );
		}
	}

	/**
	 * @param objPrefix
	 * @param obj
	 * @return
	 */
	public static String formatDataObject( String objPrefix, Object obj ) {
		return formatDataObject( objPrefix, obj, null, null );
	}

	/**
	 * @param objPrefix
	 * @param obj
	 * @param newLineBeforeAry
	 * @param newLineAfterAry
	 */
	public static String formatDataObject( String objPrefix, Object obj, String[] newLineBeforeAry, String[] newLineAfterAry ) {
		if ( newLineBeforeAry == null ) {
			newLineBeforeAry = DEFAULT_NEW_LINE_BEFORE_ARY;
		}
		if ( newLineAfterAry == null ) {
			newLineAfterAry = DEFAULT_NEW_LINE_AFTER_ARY;
		}
		String str = concat( objPrefix, obj );
		for ( String key : newLineBeforeAry ) {
			str = StringUtils.replace( str, key, concat( NEW_LINE_STR, key ) );
		}
		for ( String key : newLineAfterAry ) {
			str = StringUtils.replace( str, key, concat( key, NEW_LINE_STR ) );
		}
		return str;
	}

	/**
	 * 逐一處理listMap的map 新增資料至map
	 * 
	 * @param listMap
	 * @param key
	 * @param value
	 */
	public static void addMapKey( List<Map> listMap, String key, String value ) {
		for ( Map map : listMap ) {
			map.put( key, value );
		}
	}

	/**
	 * 逐一處理listMap的map 新增資料至map
	 * 
	 * @param listMap
	 * @param keyAddMap
	 */
	public static void addMapKey( List<Map> listMap, Map keyAddMap ) {
		for ( Map map : listMap ) {
			map.putAll( keyAddMap );
		}
	}

	/**
	 * 逐一處理listMap的map 將key換掉
	 * 
	 * @param listMap
	 * @param keyChangeMap
	 */
	public static void changeMapKey( List<Map> listMap, Map<String, String> keyChangeMap ) {
		for ( Map map : listMap ) {
			changeMapKey( map, keyChangeMap );
		}
	}

	/**
	 * 根據specKeyList的指定key 從oriMap copy其value 放到copyKeyMap
	 * 
	 * @param oriMap
	 * @param specKeyList
	 * @return
	 */
	public static Map<String, Object> copyMapKey( Map<String, Object> oriMap, List<String> specKeyList ) {
		return copyMapKey( oriMap, specKeyList.toArray( new String[] {} ) );
	}

	/**
	 * 根據specKeyList的指定key 從oriMap copy其value 放到copyKeyMap
	 * 
	 * @param oriMap
	 * @param specKeyAry
	 * @return
	 */
	public static Map<String, Object> copyMapKey( Map<String, Object> oriMap, String[] specKeyAry ) {
		Map<String, Object> copyKeyMap = new HashMap<String, Object>();
		String value = null;
		for ( String key : specKeyAry ) {
			copyKeyMap.put( key, MapUtils.getString( oriMap, key, BLANK_STR ) );
		}
		return copyKeyMap;
	}

	/**
	 * 將Map的key換掉
	 * 
	 * @param map
	 * @param keyChangeMap
	 */
	public static void changeMapKey( Map<String, Object> map, Map<String, String> keyChangeMap ) {
		String newKey = null;
		for ( String oldKey : keyChangeMap.keySet() ) {
			newKey = keyChangeMap.get( oldKey );
			if ( StringUtils.isNotBlank( newKey ) ) {
				map.put( newKey, map.get( oldKey ) );
			}
			map.remove( oldKey );
		}
	}

	/**
	 * 取得listMap內指定欄位
	 * 
	 * @param listMap
	 * @param key 將指定欄位Key
	 * @return List<keyValue>
	 */
	public static List<String> getSpecifiedKeyValueList( List<Map<String, Object>> listMap, String key ) {
		List<String> resultList = new ArrayList<String>();
		String keyValue = null;
		for ( Map<String, Object> map : listMap ) {
			keyValue = MapUtils.getString( map, key );
			if ( StringUtils.isNotBlank( keyValue ) ) {
				resultList.add( keyValue );
			}
		}
		return resultList;
	}

	/**
	 * 依指定的key分組
	 * 
	 * @param listMap
	 * @param key
	 * @return Map<String , List<Map>>
	 */
	public static Map<String, List<Map>> groupBySpecifiedKey( List<Map> listMap, String key ) {
		// 依指定的key分組
		Map<String, List<Map>> resultMap = new HashMap<String, List<Map>>();
		List<Map> list = null;
		String keyValue = null;
		for ( Map<String, String> map : listMap ) {
			keyValue = MapUtils.getString( map, key, BLANK_STR );

			if ( resultMap.containsKey( keyValue ) ) {
				list = resultMap.get( keyValue );
			} else {
				list = new ArrayList<Map>();
			}
			list.add( map );
			resultMap.put( keyValue, list );
		}
		return resultMap;
	}

	/**
	 * 逐一檢查listMap中的每一個map 用key取得value,檢查value是否有在valueList Y:將map放入resultListMap
	 * 
	 * @param listMap
	 * @param key
	 * @param valueList
	 * @return resultListMap
	 */
	public static List<Map> filterByList( List<Map> listMap, String key, List<String> valueList ) {
		String value = null;
		List<Map> resultListMap = new ArrayList<Map>();
		for ( Map map : listMap ) {
			value = MapUtils.getString( map, key );
			if ( valueList.contains( value ) ) {
				resultListMap.add( map );
			}
		}
		return resultListMap;
	}

	/**
	 * @param listMap
	 * @param groupKeyArray
	 * @param valueName
	 * @return
	 */
	public static String getGroupKeyInfo( Map<String, Object> map, String[] groupKeyArray ) {
		String info = BLANK_STR;
		for ( String key : groupKeyArray ) {
			info = concat( info, key, EQUAL_STR, map.get( key ), ONE_BLANK_STR );
		}
		return info;
	}

	/**
	 * @param map
	 * @param groupKeyArray
	 * @return
	 */
	public static String getMapGroupKey( Map<String, Object> map, String[] groupKeyArray ) {
		String key = BLANK_STR;
		for ( String str : groupKeyArray ) {
			key = concat( key, POUND_STR, map.get( str ) );
		}
		return key;
	}

	/**
	 * @param resultOrderListMap
	 */
	public static void removeNullValueItem( List<Map<String, Object>> resultOrderListMap ) { // 移除map中沒有null的key
		Set<Object> keySet = null;
		for ( Map<String, Object> map : resultOrderListMap ) {
			keySet = new HashSet<Object>();
			for ( Object key : map.keySet() ) {
				if ( map.get( key ) == null ) {
					keySet.add( key );
				}
			}
			for ( Object key : keySet ) {
				map.remove( key );
			}
		}
	}

	public static void trimValue( Map<String, Object> map ) {
		trimValue( map, null );
	}

	public static void trimValue( Map<String, Object> map, String[] specifiedKeyAry ) {
		Object vle = null;
		if ( specifiedKeyAry == null || specifiedKeyAry.length == 0 ) {
			specifiedKeyAry = map.keySet().toArray( new String[] {} );
		}
		for ( String key : specifiedKeyAry ) {
			vle = map.get( key );
			// 字串要將資料Trim
			if ( vle != null && vle instanceof String ) {
				map.put( key, StringUtils.trim( ( String ) vle ) );
			}
		}
	}

	/**
	 * @param listOrder
	 * @param oldKey
	 * @param newKey
	 * @param valueMapping
	 * @throws Exception
	 */
	public static void transFieldValue( List<Map<String, Object>> listOrder, String oldKey, String newKey, Map<String, String> valueMapping ) throws Exception {
		if ( CollectionUtils.isNotEmpty( listOrder ) ) {
			if ( StringUtils.isBlank( oldKey ) || StringUtils.isBlank( newKey ) ) {
				throw new Exception( "oldKey or newKey為null或空值" );
			}
			String oldValue = null;
			String newValue = null;
			for ( Map<String, Object> currentOrderMap : listOrder ) {
				oldValue = MapUtils.getString( currentOrderMap, oldKey, BLANK_STR );
				newValue = transValue( valueMapping, oldValue );
				currentOrderMap.put( newKey, newValue );
			}
		} else {
			throw new Exception( "listOrder為null或空值" );
		}
	}

	/**
	 * @param valueMapping
	 * @param oldValue
	 * @return
	 * @throws Exception
	 */
	public static String transValue( Map<String, String> valueMapping, String oldValue ) {
		if ( valueMapping == null || valueMapping.size() == 0 ) {
			log.info( "轉換失敗!valueMapping不得為null或空值" );
			return oldValue;
		} else {
			// 依oldValue取得對應值
			// 如果對應不到依Default找預設值
			// 如果還是對應不到,回傳原值oldValue
			return MapUtils.getString( valueMapping, oldValue, MapUtils.getString( valueMapping, Default_STR, oldValue ) );
		}
	}

	/**
	 * 將LIST分堆
	 * 
	 * @param list 欲分堆的LIST
	 * @param subCount 幾筆分一堆
	 * @return
	 */
	public static List<List> subList( List list, int subCount ) {
		List<List> resultList = new ArrayList<List>();
		int groupCnt = list.size() / subCount;
		groupCnt = list.size() % subCount == 0 ? groupCnt : groupCnt + 1;

		for ( int j = 0; j < groupCnt; j++ ) {
			List subList = new ArrayList();
			resultList.add( subList );
		}

		int i = 0;
		for ( Object ob : list ) {
			i++;
			int countMod = i % groupCnt;
			resultList.get( countMod ).add( ob );
		}
		return resultList;
	}

	/**
	 * @param originalList
	 * @param batchSize
	 * @return
	 */
	public static List<List> getSubList( List originalList, int batchSize ) {
		List subList = new ArrayList();
		int listSize = originalList.size();
		log.info( "原List的筆數:" + listSize );
		log.info( "每批件數:" + batchSize );
		int batchCnt = listSize / batchSize;
		batchCnt = listSize % batchSize == 0 ? batchCnt : batchCnt + 1;
		int startIdx = 0;
		int endIdx = 0;
		for ( int idx = 0; idx < batchCnt; idx++ ) {
			startIdx = idx * batchSize;
			endIdx = ( idx + 1 ) * batchSize;
			endIdx = endIdx > listSize ? listSize : endIdx;
			subList.add( originalList.subList( startIdx, endIdx ) );
		}
		log.info( "分批數:" + subList.size() );
		// log.info( "subList:"+subList );
		return subList;
	}

	/**
	 * @param List
	 * @param RegExp
	 * @return
	 */
	public static String[] checkAryElement( String[] inputAry, String regExp ) {
		String[] resultAry = EMPTY_STRING_ARRAY;
		if ( inputAry != null && inputAry.length > 0 ) {
			for ( String str : inputAry ) {
				if ( str.matches( regExp ) ) {
					resultAry = ArrayUtils.add( resultAry, str );
				} else {
					log.error( "str:" + str + "不符合" + regExp + "格式!!!" );
				}
			}
		}
		return resultAry;
	}

	public static boolean isOracleDB() {
		return "oracle".equals( DB_INSTANCE.toLowerCase() );
	}

	public static boolean isSqlserverDB() {
		return "sqlserver".equals( DB_INSTANCE.toLowerCase() );
	}

	public static boolean isLocal() {
		return "local".equals( ENV.toLowerCase() );
	}

	public static boolean isTest() {
		return "test".equals( ENV.toLowerCase() );
	}

	public static boolean isProd() {
		return "prod".equals( ENV.toLowerCase() );
	}

	public static String getDB_INSTANCE() {
		return DB_INSTANCE;
	}

	public static void setDB_INSTANCE( String dB_INSTANCE ) {
		DB_INSTANCE = StringUtils.isEmpty( DB_INSTANCE ) ? dB_INSTANCE : DB_INSTANCE;
	}

	public static String getENV() {
		return ENV;
	}

	public static void setENV( String eNV ) {
		ENV = StringUtils.isEmpty( ENV ) ? eNV : ENV;
	}

	/**
	 * @return the hEADER_MAP
	 */
	public static Map<String, String> getHEADER_MAP() {
		return HEADER_MAP;
	}

	/**
	 * @param hEADER_MAP the hEADER_MAP to set
	 */
	public static void setHEADER_MAP( Map<String, String> hEADER_MAP ) {
		HEADER_MAP = hEADER_MAP;
	}

	/**
	 * @return the sEND_TYPE_MAP
	 */
	public static Map<String, String> getSEND_TYPE_MAP() {
		return SEND_TYPE_MAP;
	}

	/**
	 * @param sEND_TYPE_MAP the sEND_TYPE_MAP to set
	 */
	public static void setSEND_TYPE_MAP( Map<String, String> sEND_TYPE_MAP ) {
		SEND_TYPE_MAP = sEND_TYPE_MAP;
	}

	/**
	 * @return the mSG_SEND_TYPE_MAP
	 */
	public static Map<String, String> getMSG_SEND_TYPE_MAP() {
		return MSG_SEND_TYPE_MAP;
	}

	/**
	 * @param mSG_SEND_TYPE_MAP the mSG_SEND_TYPE_MAP to set
	 */
	public static void setMSG_SEND_TYPE_MAP( Map<String, String> mSG_SEND_TYPE_MAP ) {
		MSG_SEND_TYPE_MAP = mSG_SEND_TYPE_MAP;
	}

	/**
	 * EMP_ID==>EmpId
	 * 
	 * @param str
	 * @return
	 */
	public static String toCamel( String str ) {
		return CaseFormat.UPPER_UNDERSCORE.to( CaseFormat.UPPER_CAMEL, str );
	}

	/**
	 * 把map的某一個key作轉換 EMP_ID==>EmpId
	 * 
	 * @param map
	 * @param key
	 */
	public static void toCamelKey( Map<String, Object> map, String key ) {
		String newKey = null;
		Set<String> keySet = new HashSet<String>( map.keySet() );
		for ( String oldkey : keySet ) {
			newKey = toCamel( oldkey );
			map.put( newKey, map.remove( oldkey ) );
		}
	}

	/**
	 * 把map的某一個key的值轉換 EMP_ID==>EmpId
	 * 
	 * @param map
	 * @param key
	 */
	public static void toCamelValue( Map<String, Object> map, String key ) {
		String value = toCamel( MapUtils.getString( map, key ) );
		map.put( key, value );
	}

	/**
	 * EMP_ID==>empId
	 * 
	 * @param str
	 * @return
	 */
	public static String tocamel( String str ) {
		String CamelStr = toCamel( str );
		String firstChar = StringUtils.substring( CamelStr, 0, 1 ).toLowerCase();
		String camelStr = FormatUtils.concat( firstChar, StringUtils.substring( CamelStr, 1 ) );
		return camelStr;
	}

	/**
	 * 把map的某一個key作轉換 EMP_ID==>empId
	 * 
	 * @param map
	 * @param key
	 */
	public static void tocamelKey( Map<String, Object> map, String key ) {
		String newKey = null;
		Set<String> keySet = new HashSet<String>( map.keySet() );
		for ( String oldkey : keySet ) {
			newKey = tocamel( oldkey );
			map.put( newKey, map.remove( oldkey ) );
		}
	}

	/**
	 * 把map的某一個key的值轉換 EMP_ID==>empId
	 * 
	 * @param map
	 * @param oldkey
	 */
	public static void tocamelValue( Map<String, Object> map, String key ) {
		String value = tocamel( MapUtils.getString( map, key ) );
		map.put( key, value );
	}

	/**
	 * @param errorMsgList
	 * @return
	 */
	public static String formatErrorMsgList2String( List<String> errorMsgList ) {
		return formatErrorMsgList2String( errorMsgList, BR_STR );
	}

	/**
	 * @return
	 */
	public static String formatErrorMsgList2String( List<String> errorMsgList, String endWithStr ) {
		String errorMsg = BLANK_STR;
		int idx = 1;
		for ( String str : errorMsgList ) {
			errorMsg = errorMsg.concat( String.valueOf( idx ) );
			errorMsg = errorMsg.concat( DOT_STR );
			errorMsg = errorMsg.concat( str );
			errorMsg = errorMsg.concat( endWithStr );
			idx++;
		}
		log.info( errorMsg );
		return errorMsg;
	}

	
	/**
	 * @param d
	 * @param formatPattern
	 * @return
	 */
	public static String formatDateStr(Date d,String formatPattern){
		SimpleDateFormat dt = new SimpleDateFormat(formatPattern); 
		return dt.format(d);		
	}
	/**
	 * @param strPattern
	 * @param paraMap
	 * @return
	 */
/*	
	public static String replaceByParaMap(String strPattern,Map<String,String> paraMap){
		String resultStr=strPattern;
		for(String key:paraMap.keySet()){
			resultStr=resultStr.replaceAll("@"+key+"@", paraMap.get(key));
		}
		return resultStr;
	}
*/	
	/**
	 * @param strList
	 * @return
	 */	
	public static List<String> subStringBetweenParenthesis(List<String>strList){
		List<String> newStrList=new ArrayList<String>();
		for(String str:strList){
			newStrList.add(subStringBetweenParenthesis(str));
		}
		return newStrList;
	}
	/**
	 * @param str
	 * @return
	 */	
	public static String subStringBetweenParenthesis(String str){
		return subString(str, "(", ")");
	}
	/**
	 * @param strList
	 * @param prefixStr
	 * @param suffixStr	 
	 * @return
	 */
	public static List<String> subString(List<String>strList,String prefixStr,String suffixStr){
		List<String> newStrList=new ArrayList<String>();
		for(String str:strList){
			newStrList.add(subString(str,prefixStr,suffixStr));
		}
		return newStrList;
	}	
	/**
	 * @param str
	 * @param prefixStr
	 * @param suffixStr
	 * @return
	 */
	public static String subString(String str,String prefixStr,String suffixStr){
		//logger.info(str);
		if(str.indexOf(prefixStr)>=0 && str.indexOf(suffixStr)>=0){
			return StringUtils.substringBetween(str, prefixStr, suffixStr);
		}else{
			//logger.info("dsafsafa");
			return str;
		}	
	}
	/**
	 * @param strList
	 * @return
	 */
	public static List<String> removeChinesePart(List<String> strList){
		List<String> resultList=new ArrayList<String>();
		for(String str:strList){
			resultList.add(removeChinesePart(str));
		}
		return resultList;
	}
	/**
	 * @param str
	 * @return
	 */
	public static String getChinesePart(String str){
		String otherStr=removeChinesePart(str);
		//logger.info(str);
		//logger.info(otherStr);
		String resultStr=StringUtils.remove(str,otherStr);
		//logger.info("「"+str+"」 getChinesePart==>"+resultStr);
		return resultStr;
	}
	/**
	 * @param str
	 * @return
	 */
	public static String removeChinesePart(String str){
		String resultStr=StringUtils.removePattern(str, "\\p{InCJK_UNIFIED_IDEOGRAPHS}{1,3}");
		//logger.info("「"+str+"」 removeChinesePart==>"+resultStr);
		return resultStr;
	}
	/**
	 * @param str
	 * @return
	 */
	public static boolean isNumericStr(String str){
		Pattern p = Pattern.compile( "([0-9]*)\\.*[0-9]*" );
		Matcher m = p.matcher(str);
		return m.matches();
	}
}
