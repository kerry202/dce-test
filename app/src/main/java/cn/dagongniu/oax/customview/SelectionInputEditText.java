package cn.dagongniu.oax.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class SelectionInputEditText extends EditText {

    public SelectionInputEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SelectionInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectionInputEditText(Context context) {
        super(context);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        //保证光标始终在最后面
        if(selStart==selEnd){//防止不能多选
            setSelection(getText().length());
        }

    }
}
