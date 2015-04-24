package dk.hede.dan.whackamole;


import android.content.Context;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    public GameView(Context context)
    {
        super(context);

        GameManager.getInstance().Setup(getHolder(), context);

        getHolder().addCallback(this);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return GameManager.getInstance().doTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        GameManager.getInstance().StartLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height)
    {
        GameManager.getInstance().setSurfaceSize(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {
        GameManager.getInstance().StopLoop();
    }
}