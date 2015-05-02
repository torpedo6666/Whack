package dk.hede.dan.whackamole;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLoop extends Thread {
    private boolean running;
    private long startTime;
    private long timeNow;
    private long nextMole;
    private String timer;
    private boolean start = false;
    private boolean timerStart = false;

    private final SurfaceHolder surfaceHolder;
    private List<SpriteObject> moles = new ArrayList<SpriteObject>();
    Random r = new Random();

    public void SetRunning(boolean running) {
        this.running = running;
    }

    public GameLoop(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer = Long.toString(millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {

        }
    };

    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(GameManager.getInstance().background.getImage(), 0, 0, null);
            //GameManager.getInstance().GetMole().draw(canvas);
            if(GameManager.getInstance().background.name.equals("inGame"))
            {
                Paint p = new Paint();
                p.setColor(Color.BLACK);
                p.setTextSize(100);
                if(!timerStart)
                {
                    countDownTimer.start();
                    timerStart = true;
                }
                canvas.drawText(timer, 800, 100, p);
                canvas.drawText(Integer.toString(GameManager.getInstance().GetPoints()), 100, 100, p);
                for (SpriteObject o : GameManager.getInstance().GetMoles()) {
                    o.draw(canvas);
                }
                for (SpriteObject o : GameManager.getInstance().GetMasks()) {
                    o.draw(canvas);
                }
            }
            else if(GameManager.getInstance().background.name.equals("diffi"))
            {
                for(Menu m : GameManager.getInstance().GetMenu())
                {
                    m.draw(canvas);
                }
            }
        } catch (Exception ignored) {

        }
    }

    @Override
    public void run() {
        while (running) {
            Canvas c = null;

            try {
                c = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    Update();
                    draw(c);

                }

            } finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }

    public void Update() {
        if (GameManager.getInstance().background.name.equals("inGame")) {
            if (!start) {
                startTime = SystemClock.elapsedRealtime();
                nextMole = startTime + 1000 + r.nextInt(GameManager.getInstance().GetDifficulty());
                start = true;
            }

            moles = GameManager.getInstance().GetMoles();

            if (moles.size() > 0) {
                timeNow = SystemClock.elapsedRealtime();
                if (nextMole <= timeNow) {
                    int k = r.nextInt(moles.size());
                    if (!moles.get(k).showing) {
                        moles.get(k).Show();
                        nextMole = timeNow + 1000 + r.nextInt(GameManager.getInstance().GetDifficulty());
                    }
                }
            }

            for (SpriteObject o : GameManager.getInstance().GetMoles()) {
                o.update();
            }
        }
    }
}