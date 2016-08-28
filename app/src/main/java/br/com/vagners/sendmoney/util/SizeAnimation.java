package br.com.vagners.sendmoney.util;


import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by vagners on 7/11/15.
 */
public class SizeAnimation extends Animation {

    private int startHeight;
    private float deltaHeight;

    private int startWidth;
    private float deltaWidth;

    private View view;

    public SizeAnimation(View view) {
        this.view = view;
    }

    public void setHeights(int start, int end) {
        this.startHeight = start;
        this.deltaHeight = end - this.startHeight;
        this.deltaHeight =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, deltaHeight, view.getResources().getDisplayMetrics());
    }

    public void setWidths(int start, int end) {


        this.startWidth = start;
        this.deltaWidth = end - this.startWidth;
        this.deltaWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, deltaWidth, view.getResources().getDisplayMetrics());
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (startHeight != 0) {
            if (deltaHeight > 0) {
                view.getLayoutParams().height = (int) (startHeight + deltaHeight * interpolatedTime);
            } else {
                view.getLayoutParams().height = (int) (startHeight - Math.abs(deltaHeight) * interpolatedTime);
            }
        }

        if (startWidth != 0) {
            if (deltaWidth > 0) {
                view.getLayoutParams().width = (int) (startWidth + deltaWidth * interpolatedTime);
            } else {
                view.getLayoutParams().width = (int) (startWidth - Math.abs(deltaWidth) * interpolatedTime);
            }
        }

        view.requestLayout();
    }
}
