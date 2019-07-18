package com.housekeeper.mylibrary.interf;

/**
 * Description: RecyclerView点击事件
 * Creator: Chenqiang
 * Date: 2017/2/20
 */

public interface RecyclerViewOnItemClickListener<T> {
    void onItemClick(T t, int position);
}
