/**
 * 날짜, 시각 유틸리티
 */
package kr.pe.deverexpert.common.util;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 이재형
 *  
 */
public class DateTimeUtil {

	/** 요일 상수 */
	private static final String[] DAY = {"일", "월", "화", "수", "목", "금", "토" };

	/**
	 * 입력받은 parttern의 형태로 DateTime 리턴
	 * 
	 * @param pattern yyyyMMddHHmmssSSS형식의 패턴
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getDateByPattern("yyyyMMddHHmmssSSS");
     *  결과 : 20080719153048357
     * </pre>
	 */
	public static String getDateByPattern(String pattern) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (pattern, java.util.Locale.KOREA);
		String dateString = formatter.format(new java.util.Date());
		return dateString;
	}
	
	/**
	 * 현재일자의 요일을 리턴
	 * 
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getDayOfWeek();
     *  결과 : 토
     * </pre>
	 */
    public static String getDayOfWeek() {
	    Calendar c = Calendar.getInstance();
	    return DAY[c.get(java.util.Calendar.DAY_OF_WEEK)-1];
	}

    /**
     * 입력받은 일자의 요일을 리턴
     * 
     * @param date yyyyMMdd형식의 날짜
     * @return String
     * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getDayOfWeek("20080719");
     *  결과 : 토
     * </pre>
     */
    public static String getDayOfWeek(String date) {
		
    	String sYear = StringUtils.left(date, 4);
		String sMonth = StringUtils.mid(date, 5, 2);
		String sDay = StringUtils.right(date, 2);

		Calendar cal = Calendar.getInstance();
		
		cal.set(Integer.parseInt(sYear), Integer.parseInt(sMonth) - 1, Integer.parseInt(sDay) );

	    return DAY[cal.get(java.util.Calendar.DAY_OF_WEEK)-1];
    }
	
    /**
     * 현재 년을 리런
     * 
     * @return String
     * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getYear();
     *  결과 : 2008
     * </pre>

     */
	public static String getYear() {
		return getDateByPattern("yyyy");
	}

	/**
	 * 현재 월을 리턴
	 * 
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getMonth();
     *  결과 : 07
     * </pre>
	 */
	public static String getMonth() {
		return getDateByPattern("MM");
	}
	
	/**
	 * 현재 일을 리턴
	 * 
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getDate();
     *  결과 : 19
     * </pre>
	 */
	public static String getDate() {
		return getDateByPattern("dd");
	}
	
	/**
	 * 현재 시를 리턴
	 * 
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getHour();
     *  결과 : 15
     * </pre>
	 */
	public static String getHour() {
		return getDateByPattern("HH");
	}

	/**
	 * 현재 분을 리턴
	 * 
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getMinute();
     *  결과 : 30
     * </pre>
	 */
	public static String getMinute() {
		return getDateByPattern("mm");
	}

	/**
	 * 현재 초를 리턴
	 * 
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getSecond();
     *  결과 : 48
     * </pre>
	 */
	public static String getSecond() {
		return getDateByPattern("ss");
	}

	/**
	 * 현재 밀리초를 리턴
	 * 
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getMilliSecond();
     *  결과 : 357
     * </pre>
	 */
	public static String getMilliSecond() {
		return getDateByPattern("SSS");
	}

	/**
	 * 현재 날짜를 리턴
	 * 
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getDateString();
     *  결과 : 20080719
     * </pre>
	 */
	public static String getDateString() {
		return getDateString("");
	}
	
	/**
	 * 현재 날짜를 delimiter로 구분하여 리턴
	 * 
	 * @param delimiter yyyyMMdd를 구분 할 문자
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getDateString("-");
     *  결과 : 2008-07-19
     * </pre>
	 */
	public static String getDateString(String delimiter) {
		return getDateByPattern("yyyy" + delimiter + "MM" + delimiter + "dd");
	}

	/**
	 * 현재 시각을 리턴
	 * 
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getTimeString();
     *  결과 : 153048
     * </pre>
	 */
	public static String getTimeString() {
		return getTimeString("");
	}
	
	/**
	 * 현재 시각을 delimiter로 구분하여 리턴
	 * 
	 * @param delimiter HHmmss를 구분 할 문자
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getTimeString("-");
     *  결과 : 15:30:48
     * </pre>
	 */
	public static String getTimeString(String delimiter) {
		return getDateByPattern("HH" + delimiter + "mm" + delimiter + "ss");
	}
	
	/**
	 * 현재 시각을 리턴
	 * 
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getTimeStampString();
     *  결과 : 153048357
     * </pre>
	 */
	public static String getTimeStampString() {
		return getTimeStampString("", "");
	}
	
	/**
	 * 현재 시각을 timedelimiter와 milliseconddelimiter로 구분하여 리턴
	 * 
	 * @param timedelimiter HHmmss를 구분 할 문자
	 * @param milliseconddelimiter ssSSS를 구분 할 문자
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getTimeStampString(":", ".");
     *  결과 : 15:30:48.357
     * </pre>
	 */
	public static String getTimeStampString(String timedelimiter, String milliseconddelimiter) {
		return getDateByPattern("HH" + timedelimiter + "mm" + timedelimiter + "ss" + milliseconddelimiter + "SSS");
	}
	
	/**
	 * 입력 날짜를 delimiter로 구분하여 리턴
	 * 
	 * @param date yyyyMMdd형식의 날짜
	 * @param delimiter yyyyMMdd를 구분 할 문자
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getParseDateString("20080719", "-");
     *  결과 : 2008-07-19
     * </pre>
	 */
	public static String getParseDateString(String date, String delimiter) {
		if ( StringUtils.length(date) != 8 ) { return ""; }

		return StringUtils.left(date, 4) + delimiter + StringUtils.mid(date, 5, 2) + delimiter + StringUtils.right(date, 2);
	}

	/**
	 * 입력 시각을 delimiter로 구분하여 리턴
	 * 
	 * @param time HHmmss형식의 시각
	 * @param delimiter HHmmss를 구분 할 문자
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getParseTimeString("153048", ":");
     *  결과 : 15:30:48
     * </pre>
	 */
	public static String getParseTimeString(String time, String delimiter) {
		if ( StringUtils.length(time) != 6 ) { return ""; }
		return StringUtils.left(time, 2) + delimiter + StringUtils.mid(time, 3, 2) + delimiter + StringUtils.right(time, 2);
	}

	/**
	 * 입력 시각을 timedelimiter와 milliseconddelimiter로 구분하여 리턴
	 * 
	 * @param time HHmmssSSS형식의 시각
	 * @param timedelimiter HHmmss를 구분 할 문자
	 * @param milliseconddelimiter ssSSS를 구분 할 문자
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getParseTimeStampString("153048357", ":", ".");
     *  결과 : 15:30:48.357
     * </pre>
	 */
	public static String getParseTimeStampString(String time, String timedelimiter, String milliseconddelimiter) {
		if ( StringUtils.length(time) != 9 ) { return ""; }
		return StringUtils.left(time, 2) + timedelimiter + StringUtils.mid(time, 3, 2) + timedelimiter + StringUtils.mid(time, 5, 2) + milliseconddelimiter + StringUtils.right(time, 3);
	}
	
	/**
	 * 입력 년의 윤년 여부를 리턴 
	 * 
	 * @param year yyyy형식의 년
	 * @return boolean
	 * 
     * <p><pre> 
     *  - 사용 예
     *        boolean date = DateTime.isLeapYear("2008");
     *  결과 : true
     * </pre>
	 */
	public static boolean isLeapYear(String year) {
		int nRemain = 0;
		int nRemain_1 = 0;
		int nRemain_2 = 0;
		int nYear = Integer.parseInt(year);
		
		nRemain = nYear % 4;
		nRemain_1 = nYear % 100;
		nRemain_2 = nYear % 400;
		
		// the ramain is 0 when year is divided by 4;
		if (nRemain == 0) {
			// the ramain is 0 when year is divided by 100;
			if (nRemain_1 == 0) {
				// the remain is 0 when year is divided by 400;
				if (nRemain_2 == 0) return true;
				else return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 입력 날짜의 유효성 여부를 리턴
	 * 
	 * @param date yyyyMMdd형식의 날짜
	 * @return boolean
	 * 
     * <p><pre> 
     *  - 사용 예
     *        boolean date = DateTime.isLeapYear("20080719");
     *  결과 : true
     * </pre>
	 */
	public static boolean isDate(String date) {

		boolean nRet = true;
		
		int nYear = Integer.parseInt(StringUtils.left(date, 4));
		int nMonth = Integer.parseInt(StringUtils.mid(date, 5, 2));
		int nDay = Integer.parseInt(StringUtils.right(date, 2));
		
		int [] nDateMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		
		if (isLeapYear("" + nYear)) { nDateMonth[1] = 29; }
		if ( nDay > nDateMonth[nMonth-1] || nDay < 1) nRet = false;
		if ( nMonth < 1 || nMonth > 12) nRet = false;
		if ( nMonth % 1 != 0 || nYear % 1 != 0 || nDay % 1 != 0) nRet = false;

		return nRet;
	}
	
	/**
	 * 입력 받은 년, 월의 마지막 일수를 리턴
	 * 
	 * @param year yyyy형식의 년
	 * @param month MM형식의 월
	 * @return int
	 * 
     * <p><pre> 
     *  - 사용 예
     *        int date = DateTime.getLastMonthDate("2008", "07");
     *  결과 : 31
     * </pre>
	 */
	public static int getLastMonthDate(String year, String month) {
		
		int [] nDateMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		
		if (isLeapYear(year)) { nDateMonth[1] = 29; }
		
		int day = nDateMonth[Integer.parseInt(month) - 1];
		
		return day;
	}
	
	/**
	 * 초를 시분초로 리턴
	 * 
	 * @param second 초
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getSecondToTimeString(30);
     *  결과 : 000030
     * </pre>
	 */
	public static String getSecondToTimeString(int second) {
		return getSecondToTimeString(second, "");
	}
	
	/**
	 * 초를 시분초로 리턴
	 * 
	 * @param second 초
	 * @param delimiter HHmmss를 구분 할 문자
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getSecondToTimeString(30);
     *  결과 : 00:00:30
     * </pre>
	 */
	public static String getSecondToTimeString(int second, String delimiter) {
		
		String sHh = "";
		String sMm = "";
		String sSs = "";
				  
		sHh = "" + (second / 3600);
		sHh = StringUtils.leftPad(sHh, 2, "0");
				  
		sMm = "" + (second - (Integer.parseInt(sHh) * 3600)) / 60;
		sMm = StringUtils.leftPad(sMm, 2, "0");
				  
		sSs = "" + (second - (Integer.parseInt(sHh) * 3600) - (Integer.parseInt(sMm) * 60));
		sSs = StringUtils.leftPad(sSs, 2, "0");	  
		
		return sHh + delimiter + sMm + delimiter + sSs;
	}
	
	/**
	 * 밀리초를 시분초밀리초로 리턴
	 * 
	 * @param millisecond 밀리초
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getMilliSecondToTimeStampString(30);
     *  결과 : 000000030
     * </pre>
	 */
	public static String getMilliSecondToTimeStampString(int millisecond) {
		return getMilliSecondToTimeStampString(millisecond, "", "");
	}
	
	/**
	 * 밀리초를 시분초밀리초로 리턴
	 * 
	 * @param millisecond 밀리초
	 * @param timedelimiter HHmmss를 구분 할 문자
	 * @param milliseconddelimiter ssSSS를 구분 할 문자
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getMilliSecondToTimeStampString(30, ":", ".");
     *  결과 : 00:00:00.030
     * </pre>
	 */
	public static String getMilliSecondToTimeStampString(int millisecond, String timedelimiter, String milliseconddelimiter) {
		String tmpStr = "" + millisecond;
		String retStr = "";

		if ( tmpStr.length() > 3 ) {
			retStr = getSecondToTimeString(Integer.parseInt(StringUtils.left(tmpStr, tmpStr.length() - 3)), timedelimiter);
			retStr += milliseconddelimiter + StringUtil.getMid(tmpStr, tmpStr.length() - 2, tmpStr.length() - (tmpStr.length() - 3));
		} else {
			
			
			if ( StringUtils.length(tmpStr) == 1 ) {
				tmpStr = StringUtils.leftPad(tmpStr, 3, "0");
			} else if ( StringUtil.isLengh(tmpStr, 2) ) {
				tmpStr = StringUtils.leftPad(tmpStr, 2, "0");
			}
			
			retStr = "00" + timedelimiter + "00" + timedelimiter + "00" + milliseconddelimiter + tmpStr;
		}
		
		return retStr;
	}
	
	/**
	 * 입력 시각을 초로 리턴
	 * 
	 * @param time HHmmss형식의 시각
	 * @return int
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getTimeStringToSecond("011230");
     *  결과 : 4350
     * </pre>
	 */
	public static int getTimeStringToSecond(String time) {
		if ( StringUtils.length(time) != 6 ) { return 0; }
		
		int nRet = 0;
		
		int nHour = Integer.parseInt(StringUtils.left(time, 2));
		int nMinute = Integer.parseInt(StringUtils.mid(time, 3, 2));
		int nSecond = Integer.parseInt(StringUtils.right(time, 2));
		
		nRet += nHour * 3600;
		nRet += nMinute * 60;
		nRet += nSecond;
		
		return nRet;
	}	
	
	/**
	 * 입력 시각을 밀리초로 리턴
	 * 
	 * @param time HHmmssSSS형식의 시각
	 * @return int
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getTimeStampStringToMilliSecond("011230357");
     *  결과 : 4350357
     * </pre>
	 */
	public static int getTimeStampStringToMilliSecond(String timestamp) {
		if ( StringUtils.length(timestamp) != 9 ) { return 0; }
		return (getTimeStringToSecond(StringUtils.left(timestamp, 6)) * 1000) + Integer.parseInt(StringUtils.right(timestamp,3));
	}
	
	/**
	 * 입력 날짜에 년을 더한다
	 * 
	 * @param date yyyyMMdd형식의 날짜
	 * @param amount 년
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getAddYear("20080719", 5);
     *  결과 : 20130719
     * </pre>
	 */
	public static String getAddYear(String date, int amount) {
		
		if ( date.length() != 8 ) { return ""; }
		
		String sYear = StringUtils.left(date, 4);
		String sMonth = StringUtils.mid(date, 5, 2);
		String sDay = StringUtils.right(date, 2);
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Integer.parseInt(sYear), Integer.parseInt(sMonth) - 1, Integer.parseInt(sDay) );
		cal.add(Calendar.YEAR, amount);
		
		sYear = "" + (Integer.parseInt("" + cal.get(Calendar.YEAR)));
		sMonth = "" + (Integer.parseInt("" + cal.get(Calendar.MONTH)) + 1 );
		sDay = "" + (Integer.parseInt("" + cal.get(Calendar.DATE)));
		
		sMonth = StringUtils.leftPad(sMonth, 2, "0");
		sDay = StringUtils.leftPad(sDay, 2, "0");
		
		return sYear + sMonth + sDay;
	}
	
	/**
	 * 입력 날짜에 월을 더한다
	 * 
	 * @param date yyyyMMdd형식의 날짜
	 * @param amount 월
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getAddMonth("20080719", 5);
     *  결과 : 20081219
     * </pre>
	 */
	public static String getAddMonth(String date, int amount) {
		
		if ( StringUtils.length(date) != 8 ) { return ""; }
		
		String sYear = StringUtils.left(date, 4);
		String sMonth = StringUtils.mid(date, 5, 2);
		String sDay = StringUtils.right(date, 2);
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Integer.parseInt(sYear), Integer.parseInt(sMonth) - 1, Integer.parseInt(sDay) );
		cal.add(Calendar.MONTH, amount);
		
		sYear = "" + (Integer.parseInt("" + cal.get(Calendar.YEAR)));
		sMonth = "" + (Integer.parseInt("" + cal.get(Calendar.MONTH)) + 1);
		sDay = "" + (Integer.parseInt("" + cal.get(Calendar.DATE)));
		
		sMonth = StringUtils.leftPad(sMonth, 2, "0");
		sDay = StringUtils.leftPad(sDay, 2, "0");
		
		return sYear + sMonth + sDay;
	}
	
	/**
	 * 입력 날짜에 일을 더한다
	 * 
	 * @param date yyyyMMdd형식의 날짜
	 * @param amount 일
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getAddDate("20080719", 5);
     *  결과 : 20080724
     * </pre>
	 */
	public static String getAddDate(String date, int amount) {
		
		if ( StringUtils.length(date) != 8 ) { return ""; }
		
		String sYear = StringUtils.left(date, 4);
		String sMonth = StringUtils.mid(date, 5, 2);
		String sDay = StringUtils.right(date, 2);
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Integer.parseInt(sYear), Integer.parseInt(sMonth) - 1, Integer.parseInt(sDay) );
		cal.add(Calendar.DATE, amount);
		
		sYear = "" + (Integer.parseInt("" + cal.get(Calendar.YEAR)));
		sMonth = "" + (Integer.parseInt("" + cal.get(Calendar.MONTH)) + 1);
		sDay = "" + (Integer.parseInt("" + cal.get(Calendar.DATE)));

		sMonth = StringUtils.leftPad(sMonth, 2, "0");
		sDay = StringUtils.leftPad(sDay, 2, "0");
		
		return sYear + sMonth + sDay;
	}
	
	/**
	 * 입력 시각에 시간을 더한다.
	 * 
	 * @param time HHmmss형식의 시각
	 * @param amount 시간
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getAddHour("153048", 5);
     *  결과 : 203048
     * </pre>
	 */
	public static String getAddHour(String time, int amount) {
		
		if ( StringUtils.length(time) != 6 ) { return ""; }
		
		int nRet = getTimeStringToSecond(time);
		
		nRet += amount * 3600;
		
		return getSecondToTimeString(nRet);
	}
	
	/**
	 * 입력 시각에 분을 더한다.
	 * 
	 * @param time HHmmss형식의 시각
	 * @param amount 분
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getAddHour("153048", 5);
     *  결과 : 153548
     * </pre>
	 */
	public static String getAddMinute(String time, int amount) {
		
		if ( StringUtils.length(time) != 6 ) { return ""; }
		
		int nRet = getTimeStringToSecond(time);
		
		nRet += amount * 60;
		
		return getSecondToTimeString(nRet);
	}
	
	/**
	 * 입력 시각에 초를 더한다.
	 * 
	 * @param time HHmmss형식의 시각
	 * @param amount 초
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getAddHour("153048", 5);
     *  결과 : 153053
     * </pre>
	 */
	public static String getAddSecond(String time, int amount) {
		
		if ( StringUtils.length(time) != 6 ) { return ""; }

		int nRet = getTimeStringToSecond(time);
		
		nRet += amount;
		
		return getSecondToTimeString(nRet);
	}
	
	/**
	 * 두 날짜의 사이 일수를 구한다.
	 * 
	 * @param date_1 yyyyMMdd형식의 날짜
	 * @param date_2 yyyyMMdd형식의 날짜
	 * @return int
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getDateDiff("20080724", "20080719");
     *  결과 : 5
     * </pre>
	 */
	public static int getDateDiff(String date_1, String date_2) {
		
		int nYear, nMonth, nDay;
		
		nYear = Integer.parseInt(StringUtils.left(date_1, 4));
		nMonth = Integer.parseInt(StringUtils.mid(date_1, 5, 2));
		nDay = Integer.parseInt(StringUtils.right(date_1, 2));
		
		Calendar sCal = Calendar.getInstance();
		sCal.set(nYear, nMonth - 1, nDay );

		nYear = Integer.parseInt(StringUtils.left(date_2, 4));
		nMonth = Integer.parseInt(StringUtils.mid(date_2, 5, 2));
		nDay = Integer.parseInt(StringUtils.right(date_2, 2));

		Calendar eCal = Calendar.getInstance();
		eCal.set(nYear, nMonth - 1, nDay );

		long day = eCal.getTime().getTime() - sCal.getTime().getTime();
		
		return (int)(day/(1000*60*60*24));
	}
	
	/**
	 * 두 시각의 사이 초를 구한다.
	 * 
	 * @param starttime HHmmss형식의 시각
	 * @param endtime HHmmss형식의 시각
	 * @return int
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getTimeDiff("153053", "153048");
     *  결과 : 5
     * </pre>
	 */
	public static int getTimeDiff(String starttime, String endtime) {
		int nRet = getTimeStringToSecond(endtime) - getTimeStringToSecond(starttime);
		return nRet;
	}
	
	/**
	 * 두 yyyyMMddHHmmss의 사이 초를 구한다.
	 * 
	 * @param starttime yyyyMMddHHmmss형식의 시각
	 * @param endtime yyyyMMddHHmmss형식의 시각
	 * @return int
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getDateTimeDiff("20080724153053", "20080719153048");
     *  결과 : 345605
     * </pre>
	 */	
	public static int getDateTimeDiff(String startdatetime, String enddatetime) {

		int nDay = getDateDiff(StringUtils.left(startdatetime, 8), StringUtils.left(enddatetime, 8));
		int nTime = getTimeDiff(StringUtils.left(startdatetime, 6), StringUtils.left(enddatetime, 6));

		nDay = nDay * (60 * 60 * 24);
		
		
		return nDay + nTime;
	}

	/**
	 * 두 시각의 사이 밀리초를 구한다.
	 * 
	 * @param starttime HHmmssSSS형식의 시각
	 * @param endtime HHmmssSSS형식의 시각
	 * @return int
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getTimeDiff("011230357", "011230352");
     *  결과 : 5
     * </pre>
	 */
	public static int getTimeStampDiff(String starttimestamp, String endtimestamp) {
		int nRet = getTimeStampStringToMilliSecond(starttimestamp) - getTimeStampStringToMilliSecond(endtimestamp);
		return nRet;
	}
	
	/**
	 * 두 yyyyMMddHHmmssSSS의 사이 초를 구한다.
	 * 
	 * @param starttime yyyyMMddHHmmssSSS형식의 시각
	 * @param endtime yyyyMMddHHmmssSSS형식의 시각
	 * @return int
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = DateTime.getDateTimeStampDiff("20080724011230357", "20080719011230352");
     *  결과 : 431999995
     * </pre>
	 */	
	public static int getDateTimeStampDiff(String startdatetime, String enddatetime) {

		int nDay = getDateDiff(StringUtils.left(startdatetime, 8), StringUtils.left(enddatetime, 8));
		int nTime = getTimeStampDiff(StringUtils.left(startdatetime, 9), StringUtils.left(enddatetime, 9));

		nDay = nDay * (1000 * 60 * 60 * 24);
		
		
		return nDay + nTime;
	}

}
