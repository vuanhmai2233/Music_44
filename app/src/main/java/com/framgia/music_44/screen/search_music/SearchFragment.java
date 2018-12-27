package com.framgia.music_44.screen.search_music;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.music_44.R;
import com.framgia.music_44.screen.home.HomeFragment;
import com.framgia.music_44.util.Constant;
import com.framgia.music_44.util.Navigator;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class SearchFragment extends Fragment {

    private MaterialSearchView mMaterialSearchView;
    private Navigator mNavigator;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        initView(view);
        setHasOptionsMenu(true);
        handleSearch();
        return view;
    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.search);
        toolbar.setTitleTextColor(Color.WHITE);
        mMaterialSearchView = view.findViewById(R.id.searchView);
        mNavigator = new Navigator();
    }

    private void handleSearch() {
        mMaterialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mNavigator.addFragment(HomeFragment.newInstance(Constant.SEARCH_KEY, query),
                        getFragmentManager());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.searchviewmenu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        mMaterialSearchView.setMenuItem(menuItem);
    }
}
