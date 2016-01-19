package com.ittx.netapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

public class MainActivity extends Activity {
    private XListView mListView;
//    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    private SliderLayout mSliderLayout;
    private String mHttpUrl = "http://192.168.1.139/json/around";
    private MerchantAdapter mMerchantAdapter;
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_layout);

        mProgressBar = (ProgressBar) findViewById(R.id.merchant_progressbar);
        mListView = (XListView) findViewById(R.id.list_listview);
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.merchant_swipe_layout);
//        mSwipeRefreshLayout.setColorSchemeResources(
//                android.R.color.holo_blue_dark,
//                android.R.color.holo_green_dark,
//                android.R.color.holo_orange_dark,
//                android.R.color.holo_red_dark);

        View sliderHeaderView = LayoutInflater.from(this).inflate(R.layout.slider_image_layout,null);
        mSliderLayout = (SliderLayout) sliderHeaderView.findViewById(R.id.slider_imager);
        mListView.addHeaderView(sliderHeaderView);

        HashMap<String,String> silderList = getData();
        for(final String key : silderList.keySet()){
            String url = silderList.get(key);
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.description(key);
            textSliderView.image(url);
            textSliderView.setScaleType(BaseSliderView.ScaleType.CenterCrop);
            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView baseSliderView) {
                    Toast.makeText(MainActivity.this,key,Toast.LENGTH_SHORT).show();
                }
            });
            mSliderLayout.addSlider(textSliderView);
        }
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mSliderLayout.setClickable(true);
        mSliderLayout.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_pagerindicator));

        mMerchantAdapter = new MerchantAdapter(this);
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

//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new AsyncTask<Void, Void, Void>() {
//
//                    @Override
//                    protected Void doInBackground(Void... params) {
//                        try {
//                            Thread.sleep(4000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Void aVoid) {
//                        super.onPostExecute(aVoid);
//                        getAsyscDataList();
//                    }
//                }.execute();
//
//            }
//        });
    }

    @Override
    protected void onStop() {
        super.onStop();
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
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
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
//                mSwipeRefreshLayout.setRefreshing(false);
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
