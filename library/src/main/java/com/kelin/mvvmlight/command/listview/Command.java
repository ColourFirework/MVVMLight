package com.kelin.mvvmlight.command.listview;

import android.databinding.BindingAdapter;
import android.widget.AbsListView;
import android.widget.ListView;

import com.kelin.mvvmlight.command.ReplyCommand;

/**
 * Created by kelin on 16-3-24.
 */
public final class Command {

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"onScrollChangeCommand", "onScrollStateChangedCommand"}, requireAll = false)
    public static void onScrollChangeCommand(final ListView listView,
                                             final ReplyCommand<ListViewScrollDataWrapper> onScrollChangeCommand,
                                             final ReplyCommand<Integer> onScrollStateChangedCommand) {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int scrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
                if (onScrollStateChangedCommand != null) {
                    onScrollChangeCommand.equals(scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (onScrollChangeCommand != null) {
                    onScrollChangeCommand.execute(new ListViewScrollDataWrapper(scrollState, firstVisibleItem, visibleItemCount, totalItemCount));
                }
            }
        });

    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({"onLoadMoreCommand"})
    public static void onLoadMoreCommand(final ListView listView, final ReplyCommand<Integer> onLoadMoreCommand) {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount
                        && totalItemCount != 0
                        && totalItemCount != listView.getHeaderViewsCount()
                        + listView.getFooterViewsCount()) {
                    if (onLoadMoreCommand != null) {
                        onLoadMoreCommand.execute(totalItemCount);
                    }
                }
            }
        });

    }

    public static class ListViewScrollDataWrapper {
        public int firstVisibleItem;
        public int visibleItemCount;
        public int totalItemCount;
        public int scrollState;

        public ListViewScrollDataWrapper(int scrollState, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            this.firstVisibleItem = firstVisibleItem;
            this.visibleItemCount = visibleItemCount;
            this.totalItemCount = totalItemCount;
            this.scrollState = scrollState;
        }
    }
}
