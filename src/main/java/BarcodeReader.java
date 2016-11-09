/*******************************************************************************
 * Copyright(c) 2012 SK M&S. All rights reserved.
 * This software is the proprietary information of SK M&S.
 *******************************************************************************/

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

/**
 * 바코드 리더
 * 
 * Year(2000~2175)
 * Month(1~12)
 * Date(1~31)
 * AllianceCode(0~65535)
 * StoreCode(0~16777215)
 * PosNumber(0~199)
 * ReceiptNumber(0~4999)
 * Amount(0~268435456)
 * 
 * @author <a href="mailto:dever@sk.com">이재형</a>
 * @since 2015. 4. 14.
 */
public class BarcodeReader {

    private static final byte EncryptKey[] = { (byte)0x1F, (byte)0xF4, (byte)0xB7, (byte)0xA6, (byte)0xC8, (byte)0xDD, (byte)0xA0, (byte)0xB2, (byte)0x03, (byte)0x2C, (byte)0x53, (byte)0x2D, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00 };
    
    private static final int BARCODE_DIGITUNITLEN = 13;
    private static final int BARCODE_DIGITBASE = 36;
    private static final int BARCODE_IDENTIFIER = 0xC;
    private static final int BARCODE_VERSION = 0x0;
    private static final int BARCODE_PACKEDDATASIZE = 16;
    private static final int BARCODE_MAXRECEIPTNUMBER = 4999;
    
    public static void main(String[] args) {
    	String barcode = "288T8ZJ5X20DX116IUXKJTU3SF";
    	System.out.println(barcodeReader(barcode));
    }

    /**
     * 바코드에 포함된 제휴사 키를 리턴한다.
     * @param barcode
     * @return
     */
    public static String barcodeReaderForAllianceKey(String barcode) {
        
        // TODO : 테스트를 위한 bypass
        return barcode;
/*        
        String retStr = "";
        Map<String, Object> map = null;

        try {
            map = barcodeReader(barcode);
            
            if ( String.valueOf(map.get("resCd")).equals(CommonResultCd.SUCCESS) ) {
                // 지점 코드
                retStr += StringUtils.leftPad(String.valueOf(map.get("storeCode")), 4, "0");
                // 년 2자리
                retStr += StringUtils.right(String.valueOf(map.get("year")), 2);
                // 월
                retStr += StringUtils.leftPad(String.valueOf(map.get("month")), 2, "0");
                // 일
                retStr += StringUtils.leftPad(String.valueOf(map.get("date")), 2, "0");
                // 포스넘버
                retStr += StringUtils.leftPad(String.valueOf(map.get("posNumber")), 6, "0");
                // 영수증 순번
                retStr += StringUtils.leftPad(String.valueOf(map.get("receiptNumber")), 6, "0");
            }
        } catch (Exception e) {
            System.out.println(e);
        } 
        
        return retStr;
*/
    }
    
    /**
     * 바코드의 내용을 해석하여 Map 형태로 리턴한다.
     * @param barcode
     * @return
     */
    public static Map<String, Object> barcodeReader(String barcode) {
        
        return unpackBarcode(decryptBarcode(barcode));
    }
    
