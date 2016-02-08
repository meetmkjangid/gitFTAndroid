package in.futuretrucks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Toast;

import in.futuretrucks.animations.AppIntro;
import in.futuretrucks.pager.SampleSlide;

/**
 * Created by mahadev on 12/1/15.
 */
public class AppIntroSlideActivity extends AppIntro {
    private Context appContext;
    @Override
    public void init(Bundle savedInstanceState) {
        appContext =this;

        addSlide(SampleSlide.newInstance(R.layout.intro_view1));
        addSlide(SampleSlide.newInstance(R.layout.intro_view2));
        addSlide(SampleSlide.newInstance(R.layout.intro_view3));
        addSlide(SampleSlide.newInstance(R.layout.intro_view4));
        addSlide(SampleSlide.newInstance(R.layout.intro_view5));
        addSlide(SampleSlide.newInstance(R.layout.intro_view6));
        addSlide(SampleSlide.newInstance(R.layout.intro_view7));

        //setFadeAnimation();
    }

    @Override
    public void onSignInPressed() {
        startActivity(new Intent(appContext,LoginActivity.class));
    }

    @Override
    public void onJoinPressed() {
        startActivity(new Intent(appContext, UserSelectionActivity.class));
    }
}