package com.mwk.jvm.escapeanalysis;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;

/**
 * @author MinWeikai
 * @date 22/4/2022 16:32
 */
@Slf4j
public class EscapeAnalysisDemo {

    public static void main(String[] args) {
        TimeInterval interval =  DateUtil.timer();
        int n = 100000000;
        for (int i = 0; i < n; i++) {
            escape("hello", "world");
//            noEscape("hello", "world");
        }
        log.debug("耗时 {}", interval.interval());
    }

    public static StringBuffer escape(String a, String b){
        StringBuffer sb = new StringBuffer();
        sb.append(a);
        sb.append(b);
        return sb;
    }

    public static void noEscape(String a, String b){
        StringBuffer sb = new StringBuffer();
        sb.append(a);
        sb.append(b);
    }
}
