package dk.hede.dan.whackamole;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.CountDownTimer;

public class SpriteObject
{
    private Bitmap sprite;

    private RectF collsionRect;
    Context con;
    private PointF myPos;
    private float y;
    private float oldY;
    public boolean moveing = false;
    Paint rectanglePaint;

    public SpriteObject(PointF startPos, Context context, float scaleW, float scaleH, int screenH, int screenW, boolean mole)
    {
        if(mole)
            sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole);
        else
            sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.mask);

        this.myPos = startPos;
        this.con = context;
        rectanglePaint = new Paint();
        rectanglePaint.setColor(Color.RED);
        rectanglePaint.setStyle(Paint.Style.STROKE);

        sprite = Bitmap.createScaledBitmap(sprite, (int)(sprite.getWidth() * scaleW), (int)(sprite.getHeight() * scaleH), true);

        myPos.set((myPos.x / 800) * screenW, (myPos.y / 600) * screenH);

        collsionRect = new RectF(myPos.x, myPos.y, myPos.x + sprite.getWidth(), myPos.y + sprite.getHeight());
        oldY = myPos.y;
        y = myPos.y - (sprite.getHeight() * (float)0.7);
    }

    public RectF GetCollsionRect()
    {
        return collsionRect;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(sprite, myPos.x, myPos.y, null);
        //canvas.drawRect(collsionRect, rectanglePaint);
    }

    public void Move()
    {

        moveing = true;
        myPos.set(myPos.x, y);
        collsionRect = new RectF(myPos.x, myPos.y, myPos.x + sprite.getWidth(), myPos.y + sprite.getHeight());
    }

    public void MoveDown()
    {
        moveing = false;
        myPos.set(myPos.x, oldY);
        collsionRect = new RectF(myPos.x, myPos.y, myPos.x + sprite.getWidth(), myPos.y + sprite.getHeight());
    }
}
