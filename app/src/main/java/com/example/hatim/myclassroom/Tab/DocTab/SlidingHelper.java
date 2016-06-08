package com.example.hatim.myclassroom.Tab.DocTab;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.hatim.myclassroom.R;

/**
 * Created by Hatim on 06/06/2016.
 */
public class SlidingHelper {

    View viewSlide;

    public SlidingHelper(View hiddenPanel){
        viewSlide = hiddenPanel;
    }


    public void slideUpDown(final View view) {
        if (!isPanelShown()) {
            // Show the panel
            Animation bottomUp = AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.slide_up);

            view.startAnimation(bottomUp);
            view.setVisibility(View.VISIBLE);
        }
        else {
            // Hide the Panel
            Animation bottomDown = AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.slide_down);

            view.startAnimation(bottomDown);
            view.setVisibility(View.GONE);
        }
    }


    public boolean isPanelShown() {

        return viewSlide.getVisibility() == View.VISIBLE;
    }

}
