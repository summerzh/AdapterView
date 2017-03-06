package com.gyt.adapterview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by gyt on 2016/12/5
 * 商品规格参数弹窗中的商品规格布局
 */
public class GoodsSpecLayout extends FlowLayout {

    protected OnSpecItemClickListener mOnSpecItemClickListener;
    protected SparseBooleanArray      mBooleanArray;
    // 默认第一个被选中
    protected int mLastPosition = -1;
    private SpecAdapter mSpecAdapter;

    public GoodsSpecLayout(Context context) {
        this(context, null);
    }

    public GoodsSpecLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(SpecAdapter adapter) {
        mSpecAdapter = adapter;
        removeAllViews();
        setView();
    }

    /**
     * 添加View
     */
    private void setView() {
        for (int i = 0; i < mSpecAdapter.getCount(); i++) {
            View itemView = mSpecAdapter.getView(this, i, mSpecAdapter.getItem(i));
//            FrameLayout flContainer = new FrameLayout(getContext());
            FrameLayout.LayoutParams fLp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//            ViewGroup.LayoutParams vLp = new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//            flContainer.addView(itemView, vLp);
            addView(itemView, fLp);

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnSpecItemClickListener.onSpecItemClick(v, i);
                }
            });
        }
    }

    @Override
    public boolean performClick() {

        return false;
    }


    //    public void setItemData(List<SkuMapEntity.GoodsSkuMapItemEntity> lists) {
    //        this.mSkuMapItemLists = lists;
    //        removeAllViews();
    //        mBooleanArray = new SparseBooleanArray(lists.size());
    //        for (int i = 0; i < lists.size(); i++) {
    //            SkuMapEntity.GoodsSkuMapItemEntity item = lists.get(i);
    //            if (item.isSelected()) {
    //                mLastPosition = i;
    //                mBooleanArray.put(mLastPosition, true);
    //            }
    //
    //            final TextView specTextView = new TextView(mContext);
    //            String comment = "";
    //            if (TextUtils.isEmpty(item.getComments())) {
    //            } else {
    //                comment = "[ " + item.getComments() + " ] ";
    //            }
    //            specTextView.setText(comment + item.getItemName());
    //            specTextView.setPadding(40, 10, 40, 10);
    //            specTextView.setTextColor(mBooleanArray.get(i) ? mSelectedSpecTextColor : mSpecTextColor);
    //            LinearLayout.LayoutParams lLp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    //            specTextView.setBackgroundResource(mBooleanArray.get(i) ? mSelectedBgId : mDefaultBgId);
    //            this.addView(specTextView, lLp);
    //        }
    //    }


    public interface OnSpecItemClickListener {
        void onSpecItemClick(View v, int currentPosition);
    }

    public void setOnSpecItemClickListener(OnSpecItemClickListener l) {
        mOnSpecItemClickListener  = l;
    }



    //    public void setOnSpecItemClickListener(OnSpecItemClickListener l) {
    //        this.mOnSpecItemClickListener = l;
    //        for (int i = 0; i < getChildCount(); i++) {
    //            final int finalI = i;
    //            final TextView child = (TextView) getChildAt(i);
    //
    //            child.setOnClickListener(new OnClickListener() {
    //                @Override
    //                public void onClick(View v) {
    //
    //                    if (mLastPosition != finalI) {
    //                        if (mLastPosition != -1) {
    //                            mBooleanArray.put(mLastPosition, false);
    //                            TextView mTvLast = (TextView) getChildAt(mLastPosition);
    //                            mTvLast.setBackgroundResource(mDefaultBgId);
    //                            mTvLast.setTextColor(mSpecTextColor);
    //                        }
    //                        mBooleanArray.put(finalI, true);
    //                    }
    //
    //                    TextView mTvCurrent = (TextView) getChildAt(finalI);
    //                    mTvCurrent.setBackgroundResource(mBooleanArray.get(finalI) ? mSelectedBgId : mDefaultBgId);
    //                    mTvCurrent.setTextColor(mBooleanArray.get(finalI) ? mSelectedSpecTextColor : mSpecTextColor);
    //                    // 改变skuMapItem是否选中
    //                    SkuMapEntity.GoodsSkuMapItemEntity goodsSkuMapItem = mSkuMapItemLists.get(finalI);
    //                    goodsSkuMapItem.setSelected(!goodsSkuMapItem.isSelected());
    //                    if (mLastPosition != -1) {
    //                        goodsSkuMapItem = mSkuMapItemLists.get(mLastPosition);
    //                        goodsSkuMapItem.setSelected(!goodsSkuMapItem.isSelected());
    //                    }
    //                    if (mOnSpecItemClickListener != null) {
    //                        mOnSpecItemClickListener.onSpecItemClick(v, finalI);
    //                    }
    //                    mLastPosition = finalI;
    //                }
    //            });
    //        }
    //    }


}
