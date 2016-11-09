/*******************************************************************************
 * Copyright(c) 2014 deverexpert. All rights reserved.
 * This software is the proprietary information of deverexpert.
 *******************************************************************************/
package kr.pe.deverexpert.common.util;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author <a href=mailto:deverexpert@gmail.com>deverexpert</a>
 * @since 2015. 2. 3.
 */
public class PasswordUtil {

    /**
     * 비밀번호 체크
     *
     * 1. 반복문자는 사용이 불가
     * 2. 영문 소문자/대문자, 숫자, 특수문자 2가지 조합 시 10자 이상, 3가지 조합시 8자 이상
     * 3. 키보드 상 연속 배열 문자 사용 불가(정방향/역방향)
     * 4. 유추하기 쉬운 비밀번호는 사용 불가
     *
     * @param passWd
     * @param filter
     * @return result(SUCCESS/ERROR), msg
     */
    public static HashMap<String, String> checkPasswordValidate(String passWd, List<String> filter) {
        HashMap<String, String> map = new HashMap<String, String>();

        byte[] pwd = passWd.getBytes();

        map.put("result", "SUCCESS");
        map.put("msg", "정상적인 비밀번호 입니다.");


        // 숫자
        String[] keyArrayNum = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        // 키보드 1열
        String[] keyArray1 = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
        // 키보드 2열
        String[] keyArray2 = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
        // 키보드 3열
        String[] keyArray3 = {"Z", "X", "C", "V", "B", "N", "M"};

        boolean bCheck[] = { false, false, false };
        int nCheck = 0;

        for ( int i = 0 ; i < pwd.length ; i++ ) {
            if ( i < pwd.length - 1 ) {
                if ( pwd[i] == pwd[i+1] ) {
                    map.put("result", "ERROR");
                    map.put("msg", "반복문자는 사용이 불가능 합니다.");
                    return map;
                }
            }
            if ( (pwd[i] >= 97 && pwd[i] <= 122) || (pwd[i] >= 65 && pwd[i] <= 90)) {
                // 영문자
                bCheck[0] = true;
            } else if ( pwd[i] >= 48 && pwd[i] <= 57 ) {
                // 숫자
                bCheck[1] = true;
            } else {
                // 기타
                bCheck[2] = true;
            }
        }

        for ( int i = 0 ; i < bCheck.length ; i++ ) {
            if ( bCheck[i] ) nCheck++;
        }

        if ( nCheck < 2 )  {
            map.put("result", "ERROR");
            map.put("msg", "영문 소문자/대문자, 숫자, 특수문자 2가지 조합 시 10자 이상, 3가지 조합시 8자 이상이어야 합니다.");
            return map;
        } else if ( nCheck == 2 && pwd.length < 10) {
            map.put("result", "ERROR");
            map.put("msg", "영문 소문자/대문자, 숫자, 특수문자 2가지 조합 시 10자 이상, 3가지 조합시 8자 이상이어야 합니다.");
            return map;
        } else if ( nCheck == 3 && pwd.length < 8 ) {
            map.put("result", "ERROR");
            map.put("msg", "영문 소문자/대문자, 숫자, 특수문자 2가지 조합 시 10자 이상, 3가지 조합시 8자 이상이어야 합니다.");
            return map;
        }

        String tmpPassWd = passWd;
        // 키보드 연속 배열 문자 사용 체크 숫자
        for ( int i = 0 ; i < keyArrayNum.length - 1 ; i++ ) {
            tmpPassWd = tmpPassWd.replaceAll("(?i)" + keyArrayNum[i] + keyArrayNum[i+1], "");
        }//정방향
        for ( int i = keyArray1.length - 1 ; i >= 1 ; i-- ) {
            tmpPassWd = tmpPassWd.replaceAll("(?i)" + keyArrayNum[i] + keyArrayNum[i-1], "");
        }//역방향

        // 키보드 연속 배열 문자 사용 체크 1열
        for ( int i = 0 ; i < keyArray1.length - 1 ; i++ ) {
            tmpPassWd = tmpPassWd.replaceAll("(?i)" + keyArray1[i] + keyArray1[i+1], "");
        }//정방향
        for ( int i = keyArray1.length - 1 ; i >= 1 ; i-- ) {
            tmpPassWd = tmpPassWd.replaceAll("(?i)" + keyArray1[i] + keyArray1[i-1], "");
        }//역방향

        // 키보드 연속 배열 문자 사용 체크 2열
        for ( int i = 0 ; i < keyArray2.length - 1 ; i++ ) {
            tmpPassWd = tmpPassWd.replaceAll("(?i)" + keyArray2[i] + keyArray2[i+1], "");
        }//정방향
        for ( int i = keyArray2.length - 1 ; i >= 1 ; i-- ) {
            tmpPassWd = tmpPassWd.replaceAll("(?i)" + keyArray2[i] + keyArray2[i-1], "");
        }//역방향

        // 키보드 연속 배열 문자 사용 체크 3열
        for ( int i = 0 ; i < keyArray3.length - 1 ; i++ ) {
            tmpPassWd = tmpPassWd.replaceAll("(?i)" + keyArray3[i] + keyArray3[i+1], "");
        }//정방향
        for ( int i = keyArray3.length - 1 ; i >= 1 ; i-- ) {
            tmpPassWd = tmpPassWd.replaceAll("(?i)" + keyArray3[i] + keyArray3[i-1], "");
        }//역방향

        if ( !passWd.equals(tmpPassWd) ) {
            map.put("result", "ERROR");
            map.put("msg", "키보드 상 연속 배열 문자 사용은 할 수 없습니다.");
            return map;
        }

        // 개인정보 사용 필터
        if ( filter != null ) {
            for ( int i = 0 ; i < filter.size() ; i++ ) {
                if ( passWd.indexOf(filter.get(i).toString()) > -1 ) {
                    map.put("result", "ERROR");
                    map.put("msg", "유추하기 쉬운 비밀번호는 사용하실 수 없습니다.");
                    return map;
                }
            }
        }

        return map;
    }
}
