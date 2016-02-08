package in.futuretrucks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import in.futuretrucks.customviews.CustomAutoCompleteTextView;
import in.futuretrucks.customviews.CustomTextView;

/**
 * Created by futuretrucks on 28/12/15.
 */
public class TruckBoardRecyclerViewHolder extends RecyclerView.ViewHolder{

    public CustomTextView txtENDDate;
    public CustomTextView txtmaterialType;

    public CustomTextView txttruckType;
    public ImageView imgvwloadboard;
    public CustomAutoCompleteTextView source;
    public CustomAutoCompleteTextView destination;
    public CustomTextView txtsourceCity;
    public CustomTextView txtdestinationCity;
    public CustomTextView txtExpPriceLoadBoard;
    public CustomTextView txtWeight;
    public CustomTextView txtCall;
    public CustomTextView txtSMS;
    public CustomTextView txtVerifiedFT;
    public CustomTextView txtBlocked;
    public CustomTextView txtCatapitir;
    public CustomTextView txtVehicleType;
    public CustomTextView txtENDTimeLoadBoard;
    public CustomTextView txtPostBy;
    public CustomTextView txtID;

    public TruckBoardRecyclerViewHolder(View itemView) {
        super(itemView);
        //implementing onClickListener

        txtExpPriceLoadBoard = (CustomTextView) itemView.findViewById(R.id.txtExpPriceLoadBoard);
        txtENDDate = (CustomTextView) itemView.findViewById(R.id.txtENDTimeLoadBoard);
        txtPostBy = (CustomTextView) itemView.findViewById(R.id.txtPostBy);
        txtID = (CustomTextView) itemView.findViewById(R.id.txtID);
        txtsourceCity = (CustomTextView) itemView.findViewById(R.id.txtSourceLoadBoard);
        txtdestinationCity = (CustomTextView) itemView.findViewById(R.id.txtDestinationLoadBoard);
        txtWeight = (CustomTextView) itemView.findViewById(R.id.txtWeight);
        txtVerifiedFT = (CustomTextView) itemView.findViewById(R.id.txtVerifiedFT);
        txtBlocked = (CustomTextView) itemView.findViewById(R.id.txtBlocked);
        txtCatapitir = (CustomTextView) itemView.findViewById(R.id.txtCatapitir);
        txtVehicleType = (CustomTextView) itemView.findViewById(R.id.txtVehicleType);
        txtENDTimeLoadBoard = (CustomTextView) itemView.findViewById(R.id.txtENDTimeLoadBoard);
        txtCall = (CustomTextView) itemView.findViewById(R.id.txtCall);
        txtSMS = (CustomTextView) itemView.findViewById(R.id.txtSMS);
    }
}
