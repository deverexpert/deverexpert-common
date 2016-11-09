/**
 * Number 유틸리티
 */
package kr.pe.deverexpert.common.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author 이재형
 *  
 */
public class NumberUtil {
	
    /**
     * 금액에 콤마를 삽입한다.
     * @param amt 변환할 금액
     * @param dec 소수자리
     * @return String
     * 
     * <p><pre> 
     *  - 사용 예
     *        String date = NumberUtil.getCommaNumber(123456.123, 3);
     *  결과 : 123,456.123
     * </pre>
     */
    public static String getCommaNumber(double amt, int dec){
	    NumberFormat nf = NumberFormat.getNumberInstance();
	    nf.setMaximumFractionDigits(dec);
	    return nf.format(amt);
    }
    
    /**
     * 금액에 콤마를 삽입한다.
     * @param amt 변환할 금액
     * @return String
     * 
     * <p><pre> 
     *  - 사용 예
     *        String date = NumberUtil.getCommaNumber(123456);
     *  결과 : 123,456
     * </pre>
     */
    public static String getCommaNumber(int amt){
	    NumberFormat nf = NumberFormat.getNumberInstance();
	    return nf.format(amt);
    }


	/**
	 * 금액에 콤마를 삽입하고 앞에 ￦를 붙인다.
	 * @param amt 변환할 금액
	 * @param dec 소수자리수
	 * @return String
     * 
     * <p><pre> 
     *  - 사용 예
     *        String date = NumberUtil.getCurrencyNationNumber(123456.123, 3);
     *  결과 : ￦123,456.123
     * </pre>
	 */
	public static String getCurrencyNationNumber(double amt, int dec){
	    NumberFormat nf = NumberFormat.getCurrencyInstance();
	    nf.setMaximumFractionDigits(dec);
	    return nf.format(amt);
	}

	/**
	 * 금액에 콤마를 삽입하고 앞에 ￦를 붙인다.
	 * @param amt 변환할 금액
	 * @return String
     * 
     * <p><pre> 
     *  - 사용 예
     *        String date = NumberUtil.getCurrencyNationNumber(123456);
     *  결과 : ￦123,456
     * </pre>
	 */
	public static String getCurrencyNationNumber(int amt){
	    NumberFormat nf = NumberFormat.getCurrencyInstance();
	    return nf.format(amt);
	}
	
	/**
	 * 금액에 콤마를 삽입하고 앞에 해당국가의 통화를 표시한다.
	 * @param amt 변환할 금액
	 * @param dec 소수자리수
	 * @param locale
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = NumberUtil.getCurrencyNationNumber(123456.123, 3, Locale.US);
     *  결과 : $123,456.123
     * </pre>
     */
	public static String getCurrencyNationNumber(double amt, int dec, Locale locale){
	    NumberFormat nf = NumberFormat.getCurrencyInstance( locale );
	    nf.setMaximumFractionDigits(dec);
	    return nf.format(amt);
	}
	
	/**
	 * 금액에 콤마를 삽입하고 앞에 해당국가의 통화를 표시한다.
	 * @param amt 변환할 금액
	 * @param locale 해당국가의 통화
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = NumberUtil.getCurrencyNationNumber(123456, Locale.US);
     *  결과 : $123,456.123
     * </pre>
     */
	public static String getCurrencyNationNumber(int amt, Locale locale){
	    NumberFormat nf = NumberFormat.getCurrencyInstance( locale );
	    return nf.format(amt);
	}	

	/**
	 * 금액의 뒤에 %를 붙인다.
	 * @param amt 변환할 금액
	 * @param dec 소수자리수
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = NumberUtil.getPercentNumber(123456.123, 3);
     *  결과 : 12,345,612.300% 
     * </pre>
	 */
	public static String getPercentNumber(double amt, int dec){
	    NumberFormat nf = NumberFormat.getPercentInstance();
	    nf.setMinimumFractionDigits(dec);
	    return nf.format(amt);
	}

	/**
	 * 금액의 뒤에 %를 붙인다.
	 * @param amt 변환할 금액
	 * @return String
	 * 
     * <p><pre> 
     *  - 사용 예
     *        String date = NumberUtil.getPercentNumber(123456);
     *  결과 : 12,345% 
     * </pre>
	 */
	public static String getPercentNumber(int amt){
	    NumberFormat nf = NumberFormat.getPercentInstance();
	    return nf.format(amt);
	}
	
