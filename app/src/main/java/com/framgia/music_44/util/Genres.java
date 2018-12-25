package com.framgia.music_44.util;

import android.support.annotation.StringDef;

@StringDef({
        Genres.ALL_AUDIO, Genres.ALL_MUSIC, Genres.ALTERNATIVEROCK, Genres.AMBIENT,
        Genres.CLASSICAL, Genres.COUNTRY
})
public @interface Genres {
    String ALL_MUSIC = "All Music";
    String ALL_AUDIO = "All Audio";
    String ALTERNATIVEROCK = "Laternative Rock";
    String AMBIENT = "Ambient";
    String CLASSICAL = "Classical";
    String COUNTRY = "Country";
}
