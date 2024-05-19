package com.example.randomfilm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TutorialAdapter extends BaseAdapter {
    Context context;
    List<TutorialObject> tutorialObjects;

    public TutorialAdapter (Context context, List<TutorialObject> tutorialObjects) {
        this.context = context;
        this.tutorialObjects = tutorialObjects;
    }

    @Override
    public int getCount() {
        return tutorialObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return tutorialObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_tutorial, parent, false);
        }
        TutorialObject tutorialObject = (TutorialObject) getItem(position);

        TextView textView = convertView.findViewById(R.id.tutorialText);
        ImageView imageView = convertView.findViewById(R.id.IVtutorial);

        imageView.setImageDrawable(tutorialObject.getDrawable());
        textView.setText(tutorialObject.getText());

        return convertView;
    }
}
