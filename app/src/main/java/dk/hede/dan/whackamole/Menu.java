package dk.hede.dan.whackamole;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

public class Menu {

    private Bitmap sprite;

    private RectF collisionRect;
    private PointF myPos;

    private Paint rectanglePaint;

    public Menu(Bitmap sprite, PointF startPos, Context context, float scaleW, float scaleH, int screenH, int screenW) {

        this.sprite = sprite;
        this.myPos = startPos;
        rectanglePaint = new Paint();
        rectanglePaint.setColor(Color.RED);
        rectanglePaint.setStyle(Paint.Style.STROKE);

        if(this.sprite.toString() == "gameover")
            this.sprite = Bitmap.createScaledBitmap(this.sprite, (int) (this.sprite.getWidth() * scaleW), (int) (this.sprite.getHeight() * scaleH), true);

        myPos.set((myPos.x / 800) * screenW, (myPos.y / 600) * screenH);

        collisionRect = new RectF(myPos.x, myPos.y, myPos.x + this.sprite.getWidth(), myPos.y + this.sprite.getHeight());

    }

    public RectF GetCollsionRect() {
        return collisionRect;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(sprite, myPos.x, myPos.y, null);
        //canvas.drawRect(collisionRect, rectanglePaint);
    }
}
