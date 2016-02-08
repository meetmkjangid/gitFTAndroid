package in.futuretrucks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import in.futuretrucks.customviews.CircleProgress;
import in.futuretrucks.customviews.CustomTextView;


public class TruckRecyclerViewHolder extends RecyclerView.ViewHolder{

    public CustomTextView txtTruckOwnerName;
    public CustomTextView txtTruckRegNo;
    public ImageView imgvwTruck;
    public CircleProgress progressTruckProfile;
    public RelativeLayout layoutInfo;

    public TruckRecyclerViewHolder(View itemView) {
        super(itemView);
        //implementing onClickListener
        txtTruckOwnerName = (CustomTextView) itemView.findViewById(R.id.txtTruckOwnerName);
        txtTruckRegNo = (CustomTextView) itemView.findViewById(R.id.txtTruckRegistrationNumber);
        imgvwTruck = (ImageView) itemView.findViewById(R.id.imgvwTruckPic);
        progressTruckProfile = (CircleProgress) itemView.findViewById(R.id.progressTruckProfile);
        layoutInfo=(RelativeLayout)itemView.findViewById(R.id.layoutInfo);
    }
}