package com.example.administrator.newsreader.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.newsreader.R;
import com.example.administrator.newsreader.ui.base.BaseFragent;
import com.example.administrator.newsreader.ui.base.TabPagerAdapter;
import com.example.administrator.newsreader.ui.news.NewsFragment;
import com.example.administrator.newsreader.ui.news.fragment.NewsClassFavoriteFragment;
import com.example.administrator.newsreader.ui.news.fragment.NewsClassFragment;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFavoriteFragment  extends BaseFragent implements ViewPager.OnPageChangeListener{


    private Toolbar toolbar;
    private TabLayout mTabs;
    private ViewPager mViewPager;
    private FloatingActionButton fab;

    private String[] mTitles ;
    private NewsClassFavoriteFragment[] mFragments;
    private TabPagerAdapter mAdapter;
    private int currentPosition = 0;


    public static NewsFavoriteFragment newInstance() {
        NewsFavoriteFragment fragment = new NewsFavoriteFragment();
        return fragment;
    }

    public NewsFavoriteFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_without_tab, container, false);
        ButterKnife.bind(this, view);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTabs = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        mTitles = new String[]{""};

        mFragments = new NewsClassFavoriteFragment[1];

        mFragments[0] = NewsClassFavoriteFragment.newInstance();
        mTabs.setTabMode(TabLayout.MODE_FIXED);
        mAdapter = new TabPagerAdapter(getChildFragmentManager(), mFragments);
        mAdapter.setTabTitles(mTitles);
        mViewPager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);


        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }




    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
