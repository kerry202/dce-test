package cn.dagongniu.oax.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

    public static String BigDecimalScaleToString(int newScale, BigDecimal bigDecimal) {

        String string = bigDecimal.compareTo(new BigDecimal(0E-8)) == 0 ? new BigDecimal(0).toString() : "0.00000000";

        string = bigDecimal.setScale(newScale, BigDecimal.ROUND_DOWN).toString();

        return string;
    }

}
