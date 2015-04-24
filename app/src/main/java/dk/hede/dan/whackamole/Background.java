package dk.hede.dan.whackamole;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

public class Background
{
    Bitmap myImage;
    Map<String, Bitmap> allImages;

    float originalWidth;
    float originalHeight;

    public String name;

    public void setImage(String name)
    {
        myImage = allImages.get(name);
        this.name = name;
    }

    public Bitmap getImage()
    {
        return myImage;
    }

    public float getOriginalWidth()
    {
        return originalWidth;
    }

    public float getOriginalHeight()
    {
        return originalHeight;
    }

    public Background(Context context, int screenW, int screenH)
    {
        allImages = new HashMap<String, Bitmap>();

        Bitmap menu;
        Bitmap inGame;

        menu = BitmapFactory.decodeResource(context.getResources(), R.drawable.title);


        originalWidth = menu.getWidth();
        originalHeight = menu.getHeight();

        menu = Bitmap.createScaledBitmap(menu, screenW, screenH, true);

        inGame = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        inGame = Bitmap.createScaledBitmap(inGame, screenW, screenH, true);

        allImages.put("menu", menu);
        allImages.put("inGame", inGame);

        setImage("menu");
    }



}
