package dk.hede.dan.whackamole;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.SystemClock;

public class SpriteObject {
    private Bitmap sprite;

    private RectF collisionRect;
    private PointF myPos;
    private float showY;
    private float hideY;
    private Paint rectanglePaint;
    private long showTime;

    public boolean showing = false;

    public SpriteObject(PointF startPos, Context context, float scaleW, float scaleH, int screenH, int screenW, boolean mole) {
        if (mole)
            sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole);
        else
            sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.mask);

        this.myPos = startPos;

        rectanglePaint = new Paint();
        rectanglePaint.setColor(Color.RED);
        rectanglePaint.setStyle(Paint.Style.STROKE);

        sprite = Bitmap.createScaledBitmap(sprite, (int) (sprite.getWidth() * scaleW), (int) (sprite.getHeight() * scaleH), true);

        myPos.set((myPos.x / 800) * screenW, (myPos.y / 600) * screenH);

        collisionRect = new RectF(myPos.x, myPos.y, myPos.x + sprite.getWidth(), myPos.y + sprite.getHeight());

        hideY = myPos.y;
        showY = myPos.y - (sprite.getHeight() * (float) 0.7);
    }

    public RectF GetCollsionRect() {
        return collisionRect;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(sprite, myPos.x, myPos.y, null);
        //canvas.drawRect(collisionRect, rectanglePaint);
    }

    public void update() {
        if (showing) {
            if (showTime + GameManager.getInstance().GetDifficulty() - 500 <= SystemClock.elapsedRealtime()) {
                Hide();
            }
        }
    }

    public void Show() {
        showing = true;
        myPos.set(myPos.x, showY);
        collisionRect = new RectF(myPos.x, myPos.y, myPos.x + sprite.getWidth(), myPos.y + sprite.getHeight());
        showTime = SystemClock.elapsedRealtime();
    }

    public void Hide() {
        showing = false;
        myPos.set(myPos.x, hideY);
        collisionRect = new RectF(myPos.x, myPos.y, myPos.x + sprite.getWidth(), myPos.y + sprite.getHeight());
    }
}
