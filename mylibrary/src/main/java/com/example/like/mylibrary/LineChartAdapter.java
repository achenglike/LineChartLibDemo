package com.example.like.mylibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by like on 2015/10/9.
 */
public abstract class LineChartAdapter<T> {

    private List<DataItem> innerData;
    private List<T> mDatas;
    private DataChangeListener mDataChangeListener;

    private int maxVlaue = 0;

    public LineChartAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
        this.innerData = new ArrayList<DataItem>();
        generateData();
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
        generateData();
    }

    public void notifyDataChanged() {
        mDataChangeListener.onChange();
    }

    private List<DataItem> generateData() {
        innerData.clear();
        for (int i = 0; i < getCount(); i++) {
            int value = getValue(getItem(i));
            maxVlaue = Math.max(maxVlaue, value);
            innerData.add(new DataItem(i, getIndex(getItem(i)), value));
        }
        return innerData;
    }

    protected abstract String getIndex(T t);
    protected abstract int getValue(T t);

    public int getMaxVlaue() {
        return maxVlaue;
    }

    public int getValueByPosition(int position) {
        return innerData.get(position).getValue();
    }

    public String getIndexByPosition(int position) {
        return innerData.get(position).getIndex();
    }

    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public T getItem(int position) {
        return (mDatas==null || mDatas.size() == 0) ? null : mDatas.get(position);
    }

    public void setDataChangeListener(DataChangeListener listener) {
        this.mDataChangeListener = listener;
    }

    interface DataChangeListener {
        void onChange();
    }

    static class DataItem {
        int postion;
        String index;
        int value;

        public DataItem(int postion, String index, int value) {
            this.index = index;
            this.postion = postion;
            this.value = value;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public int getPostion() {
            return postion;
        }

        public void setPostion(int postion) {
            this.postion = postion;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
