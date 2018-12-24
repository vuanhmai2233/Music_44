package com.framgia.music_44.screen.play_music;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.framgia.music_44.R;
import com.framgia.music_44.data.model.Songs;
import com.framgia.music_44.screen.mainActivity.MainActivity;
import com.framgia.music_44.screen.play_music.service.ServicePlayMusic;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.music_44.util.Options.LOOP_ALL;
import static com.framgia.music_44.util.Options.LOOP_ONE;
import static com.framgia.music_44.util.Options.NON_LOOP;
import static com.framgia.music_44.util.Options.STRING_LOOP_ALL;
import static com.framgia.music_44.util.Options.STRING_LOOP_ONE;
import static com.framgia.music_44.util.Options.STRING_NON_LOOP;

public class PlayMusicFragment extends Fragment
        implements View.OnClickListener, MediaPlayer.OnCompletionListener {
    private static final String ARGUMENT_SONGS = "ARGUMENT_SONGS";
    private static final String ARGUMENT_POSITION = "ARGUMENT_POSITION";
    private List<Songs> mSongs;
    private int mPosition;
    private TextView mTextViewNameSong;
    private ImageView mImageViewSongs;
    private ImageButton mImageButtonPlay, mImageButtonPause, mImageButtonLoopOne;
    private MediaPlayer mMediaPlayer;
    private int mState = NON_LOOP;
    private ServicePlayMusic mServicePlayMusic;

    public static PlayMusicFragment newInstance(int position, List<Songs> songs) {
        PlayMusicFragment playMusicFragment = new PlayMusicFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARGUMENT_SONGS, (ArrayList<? extends Parcelable>) songs);
        bundle.putInt(ARGUMENT_POSITION, position);
        playMusicFragment.setArguments(bundle);
        return playMusicFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mServicePlayMusic = mainActivity.getServicePlayMusic();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen_play_music, container, false);
        initView(view);
        initData();
        mServicePlayMusic.autoPlay(mPosition, mSongs);
        return view;
    }

    private void initView(View view) {
        mImageButtonLoopOne = view.findViewById(R.id.imageButtonLoopOne);
        view.findViewById(R.id.imageButtonDownLoad).setOnClickListener(this);
        view.findViewById(R.id.imageButtonBack).setOnClickListener(this);
        mImageButtonPlay = view.findViewById(R.id.imageButtonPlay);
        mImageButtonPause = view.findViewById(R.id.imageButtonPause);
        view.findViewById(R.id.imageButtonNext).setOnClickListener(this);
        mTextViewNameSong = view.findViewById(R.id.textViewName);
        mImageViewSongs = view.findViewById(R.id.imageView);
        mImageButtonPlay.setVisibility(View.GONE);
        mImageButtonPause.setVisibility(View.VISIBLE);
        mImageButtonPlay.setOnClickListener(this);
        mImageButtonPause.setOnClickListener(this);
        mImageButtonLoopOne.setOnClickListener(this);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mSongs = bundle.getParcelableArrayList(ARGUMENT_SONGS);
            mPosition = bundle.getInt(ARGUMENT_POSITION);
            setTextImage();
        }
        mMediaPlayer = mServicePlayMusic.getMediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonDownLoad:
                mServicePlayMusic.onDownloadMusic();
                break;
            case R.id.imageButtonLoopOne:
                setButtonLoop();
                break;
            case R.id.imageButtonBack:
                mPosition = mServicePlayMusic.backPlayer();
                setTextImage();
                setButtonPlayPause();
                break;
            case R.id.imageButtonPlay:
                mServicePlayMusic.resetMusic();
                setButtonPlayPause();
                break;
            case R.id.imageButtonPause:
                mServicePlayMusic.pauseMusic();
                setButtonPlayPause();
                break;
            case R.id.imageButtonNext:
                setButtonNext();
                setButtonPlayPause();
                break;
            default:
                break;
        }
    }

    private void setButtonPlayPause() {
        if (mServicePlayMusic.checked()) {
            mImageButtonPause.setVisibility(View.VISIBLE);
            mImageButtonPlay.setVisibility(View.GONE);
        } else {
            mImageButtonPlay.setVisibility(View.VISIBLE);
            mImageButtonPause.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        switch (mState) {
            case NON_LOOP:
                mMediaPlayer.stop();
                mImageButtonPlay.setVisibility(View.VISIBLE);
                mImageButtonPause.setVisibility(View.GONE);
                break;
            case LOOP_ONE:
                mPosition = mServicePlayMusic.loopOne();
                break;
            case LOOP_ALL:
                mPosition = mServicePlayMusic.nextPlayer();
                setTextImage();
                break;
        }
    }

    private void setButtonNext() {
        mPosition = mServicePlayMusic.nextPlayer();
        setTextImage();
    }

    private void setButtonLoop() {
        switch (mState) {
            case NON_LOOP:
                mState = LOOP_ONE;
                mImageButtonLoopOne.setImageResource(R.drawable.loop_one);
                Toast.makeText(getContext(), STRING_LOOP_ONE, Toast.LENGTH_SHORT).show();
                break;
            case LOOP_ONE:
                mState = LOOP_ALL;
                mImageButtonLoopOne.setImageResource(R.drawable.loop_all);
                Toast.makeText(getContext(), STRING_LOOP_ALL, Toast.LENGTH_SHORT).show();
                break;
            case LOOP_ALL:
                mState = NON_LOOP;
                mImageButtonLoopOne.setImageResource(R.drawable.repeat);
                Toast.makeText(getContext(), STRING_NON_LOOP, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setTextImage() {
        mTextViewNameSong.setText(mSongs.get(mPosition).getNameSong());
        Glide.with(getActivity().getApplicationContext())
                .load(mSongs.get(mPosition).getImage())
                .into(mImageViewSongs);
    }
}
