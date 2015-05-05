package dk.hede.dan.whackamole;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

public class Background {
    public String name;
    Bitmap myImage;
    Map<String, Bitmap> allImages;
    float originalWidth;
    float originalHeight;

    public Background(Context context, int screenW, int screenH) {
        allImages = new HashMap<String, Bitmap>();

        Bitmap menu;
        Bitmap inGame;
        Bitmap diffi;

        menu = BitmapFactory.decodeResource(context.getResources(), R.drawable.title);
        diffi = BitmapFactory.decodeResource(context.getResources(), R.drawable.diffi);

        originalWidth = menu.getWidth();
        originalHeight = menu.getHeight();

        menu = Bitmap.createScaledBitmap(menu, screenW, screenH, true);
        diffi = Bitmap.createScaledBitmap(diffi, screenW, screenH, true);

        inGame = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        inGame = Bitmap.createScaledBitmap(inGame, screenW, screenH, true);

        allImages.put("menu", menu);
        allImages.put("inGame", inGame);
        allImages.put("diffi", diffi);

        setImage("menu");
    }

    public Bitmap getImage() {
        return myImage;
    }

    public void setImage(String name) {
        myImage = allImages.get(name);
        this.name = name;
    }

    public float getOriginalWidth() {
        return originalWidth;
    }

    public float getOriginalHeight() {
        return originalHeight;
    }


}
