package com.example.administrator.newsreader.ui.news.contrant;

import com.example.administrator.newsreader.bean.NewsGson;

import java.util.List;

/**
 *
 * 头条契约类
 */

public interface NewsContract {


    interface View{
        void returnData(List<NewsGson.NewslistBean> datas);
    }

    interface OnLoadFirstDataListener{
        void  onSuccess(List<NewsGson.NewslistBean> list);
        void  onFailure(String str,Throwable e);
    }

    interface Presenter  {
        void loadData(int type, int page);
    }

    interface Model {
        void loadData( int type, OnLoadFirstDataListener listener,int page);
    }
}
