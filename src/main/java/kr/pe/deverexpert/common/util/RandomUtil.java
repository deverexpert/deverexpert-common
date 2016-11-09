/*******************************************************************************
 * Copyright(c) 2014 deverexpert. All rights reserved.
 * This software is the proprietary information of deverexpert.
 *******************************************************************************/
package kr.pe.deverexpert.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author <a href=mailto:dever@sk.com>이재형</a>
 * @since 2015. 2. 17.
 */
public class RandomUtil {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        String code = "";
        long loopCnt = 0;
        Map map = new HashMap<String, String>();
        
        while(loopCnt < 1000000) {
            
            code = getRandomCode(5);
            
            if ( map.get(code) == null ) {
                //System.out.println(StringUtils.leftPad(String.valueOf(loopCnt++), 6, "0") + " : " + code);
                System.out.println(StringUtils.leftPad(String.valueOf(loopCnt++), 6, "0") + " : " + "INSERT INTO OCB_T_INSUR_PROMO_CODE(PROMO_CODE, CREDATE) VALUES('" + code + "', TO_DATE('20150223', 'YYYYMMDD'));");
                map.put(code, code);
            }
        }
        
    }
    
    public static String getRandomCode(int size) {
        
        String str = "";

        for ( int i = 0 ; i < size ; i ++ ) {
            Random random = new Random(); //현재의 시간을 초기값으로 하는 난수발생.
            
            if ( (random.nextInt(10)%2) == 0 ) {
                str += getAlphabetUpper();
            } else {
                str += getNumberic();
            }
            
        }
        
        return str;
    }

    public static String getAlphabetUpper() {
        
        Random random = new Random(); //현재의 시간을 초기값으로 하는 난수발생.
        
        char ch = (char)(random.nextInt(26) + 65);

        return String.valueOf(ch);
    }
    
    public static String getAlphabetLower() {
        
        Random random = new Random(); //현재의 시간을 초기값으로 하는 난수발생.
        
        char ch = (char)(random.nextInt(26) + 97);

        return String.valueOf(ch);
    }
    
    public static String getNumberic() {
        
        Random random = new Random(); //현재의 시간을 초기값으로 하는 난수발생.
        
        char ch = (char)(random.nextInt(10) + 48);

        return String.valueOf(ch);
    }
    
    
}
