package com.framgia.music_44.screen.play_music;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.framgia.music_44.R;
import com.framgia.music_44.data.model.Songs;
import com.framgia.music_44.screen.mainActivity.MainActivity;
import com.framgia.music_44.screen.play_music.service.ServicePlayMusic;
import com.framgia.music_44.util.Constant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.framgia.music_44.util.Options.LOOP_ALL;
import static com.framgia.music_44.util.Options.LOOP_ONE;
import static com.framgia.music_44.util.Options.NON_LOOP;
import static com.framgia.music_44.util.Options.NON_SHUFFLE;
import static com.framgia.music_44.util.Options.SHUFFLE;
import static com.framgia.music_44.util.Options.STRING_LOOP_ALL;
import static com.framgia.music_44.util.Options.STRING_LOOP_ONE;
import static com.framgia.music_44.util.Options.STRING_NON_LOOP;

public class PlayMusicFragment extends Fragment
        implements View.OnClickListener, MediaPlayer.OnCompletionListener,
        SeekBar.OnSeekBarChangeListener {

    private static final String ARGUMENT_SONGS = "ARGUMENT_SONGS";
    private static final String ARGUMENT_POSITION = "ARGUMENT_POSITION";
    private static final String FORMAT_TIME = "%02d:%02d";

    private List<Songs> mSongs;
    private int mPosition;
    private TextView mTextViewNameSong, mTextViewTimeSongPosition, mTextViewTimeSongDuration;
    private ImageView mImageViewSongs;
    private ImageButton mImageButtonPlay, mImageButtonPause, mImageButtonLoopOne,
            mImageButtonShuffle;
    private MediaPlayer mMediaPlayer;
    private int mState = NON_LOOP;
    private int mStateShuffle = NON_SHUFFLE;
    private SeekBar mSeekBar;
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
        setSeekBar();
        return view;
    }

    private void initView(View view) {
        mImageButtonLoopOne = view.findViewById(R.id.imageButtonLoopOne);
        view.findViewById(R.id.imageButtonDownLoad).setOnClickListener(this);
        view.findViewById(R.id.imageButtonBack).setOnClickListener(this);
        mImageButtonPlay = view.findViewById(R.id.imageButtonPlay);
        mImageButtonPause = view.findViewById(R.id.imageButtonPause);
        mSeekBar = view.findViewById(R.id.seekBarSong);
        view.findViewById(R.id.imageButtonNext).setOnClickListener(this);
        mImageButtonShuffle = view.findViewById(R.id.imageButtonShuffle);
        mImageButtonShuffle.setOnClickListener(this);
        mTextViewNameSong = view.findViewById(R.id.textViewName);
        mTextViewTimeSongPosition = view.findViewById(R.id.textViewTimeSongPosition);
        mTextViewTimeSongDuration = view.findViewById(R.id.textViewTimeSongDuration);
        mImageViewSongs = view.findViewById(R.id.imageView);
        mImageButtonPlay.setVisibility(View.GONE);
        mImageButtonPause.setVisibility(View.VISIBLE);
        mImageButtonPlay.setOnClickListener(this);
        mImageButtonPause.setOnClickListener(this);
        mImageButtonLoopOne.setOnClickListener(this);
    }

    private void initData() {
        if (getArguments() != null) {
            mSongs = getArguments().getParcelableArrayList(ARGUMENT_SONGS);
            mPosition = getArguments().getInt(ARGUMENT_POSITION);
            setTextImage();
        }
        mMediaPlayer = mServicePlayMusic.getMediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
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
            case R.id.imageButtonShuffle:
                setButtonShuffle();
                break;
            default:
                break;
        }
    }

    private void setSeekBar() {
        if (mMediaPlayer.isPlaying()) {
            final Handler handler = new Handler();
            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSeekBar.setMax(mMediaPlayer.getDuration() / Constant.ONE_THOUSAND);
                    int mCurrentPosition =
                            mMediaPlayer.getCurrentPosition() / Constant.ONE_THOUSAND;
                    mSeekBar.setProgress(mCurrentPosition);
                    mTextViewTimeSongPosition.setText(
                            parseDurationToStringTime(mMediaPlayer.getCurrentPosition()));
                    mTextViewTimeSongDuration.setText(
                            parseDurationToStringTime(mMediaPlayer.getDuration()));
                    handler.postDelayed(this, Constant.ONE_THOUSAND);
                }
            });
        }
    }

    private String parseDurationToStringTime(long duration) {
        return String.format(Locale.getDefault(), FORMAT_TIME,
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(duration)));
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
                mPosition = mServicePlayMusic.nextPlayer();
                setTextImage();
                if (mPosition == 0) {
                    mMediaPlayer.stop();
                }
                mImageButtonPause.setVisibility(View.GONE);
                mImageButtonPlay.setVisibility(View.VISIBLE);
                break;
            case LOOP_ONE:
                mPosition = mServicePlayMusic.loopOne();
                break;
            case LOOP_ALL:
                mPosition = mServicePlayMusic.nextPlayer();
                setTextImage();
                break;
        }
        switch (mStateShuffle) {
            case SHUFFLE:
                handleShuffle();
                break;
        }
    }

    private void handleShuffle() {
        mPosition = new Random().nextInt((mSongs.size() - Constant.ONE) + Constant.ONE);
        mServicePlayMusic.autoPlay(mPosition, mSongs);
        setTextImage();
        mImageButtonPause.setVisibility(View.VISIBLE);
        mImageButtonPlay.setVisibility(View.GONE);
    }

    private void setButtonShuffle() {
        if (mStateShuffle == NON_SHUFFLE) {
            mStateShuffle = SHUFFLE;
            mImageButtonShuffle.setImageResource(R.drawable.ic_shuffle_black_24dp);
            Toast.makeText(getContext(), getString(R.string.shuffle), Toast.LENGTH_SHORT).show();
        } else {
            mStateShuffle = NON_SHUFFLE;
            mImageButtonShuffle.setImageResource(R.drawable.ic_shuffle_white_24dp);
            Toast.makeText(getContext(), getString(R.string.no_shuffle), Toast.LENGTH_SHORT).show();
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
        Glide.with(Objects.requireNonNull(getActivity()).getApplicationContext())
                .load(mSongs.get(mPosition).getImage())
                .into(mImageViewSongs);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mMediaPlayer != null && fromUser) {
            mMediaPlayer.seekTo(progress * Constant.ONE_THOUSAND);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
