package com.framgia.music_44.screen.play_music.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import com.framgia.music_44.data.model.Songs;
import com.framgia.music_44.util.Constant;
import java.io.IOException;
import java.util.List;

public class ServicePlayMusic extends Service {
    private static final String STATUS_DOWNLOAD = "DownLoading";
    private MediaPlayer mMediaPlayer;
    private List<Songs> mSongs;
    private int mPosition;
    private IBinder mIBinder;

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    @Override
    public IBinder onBind(Intent intent) {
        mMediaPlayer = new MediaPlayer();
        return mIBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mIBinder = new serviceSongs();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void autoPlay(int position, List<Songs> songsList) {
        mPosition = position;
        mSongs = songsList;
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(songsList.get(position).getUri());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    public int backPlayer() {
        mPosition--;
        if (mPosition < Constant.ZERO) {
            mPosition = mSongs.size() - Constant.ONE;
        }
        autoPlay(mPosition, mSongs);
        return mPosition;
    }

    public int nextPlayer() {
        mPosition++;
        if (mPosition > mSongs.size() - Constant.ONE) {
            mPosition = Constant.ZERO;
        }
        autoPlay(mPosition, mSongs);
        return mPosition;
    }

    public int loopOne() {
        autoPlay(mPosition, mSongs);
        return mPosition;
    }

    public void pauseMusic() {
        mMediaPlayer.pause();
    }

    public void resetMusic() {
        if (!mMediaPlayer.isPlaying() && mMediaPlayer.getCurrentPosition() != Constant.ZERO) {
            mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition());
            mMediaPlayer.start();
        } else {
            mMediaPlayer.seekTo(Constant.ZERO);
            mMediaPlayer.start();
        }
    }

    public void onDownloadMusic() {
        DownloadManager downloadmanager =
                (DownloadManager) getApplicationContext().getSystemService(
                        Context.DOWNLOAD_SERVICE);
        if (downloadmanager != null) {
            Uri uri = Uri.parse(mSongs.get(mPosition).getUri());
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(mSongs.get(mPosition).getNameSong());
            request.setDescription(STATUS_DOWNLOAD);
            request.setNotificationVisibility(
                    DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC,
                    mSongs.get(mPosition).getNameSong() + Constant.MUSIC_FORMAT_MP3);
            downloadmanager.enqueue(request);
        }
    }

    public boolean checked() {
        return mMediaPlayer.isPlaying();
    }

    public class serviceSongs extends Binder {
        public ServicePlayMusic getBoundService() {
            return ServicePlayMusic.this;
        }
    }
}
