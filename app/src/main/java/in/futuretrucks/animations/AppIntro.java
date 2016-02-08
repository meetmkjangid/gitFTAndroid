package in.futuretrucks.animations;

import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.warnyul.android.widget.FastVideoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import in.futuretrucks.R;

public abstract class AppIntro extends AppCompatActivity {
    public final static int DEFAULT_COLOR = 1;
    private static final int DEFAULT_SCROLL_DURATION_FACTOR = 1;

    protected PagerAdapter mPagerAdapter;
    protected AppIntroViewPager pager;
    protected List<Fragment> fragments = new Vector<>();
    protected List<ImageView> dots;
    protected int slidesNumber;
    protected Vibrator mVibrator;
    protected IndicatorController mController;
    protected boolean isVibrateOn = false;
    protected int vibrateIntensity = 20;
    protected boolean skipButtonEnabled = true;
    protected boolean baseProgressButtonEnabled = true;
    protected boolean progressButtonEnabled = true;
    protected int selectedIndicatorColor = DEFAULT_COLOR;
    protected int unselectedIndicatorColor = DEFAULT_COLOR;
    protected View txtSignIn;
    protected View txtJoin;
    protected int savedCurrentItem;
    protected ArrayList<PermissionObject> permissionsArray = new ArrayList<>();
    private static final int PERMISSIONS_REQUEST_ALL_PERMISSIONS = 1;

    private MediaController mediaControls;
    FastVideoView videoView;

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.intro_layout);

        txtSignIn = findViewById(R.id.txtLogin);
        txtJoin = findViewById(R.id.txtJoin);
        //doneButton = findViewById(R.id.done);
        mVibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        pager = (AppIntroViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(this.mPagerAdapter);

        if (savedInstanceState != null) {
            restoreLockingState(savedInstanceState);
        }

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                if (isVibrateOn) {
                    mVibrator.vibrate(vibrateIntensity);
                }
                onSignInPressed();
            }
        });

        txtJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                if (isVibrateOn) {
                    mVibrator.vibrate(vibrateIntensity);
                }
                onJoinPressed();
            }
        });

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        pager = (AppIntroViewPager) findViewById(R.id.view_pager);

        pager.setAdapter(this.mPagerAdapter);


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (slidesNumber > 1)
                    mController.selectPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setCurrentItem(savedCurrentItem); //required for triggering onPageSelected for first page

        setScrollDurationFactor(DEFAULT_SCROLL_DURATION_FACTOR);

        init(savedInstanceState);
        slidesNumber = fragments.size();

        if (slidesNumber == 1) {
            //setProgressButtonEnabled(progressButtonEnabled);
        } else {
            initController();
        }
        getWindow().setFormat(PixelFormat.UNKNOWN);
        startWelcomeVideo();
    }

    public void startWelcomeVideo(){
        try {
            videoView = (FastVideoView) findViewById(R.id.video);
            videoView.setMediaController(new MediaController(this));
            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.welcome));
            videoView.setMediaController(null);
            videoView.start();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });
        }catch (Exception e){
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startWelcomeVideo();
    }

    protected void setScrollDurationFactor(int factor) {
        pager.setScrollDurationFactor(factor);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("baseProgressButtonEnabled", baseProgressButtonEnabled);
        outState.putBoolean("progressButtonEnabled", progressButtonEnabled);
        outState.putBoolean("skipButtonEnabled", skipButtonEnabled);
        outState.putBoolean("nextEnabled", pager.isPagingEnabled());
        outState.putBoolean("nextPagingEnabled", pager.isNextPagingEnabled());
        outState.putInt("lockPage", pager.getLockPage());
        outState.putInt("currentItem", pager.getCurrentItem());
    }


    protected void restoreLockingState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.baseProgressButtonEnabled = savedInstanceState.getBoolean("baseProgressButtonEnabled");
        this.progressButtonEnabled = savedInstanceState.getBoolean("progressButtonEnabled");
        this.skipButtonEnabled = savedInstanceState.getBoolean("skipButtonEnabled");
        this.savedCurrentItem = savedInstanceState.getInt("currentItem");
        pager.setPagingEnabled(savedInstanceState.getBoolean("nextEnabled"));
        pager.setNextPagingEnabled(savedInstanceState.getBoolean("nextPagingEnabled"));
        pager.setLockPage(savedInstanceState.getInt("lockPage"));
    }

    public AppIntroViewPager getPager() {
        return pager;
    }

    private void initController() {
        if (mController == null)
            mController = new DefaultIndicatorController();

        FrameLayout indicatorContainer = (FrameLayout) findViewById(R.id.indicator_container);
        indicatorContainer.addView(mController.newInstance(this));

        mController.initialize(slidesNumber);
        if (selectedIndicatorColor != DEFAULT_COLOR)
            mController.setSelectedIndicatorColor(selectedIndicatorColor);
        if (unselectedIndicatorColor != DEFAULT_COLOR)
            mController.setUnselectedIndicatorColor(unselectedIndicatorColor);
    }

    public void addSlide(@NonNull Fragment fragment) {
        fragments.add(fragment);
        mPagerAdapter.notifyDataSetChanged();
    }

    public abstract void init(@Nullable Bundle savedInstanceState);

    public abstract void onSignInPressed();
    public abstract void onJoinPressed();

    //public abstract void onNextPressed();



   // public abstract void onSlideChanged();

    @Override
    public boolean onKeyDown(int code, KeyEvent kvent) {
        if (code == KeyEvent.KEYCODE_ENTER || code == KeyEvent.KEYCODE_BUTTON_A || code == KeyEvent.KEYCODE_DPAD_CENTER) {
            ViewPager vp = (ViewPager) this.findViewById(R.id.view_pager);
            if (vp.getCurrentItem() == vp.getAdapter().getCount() - 1) {
            } else {
                vp.setCurrentItem(vp.getCurrentItem() + 1);
            }
            return false;
        }
        return super.onKeyDown(code, kvent);
    }


    public void setIndicatorColor(int selectedIndicatorColor, int unselectedIndicatorColor) {
        this.selectedIndicatorColor = selectedIndicatorColor;
        this.unselectedIndicatorColor = unselectedIndicatorColor;

        if (mController != null) {
            if (selectedIndicatorColor != DEFAULT_COLOR)
                mController.setSelectedIndicatorColor(selectedIndicatorColor);
            if (unselectedIndicatorColor != DEFAULT_COLOR)
                mController.setUnselectedIndicatorColor(unselectedIndicatorColor);
        }
    }


    public void setSwipeLock(boolean lockEnable) {
        if (lockEnable) {
            // if locking, save current progress button visibility
            baseProgressButtonEnabled = progressButtonEnabled;
            //setProgressButtonEnabled(!lockEnable);
        } else {
            // if unlocking, restore original button visibility
            //setProgressButtonEnabled(baseProgressButtonEnabled);
        }
        pager.setPagingEnabled(!lockEnable);
    }

    private static String TAG = "AppIntro1";



    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ALL_PERMISSIONS:
                pager.setCurrentItem(pager.getCurrentItem() + 1);
                break;
            default:
                Log.e(TAG, "Unexpected request code");
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(videoView!=null){
            videoView.stopPlayback();
        }
    }
}
