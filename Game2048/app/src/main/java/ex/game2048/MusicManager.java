package ex.game2048;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class MusicManager {
    static final int MUSIC_PREVIOUS = -1;
    public static boolean BGMusic = true;
    static MediaPlayer mp;
    private static int currentMusic = -1;
    private static int previousMusic = -1;

    public static void start(Context context, int music) {
        start(context, music, false);
    }

    public static void start(Context context, int music, boolean force) {
        if (!force && currentMusic > -1) {
// already playing some music and not forced to change
            return;
        }


        if (music == MUSIC_PREVIOUS) {
            music = previousMusic;
        }
        if (currentMusic == music) {
// already playing this music
            return;
        }
        if (currentMusic != -1) {
            previousMusic = currentMusic;
// playing some other music, pause it and change
            pause();
        }
        currentMusic = music;
        if (mp != null) {
            if (!mp.isPlaying()) {
                mp.start();
            }
        } else {
            mp = MediaPlayer.create(context, R.raw.thrones); //Ur BackGround Music
        }

        if (mp == null) {
        } else {
            try {
                mp.setLooping(true);
                mp.start();
            } catch (Exception e) {
            }
        }
    }

    public static void pause() {
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.pause();
            }
        }

// previousMusic should always be something valid
        if (currentMusic != -1) {
            {
                previousMusic = currentMusic;
            }
            currentMusic = -1;
        }
    }

    public static void release() {
        try {
            if (mp != null) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
                mp.release();
            }
        } catch (Exception e) {
        }

        if (currentMusic != -1) {
            previousMusic = currentMusic;
        }
        currentMusic = -1;
    }
}
