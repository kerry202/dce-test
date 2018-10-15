package cn.dagongniu.oax.kline.kchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.socks.library.KLog;

import java.util.List;

import cn.dagongniu.oax.kline.bean.TradingInfoBean;

public class MyCombinedChartX extends CombinedChart {

    private MyLeftMarkerView myMarkerViewLeft;
    private MyRightMarkerView myMarkerViewRight;
    private MyBottomMarkerView mMyBottomMarkerView;
    private List<TradingInfoBean.KlineListBean> minuteHelper;
    public RectF r;
    private boolean isFromKline;


    public MyCombinedChartX(Context context) {
        super(context);
    }

    public MyCombinedChartX(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCombinedChartX(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        /*此两处不能重新示例*/
        mXAxis = new MyXAxis();
        mAxisLeft = new MyYAxis(YAxis.AxisDependency.LEFT);
        mXAxisRenderer = new MyXAxisRenderer(mViewPortHandler, (MyXAxis) mXAxis, mLeftAxisTransformer, this);
        mAxisRendererLeft = new MyYAxisRenderer(mViewPortHandler, (MyYAxis) mAxisLeft, mLeftAxisTransformer);

        mAxisRight = new MyYAxis(YAxis.AxisDependency.RIGHT);
        mAxisRendererRight = new MyYAxisRenderer(mViewPortHandler, (MyYAxis) mAxisRight, mRightAxisTransformer);

    }

    /*返回转型后的左右轴*/
    @Override
    public MyYAxis getAxisLeft() {
        return (MyYAxis) super.getAxisLeft();
    }

    @Override
    public MyXAxis getXAxis() {
        return (MyXAxis) super.getXAxis();
    }


    @Override
    public MyYAxis getAxisRight() {
        return (MyYAxis) super.getAxisRight();
    }

    public void setMarker(MyLeftMarkerView markerLeft, MyRightMarkerView markerRight, MyBottomMarkerView markerBottom, List<TradingInfoBean.KlineListBean> minuteHelper, boolean isFromKline) {
        this.myMarkerViewLeft = markerLeft;
        this.myMarkerViewRight = markerRight;
        this.mMyBottomMarkerView = markerBottom;
        this.minuteHelper = minuteHelper;
        this.isFromKline = isFromKline;
    }

    public void setHighlightValue(Highlight h) {
        if (mData == null) {
//            Log.e("mData == ", "null");
            mIndicesToHighlight = null;
        } else {
//            Log.e("mData != ", "null");
            mIndicesToHighlight = new Highlight[]{h};
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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
//                Log.d("isInBounds ", "== true");
                float yValForXIndex1 = 0;
//                if (isFromKline) {
                yValForXIndex1 = Float.valueOf(minuteHelper.get(mIndicesToHighlight[i].getXIndex()).getClose().toPlainString());
//                } else {
//                    yValForXIndex1 = minuteHelper.getDatas().get(mIndicesToHighlight[i].getXIndex()).cjprice;
//                }
                float yValForXIndex2 = 0;
//                if (isFromKline) {
                yValForXIndex2 = Float.valueOf(minuteHelper.get(mIndicesToHighlight[i].getXIndex()).getQty().toPlainString());
//                } else {
//                    yValForXIndex2 = minuteHelper.getDatas().get(mIndicesToHighlight[i].getXIndex()).per;
//                }

                String time = "";
//                if (isFromKline) {
                time = minuteHelper.get(mIndicesToHighlight[i].getXIndex()).getCreateTime();
//                } else {
//                    time = minuteHelper.getDatas().get(mIndicesToHighlight[i].getXIndex()).time;
//                }
//                Log.d("yValForXIndex ", "== " + yValForXIndex1);
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
//                    Log.d("mIndicesToHighlight[i] ", "== " + mIndicesToHighlight[i]);
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
