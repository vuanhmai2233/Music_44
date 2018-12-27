package com.framgia.music_44.screen.home;

import com.framgia.music_44.data.model.Songs;
import java.util.List;

public interface HomeContract {
    interface View {
        void onGetSongsSuccess(List<Songs> songsList);

        void onGetSongsFail(Exception e);
    }

    interface Presenter {
        void getSongsLocal();

        void getSongRemote(String genresSong);

        void getSongBySearch(String querySearch);
    }
}
