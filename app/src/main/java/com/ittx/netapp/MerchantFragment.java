package com.ittx.netapp;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ittx.netapp.model.InfoBean;
import com.ittx.netapp.model.MerchantBean;
import com.ittx.netapp.model.MerchantResultBean;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.scxh.slider.library.Indicators.PagerIndicator;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;
import com.warmtel.android.xlistview.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MerchantFragment extends Fragment {
    private XListView mListView;
    private ProgressBar mProgressBar;
    private SliderLayout mSliderLayout;
    private ImageView mBackImg;
    private String mHttpUrl = "http://192.168.1.139/json/around";
    private MerchantAdapter mMerchantAdapter;
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    private OnMerchantFragmentInteractionListener mListener;
    public interface OnMerchantFragmentInteractionListener{
        void backMain();
    }

    public static MerchantFragment newInstance() {
        MerchantFragment fragment = new MerchantFragment();
        return fragment;
    }
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnMerchantFragmentInteractionListener) {
            mListener = (OnMerchantFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMerchantFragmentInteractionListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.activity_main_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgressBar = (ProgressBar) getView().findViewById(R.id.merchant_progressbar);
        mListView = (XListView)  getView().findViewById(R.id.list_listview);
        mBackImg = (ImageView) getView().findViewById(R.id.title_imageview);
        View sliderHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.slider_image_layout,null);
        mSliderLayout = (SliderLayout) sliderHeaderView.findViewById(R.id.slider_imager);
        mListView.addHeaderView(sliderHeaderView);

        HashMap<String,String> silderList = getData();
        for(final String key : silderList.keySet()){
            String url = silderList.get(key);
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.description(key);
            textSliderView.image(url);
            textSliderView.setScaleType(BaseSliderView.ScaleType.CenterCrop);
            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView baseSliderView) {
                    Toast.makeText(getActivity(), key, Toast.LENGTH_SHORT).show();
                }
            });
            mSliderLayout.addSlider(textSliderView);
        }
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mSliderLayout.setClickable(true);
        mSliderLayout.setCustomIndicator((PagerIndicator) getView().findViewById(R.id.custom_pagerindicator));

        mMerchantAdapter = new MerchantAdapter(getActivity());
        mListView.setAdapter(mMerchantAdapter);
        mListView.setEmptyView(mProgressBar);

        getAsyscDataList();

        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        getAsyscDataList();
                    }
                }.execute();
            }

            @Override
            public void onLoadMore() {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        getAsyscDataList();
                    }
                }.execute();
            }
        });
        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.backMain();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mSliderLayout !=null) {
            mSliderLayout.stopAutoCycle();
            mSliderLayout = null;
        }
    }

    private HashMap<String,String> getData(){
        HashMap<String,String> http_url_maps = new HashMap<String, String>();
        http_url_maps.put("习近平接受八国新任驻华大使递交国书", "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg");
        http_url_maps.put("天津港总裁出席发布会", "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg");
        http_url_maps.put("瑞海公司从消防鉴定到安评一路畅通无阻", "http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg");
        http_url_maps.put("Airbnb高调入华 命运将如Uber一样吗？", "http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg");

        return http_url_maps;
    }

    public void getAsyscDataList() {
        asyncHttpClient.get(mHttpUrl, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.e("tag", "s :" + s);
                Gson gson = new Gson();
                MerchantResultBean merchantResultBean = gson.fromJson(s, MerchantResultBean.class);
                InfoBean info = merchantResultBean.getInfo();
                List<MerchantBean> merchantBeanList = info.getMerchantKey();

                mMerchantAdapter.setList(merchantBeanList);

                mListView.setRefreshTime(new SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis()));
                mListView.stopRefresh();
                mListView.stopLoadMore();
            }
        });
    }
    public static class MerchantAdapter extends BaseAdapter {
        private static final int TYPE_ONE = 0;
        private static final int TYPE_TWO = 1;
        private List<MerchantBean> list = new ArrayList<>();
        private LayoutInflater layoutInflater;
        private Context context;

        public MerchantAdapter(Context context) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
        }

        public void setList(List<MerchantBean> merchantBeanList) {
            list = merchantBeanList;
            notifyDataSetChanged(); //通知刷新适配器数据源
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            MerchantBean merchantBean = (MerchantBean) getItem(position);
            if(merchantBean.getCardType().equalsIgnoreCase("YES")){
                return TYPE_TWO;
            }else{
                return TYPE_ONE;
            }
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(getItemViewType(position) == TYPE_TWO){
                return getTypeTwoView(position,convertView,parent);
            }else{
                return getTypeOneView(position,convertView,parent);
            }
        }

        public View getTypeTwoView(int position, View convertView, ViewGroup parent) {
            ViewTwoHodler viewTwoHodler = null;
            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.item_list_view_two_layout,null);

                viewTwoHodler = new ViewTwoHodler();
                viewTwoHodler.icon1Img = (ImageView) convertView.findViewById(R.id.list_two_icon1_img);
                viewTwoHodler.icon2Img = (ImageView) convertView.findViewById(R.id.list_two_icon2_img);
                viewTwoHodler.icon3Img = (ImageView) convertView.findViewById(R.id.list_two_icon3_img);
                convertView.setTag(viewTwoHodler);
            }
            viewTwoHodler = (ViewTwoHodler) convertView.getTag();
            MerchantBean merchantBean = (MerchantBean) getItem(position);
            Picasso.with(context).load(merchantBean.getPicUrl()).into(viewTwoHodler.icon1Img);
            Picasso.with(context).load(merchantBean.getPicUrl()).into(viewTwoHodler.icon2Img);
            Picasso.with(context).load(merchantBean.getPicUrl()).into(viewTwoHodler.icon3Img);
            return convertView;
        }

        public View getTypeOneView(int position, View convertView, ViewGroup parent) {
            ViewHodler viewHodler = null;
            if (convertView == null) {
                //一级优化
                convertView = layoutInflater.inflate(R.layout.item_list_view_layout, null);
                //二级优化
                viewHodler = new ViewHodler();
                viewHodler.iconImg = (ImageView) convertView.findViewById(R.id.list_icon_img);
                viewHodler.nameTxt = (TextView) convertView.findViewById(R.id.list_name_txt);
                viewHodler.couponTxt = (TextView) convertView.findViewById(R.id.list_coupe_txt);
                viewHodler.locationTxt = (TextView) convertView.findViewById(R.id.list_location_txt);
                viewHodler.distanceTxt = (TextView) convertView.findViewById(R.id.list_distance_txt);
                viewHodler.cardImg = (ImageView) convertView.findViewById(R.id.list_card_img);
                viewHodler.groupImg = (ImageView) convertView.findViewById(R.id.list_group_img);
                viewHodler.ticketImg = (ImageView) convertView.findViewById(R.id.list_ticket_img);
                convertView.setTag(viewHodler);
            }
            viewHodler = (ViewHodler) convertView.getTag();

            MerchantBean merchantBean = (MerchantBean) getItem(position);
            Picasso.with(context).load(merchantBean.getPicUrl()).into(viewHodler.iconImg);
            viewHodler.nameTxt.setText(merchantBean.getName());
            viewHodler.couponTxt.setText(merchantBean.getCoupon());
            viewHodler.locationTxt.setText(merchantBean.getLocation());
            viewHodler.distanceTxt.setText(merchantBean.getDistance());

            if (merchantBean.getCardType().equalsIgnoreCase("YES")) {
                viewHodler.cardImg.setVisibility(View.VISIBLE);
            } else {
                viewHodler.cardImg.setVisibility(View.GONE);
            }
            if (merchantBean.getGroupType().equalsIgnoreCase("YES")) {
                viewHodler.groupImg.setVisibility(View.VISIBLE);
            } else {
                viewHodler.groupImg.setVisibility(View.GONE);
            }
            if (merchantBean.getCouponType().equalsIgnoreCase("YES")) {
                viewHodler.ticketImg.setVisibility(View.VISIBLE);
            } else {
                viewHodler.ticketImg.setVisibility(View.GONE);
            }
            return convertView;
        }

        static class ViewHodler {
            ImageView iconImg;
            TextView nameTxt;
            TextView couponTxt;
            TextView locationTxt;
            TextView distanceTxt;
            ImageView cardImg;
            ImageView groupImg;
            ImageView ticketImg;
        }

        static class ViewTwoHodler {
            ImageView icon1Img;
            ImageView icon2Img;
            ImageView icon3Img;
        }
    }
}
