/**
 * String 유틸리티
 */
package kr.pe.deverexpert.common.util;

/**
 * @author 이재형
 *  
 */
public class StringUtil {
	
	/**
	 * 입력 문자열을 입력 길이만큼 parttern문자를 왼쪽에 채워 리턴
	 * 
	 * @param str 입력 문자열
	 * @param parttern 채울 문자
	 * @param length 총 길이
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = StringUtil.getLPad("123", "0", 5);
     *  결과 : 00123
     * </pre>
	 * 
	 */
	public static String getLPad(String str, String parttern, int length) {
		
		String strTmp = "";
		int nStr = str.length();
		
		if ( parttern.length() != 1 ) { return ""; }
		if ( nStr == length ) { return str; }
		if ( nStr > length ) { return ""; }
		
		for ( int inx = 0 ; inx < (length - nStr) ; inx++ ) {
			strTmp += parttern;
		}
		
		return strTmp + str;
	}

	/**
	 * 입력 문자열을 입력 길이만큼 parttern문자를 오른쪽에 채워 리턴
	 * 
	 * @param str 입력 문자열
	 * @param parttern 채울 문자
	 * @param length 총 길이
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = StringUtil.getRPad("123", "0", 5);
     *  결과 : 12300
     * </pre>
	 */
	public static String getRPad(String str, String parttern, int length) {
		
		String strTmp = "";
		int nStr = str.length();
		
		if ( parttern.length() != 1 ) { return ""; }
		if ( nStr == length ) { return str; }
		if ( nStr > length ) { return ""; }
		
		for ( int inx = 0 ; inx < (length - nStr) ; inx++ ) {
			strTmp += parttern;
		}
		
		return str + strTmp;
	}
	/**
	 * 입력 문자열이 NULL인지 체크
	 * 
	 * @param str NULL 체크 문자열
	 * @return boolean
	 * 
     * <p><pre> 
     *  - 사용 예
     *        boolean date = StringUtil.isNull(null);
     *  결과 : true
     * </pre>
	 */
	public static boolean isNull(String str) {
		
		boolean nRet = false;
		
		if ( str == null ) { nRet = true; }
		
		return nRet;
	}
	
	/**
	 * 입력 문자열이 NULL인지 체크하여 문자열 대체
	 * 
	 * @param str NULL 체크 문자열
	 * @param changeStr 대체 문자열
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = StringUtil.ifNull(null, "Empty");
     *  결과 : Empty
     * </pre>
	 */
	public static String ifNull(String str, String changeStr) {
		
		String retStr = "";
		if ( isNull(str) && !isNull(changeStr) ) { 
			retStr = changeStr; 
		} else {
			retStr = null;
		}
		
		return retStr;
	}
	
	/**
	 * 입력 문자열이 비어있는지 체크
	 * 
	 * @param str 입력 문자열
	 * @return boolean
	 * 
     * <p><pre> 
     *  - 사용 예
     *        boolean date = StringUtil.isEmpty("");
     *  결과 : true
     * </pre>
	 */
	public static boolean isEmpty(String str) {
		boolean nRet = false;
		if ( str.equals("") ) { nRet = true; }
		return nRet;
	}
	
	/**
	 * 입력 문자열을 trim하여 비어있는지 체크
	 * 
	 * @param str 입력 문자열
	 * @return boolean
	 * 
     * <p><pre> 
     *  - 사용 예
     *        boolean date = StringUtil.isEmptyTrim("    ");
     *  결과 : true
     * </pre>
	 */
	public static boolean isEmptyTrim(String str) {
		return isEmpty(str.trim());
	}
	
	/**
	 * 문자열을 왼쪽에서 부터 짤라온다
	 * 
	 * @param str 문자열
	 * @param index 인덱스
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        boolean date = StringUtil.getLeft("ABCDEFGHI", 3);
     *  결과 : ABC
     * </pre>
	 */
	public static String getLeft(String str, int index) {
		return str.substring(0, index);
	}
	
	/**
	 * 문자열을 오른쪽에서 부터 짤라온다
	 * 
	 * @param str 문자열
	 * @param index 인덱스
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        boolean date = StringUtil.getRight("ABCDEFGHI", 3);
     *  결과 : GHI
     * </pre>
	 */
	public static String getRight(String str, int index) {
		return str.substring(str.length() - index, str.length());
	}
	
	/**
	 * 문자열 가운데를 짤라온다
	 * 
	 * @param str 문자열
	 * @param startindex 시작인덱스
	 * @param chrcount 문자개수
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        boolean date = StringUtil.getMid("ABCDEFGHI", 4, 3);
     *  결과 : DEF
     * </pre>
	 */
	public static String getMid(String str, int startindex, int chrcount) {
		return str.substring(startindex - 1, startindex - 1 + chrcount);
	}

	/**
	 * 문자열의 길이가 length와 같은지 체크
	 * 
	 * @param str 입력문자열
	 * @param length 문자열길이
	 * @return boolean
	 * 
     * <p><pre> 
     *  - 사용 예
     *        boolean date = StringUtil.isLengh("ABCDEFGHI", 9);
     *  결과 : true
     * </pre>
	 */
	public static boolean isLengh(String str, int length) {
		boolean nRet = false;
		if ( str.length() == length ) {
			nRet = true;
		}
		return nRet;
	}

}
