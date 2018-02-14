package hu.bme.aut.amorg.examples.viewlabor.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import hu.bme.aut.amorg.examples.viewlabor.R;

public class ChoiceLayout extends LinearLayout {

    int multiple = 1;

    public ChoiceLayout(Context context) {
        super(context);
        initLayout(context, null);
    }

    public ChoiceLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context, attrs);
    }

    public ChoiceLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLayout(context, attrs);
    }

    protected void initLayout(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.VERTICAL);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChoiceLayout);
            try {
                multiple = a.getInt(R.styleable.ChoiceLayout_multiple, 1);
            } finally {
                a.recycle();
            }
        }
        Log.d("ChoiceLayout", "multiple: " + multiple);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        refreshAfterAdd(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        refreshAfterAdd(child);
    }

    private void refreshAfterAdd(final View newChild) {
        newChild.setClickable(true);
        newChild.setOnClickListener(choiceOnClickListener);
    }

    private int getSelectedCount() {
        int selectedCnt = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            if (getChildAt(i).isSelected()) {
                selectedCnt++;
            }
        }
        return selectedCnt;
    }

    private OnClickListener choiceOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if(multiple > 1) {
                if (view.isSelected() || getSelectedCount() < multiple) {
                    view.setSelected(!view.isSelected());
                }
            } else {
                int count = getChildCount();
                for (int i = 0; i < count; i++) {
                    View v = getChildAt(i);
                    v.setSelected(v == view);
                }
            }
        }
    };
}