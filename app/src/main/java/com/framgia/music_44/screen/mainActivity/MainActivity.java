package com.framgia.music_44.screen.mainActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.framgia.music_44.R;
import com.framgia.music_44.util.Constant;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.viewPager);
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        setupViewPager(mViewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewpagerAdapter);
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
