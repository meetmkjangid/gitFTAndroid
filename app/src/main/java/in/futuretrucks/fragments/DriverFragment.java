package in.futuretrucks.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.DriverRegisterActivity;
import in.futuretrucks.R;
import in.futuretrucks.ServiceGenerator;
import in.futuretrucks.adapter.DriverRecyclerViewAdapter;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.model.Driver;
import in.futuretrucks.services.DriverApiService;
import in.futuretrucks.utility.ConnectionDetector;
import in.futuretrucks.utility.PreferenceClass;
import in.futuretrucks.utility.ProgressHUD;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by futuretrucks on 16/12/15.
 */

public class DriverFragment extends Fragment {
    private static final String TAG = "RecyclerViewExample";
    private View rootView;
    private LinearLayoutManager layoutManager;

    private Context appContext;
    private List<Driver> driverlist;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ConnectionDetector cd;
    private ProgressHUD progress;
    private DriverRecyclerViewAdapter adapter;

    public DriverFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_driver, container, false);
        appContext = this.getActivity();

        initComponent();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(PreferenceClass.getBooleanPreferences(appContext, "Driver_List_Update")){
            PreferenceClass.setBooleanPreference(appContext, "Driver_List_Update",false);
            initComponent();
        }
    }

    private void initComponent(){
        cd=new ConnectionDetector(appContext);
        layoutManager = new LinearLayoutManager(appContext);
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerLoadMore);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.layoutRefresh);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);

        driverlist = new ArrayList<Driver>();

        ((CustomTextView) rootView.findViewById(R.id.txtRegisterDriver)).setOnClickListener(clickListener);

        if(cd.isConnectingToInternet()){
            getDriverListFromServer(false);
        }else{
            Toast.makeText(appContext,R.string.check_network,Toast.LENGTH_LONG).show();
        }


        //Pull to Refresh
        swipeRefreshLayout.setColorSchemeColors(Color.GRAY, Color.LTGRAY, Color.DKGRAY, Color.GRAY);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                driverlist.clear();
                if (cd.isConnectingToInternet()) {
                    getDriverListFromServer(true);
                } else {
                    Toast.makeText(appContext, R.string.check_network, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void getDriverListFromServer(final boolean isRefresh){
        String jwt = PreferenceClass.getStringPreferences(appContext, Constant.USER_TOKEN);
        DriverApiService service =ServiceGenerator.createService(DriverApiService.class);

        if(!isRefresh){
            progress = ProgressHUD.show(appContext, "Please wait...", true, false, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                }
            });
        }

        Call<List<Driver>> call = service.getDriversList(jwt);
        call.enqueue(new Callback<List<Driver>>() {
            @Override
            public void onResponse(Response<List<Driver>> response, Retrofit retrofit) {
                driverlist = response.body();
                System.out.println("driverlist Size: "+driverlist.size());
                if(driverlist.size()==0){
                    ((CustomTextView)rootView.findViewById(R.id.txtNoData)).setVisibility(View.VISIBLE);
                    ((CustomTextView)rootView.findViewById(R.id.txtNoData)).setText(R.string.Driver_Not_Added);
                }else{
                    ((CustomTextView)rootView.findViewById(R.id.txtNoData)).setVisibility(View.GONE);
                    adapter = new DriverRecyclerViewAdapter(appContext, driverlist,clickListener);
                    mRecyclerView.setAdapter(adapter);
                }
                if(isRefresh){
                    swipeRefreshLayout.setRefreshing(false);
                }else{
                    if(progress.isShowing() && progress!=null){
                        progress.dismiss();
                    }
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e("Upload failure", t.getMessage());
                if(isRefresh){
                    swipeRefreshLayout.setRefreshing(false);
                }else{
                    if(progress.isShowing() && progress!=null){
                        progress.dismiss();
                    }
                }
            }
        });
    }

    View.OnClickListener clickListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.txtRegisterDriver) {
                PreferenceClass.setBooleanPreference(appContext, "Driver_List_Update",false);
                startActivity(new Intent(appContext, DriverRegisterActivity.class));
            }else if(v.getId()==R.id.txtDriverName || v.getId()==R.id.txtDriverDLNo || v.getId()==R.id.imgvwDriverProfilePic || v.getId()==R.id.progressDriverProfile || v.getId()==R.id.layoutInfo){
                int position=Integer.parseInt(v.getTag().toString());
                System.out.println("Selecetd Driver Object" + driverlist.get(position));
                Toast.makeText(appContext, "In Processs...", Toast.LENGTH_LONG).show();
            }
        }
    };
}
