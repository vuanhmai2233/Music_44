package com.framgia.music_44.data.source.remote;

import com.framgia.music_44.data.model.Songs;
import java.util.List;

public interface OnResultDataListenerRemote {
    void onSuccess(List<Songs> data);

    void onFail(Exception e);
}
