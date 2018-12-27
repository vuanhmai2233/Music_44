package com.framgia.music_44.util;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

@StringDef({
        Options.STRING_LOOP_ALL, Options.STRING_LOOP_ONE, Options.STRING_NON_LOOP
})
@IntDef({
        Options.LOOP_ALL, Options.NON_LOOP, Options.LOOP_ONE, Options.NON_SHUFFLE, Options.SHUFFLE
})
public @interface Options {
    int NON_LOOP = 0;
    int LOOP_ONE = 1;
    int LOOP_ALL = 2;
    int NON_SHUFFLE = 3;
    int SHUFFLE = 4;
    String STRING_NON_LOOP = "NO LOOP";
    String STRING_LOOP_ONE = "LOOP ONE";
    String STRING_LOOP_ALL = "LOOP ALL";
}
