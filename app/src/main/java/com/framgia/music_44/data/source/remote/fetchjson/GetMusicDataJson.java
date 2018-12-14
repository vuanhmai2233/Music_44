package com.framgia.music_44.data.source.remote.fetchjson;

import android.os.AsyncTask;
import com.framgia.music_44.data.model.Songs;
import com.framgia.music_44.data.source.remote.OnResultDataListenerRemote;
import com.framgia.music_44.util.Constant;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetMusicDataJson extends AsyncTask<String, String, List<Songs>> {

    private static final String COLLECTION = "collection";
    private OnResultDataListenerRemote mOnResultDataListenerRemote;

    public GetMusicDataJson(OnResultDataListenerRemote onResultDataListenerRemote) {
        mOnResultDataListenerRemote = onResultDataListenerRemote;
    }

    @Override
    protected List<Songs> doInBackground(String... strings) {
        String data = getJsonFromUrl(strings[0]);

        return parseJsonToData(data);
    }

    @Override
    protected void onPostExecute(List<Songs> data) {
        super.onPostExecute(data);
        mOnResultDataListenerRemote.onSuccess(data);
    }

    private String getJsonFromUrl(String strings) {
        String data = "";
        try {
            URL url = new URL(strings);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private List<Songs> parseJsonToData(String data) {
        List<Songs> songsList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray(COLLECTION);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Songs songs = new Songs.SongsBuilder().duration(
                        object.getString(Songs.SongsEntry.DURATION))
                        .id(object.getString(Songs.SongsEntry.ID))
                        .image(object.getString(Songs.SongsEntry.ARTWORK_URL))
                        .nameArtist(object.getString(Songs.SongsEntry.LABLE_NAME))
                        .nameSong(object.getString(Songs.SongsEntry.TITLE))
                        .uri(object.getString(Songs.SongsEntry.STREAM_URL) + Constant.CLIENT_ID)
                        .build();
                songsList.add(songs);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return songsList;
    }
}
