package dk.hede.dan.whackamole;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class GameManager
{

    private SurfaceHolder surfaceHolder;

    private int screenW, screenH;
    private float scaleW, scaleH;

    public Background background;

    private boolean onTitle, onDiffi, gameOver;

    private Context context;

    private int points = 0;

    private int difficulty;

    private GameLoop gameLoop;

    private SoundPool sounds;
    private int whackSound;

    private Menu gameOverScreen;

    private GoogleApiClient mGoogle;

    private List<SpriteObject> moles = new ArrayList<SpriteObject>();
    private List<SpriteObject> masks = new ArrayList<SpriteObject>();
    private List<Menu> MenuItems = new ArrayList<Menu>();

    private static GameManager instance;

    public static GameManager getInstance()
    {
        if(instance == null)
        {
            instance = new GameManager();
        }
        return instance;
    }

    public List<SpriteObject> GetMoles()
    {
        return moles;
    }

    public List<SpriteObject> GetMasks()
    {
        return masks;
    }

    public List<Menu> GetMenu() {return MenuItems;}

    public int GetDifficulty() {return difficulty;}

    public int GetPoints() {return points;}

    public void SetPoints(int i) {points = i;}

    public Menu GetGameOverScreen() {return gameOverScreen;}

    public void SetGameOver(boolean g) {this.gameOver = g;}

    public void SetGoogleAPI(GoogleApiClient googleApiClient) {mGoogle = googleApiClient;}
    public GoogleApiClient GetGoogleAPI() {return mGoogle;}

    private GameManager()
    {

    }

    public void Setup(SurfaceHolder surfaceHolder, Context context)
    {
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        onTitle = true;
        sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        whackSound = sounds.load(context, R.raw.whacksound, 1);

    }

    public void setSurfaceSize(int width, int height)
    {
        synchronized (surfaceHolder)
        {
            screenW = width;
            screenH = height;

            if(background == null)
            {
                background = new Background(context, screenW, screenH);
            }

            scaleW = (float)screenW / (float)background.getOriginalWidth();
            scaleH = (float)screenH / (float)background.getOriginalHeight();
        }
    }

    public boolean doTouchEvent(MotionEvent event)
    {
        synchronized (surfaceHolder)
        {
            int eventAction = event.getAction();
            int x = (int)event.getX();
            int y = (int)event.getY();
            switch (eventAction)
            {
                case MotionEvent.ACTION_UP:
                    if(onTitle)
                    {
                        onDiffi = true;
                        background.setImage("diffi");
                        gameOverScreen = new Menu(BitmapFactory.decodeResource(context.getResources(), R.drawable.gameover),new PointF(50, 50), context, scaleW, scaleH, screenH, screenW);
                    }
                    if(onDiffi)
                    {
                        Menu easy = new Menu(BitmapFactory.decodeResource(context.getResources(), R.drawable.easy),new PointF(290, 165), context, scaleW, scaleH, screenH, screenW);
                        Menu medium = new Menu(BitmapFactory.decodeResource(context.getResources(), R.drawable.medium),new PointF(290,285), context, scaleW, scaleH, screenH, screenW);
                        Menu hard = new Menu(BitmapFactory.decodeResource(context.getResources(), R.drawable.hard),new PointF(290,405), context, scaleW, scaleH, screenH, screenW);
                        MenuItems.add(easy);
                        MenuItems.add(medium);
                        MenuItems.add(hard);
                        if(easy.GetCollsionRect().contains(x,y) || medium.GetCollsionRect().contains(x,y) || hard.GetCollsionRect().contains(x,y)) {
                            if(easy.GetCollsionRect().contains(x,y))
                            {
                                difficulty = 3000;
                            }
                            else if(medium.GetCollsionRect().contains(x,y))
                            {
                                difficulty = 2000;
                            }
                            else if(hard.GetCollsionRect().contains(x,y))
                            {
                                difficulty = 1000;
                            }
                            onTitle = false;
                            onDiffi = false;
                            background.setImage("inGame");
                            MakeMoles();
                        }
                    }
                    if(gameOver)
                    {

                        onDiffi = true;
                        background.setImage("diffi");
                        gameOver = false;
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    if (!onTitle && !onDiffi)
                    {
                        for (SpriteObject sprite : GameManager.getInstance().moles)
                        {
                            if (sprite.GetCollsionRect().contains(x,y) && sprite.showing)
                            {
                                AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                                float volume = audioManager.getStreamVolume(audioManager.STREAM_MUSIC);
                                sounds.play(whackSound,volume, volume, 1,0,1);
                                points += 100;
                                sprite.Hide();
                            }
                        }
                    }
                    break;
            }
        }
        return true;
    }

    public void StartLoop()
    {
        gameLoop = new GameLoop(surfaceHolder);

        gameLoop.SetRunning(true);

        gameLoop.start();
    }

    public void StopLoop()
    {
        gameLoop.SetRunning(false);

        while (true)
        {
            try
            {
                gameLoop.join();
            }
            catch (InterruptedException e)
            {

            }
            break;
        }
    }

    public void MakeMoles()
    {
        moles.add(new SpriteObject(new PointF(55,450), context, scaleW, scaleH, screenH, screenW, true));
        moles.add(new SpriteObject(new PointF(155,400), context, scaleW, scaleH, screenH, screenW, true));
        moles.add(new SpriteObject(new PointF(255,450), context, scaleW, scaleH, screenH, screenW, true));
        moles.add(new SpriteObject(new PointF(355,400), context, scaleW, scaleH, screenH, screenW, true));
        moles.add(new SpriteObject(new PointF(455,450), context, scaleW, scaleH, screenH, screenW, true));
        moles.add(new SpriteObject(new PointF(555,400), context, scaleW, scaleH, screenH, screenW, true));
        moles.add(new SpriteObject(new PointF(655,450), context, scaleW, scaleH, screenH, screenW, true));

        masks.add(new SpriteObject(new PointF(50, 450), context, scaleW, scaleH, screenH, screenW, false));
        masks.add(new SpriteObject(new PointF(150, 400), context, scaleW, scaleH, screenH, screenW, false));
        masks.add(new SpriteObject(new PointF(250, 450), context, scaleW, scaleH, screenH, screenW, false));
        masks.add(new SpriteObject(new PointF(350, 400), context, scaleW, scaleH, screenH, screenW, false));
        masks.add(new SpriteObject(new PointF(450, 450), context, scaleW, scaleH, screenH, screenW, false));
        masks.add(new SpriteObject(new PointF(550, 400), context, scaleW, scaleH, screenH, screenW, false));
        masks.add(new SpriteObject(new PointF(650, 450), context, scaleW, scaleH, screenH, screenW, false));
    }
}
