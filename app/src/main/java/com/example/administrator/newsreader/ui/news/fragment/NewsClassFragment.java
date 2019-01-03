package com.example.administrator.newsreader.ui.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.newsreader.R;
import com.example.administrator.newsreader.bean.NewsGson;
import com.example.administrator.newsreader.database.DBManager;
import com.example.administrator.newsreader.database.databaseSettings;
import com.example.administrator.newsreader.ui.base.BaseFragent;
import com.example.administrator.newsreader.ui.news.NewsDetailsActivity;
import com.example.administrator.newsreader.ui.news.adapter.NewsAdapter;
import com.example.administrator.newsreader.ui.news.contrant.NewsContract;
import com.example.administrator.newsreader.ui.news.presenter.NewsPresenter;
import com.example.administrator.newsreader.util.PixUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/24.
 */

public class NewsClassFragment extends BaseFragent implements NewsContract.View{
    private NewsAdapter adapter;
    private NewsContract.Presenter mPresenter;

    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据
    private LinearLayoutManager layoutManager;
    private int type;

    private DBManager dbManager;


    public static NewsClassFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        NewsClassFragment fragment = new NewsClassFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }



    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    private int pageIndex = 1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
        dbManager = new DBManager(getContext());

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_type, container, false);
        ButterKnife.bind(this, view);


        mPresenter=new NewsPresenter(this,getContext());

        recyclerView.setAdapter(adapter = new NewsAdapter(getActivity()));
        recyclerView.setLayoutManager(layoutManager = new LinearLayoutManager(getActivity()));

        //添加边框
        SpaceDecoration itemDecoration = new SpaceDecoration((int) PixUtil.convertDpToPixel(8, getContext()));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);

        //更多加载
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                Log.e("更多","更多");
                mPresenter.loadData(type,pageIndex);
                pageIndex=pageIndex+1;
            }

            @Override
            public void onMoreClick() {

            }
        });
        //写刷新事件
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        pageIndex = 0;
                        mPresenter.loadData(type,pageIndex);
                    }
                }, 1000);
            }
        });

        //点击事件
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ArrayList<String> data = new ArrayList<String>();
                data.add(adapter.getAllData().get(position).getPicUrl());
                data.add(adapter.getAllData().get(position).getUrl());
                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                //用Bundle携带数据
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("data", data);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        adapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final int position) {
                View view = layoutManager.findViewByPosition(position);
                PopupMenu popupMenu = new PopupMenu(getContext(), layoutManager.findViewByPosition(position));
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.collect_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        NewsGson.NewslistBean data = adapter.getAllData().get(position);
                        List<NewsGson.NewslistBean> insert = new ArrayList<>();
                        insert.add(new NewsGson.NewslistBean(
                                data.getTitle(),
                                data.getCtime(),
                                data.getUrl(),
                                data.getPicUrl()
                        ));

                        switch (menuItem.getItemId()) {
                            case R.id.cm_collect:
                                dbManager.insert(insert, databaseSettings.TI_collection, getContext());
                                break;
                                default:
                                    Toast.makeText(getContext(), "你点到了奇怪的地方O_o", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        });


        isViewPrepared = true;

        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void returnData(List<NewsGson.NewslistBean> datas) {


        adapter.addAll(datas);

        Log.e("adapter",adapter.getAllData().size()+"");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void lazyFetchDataIfPrepared() {
        Log.e("data",type+""+isViewPrepared+"&&&"+hasFetchData);
        if (isViewPrepared && getUserVisibleHint() && !hasFetchData) {
            lazyFetchData();
            hasFetchData = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //视图销毁 数据设置为空
        hasFetchData=false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hasFetchData = false;
        isViewPrepared = false;
        dbManager.closeDB();
    }

    protected void lazyFetchData() {
        mPresenter.loadData(type,pageIndex);
        pageIndex=pageIndex+1;
    }
}
