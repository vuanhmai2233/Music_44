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
import com.framgia.music_44.screen.play_music.PlayMusicFragment;
import com.framgia.music_44.util.Navigator;
import com.framgia.music_44.util.OnItemClickListener;
import java.util.List;
import java.util.Objects;

import static com.framgia.music_44.util.Constant.LOCAL;
import static com.framgia.music_44.util.Constant.REMOTE;

public class HomeFragment extends Fragment implements HomeContract.View, OnItemClickListener {

    public static final String ARGUMENT_GENRES = "ARGUMENT_GENRES";
    public static final String ARGUMENT_KEY = "ARGUMENT_KEY";

    private HomeContract.Presenter mPresenter;
    private HomeAdapter mHomeAdapter;
    private List<Songs> mSongs;
    private Navigator mNavigator;

    public static HomeFragment newInstance(String key) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_KEY, key);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    public static HomeFragment newInstance(String key, String genres) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_KEY, key);
        bundle.putString(ARGUMENT_GENRES, genres);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initPresenter();
        initData();
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewHome);
        recyclerView.setHasFixedSize(true);
        mHomeAdapter = new HomeAdapter(getContext(), this);
        recyclerView.setAdapter(mHomeAdapter);
        mNavigator = new Navigator();
    }

    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            switch (Objects.requireNonNull(bundle.getString(ARGUMENT_KEY))) {
                case LOCAL:
                    mPresenter.getSongsLocal();
                    break;
                case REMOTE:
                    String genre = bundle.getString(ARGUMENT_GENRES);
                    mPresenter.getSongRemote(genre);
                    break;
            }
        }
    }

    private void initPresenter() {
        SongLocalDataSource songLocalDataSource = SongLocalDataSource.getInstance(getContext());
        SongRemoteDataSource songRemoteDataSource = SongRemoteDataSource.getsInstance();
        SongsRepository songsRepository =
                SongsRepository.getsInstance(songLocalDataSource, songRemoteDataSource);
        mPresenter = new HomePresenter(songsRepository, this);
    }

    @Override
    public void onGetSongsSuccess(List<Songs> songs) {
        if (songs != null) {
            mSongs = songs;
            mHomeAdapter.addSongs(songs);
        }
    }

    @Override
    public void onItemClick(int position) {
        mNavigator.addFragment(PlayMusicFragment.newInstance(position, mSongs),
                getFragmentManager());
    }
}
