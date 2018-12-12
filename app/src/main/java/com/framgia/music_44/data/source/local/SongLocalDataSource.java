package com.framgia.music_44.data.source.local;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.framgia.music_44.data.model.Songs;
import com.framgia.music_44.data.source.SongDataSource;
import java.util.ArrayList;
import java.util.List;

public class SongLocalDataSource implements SongDataSource.Local {
    private static SongLocalDataSource sInstance;
    private Context mContext;

    private SongLocalDataSource(Context context) {
        mContext = context;
    }

    public static SongLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SongLocalDataSource(context);
        }
        return sInstance;
    }

    @Override
    public void getSongsLocal(OnResultDataListener songLocal) {
        getSongData(mContext, songLocal);
    }

    public void getSongData(Context context, OnResultDataListener songLocal) {
        List<Songs> songsList = new ArrayList<>();
        ContentResolver musicResolver = context.getContentResolver();
        Uri URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = musicResolver.query(URI, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int uri = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int nameSong = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int nameArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int image = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            do {
                String currenUri = cursor.getString(uri);
                String currenNameSong = cursor.getString(nameSong);
                String currenNameArtist = cursor.getString(nameArtist);
                String currenImage = String.valueOf(ContentUris.withAppendedId(
                        Uri.parse("content://media/external/audio/albumart"),
                        cursor.getInt(image)));
                String currenId = cursor.getString(id);
                String currenDuration = cursor.getString(duration);
                songsList.add(
                        new Songs(currenNameSong, currenNameArtist, currenImage, currenDuration,
                                currenUri, currenId));
            } while (cursor.moveToNext());
            cursor.close();
        }

        songLocal.onSuccess(songsList);
    }
}
