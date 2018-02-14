package hu.bme.aut.amorg.examples.viewlabor.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import hu.bme.aut.amorg.examples.viewlabor.R;

public class ChoiceLayout extends LinearLayout {

    public static final int DIVIDER_NONE=0;
    public static final int DIVIDER_SIMPLE=1;
    public static final int DIVIDER_DOUBLE=2;
    int dividerType;
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
                dividerType = a.getInt(R.styleable.ChoiceLayout_dividerType, 0);
            } finally {
                a.recycle();
            }
        }
        Log.d("ChoiceLayout", "multiple: " + multiple);
    }

    @Override
    public void addView(View child) {
        if(getChildCount() > 0) {
            addDivider();
        }
        super.addView(child);
        refreshAfterAdd(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        if(getChildCount() > 0) {
            addDivider();
        }
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

    public void addDivider() {
        if(dividerType != DIVIDER_NONE) {
            ImageView div = new ImageView(getContext());
            switch (dividerType) {
                case DIVIDER_SIMPLE:
                    div.setImageResource(R.drawable.choice_divider_simple);
                    break;
                case DIVIDER_DOUBLE:
                    div.setImageResource(R.drawable.choice_divider_double);
                    break;
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            super.addView(div, lp);
        }
    }
}