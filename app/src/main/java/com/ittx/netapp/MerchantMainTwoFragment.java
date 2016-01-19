package com.ittx.netapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MerchantMainTwoFragment extends Fragment implements View.OnClickListener{
    private ViewPager mViewPager;
    private TextView mTabFristTxt,mTabFunTxt,mTabItTxt;
    public static MerchantMainTwoFragment newInstance() {
        MerchantMainTwoFragment fragment = new MerchantMainTwoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_merchant_main_two_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewPager = (ViewPager) getView().findViewById(R.id.merchant_main_fragment_two_viewpager);
        mTabFristTxt = (TextView) getView().findViewById(R.id.news_first_txt);
        mTabFunTxt = (TextView) getView().findViewById(R.id.news_fun_txt);
        mTabItTxt = (TextView) getView().findViewById(R.id.news_it_txt);
        mTabFristTxt.setOnClickListener(this);
        mTabFunTxt.setOnClickListener(this);
        mTabItTxt.setOnClickListener(this);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(MerchantFragment.newInstance());
        fragmentList.add(OneFragment.newInstance());
        fragmentList.add(MerchantFragment.newInstance());

        MerchantFragmentAdapter adapter = new MerchantFragmentAdapter(getFragmentManager(), fragmentList);
        mViewPager.setAdapter(adapter);

        mTabFristTxt.setTextColor(Color.RED);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectTab();
                switch (position){
                    case 0:
                        mTabFristTxt.setTextColor(Color.RED);
                        break;
                    case 1:
                        mTabFunTxt.setTextColor(Color.RED);
                        break;
                    case 2:
                        mTabItTxt.setTextColor(Color.RED);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        selectTab();
        switch (v.getId()){
            case R.id.news_first_txt:
                mViewPager.setCurrentItem(0);
                mTabFristTxt.setTextColor(Color.RED);
                break;
            case R.id.news_fun_txt:
                mViewPager.setCurrentItem(1);
                mTabFunTxt.setTextColor(Color.RED);
                break;
            case R.id.news_it_txt:
                mViewPager.setCurrentItem(2);
                mTabItTxt.setTextColor(Color.RED);
                break;
        }
    }

    public void selectTab(){
        mTabFristTxt.setTextColor(Color.WHITE);
        mTabFunTxt.setTextColor(Color.WHITE);
        mTabItTxt.setTextColor(Color.WHITE);
    }

    public static class MerchantFragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> list = new ArrayList<>();

        public MerchantFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            list = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

}
