package com.gyt.adapterview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gyt on 2017/2/7
 * 流式布局，基类
 *
 */
public class FlowLayout extends ViewGroup {

    // 默认的水平间距
    protected static final int DEFAULT_HOR_SPACING = 30;
    // 默认的垂直间距
    protected static final int DEFAULT_VER_SPACING = 20;
    // 默认的字体大小
    protected static final int DEFAULT_TEXT_SIZE   = 12;
    // 默认的每行item个数
    protected static final int DEFAULT_MAX_ROW     = 100;

    protected       Context        mContext;
    protected final int            mPopBgId;
    protected final int            mPopTextSize;
    protected final ColorStateList mPopTextColor;
    protected final ColorStateList mSelectedSpecTextColor;
    protected       int            mHorSpacing;
    protected       int            mVerSpacing;
    protected       float          mSpecTextSize;
    protected       int            mDefaultBgId;
    protected       int            mSelectedBgId;
    protected       int            mMaxRow;
    protected       ColorStateList mSpecTextColor;


    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GoodsSpecLayout, defStyleAttr, 0);
        mHorSpacing = typedArray.getDimensionPixelSize(R.styleable.GoodsSpecLayout_horSpacing, DEFAULT_HOR_SPACING);
        mVerSpacing = typedArray.getDimensionPixelSize(R.styleable.GoodsSpecLayout_verSpacing, DEFAULT_VER_SPACING);
        mMaxRow = typedArray.getInt(R.styleable.GoodsSpecLayout_maxRow, DEFAULT_MAX_ROW);
        mSpecTextSize = px2sp(context, typedArray.getDimensionPixelSize(R.styleable.GoodsSpecLayout_specTextSize, DEFAULT_TEXT_SIZE));
        mPopTextSize = px2sp(context, typedArray.getDimensionPixelSize(R.styleable.GoodsSpecLayout_popTextSize, DEFAULT_TEXT_SIZE));
        mDefaultBgId = typedArray.getResourceId(R.styleable.GoodsSpecLayout_defaultBackground, 0);
        mSelectedBgId = typedArray.getResourceId(R.styleable.GoodsSpecLayout_selectedBackground, 0);
        mPopBgId = typedArray.getResourceId(R.styleable.GoodsSpecLayout_popBackground, 0);
        mSpecTextColor = typedArray.getColorStateList(R.styleable.GoodsSpecLayout_specTextColor);
        mSelectedSpecTextColor = typedArray.getColorStateList(R.styleable.GoodsSpecLayout_selectedSpecTextColor);
        mPopTextColor = typedArray.getColorStateList(R.styleable.GoodsSpecLayout_popTextColor);
        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widMode = MeasureSpec.getMode(widthMeasureSpec);
        int widSize = MeasureSpec.getSize(widthMeasureSpec);
        int heiMode = MeasureSpec.getMode(heightMeasureSpec);
        int heiSize = MeasureSpec.getSize(heightMeasureSpec);

        // 排列的高度
        int lineHeight = mVerSpacing;
        // 排列的宽度
        int lineWidth = mHorSpacing;

        int viewCount = getChildCount();

        // 每行中宽度的最大值,
        int maxWidth = 0;
        // 每行中item的个数
        int maxRow = 0;
        for (int i = 0; i < viewCount; i++) {
            View child = getChildAt(i);
            // 因为child设置了padding，所以在测量时要传入padding值，
            LayoutParams lp = child.getLayoutParams();
            child.measure(getChildMeasureSpec(widthMeasureSpec, child.getPaddingLeft() + child.getPaddingRight(), lp.width),
                    getChildMeasureSpec(heightMeasureSpec, child.getPaddingTop() + child.getPaddingBottom(), lp.height));

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (i == 0) {
                lineHeight = childHeight + mVerSpacing * 2;
            }
            //            if (i % 2 == 0) {
            //                frontWidth = Math.max(frontWidth, childWidth);
            //            }

            //当每行的宽度大于给定的宽度或者个数达到了指定的个数则换行
            if (lineWidth + childWidth + mHorSpacing > widSize || maxRow == mMaxRow) {
                maxWidth = Math.max(maxWidth, lineWidth);
                lineHeight += childHeight + mVerSpacing;
                lineWidth = childWidth + mHorSpacing * 2;
                maxRow = 1;
            } else {
                maxRow++;
                lineWidth += childWidth + mHorSpacing;
                maxWidth = lineWidth;
            }
        }

        // 如果是match_parent获取具体的数值，则按照父容器的要求测量
        setMeasuredDimension(widMode == MeasureSpec.EXACTLY ? widSize : maxWidth, heiMode == MeasureSpec.EXACTLY ? heiSize : lineHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int width = getWidth();
        int lineWidth = /*(getWidth() - getChildAt(0).getMeasuredWidth()) / 2*/mHorSpacing;

        int childLeft;
        int childTop = mVerSpacing;
        int childRight;
        int childBottom;

        int childCount = getChildCount();

        int maxRow = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (lineWidth + childWidth + mHorSpacing > width || maxRow == mMaxRow) {// 换行
                childTop += childHeight + mVerSpacing;
                childLeft = mHorSpacing;
                lineWidth = childWidth + mHorSpacing * 2;
                maxRow = 1;
            } else {
                maxRow++;
                childLeft = lineWidth;
                lineWidth += childWidth + mHorSpacing;
            }

            childRight = childLeft + childWidth;
            childBottom = childTop + childHeight;
            child.layout(childLeft, childTop, childRight, childBottom);
        }
    }


    /**
     * 描述：dip转换为px
     */
    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


}
