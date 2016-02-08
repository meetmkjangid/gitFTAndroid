package in.futuretrucks.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.ArrayList;

import in.futuretrucks.R;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.utility.AnimationUtils;

public class VehicleSubCategoryJSONAdapter extends RecyclerView.Adapter<VehicleSubCategoryJSONAdapter.ViewHolder> {
    private ArrayList<JSONObject> mDataset=new ArrayList<JSONObject>();
    private Context appContext;
    private View.OnClickListener clickListener;
    private int requestFor;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CustomTextView txtTruckCategoryName,txtTruckCategoryCounter;

        public ViewHolder(View v) {
            super(v);
            switch (requestFor) {
                case 1:
                    txtTruckCategoryName=(CustomTextView)v.findViewById(R.id.txtTruckCategoryName);
                    txtTruckCategoryCounter=(CustomTextView)v.findViewById(R.id.txtTruckCategoryCounter);
                break;
            }
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public VehicleSubCategoryJSONAdapter(Context context, int resource, ArrayList<JSONObject> myDataset, View.OnClickListener clickListener) {
        mDataset = myDataset;
        appContext=context;
        requestFor=resource;
        this.clickListener=clickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public VehicleSubCategoryJSONAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v=null;
        switch (requestFor) {
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_truck_cat_item, parent, false);
                break;
        }
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        switch (requestFor) {
            case 1:
                AnimationUtils.scaleXY(holder);
                try {
                   holder.txtTruckCategoryName.setText(mDataset.get(position).getString("truck_type"));
                   holder.txtTruckCategoryName.setTag(String.valueOf(position));
                   holder.txtTruckCategoryName.setOnClickListener(clickListener);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}