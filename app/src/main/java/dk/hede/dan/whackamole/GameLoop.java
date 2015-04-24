package dk.hede.dan.whackamole;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.os.Looper;
import android.os.SystemClock;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameLoop extends Thread
{
    private boolean running;
    private long startTime;
    private long timeNow;
    private boolean start = false;
    private SurfaceHolder surfaceHolder;
    private List<SpriteObject> moles = new ArrayList<SpriteObject>();
    Random r = new Random();
    public void SetRunning(boolean running)
    {
        this.running = running;
    }

    public GameLoop(SurfaceHolder surfaceHolder)
    {
        this.surfaceHolder = surfaceHolder;
    }

    public void draw(Canvas canvas)
    {
        try
        {
            canvas.drawBitmap(GameManager.getInstance().background.getImage(), 0,0,null);
            //GameManager.getInstance().GetMole().draw(canvas);
            for (SpriteObject o : GameManager.getInstance().GetMoles())
            {
                o.draw(canvas);
            }
            for (SpriteObject o : GameManager.getInstance().GetMasks())
            {
                o.draw(canvas);
            }
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void run()
    {


        while(running)
        {
            Canvas c = null;

            try
            {
                c=surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder)
                {
                    Update();
                    draw(c);

                }

            }
            finally
            {
                if(c!=null)
                {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }


        }
    }

    public void Update()
    {
        if(GameManager.getInstance().background.name == "inGame") {
            if(!start) {
                startTime = SystemClock.elapsedRealtime() ;
                start = true;
            }

            moles = GameManager.getInstance().GetMoles();

            if (moles.size() > 0) {
                timeNow = SystemClock.elapsedRealtime();
                long timeToGo = 3 - ((timeNow - startTime) / 1000);
                int k = r.nextInt(moles.size());
                if (moles.get(k).moveing & timeToGo > 2)
                    moles.get(k).MoveDown();


                if (timeToGo <= 0.1) {
                    if (!moles.get(k).moveing)
                        moles.get(k).Move();
                    startTime = SystemClock.elapsedRealtime();
                }
            }
        }

    }
}