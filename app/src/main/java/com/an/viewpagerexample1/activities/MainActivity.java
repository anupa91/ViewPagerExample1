package com.an.viewpagerexample1.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.an.viewpagerexample1.R;
import com.an.viewpagerexample1.adapters.ViewPagerAdapter;
import com.an.viewpagerexample1.fragments.PagerFragment;

public class MainActivity extends AppCompatActivity {

    // Constants
    private static final String TAG = MainActivity.class.getSimpleName();

    // UI Components
    private ViewPager mVpMainPager;

    // Other objects
    private ViewPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mVpMainPager = findViewById(R.id.activity_main_vp_main_pager);

        setupViewPager();
    }

    private void setupViewPager() {
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragment(PagerFragment.newInstance("IMG_1"), "Image 1"); // Position 0
        mPagerAdapter.addFragment(PagerFragment.newInstance("VDO_1"), "Video 1"); // Position 1
        mPagerAdapter.addFragment(PagerFragment.newInstance("IMG_2"), "Image 2"); // Position 2
        mPagerAdapter.addFragment(PagerFragment.newInstance("VDO_2"), "Video 2"); // Position 3
        mVpMainPager.setAdapter(mPagerAdapter);
        mVpMainPager.setOffscreenPageLimit(3);

        mVpMainPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0 || position == 2) {
                    PagerFragment videoFragment1 = (PagerFragment) mPagerAdapter.getItem(1);
                    videoFragment1.pausePlay();
                    PagerFragment videoFragment2 = (PagerFragment) mPagerAdapter.getItem(3);
                    videoFragment2.pausePlay();
                } else if (position == 1) {
                    PagerFragment videoFragment1 = (PagerFragment) mPagerAdapter.getItem(1);
                    videoFragment1.autoStart();
                } else if (position == 3) {
                    PagerFragment videoFragment2 = (PagerFragment) mPagerAdapter.getItem(3);
                    videoFragment2.autoStart();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
