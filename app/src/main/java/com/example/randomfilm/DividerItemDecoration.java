package com.example.randomfilm;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Paint dividerPaint;

    public DividerItemDecoration(Context context) {
        dividerPaint = new Paint();
        dividerPaint.setColor(ContextCompat.getColor(context, R.color.white));
        dividerPaint.setStrokeWidth(0.8f);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = 0;
        int right = parent.getWidth();
        for (int i = 0; i < parent.getChildCount() - 1; i++) {
            RecyclerView.ViewHolder child = parent.getChildViewHolder(parent.getChildAt(i));
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.itemView.getLayoutParams();
            float top = child.itemView.getBottom() + params.bottomMargin;
            float bottom = top + dividerPaint.getStrokeWidth();
            c.drawLine(left, top, right, bottom, dividerPaint);
        }
    }
}
