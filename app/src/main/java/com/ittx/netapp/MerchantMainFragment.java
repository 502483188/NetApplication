package com.ittx.netapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MerchantMainFragment extends Fragment {
    private ViewPager mViewPager;
    private PagerTabStrip mPagerTabStrip;

    public static MerchantMainFragment newInstance() {
        MerchantMainFragment fragment = new MerchantMainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_merchant_main_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPagerTabStrip = (PagerTabStrip) getView().findViewById(R.id.merchant_pagertabstrip);
        mViewPager = (ViewPager) getView().findViewById(R.id.merchant_main_fragment_viewpager);
        mPagerTabStrip.setTabIndicatorColor(Color.GREEN);
        mPagerTabStrip.setBackgroundColor(Color.BLUE);
        mPagerTabStrip.setTextColor(Color.WHITE);

        List<String> pagerTabLists = new ArrayList<>();
        pagerTabLists.add("头条");
        pagerTabLists.add("娱乐");
        pagerTabLists.add("科技");

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(MerchantFragment.newInstance());
        fragmentList.add(OneFragment.newInstance());
        fragmentList.add(MerchantFragment.newInstance());

        MerchantFragmentAdapter adapter = new MerchantFragmentAdapter(getFragmentManager(), fragmentList,pagerTabLists);
        mViewPager.setAdapter(adapter);
    }

    public static class MerchantFragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> list = new ArrayList<>();
        private List<String> pageTabs = new ArrayList<>();

        public MerchantFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList,List<String> pageTab) {
            super(fm);
            Log.e("tag", "MerchantFragmentAdapter ");
            list = fragmentList;
            pageTabs = pageTab;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTabs.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            Log.e("tag","getItem>>>>>>>>position  :"+position);
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    public static class MerchantFragmentNotifyAdapter extends FragmentPagerAdapter {
        private List<Fragment> list = new ArrayList<>();
        private List<String> pageTabs = new ArrayList<>();
        private FragmentManager fm;
        public MerchantFragmentNotifyAdapter(FragmentManager fm, List<Fragment> fragmentList,List<String> pageTab) {
            super(fm);
            this.fm = fm;
//            list = fragmentList;
            setFragments(fragmentList);
            pageTabs = pageTab;

        }
        public void setFragments(List<Fragment> fragments) {
            Log.e("tag","setFragments>>>>>>>>position : "+list.size());
            if(fragments != null && fragments.size() > 0){
                FragmentTransaction ft = fm.beginTransaction();
                for(Fragment f: fragments){
                    ft.remove(f);
                }
                ft.commit();
                ft=null;
//                fm.executePendingTransactions();
            }
            list = fragments;
            notifyDataSetChanged();
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return pageTabs.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            Log.e("tag","getItem>>>>>>>>position : "+position);
            return list.get(position);
        }

//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            Log.e("tag", "instantiateItem>>>>>>>>position :" + position);
//            Fragment fragment = (Fragment) super.instantiateItem(container, position);
//            String tag = fragment.getTag();
//            Log.e("tag", "instantiateItem>>>>>>>>position :" + position+ " tag :"+tag);
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.remove(fragment);
//            ft.commit();
//            Fragment newFragment = list.get(position);
//
//            fm.beginTransaction().add(container.getId(), newFragment, tag);
//            fm.beginTransaction().attach(newFragment);
//            fm.beginTransaction().commit();
//            return newFragment;
//        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
