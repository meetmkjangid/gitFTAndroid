package in.futuretrucks.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.futuretrucks.R;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.utility.AnimationUtils;

public class VehicleCategoryAdapter extends RecyclerView.Adapter<VehicleCategoryAdapter.ViewHolder> {
    private ArrayList<String> mDataset=new ArrayList<String>();
    private Context appContext;
    private View.OnClickListener clickListener;
    private int requestFor;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CustomTextView txtTruckCategoryName,txtTruckCategoryCounter,txtWeight;

        public ViewHolder(View v) {
            super(v);
            switch (requestFor) {
                case 1:
                    txtTruckCategoryName=(CustomTextView)v.findViewById(R.id.txtTruckCategoryName);
                    txtTruckCategoryCounter=(CustomTextView)v.findViewById(R.id.txtTruckCategoryCounter);
                break;
                case 2:
                    txtTruckCategoryName=(CustomTextView)v.findViewById(R.id.txtTruckCategoryName);
                    txtTruckCategoryCounter=(CustomTextView)v.findViewById(R.id.txtTruckCategoryCounter);
                    break;
                case 3:
                    txtWeight=(CustomTextView)v.findViewById(R.id.txtWeight);
                    break;
                case 4:
                    txtWeight=(CustomTextView)v.findViewById(R.id.txtWeight);
                    break;
            }
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public VehicleCategoryAdapter(Context context, int resource, ArrayList<String> myDataset, View.OnClickListener clickListener) {
        mDataset = myDataset;
        appContext=context;
        requestFor=resource;
        this.clickListener=clickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public VehicleCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v=null;
        switch (requestFor) {
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_truck_cat_item, parent, false);
                break;
            case 2:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_truck_cat_item, parent, false);
                break;

            case 3:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_weight_item, parent, false);
                break;
            case 4:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_weight_item, parent, false);
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
                   String[] truck_info=mDataset.get(position).split("::");
                   holder.txtTruckCategoryName.setText(truck_info[0]);
                   holder.txtTruckCategoryCounter.setText(truck_info[1]+"/"+truck_info[2]);
                   holder.txtTruckCategoryName.setTag(String.valueOf(position));
                   holder.txtTruckCategoryName.setOnClickListener(clickListener);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case 2:
                AnimationUtils.scaleXY(holder);
                try {
                    holder.txtTruckCategoryName.setText(mDataset.get(position));
                    holder.txtTruckCategoryName.setTag(String.valueOf(position));
                    holder.txtTruckCategoryName.setOnClickListener(clickListener);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case 3:
                AnimationUtils.scaleXY(holder);
                try {
                    holder.txtWeight.setText(mDataset.get(position));
                    holder.txtWeight.setTag(String.valueOf(position));
                    holder.txtWeight.setOnClickListener(clickListener);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 4:
                AnimationUtils.scaleXY(holder);
                try {
                    holder.txtWeight.setText(mDataset.get(position));
                    holder.txtWeight.setTag(String.valueOf(position));
                    holder.txtWeight.setOnClickListener(clickListener);
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