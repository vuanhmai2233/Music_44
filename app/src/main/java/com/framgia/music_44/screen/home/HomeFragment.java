package com.framgia.music_44.screen.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.music_44.R;
import com.framgia.music_44.data.model.Songs;
import com.framgia.music_44.data.source.SongsRepository;
import com.framgia.music_44.data.source.local.SongLocalDataSource;
import com.framgia.music_44.data.source.remote.SongRemoteDataSource;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {
    private List<Songs> mSongsList;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mSongsList = new ArrayList<>();
        initPresenter();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    void initPresenter() {
        SongLocalDataSource songLocalDataSource = SongLocalDataSource.getInstance(getContext());
        SongRemoteDataSource songRemoteDataSource = SongRemoteDataSource.getsInstance();
        SongsRepository songsRepository =
                SongsRepository.getsInstance(songLocalDataSource, songRemoteDataSource);
        HomeContract.Presenter presenter = new HomePresenter(songsRepository, this);
        presenter.getSongsLocal();
        presenter.getSongRemote();
    }

    @Override
    public void onGetSongsSuccess(List<Songs> songsList) {
        mSongsList = songsList;
    }
}