	/**
	 * 입력받은 실수를 올림하여 원하는 자리까지 표현한다.
	 * 
	 * @param num 변환할 실수
	 * @param dec 표현할 소숫수자리
	 * @return double
	 * 
	 * <p><pre> 
	 *  - 사용 예
	 *        String num = NumberUtil.ceil(1163.512, -2));
	 *        결과 : 1200.0 
	 *        String num = NumberUtil.ceil(1163.512, 2));
	 *        결과 : 1163.52
	 * </pre> 
	 */
	public static double ceil(double num, int dec){
	        
		double temp = decToDigit(dec);
		
		num = num*temp;
		num = Math.ceil(num); 
		num = num/temp;
		
		return num;
	}

	/**
	 * 입력받은 실수를 반올림하여 원하는 자리까지 표현한다.
	 * @param num 변환할 실수
	 * @param dec 표현할 소숫자리
	 * @return double
	 * 
	 * <p><pre> 
	 *  - 사용 예
	 *        String num = NumberUtil.round(1163.512, -2));
	 *        결과 : 1200.0 
	 *        String num = NumberUtil.ceil(1163.512, 2));
	 *        결과 : 1163.51 
	 * </pre> 
	 */    
	public static double round(double num, int dec){
	        
		double temp = decToDigit(dec);
		
		num = num*temp;
		num = Math.round(num);
		num = num/temp;
		return num;
	}

	/**
	 * 입력받은 실수를 내림하여 원하는 자리까지 표현한다.
	 * @param num 변환할 실수
	 * @param dec 표현할 소숫자리
	 * @return double
	 * 
	 * <p><pre> 
	 *  - 사용 예
	 *        String num = NumberUtil.floor(1163.512, -2));
	 *        결과 : 1100.0
	 *        String num = NumberUtil.ceil(1163.512, 2));
	 *        결과 : 1163.51 
	 * </pre> 
	 */    
	public static double floor(double num, int dec){
		double temp = decToDigit(dec);
		
		num = num*temp;
		num = Math.floor(num); 
		num = num/temp;
		
		return num;
	}


	/**
	 * 소수자리를 계산하기위한 정수로 변환
	 * @param dec 표현할 소숫자리
	 * @return double
	 * 
	 * <p><pre> 
	 *  - 사용 예
	 *        String num = NumberUtil.decToDigit(2);
	 *        결과 : 100.0
	 * </pre> 
	 */    
	private static double decToDigit(int dec){
		double temp = 1;
		
		if(dec>=1){
	        for(int i = 0 ; i < dec ; i++){
                temp = temp*10;
	        }
		}else if(dec < 1){
	        for(int i = dec ; i < 0 ; i++){
                temp = temp/10;
	        }
		}
		return temp;
	}


	/**
	 * 숫자와 '.'가 아닌문자의 경우 문자를제외후 double형으로 리턴한다.
	 * @param str
	 * @return double
	 * 
	 * <p><pre> 
	 *  - 사용 예
	 *        String num = NumberUtil.stringToNumber("####1163.51244***123#####");
	 *        결과 : 1163.51244123
	 * </pre> 
	 */
	public static double stringToNumber(String str){
        StringBuffer sb = new StringBuffer();
        String number = "1234567890.";
        
        for(int i=0; i<str.length(); i++){
            if(number.indexOf(str.charAt(i)) > -1){
                sb.append(str.charAt(i));
            }
        }
        
        return Double.parseDouble(sb.toString());
	}


	/**
	 * 실수형을 정수형으로...
	 * @param num 실수
	 * @return int 
	 * 
	 * <p><pre> 
	 *  - 사용 예
	 *        String num = NumberUtil.doubleToInt(23.124);
	 *        결과 : 23
	 * </pre> 
	 */
	public static int doubleToInt(double num){
        return (int)num;
	}


	/**
	 * 정수형을 실수형으로...
	 * @param num 정수
	 * @return double
	 * 
	 * <p><pre> 
	 *  - 사용 예
	 *        String num = NumberUtil.intToDouble(23);
	 *        결과 : 23.0
	 * </pre> 
	 */
	public static double intToDouble(int num){
        return (double)num;
	}
	
	/**
	 * Unsigned Short
	 * @param c
	 * @return int
	 * 
	 * <p><pre> 
	 *  - 0 ~ 65536
	 * </pre> 
	 */	
	public static int getUnsignedShort(char c) {
		return (int)c;
	}
	
	/**
	 * Unsigned Int
	 * @param n
	 * @return Long
	 * 
	 * <p><pre> 
	 *  - 0 ~ 4294967296
	 * </pre> 
	 */		
	public static Long getUnsignedInt(int n) {
		return n & 0xFFFFFFFFL;
	}
	
}
