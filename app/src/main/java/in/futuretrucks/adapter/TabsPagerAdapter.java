package in.futuretrucks.adapter;

/**
 * Created by win on 7/17/2015.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.futuretrucks.fragments.LoadBoardFragment;
import in.futuretrucks.fragments.OfferFragment;
import in.futuretrucks.fragments.TruckBoardFragment;
import in.futuretrucks.fragments.DocumentsFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    public TabsPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb)  {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }
   @Override
    public Fragment getItem(int position) {
       Fragment fragment=null;
       if(position == 0){
           System.out.println("Tab Position is: "+position);
            fragment=new LoadBoardFragment();
        }else if(position== 1){
           System.out.println("Tab Position is: "+position);
            fragment=new TruckBoardFragment();
        }else if(position==2){
           System.out.println("Tab Position is: "+position);
            fragment=new OfferFragment();
        }
      return  fragment;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}