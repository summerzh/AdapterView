package com.gyt.adapterview;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gyt on 2017/2/10
 */
public abstract class SpecAdapter<T> {

    private final List<T> mDatas;
    private OnItemClickListener mOnItemClickListener;

    public SpecAdapter(List<T> datas) {
        mDatas = datas;
    }

    public SpecAdapter(T[] datas) {
        mDatas = new ArrayList<T>(Arrays.asList(datas));
    }

    public abstract View getView(FlowLayout parent, int position, T t);

    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.mOnItemClickListener = l;
    }
}
