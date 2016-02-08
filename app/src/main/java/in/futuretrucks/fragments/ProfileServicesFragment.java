package in.futuretrucks.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONObject;

import in.futuretrucks.CityListActivity;
import in.futuretrucks.Constant.Constant;
import in.futuretrucks.DrawerHomeActivity;
import in.futuretrucks.R;
import in.futuretrucks.UploadDocumentsActivity;
import in.futuretrucks.VehicleCategoryActivity;
import in.futuretrucks.customviews.CustomButton;
import in.futuretrucks.customviews.CustomEditText;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.utility.PreferenceClass;

public class ProfileServicesFragment extends Fragment implements View.OnClickListener{
    private Context appContext;
    View rootView;
    private DrawerHomeActivity myContext;

    public ProfileServicesFragment(){
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            rootView= inflater.inflate(R.layout.fragment_profile_services, container, false);
            appContext=this.getActivity();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(DrawerHomeActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        initComponent();
    }

    private void initComponent(){

        //Set User Info
        ((CustomTextView)rootView.findViewById(R.id.txtUsernameProfileService)).setText(PreferenceClass.getStringPreferences(appContext,Constant.OWNER_NAME));
        ((CustomTextView)rootView.findViewById(R.id.txtEmailProfileService)).setText(PreferenceClass.getStringPreferences(appContext, Constant.USER_EMAIl));

        if(PreferenceClass.getStringPreferences(appContext, Constant.USER_TYPE).equals(Constant.sl)){
            ((CustomTextView)rootView.findViewById(R.id.txtUserTypeProfileService)).setText(Constant.Shipping_line);
        }else if(PreferenceClass.getStringPreferences(appContext, Constant.USER_TYPE).equals(Constant.truck_owner)){
            ((CustomTextView)rootView.findViewById(R.id.txtUserTypeProfileService)).setText(Constant.Truck_Owner);
        }else if(PreferenceClass.getStringPreferences(appContext, Constant.USER_TYPE).equals(Constant.cha)){
            ((CustomTextView)rootView.findViewById(R.id.txtUserTypeProfileService)).setText(Constant.CHA);
        }else if(PreferenceClass.getStringPreferences(appContext, Constant.USER_TYPE).equals(Constant.transporter)){
            ((CustomTextView)rootView.findViewById(R.id.txtUserTypeProfileService)).setText(Constant.Transporter);
        }else if(PreferenceClass.getStringPreferences(appContext, Constant.USER_TYPE).equals(Constant.customer)){
            ((CustomTextView)rootView.findViewById(R.id.txtUserTypeProfileService)).setText(Constant.Customer);
        }

        ((CustomTextView)rootView.findViewById(R.id.txtInterestingCities)).setOnClickListener(this);
        ((CustomTextView)rootView.findViewById(R.id.txtDailyServiceInCity)).setOnClickListener(this);
        ((CustomTextView)rootView.findViewById(R.id.txtBranchOfficeInCities)).setOnClickListener(this);
        ((CustomTextView)rootView.findViewById(R.id.txtVehicleProvided)).setOnClickListener(this);
        ((CustomTextView)rootView.findViewById(R.id.txtUploadDocuments)).setOnClickListener(this);
        ((CustomButton)rootView.findViewById(R.id.btnCompleteProfileLater)).setOnClickListener(this);

        try {

            JSONObject objDetails = new JSONObject(PreferenceClass.getStringPreferences(appContext, Constant.USER_NON_CUST_VALUES));

            ((CustomTextView)rootView.findViewById(R.id.txtInterestingCitiesCounter)).setText("" + objDetails.getJSONArray("interested_cities").length());
            ((CustomTextView)rootView.findViewById(R.id.txtDailyServiceInCityCounter)).setText("" + objDetails.getJSONArray("daily_serv_cities").length());
            ((CustomTextView)rootView.findViewById(R.id.txtBranchOfficeInCitiesCounter)).setText(""+objDetails.getJSONArray("branch_cities").length());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtDailyServiceInCity:
                PreferenceClass.setIntegerPreference(appContext, Constant.CITY_LIST_REQUEST,Constant.DAILY_SERVICE_IN_CITY);
                startActivity(new Intent(appContext, CityListActivity.class));
                break;
            case R.id.txtInterestingCities:
                PreferenceClass.setIntegerPreference(appContext, Constant.CITY_LIST_REQUEST,Constant.INTERESTED_CITY);
                startActivity(new Intent(appContext, CityListActivity.class));
                break;
            case R.id.txtBranchOfficeInCities:
                PreferenceClass.setIntegerPreference(appContext, Constant.CITY_LIST_REQUEST,Constant.BRANCH_IN_CITY);
                startActivity(new Intent(appContext, CityListActivity.class));
                break;
            case R.id.txtVehicleProvided:
                startActivity(new Intent(appContext, VehicleCategoryActivity.class));
                break;

            case R.id.txtUploadDocuments:
                startActivity(new Intent(appContext, UploadDocumentsActivity.class));
                break;

            case R.id.btnCompleteProfileLater:
                myContext.displayView(1);
                break;

            default:
                break;
        }
    }
}
