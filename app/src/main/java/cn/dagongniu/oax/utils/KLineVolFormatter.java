package cn.dagongniu.oax.utils;


import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

public class KLineVolFormatter implements YAxisValueFormatter {

    private final int unit;
    private DecimalFormat mFormat;
    private String u;

    public KLineVolFormatter(int unit) {
        if (unit == 1) {
            mFormat = new DecimalFormat("#0");
        } else {
            mFormat = new DecimalFormat("#0.00");
        }
        this.unit = unit;
        this.u = KLineUtils.getVolUnit(unit);
    }


    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        value = value / unit;
        if (value == 0) {
            return u;
        }
        return mFormat.format(value);
    }
}

