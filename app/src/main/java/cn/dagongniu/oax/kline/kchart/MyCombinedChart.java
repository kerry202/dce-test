package cn.dagongniu.oax.kline.kchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.List;

import cn.dagongniu.oax.kline.bean.TradingInfoBean;

/**
 * Created by loro on 2017/2/8.
 */
public class MyCombinedChart extends CombinedChart {
    private MyLeftMarkerView myMarkerViewLeft;
    private MyRightMarkerView myMarkerViewRight;
    private MyBottomMarkerView mMyBottomMarkerView;
    private List<TradingInfoBean.KlineListBean> minuteHelper;

    public MyCombinedChart(Context context) {
        super(context);
    }

    public MyCombinedChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCombinedChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setMarker(MyLeftMarkerView markerLeft, MyBottomMarkerView markerBottom, MyRightMarkerView markerRight, List<TradingInfoBean.KlineListBean> minuteHelper) {
        this.myMarkerViewLeft = markerLeft;
        this.mMyBottomMarkerView = markerBottom;
        this.myMarkerViewRight = markerRight;
        this.minuteHelper = minuteHelper;
    }

    public void setHighlightValue(Highlight h) {
        if (mData == null) {
            mIndicesToHighlight = null;
        } else {
            mIndicesToHighlight = new Highlight[]{h};
        }
        invalidate();
    }

    @Override
    protected void drawMarkers(Canvas canvas) {
        if (!mDrawMarkerViews || !valuesToHighlight())
            return;
        for (int i = 0; i < mIndicesToHighlight.length; i++) {
            Highlight highlight = mIndicesToHighlight[i];
            int xIndex = mIndicesToHighlight[i].getXIndex();
            int dataSetIndex = mIndicesToHighlight[i].getDataSetIndex();
            float deltaX = mXAxis != null
                    ? mXAxis.mAxisRange
                    : ((mData == null ? 0.f : mData.getXValCount()) - 1.f);
            if (xIndex <= deltaX && xIndex <= deltaX * mAnimator.getPhaseX()) {
                Entry e = mData.getEntryForHighlight(mIndicesToHighlight[i]);
                // make sure entry not null
                if (e == null || e.getXIndex() != mIndicesToHighlight[i].getXIndex())
                    continue;

                float[] pos = getMarkerPosition(e, highlight);
                // check bounds
                if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                    continue;
                float yValForXIndex1 = 0;
                yValForXIndex1 = Float.valueOf(minuteHelper.get(mIndicesToHighlight[i].getXIndex()).getClose().toPlainString());
                float yValForXIndex2 = 0;
                yValForXIndex2 = Float.valueOf(minuteHelper.get(mIndicesToHighlight[i].getXIndex()).getQty().toPlainString());

                String time = "";
                time = minuteHelper.get(mIndicesToHighlight[i].getXIndex()).getCreateTime();
                if (myMarkerViewLeft != null) {
                    myMarkerViewLeft.setData(yValForXIndex1);

                }
                if (myMarkerViewRight != null) {
                    myMarkerViewRight.setData(yValForXIndex2);
                }
                if (mMyBottomMarkerView != null) {
                    mMyBottomMarkerView.setData(time);
                }
                if (myMarkerViewLeft != null) {
                    myMarkerViewLeft.refreshContent(e, mIndicesToHighlight[i]);
                }
                if (myMarkerViewRight != null) {
                    myMarkerViewRight.refreshContent(e, mIndicesToHighlight[i]);
                }
                if (mMyBottomMarkerView != null) {
                    mMyBottomMarkerView.refreshContent(e, mIndicesToHighlight[i]);
                }

                /*修复bug*/
                // invalidate();
                /*重新计算大小*/
                if (myMarkerViewLeft != null) {
                    myMarkerViewLeft.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//                    myMarkerViewLeft.layout(0, 0, myMarkerViewLeft.getMeasuredWidth(),
//                            myMarkerViewLeft.getMeasuredHeight());
                    myMarkerViewLeft.layout(0, 0, -0,
                            myMarkerViewLeft.getMeasuredHeight());
                }
                if (myMarkerViewRight != null) {
                    myMarkerViewRight.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    myMarkerViewRight.layout(0, 0, 0,
                            myMarkerViewRight.getMeasuredHeight());
                }
                if (mMyBottomMarkerView != null) {
                    mMyBottomMarkerView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    mMyBottomMarkerView.layout(0, 0, mMyBottomMarkerView.getMeasuredWidth(),
                            mMyBottomMarkerView.getMeasuredHeight());
                }

                if (mMyBottomMarkerView != null) {
                    mMyBottomMarkerView.draw(canvas, pos[0] - mMyBottomMarkerView.getWidth() / 2, mViewPortHandler.contentBottom());
                }
                if (myMarkerViewLeft != null) {
                    myMarkerViewLeft.draw(canvas, mViewPortHandler.contentLeft() - myMarkerViewLeft.getWidth(), pos[1] - myMarkerViewLeft.getHeight() / 2);
                }
                if (myMarkerViewRight != null) {
                    myMarkerViewRight.draw(canvas, mViewPortHandler.contentRight(), pos[1] - myMarkerViewRight.getHeight() / 2);
                }
                if (mPoint == null) {
                    return;
                }
                Paint p = new Paint();
                p.setColor(Color.WHITE);// 设置红色
                canvas.drawLine(mPoint[0], 40, mPoint[0], this.getHeight() - 40, p);
                canvas.drawLine(95, mPoint[1], this.getWidth() - 45, mPoint[1], p);
            }
        }
    }
}
