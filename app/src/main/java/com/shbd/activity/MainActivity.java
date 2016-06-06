package com.shbd.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jauker.widget.BadgeView;
import com.shbd.fragment.AddressListFragment;
import com.shbd.fragment.ChatFragment;
import com.shbd.fragment.FindFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;

    private MyAdapter mAdapter;

    private List<Fragment> fragmentList;

    private LinearLayout mLlChat;

    private TextView mTvChat;
    private TextView mTvFind;
    private TextView mTvAddressList;

    //小红点
    private BadgeView mBadgeView;

    //蓝线
    private ImageView mIvTabLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new ChatFragment());
        fragmentList.add(new FindFragment());
        fragmentList.add(new AddressListFragment());
    }

    private void initView() {
        mBadgeView = new BadgeView(getApplicationContext());
        mBadgeView.setBadgeCount(7);

        mIvTabLine = (ImageView) findViewById(R.id.iv_tabline);

        mLlChat = (LinearLayout) findViewById(R.id.ll_chat);

        mTvChat = (TextView) findViewById(R.id.txt_chat);
        mTvFind = (TextView) findViewById(R.id.txt_find);
        mTvAddressList = (TextView) findViewById(R.id.txt_addresslist);


        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        setTabLineWidth();

        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                removeTabLine(position, positionOffset);
            }


            @Override
            public void onPageSelected(int position) {
                resetTextViewColor();
                setTextViewColor(position);
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mLlChat.addView(mBadgeView);
    }

    /**
     * 移动蓝线的位置
     *
     * @param position
     */
    private void removeTabLine(int position, float positionOffset) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mIvTabLine.getLayoutParams();
        params.leftMargin = (int) (position * getScreenWidth() / 3 + positionOffset * getScreenWidth() / 3);
        mIvTabLine.setLayoutParams(params);
    }

    /**
     * 设置蓝线的宽度
     */
    private void setTabLineWidth() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mIvTabLine.getLayoutParams();
        params.width = getScreenWidth() / 3;
        mIvTabLine.setLayoutParams(params);
    }

    /**
     * 设置页签文字颜色
     */
    private void setTextViewColor(int position) {
        switch (position) {
            case 0:
                mTvChat.setTextColor(Color.parseColor("#325fb9"));
                break;
            case 1:
                mTvFind.setTextColor(Color.parseColor("#325fb9"));
                break;
            case 2:
                mTvAddressList.setTextColor(Color.parseColor("#325fb9"));
                break;
        }
    }

    /**
     * 重置所有页签文字颜色
     */
    private void resetTextViewColor() {
        mTvChat.setTextColor(Color.BLACK);
        mTvFind.setTextColor(Color.BLACK);
        mTvAddressList.setTextColor(Color.BLACK);
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public int getScreenWidth() {
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
