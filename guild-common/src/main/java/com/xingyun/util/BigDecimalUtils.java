package com.xingyun.util;

import java.math.BigDecimal;
import java.util.Objects;

public class BigDecimalUtils {

    /**
     * 精度处理
     *
     * 原始数值
     *
     * @param origin
     *
     * @param scale
     *            保留的精度
     * @return BigDecimal
     */
    public static BigDecimal precision(BigDecimal origin, int scale) {
        if (Objects.isNull(origin)) {
            return BigDecimal.ZERO;
        }
        String originStr = origin.toPlainString();
        int indexOf = originStr.indexOf(".");
        String reOrigin = originStr;
        if (indexOf > 0) {
            String zsP = originStr.substring(0, indexOf);
            scale = scale + 2;
            BigDecimal remainder = origin.remainder(BigDecimal.ONE);
            String str = remainder.toPlainString();
            int i = str.lastIndexOf(".");
            if (str.length() > scale) {
                str = str.substring(i + 1, scale);
            } else {
                str = str.substring(i + 1);
            }
            reOrigin = String.format("%s.%s", zsP, str);
        }
        return new BigDecimal(reOrigin);
    }

}
