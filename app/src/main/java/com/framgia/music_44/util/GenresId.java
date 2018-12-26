package com.framgia.music_44.util;

import android.support.annotation.IntDef;

@IntDef({
        GenresId.ALL_AUDIO_INT, GenresId.ALL_MUSIC_INT, GenresId.ALTERNATIVE_ROCK_INT,
        GenresId.AMBIENT_INT, GenresId.CLASSICAL_INT, GenresId.COUNTRY_INT
})
public @interface GenresId {
    int ALL_MUSIC_INT = 0;
    int ALL_AUDIO_INT = 1;
    int ALTERNATIVE_ROCK_INT = 2;
    int AMBIENT_INT = 3;
    int CLASSICAL_INT = 4;
    int COUNTRY_INT = 5;
}
