package com.wennuan.olddriver.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;
import com.wennuan.olddriver.R;
import com.wennuan.olddriver.base.BaseActivity;
import com.wennuan.olddriver.base.BaseFragment;
import com.wennuan.olddriver.ui.main.chat.MessageFragment;
import com.wennuan.olddriver.ui.main.contact.ContactsFragment;
import com.wennuan.olddriver.ui.main.discover.AddDiscoverActivity;
import com.wennuan.olddriver.ui.main.discover.DiscoverFragment;
import com.wennuan.olddriver.ui.main.mime.MimeFragment;
import com.wennuan.olddriver.ui.map.MapActivity;
import com.wennuan.olddriver.ui.widget.NoScrollViewPager;
import com.wennuan.olddriver.util.BadgesEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_bar_tab)
    BottomBar mBottomBarTab;
    @BindView(R.id.tv_main_title)
    TextView mMainTitleTv;
    @BindView(R.id.vp_main)
    NoScrollViewPager mMainPager;
    @BindView(R.id.tv_main_map)
    TextView mMainMapTv;

    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private long firstTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViewPager();
//        startActivity(new Intent(this, ChatDetailActivity.class));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void initViewPager() {
        mFragmentList.add(MessageFragment.newInstance());
        mFragmentList.add(ContactsFragment.newInstance());
        mFragmentList.add(DiscoverFragment.newInstance());
        mFragmentList.add(MimeFragment.newInstance());
        mMainPager.setOffscreenPageLimit(mFragmentList.size());
        mMainPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        mMainPager.setScrollble(false);
        mBottomBarTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_message:
                        mMainPager.setCurrentItem(0);
                        mMainTitleTv.setText(mFragmentList.get(0).getFragmentName());
                        mMainMapTv.setText("地图");
                        break;
                    case R.id.tab_contacts:
                        mMainPager.setCurrentItem(1);
                        mMainTitleTv.setText(mFragmentList.get(1).getFragmentName());
                        mMainMapTv.setText("地图");
                        break;
                    case R.id.tab_discover:
                        mMainPager.setCurrentItem(2);
                        mMainTitleTv.setText(mFragmentList.get(2).getFragmentName());
                        mMainMapTv.setText("添加");
                        break;
                    case R.id.tab_mime:
                        mMainPager.setCurrentItem(3);
                        mMainTitleTv.setText(mFragmentList.get(3).getFragmentName());
                        mMainMapTv.setText("地图");
                        break;
                }
            }
        });
        mMainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomBarTab.selectTabAtPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mMainMapTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMainMapTv.getText().equals("地图"))
                    startActivity(new Intent(MainActivity.this, MapActivity.class));
                else
                    startActivity(new Intent(MainActivity.this, AddDiscoverActivity.class));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showBadges(BadgesEvent badgesEvent) {
        BottomBarTab tab = mBottomBarTab.getTabWithId(R.id.tab_message);
        int count = badgesEvent.count;
        if (count > 0) {
            tab.setBadgeCount(badgesEvent.count);
        } else {
            tab.removeBadge();
        }

    }


    private class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

    }


}
