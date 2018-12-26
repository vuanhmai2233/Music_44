package com.framgia.music_44.screen.mainActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;
import com.framgia.music_44.R;
import com.framgia.music_44.screen.genres.GenresFragment;
import com.framgia.music_44.screen.home.HomeFragment;
import com.framgia.music_44.screen.play_music.service.ServicePlayMusic;
import com.framgia.music_44.util.Constant;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener {
    private ServicePlayMusic mServicePlayMusic;
    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewPager;
    private MenuItem mMenuItem;
    private boolean IsRunning;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServicePlayMusic.serviceSongs serviceSongs = (ServicePlayMusic.serviceSongs) service;
            mServicePlayMusic = serviceSongs.getBoundService();
            IsRunning = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            IsRunning = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissionReadExternalStorage(this);
        initView();
        setupViewPager();
        Intent intentService = new Intent(this, ServicePlayMusic.class);
        bindService(intentService, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public ServicePlayMusic getServicePlayMusic() {
        return mServicePlayMusic;
    }

    private void initView() {
        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mViewPager = findViewById(R.id.viewPager);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void setupViewPager() {
        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewpagerAdapter.addFragment(HomeFragment.newInstance(Constant.LOCAL));
        viewpagerAdapter.addFragment(GenresFragment.newInstance());
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

    private void checkPermissionReadExternalStorage(final Context context) {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog(String.valueOf(R.string.external_storage), context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                } else {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                            Constant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
            }
        }
    }

    private void showDialog(final String msg, final Context context, final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(R.string.permission_necessary);
        alertBuilder.setMessage(msg + R.string.permission_is_necessary);
        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions((Activity) context, new String[] { permission },
                        Constant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constant.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(MainActivity.this, R.string.get_accounts_denied,
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
