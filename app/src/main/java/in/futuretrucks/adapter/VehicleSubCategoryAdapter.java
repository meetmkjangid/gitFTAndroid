package in.futuretrucks.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.json.JSONObject;

import java.util.ArrayList;

import in.futuretrucks.R;
import in.futuretrucks.customviews.CustomTextView;
import in.futuretrucks.model.SubCatSelectionModel;
import in.futuretrucks.utility.AnimationUtils;

public class VehicleSubCategoryAdapter extends RecyclerView.Adapter<VehicleSubCategoryAdapter.ViewHolder> {
    private ArrayList<SubCatSelectionModel> mDataset=new ArrayList<SubCatSelectionModel>();
    private Context appContext;
    private int requestFor;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CustomTextView txtTruckType;
        CheckBox chkCatSelected;

        public ViewHolder(View v) {
            super(v);
            switch (requestFor) {
                case 1:
                    txtTruckType=(CustomTextView)v.findViewById(R.id.txtTruckSubCategoryName);
                    chkCatSelected=(CheckBox)v.findViewById(R.id.chkSelectVehicle);
                    break;
            }
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public VehicleSubCategoryAdapter(Context context, int resource, ArrayList<SubCatSelectionModel> myDataset) {
        mDataset = myDataset;
        appContext=context;
        requestFor=resource;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public VehicleSubCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v=null;
        switch (requestFor) {
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_truck_sub_cat_item, parent, false);
                break;
        }
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        switch (requestFor) {
            case 1:

                //AnimationUtils.scaleXY(holder);

                holder.chkCatSelected.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        SubCatSelectionModel install_app = (SubCatSelectionModel) cb.getTag();
                        install_app.setSelected(cb.isChecked());
                    }
                });

                holder.txtTruckType.setText(mDataset.get(position).getTruckType());
                holder.chkCatSelected.setChecked(mDataset.get(position).isSelected());
                holder.chkCatSelected.setTag(mDataset.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}