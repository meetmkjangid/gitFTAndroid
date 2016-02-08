package in.futuretrucks;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.adapter.NavDrawerListAdapter;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.fragments.DriverFragment;
import in.futuretrucks.fragments.HomeFragment;
import in.futuretrucks.fragments.SettingFragment;
import in.futuretrucks.fragments.ProfileServicesFragment;
import in.futuretrucks.fragments.TruckFragment;
import in.futuretrucks.model.NavDrawerItem;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;

public class DrawerHomeActivity extends AppCompatActivity {
    private Context appContext;
    ProgressHUD progress;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    ActionBar actionBar;
    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;
    Toolbar mToolbar;
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).resetViewBeforeLoading(true).displayer(new RoundedBitmapDisplayer(1000)).showImageForEmptyUri(R.drawable.default_pic).showImageOnFail(R.drawable.default_pic).showImageOnLoading(R.drawable.default_pic).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appContext=this;
        imageLoader.init(ImageLoaderConfiguration.createDefault(appContext));

        initDrawer(savedInstanceState);

    }


    private void initDrawer(Bundle savedInstanceState){
        PreferenceClass.setBooleanPreference(appContext, Constant.isLogin, true);
        //mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.header_drawer_profile, mDrawerList, false);
        mDrawerList.addHeaderView(header, null, false);

        ((CustomTextView)header.findViewById(R.id.txtUsernameDrawer)).setText(PreferenceClass.getStringPreferences(appContext, Constant.OWNER_NAME));
        ((CustomTextView)header.findViewById(R.id.txtUserMobileDrawer)).setText(PreferenceClass.getStringPreferences(appContext, Constant.USER_MOBILE));
        String url=PreferenceClass.getStringPreferences(appContext,Constant.USER_PROFILE_PIC).replace(" ","%20");

        System.out.println("Profile Pic URl is: " + url);

        if(url.length()>10){
            url=Constant.BASE_URL_IMAGE+url;
            imageLoader.displayImage(url.replace(" ","%20"),((ImageView)header.findViewById(R.id.imgvwProfileDrawer)) , options);
        }else{
            String imageUri = "drawable://" + R.drawable.default_pic;
            imageLoader.displayImage(imageUri,((ImageView)header.findViewById(R.id.imgvwProfileDrawer)) , options);
        }

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -2)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -3)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -4)));

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        mToolbar = (Toolbar) findViewById(R.id.layoutHeader);
        setSupportActionBar(mToolbar);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                //((CustomTextView)findViewById(R.id.txtHeading)).setText(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                //((CustomTextView)findViewById(R.id.txtHeading)).setText(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(1);
        }
    }

    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    public void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 1:
                fragment = new HomeFragment();
                break;
            case 2:
                fragment = new ProfileServicesFragment();
                break;
            case 3:
                fragment = new DriverFragment();
                break;
            case 4:
                fragment = new TruckFragment();
                break;
            case 5:
                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                setTitle(navMenuTitles[position-1]);
                mDrawerLayout.closeDrawer(mDrawerList);
                loginLogout();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        //((CustomTextView)findViewById(R.id.txtHeading)).setText(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



    public void restoreActionBar() {

        actionBar=getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        ((CustomTextView)findViewById(R.id.txtHeading)).setText(getString(R.string.app_name));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        restoreActionBar();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            /*case R.id.action_settings:
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    public void loginLogout() {

        progress = ProgressHUD.show(appContext, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
            }
        });

        final AQuery aq = new AQuery(appContext);
        String url = Constant.BASE_URL+Constant.LOGOUT;
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", PreferenceClass.getStringPreferences(appContext,Constant.USER_TOKEN));

        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jObj, AjaxStatus status) {
                try {
                    if (progress.isShowing() && progress != null) {
                        progress.dismiss();
                    }
                    if (jObj != null) {
                        String status_response = jObj.getString("status");
                        if (status_response.equals("OK")) {
                            PreferenceClass.clearPreference(appContext);
                            finish();
                        } else {
                            String error_message = jObj.getString("error_message");
                            Toast.makeText(appContext, error_message, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(appContext, R.string.check_network, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.headers(header).method(AQuery.METHOD_POST));
    }
}