package com.framgia.music_44.screen.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.music_44.R;
import com.framgia.music_44.data.model.Songs;
import com.framgia.music_44.data.source.SongsRepository;
import com.framgia.music_44.data.source.local.SongLocalDataSource;
import com.framgia.music_44.data.source.remote.SongRemoteDataSource;
import com.framgia.music_44.screen.home.adapter.HomeAdapter;
import com.framgia.music_44.util.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View, OnItemClickListener {
    private HomeAdapter mHomeAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initPresenter();
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recylerViewHome);
        recyclerView.setHasFixedSize(true);
        mHomeAdapter = new HomeAdapter(getContext(), this);
        recyclerView.setAdapter(mHomeAdapter);
    }

    private void initPresenter() {
        SongLocalDataSource songLocalDataSource = SongLocalDataSource.getInstance(getContext());
        SongRemoteDataSource songRemoteDataSource = SongRemoteDataSource.getsInstance();
        SongsRepository songsRepository =
                SongsRepository.getsInstance(songLocalDataSource, songRemoteDataSource);
        HomeContract.Presenter presenter = new HomePresenter(songsRepository, this);
        presenter.getSongsLocal();
    }

    @Override
    public void onGetSongsSuccess(List<Songs> songs) {
        if (songs != null) {
            mHomeAdapter.addSongs(songs);
        }
    }

    @Override
    public void onItemClick(int position) {
    }
}
