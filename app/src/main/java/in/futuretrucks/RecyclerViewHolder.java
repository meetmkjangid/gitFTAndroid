package in.futuretrucks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import in.futuretrucks.customviews.CircleProgress;
import in.futuretrucks.customviews.CustomTextView;


public class RecyclerViewHolder extends RecyclerView.ViewHolder{

    public CustomTextView txtDriverName;
    public CustomTextView txtDriverDLNo;
    public ImageView imgvwDriverProfilePic;
    public CircleProgress progressDriverProfile;
    public RelativeLayout layoutInfo;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        //implementing onClickListener
        txtDriverName = (CustomTextView) itemView.findViewById(R.id.txtDriverName);
        txtDriverDLNo = (CustomTextView) itemView.findViewById(R.id.txtDriverDLNo);
        imgvwDriverProfilePic = (ImageView) itemView.findViewById(R.id.imgvwDriverProfilePic);
        progressDriverProfile = (CircleProgress) itemView.findViewById(R.id.progressDriverProfile);
        layoutInfo=(RelativeLayout)itemView.findViewById(R.id.layoutInfo);
    }

}