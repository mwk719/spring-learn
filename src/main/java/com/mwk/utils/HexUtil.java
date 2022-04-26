package com.mwk.utils;

import java.io.UnsupportedEncodingException;

public class HexUtil {
    private static final char[] HEXES = {
            '0', '1', '2', '3',
            '4', '5', '6', '7',
            '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f'
    };

    /**
     * byte数组 转换成 16进制小写字符串
     */
    public static String bytes2Hex(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        StringBuilder hex = new StringBuilder();

        for (byte b : bytes) {
            hex.append(HEXES[(b >> 4) & 0x0F]);
            hex.append(HEXES[b & 0x0F]);
        }

        return hex.toString();
    }

    /**
     * 16进制字符串 转换为对应的 byte数组
     */
    public static byte[] hex2Bytes(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }

        char[] hexChars = hex.replace(" ", "").toCharArray();
        byte[] bytes = new byte[hexChars.length / 2];   // 如果 hex 中的字符不是偶数个, 则忽略最后一个
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt("" + hexChars[i * 2] + hexChars[i * 2 + 1], 16);
        }
        return bytes;
    }
    /**
     * 十六进制字符串转二进制字符串
     * @param hexStr 十六进制字符串
     * @return 二进制字符串
     */
    public static String hex2Binary(String hexStr) {
        String hexToBinStr = "";
        try {
            hexToBinStr = Long.toBinaryString(Long.valueOf(hexStr, 16));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        StringBuilder prefixSB = new StringBuilder();
        if (8 - hexToBinStr.length() > 0) {
            for (int i = 0; i < 8 - hexToBinStr.length(); i ++) {
                prefixSB.append("0");
            }
        }
        return prefixSB.toString() + hexToBinStr;
    }


    public static long ToUInt32(byte[] bytes, int offset) {
        long result = (int)bytes[offset]&0xff;
        result |= ((int)bytes[offset+1]&0xff) << 8;
        result |= ((int)bytes[offset+2]&0xff) << 16;
        result |= ((int)bytes[offset+3]&0xff) << 24;
        return result & 0xFFFFFFFFL;
    }

    public static String hexToGB18130(String hexStr) throws UnsupportedEncodingException {
        byte[] bytes = HexUtil.hex2Bytes(hexStr);
        return new String(bytes, "GB18030");
    }
}
