package com.ittx.netapp;

import android.os.Bundle;
import android.view.Window;

import com.warmtel.slidingmenu.lib.SlidingMenu;
import com.warmtel.slidingmenu.lib.app.SlidingActivity;

public class MerchantActivity extends SlidingActivity implements MenuFragment.OnMenuFragmentInteractionListener
        ,MerchantFragment.OnMerchantFragmentInteractionListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_merchant_layout);

        getSupportFragmentManager().beginTransaction().
                add(R.id.merchant_container_layout, MerchantMainTwoFragment.newInstance()).commit();

        //--------------侧滑菜单------------------
        setBehindContentView(R.layout.sliding_menu_merchant_layout);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.sliding_merchant_layout,MenuFragment.newInstance()).commit();

        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

    }

    @Override
    public void toPicture() {
        /*getSupportFragmentManager().beginTransaction().replace(R.id.merchant_container_layout, PictureActivity.newInstance()).commit();
        toggle();*/
        PictureActivity.actionToPictureActivity(this);
        toggle();
    }

    @Override
    public void toVideo() {
        VideoActivity.actionToVideoActivity(this);
        toggle();
    }

    @Override
    public void toMusic() {
        MusicActivity.actionToMusicFragment(this);
        toggle();
    }

    @Override
    public void backMain() {
        toggle();
    }
}
