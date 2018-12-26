package com.framgia.music_44.util;

import android.support.annotation.StringDef;

@StringDef({
        Genres.ALL_AUDIO, Genres.ALL_MUSIC, Genres.ALTERNATIVE_ROCK, Genres.AMBIENT,
        Genres.CLASSICAL, Genres.COUNTRY
})

public @interface Genres {
    String ALL_MUSIC = "Music";
    String ALL_AUDIO = "Audio";
    String ALTERNATIVE_ROCK = "Alaternative Rock";
    String AMBIENT = "Ambient";
    String CLASSICAL = "Classical";
    String COUNTRY = "Country";
}
