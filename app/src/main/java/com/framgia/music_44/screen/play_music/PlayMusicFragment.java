package com.framgia.music_44.screen.play_music;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.framgia.music_44.R;
import com.framgia.music_44.data.model.Songs;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicFragment extends Fragment implements View.OnClickListener {
    private static final String ARGUMENT_SONGS = "ARGUMENT_SONGS";
    private static final String ARGUMENT_POSITION = "ARGUMENT_POSITION";

    private TextView mTextViewNameSong;
    private ImageView mImageViewSongs;

    public static PlayMusicFragment newInstance(int position, List<Songs> songs) {
        PlayMusicFragment playMusicFragment = new PlayMusicFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARGUMENT_SONGS, (ArrayList<? extends Parcelable>) songs);
        bundle.putInt(ARGUMENT_POSITION, position);
        playMusicFragment.setArguments(bundle);
        return playMusicFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen_play_music, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.imageButtonLoopAll).setOnClickListener(this);
        view.findViewById(R.id.imageButtonLoopOne).setOnClickListener(this);
        view.findViewById(R.id.imageButtonDownLoad).setOnClickListener(this);
        view.findViewById(R.id.imageButtonBack).setOnClickListener(this);
        view.findViewById(R.id.imageButtonPlay).setOnClickListener(this);
        view.findViewById(R.id.imageButtonPause).setOnClickListener(this);
        view.findViewById(R.id.imageButtonNext).setOnClickListener(this);
        mTextViewNameSong = view.findViewById(R.id.textViewName);
        mImageViewSongs = view.findViewById(R.id.imageView);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            List<Songs> mSongs = bundle.getParcelableArrayList(ARGUMENT_SONGS);
            int mPosition = bundle.getInt(ARGUMENT_POSITION);
            mTextViewNameSong.setText(mSongs.get(mPosition).getNameSong());
            Glide.with(getContext()).load(mSongs.get(mPosition).getImage()).into(mImageViewSongs);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonLoopAll:
                break;
            case R.id.imageButtonDownLoad:
                break;
            case R.id.imageButtonLoopOne:
                break;
            case R.id.imageButtonBack:
                break;
            case R.id.imageButtonPlay:
                break;
            case R.id.imageButtonPause:
                break;
            case R.id.imageButtonNext:
                break;
            default:
                break;
        }
    }
}
