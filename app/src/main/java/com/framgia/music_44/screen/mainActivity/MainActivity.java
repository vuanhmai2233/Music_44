package com.framgia.music_44.screen.mainActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.framgia.music_44.R;
import com.framgia.music_44.screen.home.HomeFragment;
import com.framgia.music_44.util.Constant;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewPager;
    private MenuItem mMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setupViewPager();
    }

    private void initView() {
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mViewPager = findViewById(R.id.viewPager);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void setupViewPager() {
        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewpagerAdapter.addFragment(HomeFragment.newInstance());
        mViewPager.setAdapter(viewpagerAdapter);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        if (mMenuItem != null) {
            mMenuItem.setChecked(false);
        } else {
            mBottomNavigationView.getMenu().getItem(0).setChecked(false);
        }
        mBottomNavigationView.getMenu().getItem(i).setChecked(true);
        mMenuItem = mBottomNavigationView.getMenu().getItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigationHome:
                mViewPager.setCurrentItem(Constant.SCREEN_HOME_ID);
                break;
            case R.id.navigationGenres:
                mViewPager.setCurrentItem(Constant.SCREEN_GENRES_ID);
                break;
            case R.id.navigationSearch:
                mViewPager.setCurrentItem(Constant.SCREEN_SEARCH_ID);
                break;
        }
        return false;
    }
}
