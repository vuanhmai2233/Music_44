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
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class SearchFragment extends Fragment {

    private MaterialSearchView mMaterialSearchView;

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
        return view;
    }

    private void initView(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.search);
        toolbar.setTitleTextColor(Color.parseColor(getString(R.string.dark)));
        mMaterialSearchView = view.findViewById(R.id.searchView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.searchviewmenu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        mMaterialSearchView.setMenuItem(menuItem);
    }
}
