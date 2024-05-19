package com.example.randomfilm;

import android.graphics.drawable.Drawable;

public class TutorialObject {
    String text;
    Drawable drawable;

    public TutorialObject (String text, Drawable drawable) {
        this.text = text;
        this.drawable = drawable;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public String getText() {
        return text;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setText(String text) {
        this.text = text;
    }
}
