package in.futuretrucks.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.futuretrucks.DrawerHomeActivity;
import in.futuretrucks.R;
import in.futuretrucks.adapter.TabsPagerAdapter;
import in.futuretrucks.customviews.SlidingTabLayout;
import in.futuretrucks.utility.PreferenceClass;

public class HomeFragment extends Fragment {
    private Context appContext;
    private ViewPager viewPager;
    SlidingTabLayout tabs;
    View rootView;
    CharSequence Tabs_Titles[] = {"LOAD BOARD", "TRUCK BOARD", "OFFERS"};
    private TabsPagerAdapter TAdapter;
    int no_of_tabs = 3;
    private DrawerHomeActivity myContext;

    public HomeFragment(){
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            rootView= inflater.inflate(R.layout.fragment_home, container, false);
            appContext=this.getActivity();
            initComponent();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(DrawerHomeActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initComponent(){
        viewPager = (ViewPager)rootView.findViewById(R.id.pager);
        TAdapter = new TabsPagerAdapter(myContext.getSupportFragmentManager(), Tabs_Titles, no_of_tabs);
        viewPager.setAdapter(TAdapter);
        tabs = (SlidingTabLayout)rootView.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        viewPager.setCurrentItem(PreferenceClass.getIntegerPreferences(appContext,"TAB_REQ"));
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.color_header);
            }
        });
        tabs.setViewPager(viewPager);
        TAdapter.notifyDataSetChanged();
    }
}