    /**
     * 바코드를 복호화 한다.
     * @param barcode
     * @return
     */
    private static byte[] decryptBarcode(String barcode) {
        

        byte[] decrytByte = null;
        byte[] encrytByte = new byte[(barcode.length() / BARCODE_DIGITUNITLEN) * 8];
        byte[] encrytByteTmp = null;
        
        Long ln = 0L;


        if (!(barcode.length() % BARCODE_DIGITUNITLEN == 0)) return null;

        try {

            for (int i = 0; i < barcode.length(); i += BARCODE_DIGITUNITLEN) {

                ln = convertTo8Byte(StringUtils.mid(barcode, i, BARCODE_DIGITUNITLEN), BARCODE_DIGITBASE);
                encrytByteTmp = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(ln).array();
                
                System.arraycopy(encrytByteTmp, 0, encrytByte, (i / BARCODE_DIGITUNITLEN) * 8, encrytByteTmp.length);
                
            }
            
            SecretKeySpec key = new SecretKeySpec(EncryptKey, "Blowfish");
            
            Cipher cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");
            
            cipher.init(Cipher.DECRYPT_MODE, key);

            decrytByte = cipher.doFinal(byteOrderSortForC(encrytByte));
            
            System.out.println("EncryptKey : " + bytesToHex(EncryptKey));
            System.out.println("암호화 : " + bytesToHex(encrytByte));
            System.out.println("복호화 : " + bytesToHex(byteOrderSortForC(decrytByte)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return byteOrderSortForC(decrytByte);
    }
    
    /**
     * 복호화된 바이트를 UnP한다
     * @param binary
     * @return
     */
    private static Map<String, Object> unpackBarcode(byte[] binary) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("resCd", Constant.CommonResultCd.SUCCESS);
        map.put("resMsg", "");
        
        if (binary.length != BARCODE_PACKEDDATASIZE) {
            map.put("resCd", Constant.CommonResultCd.ERROR);
            map.put("resMsg", "The packet size does not match.");
            return map;
        }
        
        //B0: 4bit 식별자, 4bit 버전
        Long identifier = Long.parseLong(bytesToHex(binary[0]), 16);
        if ( identifier >> 4 != BARCODE_IDENTIFIER) {
            map.put("resCd", Constant.CommonResultCd.ERROR);
            map.put("resMsg", "The 4bit identifiers do not match.");
            return map;
        }
        
        if((identifier & 0xF) != BARCODE_VERSION) {
            map.put("resCd", Constant.CommonResultCd.ERROR);
            map.put("resMsg", "This version does not match the barcode.");
            return map;
        }

        //B1-2: 날짜
        long nDate = Long.parseLong(bytesToHex(binary[2]) + bytesToHex(binary[1]), 16);
        
        long year = (nDate / 372) + 2000;
        long month = (nDate % 372) / 31 + 1;
        long date = (nDate % 31) + 1;
        
        map.put("year", year);
        map.put("month", month);
        map.put("date", date);
        
        //B3-4: 제휴사코드
        long allianceCode = Long.parseLong(bytesToHex(binary[4]) + bytesToHex(binary[3]), 16);
        map.put("allianceCode", allianceCode);
        
        //B5-7: 매장번호
        long storeCode = Long.parseLong(bytesToHex(binary[7]) + bytesToHex(binary[6]) + bytesToHex(binary[5]), 16);
        map.put("storeCode", storeCode);
        
        long nMix = Long.parseLong(bytesToHex(binary[13]) + bytesToHex(binary[12]) + bytesToHex(binary[11]) + bytesToHex(binary[10]) + bytesToHex(binary[9]) + bytesToHex(binary[8]), 16);
        long nPosAndRecitptNumber = (nMix >> 28);
        long posNumber = nPosAndRecitptNumber / (BARCODE_MAXRECEIPTNUMBER + 1);
        long receiptNumber = nPosAndRecitptNumber % (BARCODE_MAXRECEIPTNUMBER + 1);
        long amount = nMix & 0xFFFFFFF;
        
        map.put("posNumber", posNumber);
        map.put("receiptNumber", receiptNumber);
        map.put("amount", amount);
        
        Long parity = Long.parseLong(bytesToHex(binary[14]), 16);

        map.put("parity", parity);
        
        long A = BARCODE_IDENTIFIER;
        long B = BARCODE_VERSION;
        long C = nDate;
        long D = allianceCode;
        long E = storeCode;
        long F = nMix;
        long G = amount;
        
        long BARCODE_GETPARITY = (A*997+B*113+C*157+D*887+D*503+E*797+F*67+G*811)%256;
        
        if ( parity != BARCODE_GETPARITY ) {
            map.put("resCd", Constant.CommonResultCd.ERROR);
            map.put("resMsg", "Parity bits do not match.");
            return map;
        }
        
        return map;
    }
    
    /**
     * @param str
     * @param radix
     * @return
     */
    private static Long convertTo8Byte(String str, int radix) {

        String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        long dest = 0;
        for (int i = 0; i < str.length(); i++) {
            dest = dest * radix + digits.indexOf(str.toCharArray()[i]);
        }

        return dest;

    }
    
    
    /**
     * @param data
     * @return
     */
    private static byte[] byteOrderSortForC(byte[] data) {
        
        // 4바이트 씩 끊는다.
        int index = 0;
        byte newData[] = new byte[data.length];
        
        for ( int i = 0 ; i < data.length ; i+= 4) {
            for ( int j = 3 ; j >= 0 ; j-- ) {
                newData[index++] = data[(1*i) + j];
            }
        }
        
        return newData;
        
    }
    
    /**
     * @param data
     * @return
     */
    private static String bytesToHex(byte data) {
    	
        if (data == 0x00) return "00";

        String str = "";
        
        if ((data & 0xFF) < 16)
            str = str + "0" + java.lang.Integer.toHexString(data & 0xFF);
        else
            str = str + java.lang.Integer.toHexString(data & 0xFF);
        
        return str;
    }
    
    /**
     * @param data
     * @return
     */
    private static String bytesToHex(byte[] data) {

        if (data == null) return "0x00";

        String str = "";

        for (int i = 0; i < data.length; i++) {
            if ((data[i] & 0xFF) < 16)
                str = str + "0x" + "0" + java.lang.Integer.toHexString(data[i] & 0xFF) + " ";
            else
                str = str + "0x" + java.lang.Integer.toHexString(data[i] & 0xFF) + " ";
        }

        return str;

    }
    
    public class Constant {
    	public class CommonResultCd {
            /** 성공 */
            public static final String SUCCESS = "0000";
            /** 기타오류 */
            public static final String ERROR = "9999";
    	}
    }

}
