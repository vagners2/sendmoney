package br.com.vagners.sendmoney.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import br.com.vagners.sendmoney.R;

/**
 * Created by vagnerss on 25/08/16.
 */


public class VideoSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private MediaPlayer mediaplayer;
    private boolean started = false;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VideoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoSurfaceView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mediaplayer = new MediaPlayer();
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.the_station);

            if (!started) {
                started = true;
                mediaplayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getDeclaredLength());
                mediaplayer.prepare();
            }

            android.view.ViewGroup.LayoutParams param = getLayoutParams();
            param.height = getHeight();
            param.width = getWidth();

            setLayoutParams(param);
            mediaplayer.setDisplay(getHolder());
            mediaplayer.setLooping(true);
            mediaplayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mediaplayer.pause();
    }
}