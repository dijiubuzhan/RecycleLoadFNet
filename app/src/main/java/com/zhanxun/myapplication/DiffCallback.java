package com.zhanxun.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.zhanxun.myapplication.bean.MultiNewsArticleDataModel;

import java.util.List;

public class DiffCallback extends DiffUtil.Callback {

    private final List<MultiNewsArticleDataModel> mOldItems, mNewItems;

    private DiffCallback(@NonNull List<MultiNewsArticleDataModel> oldItems, @NonNull List<MultiNewsArticleDataModel> mNewItems) {
        this.mOldItems = oldItems;
        this.mNewItems = mNewItems;
    }

    public static void create(@NonNull List<MultiNewsArticleDataModel> oldList, @NonNull List<MultiNewsArticleDataModel> newList, @NonNull RecycleAdapter adapter) {
        DiffCallback diffCallback = new DiffCallback(oldList, newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback, true);
        result.dispatchUpdatesTo(adapter);
    }

    @Override
    public int getOldListSize() {
        return mOldItems != null ? mOldItems.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewItems != null ? mNewItems.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldItems.get(oldItemPosition).equals(mNewItems.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldItems.get(oldItemPosition).hashCode() == mNewItems.get(newItemPosition).hashCode();
    }
}

