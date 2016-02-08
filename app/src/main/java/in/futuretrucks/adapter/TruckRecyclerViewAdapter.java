package in.futuretrucks.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

import in.futuretrucks.Constant.Constant;
import in.futuretrucks.R;
import in.futuretrucks.TruckRecyclerViewHolder;
import in.futuretrucks.model.Truck;


public class  TruckRecyclerViewAdapter extends RecyclerView.Adapter<TruckRecyclerViewHolder> {
    private ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).resetViewBeforeLoading(true).displayer(new RoundedBitmapDisplayer(4)).showImageForEmptyUri(R.drawable.final_logo).showImageOnFail(R.drawable.final_logo).showImageOnLoading(R.drawable.final_logo).build();
    private List<Truck> itemList;
    private Context context;
    View.OnClickListener clickListener;

    public TruckRecyclerViewAdapter(Context context, List<Truck> itemList,View.OnClickListener clickListener) {
        this.itemList = itemList;
        this.context = context;
        imageLoader.destroy();
        this.clickListener=clickListener;
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public TruckRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_truck_item, null);
        TruckRecyclerViewHolder viewHolder = new TruckRecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TruckRecyclerViewHolder holder, int position) {

        holder.layoutInfo.setTag(String.valueOf(position));
        holder.txtTruckOwnerName.setTag(String.valueOf(position));
        holder.txtTruckRegNo.setTag(String.valueOf(position));
        holder.imgvwTruck.setTag(String.valueOf(position));
        holder.progressTruckProfile.setTag(String.valueOf(position));

        holder.layoutInfo.setOnClickListener(clickListener);
        holder.txtTruckOwnerName.setOnClickListener(clickListener);
        holder.txtTruckRegNo.setOnClickListener(clickListener);
        holder.imgvwTruck.setOnClickListener(clickListener);
        holder.progressTruckProfile.setOnClickListener(clickListener);

        if(itemList.get(position).getLicensePlateNum()!=null){
            holder.txtTruckOwnerName.setText(itemList.get(position).getLicensePlateNum());
        }
        if(itemList.get(position).getManufecturer()!=null){
            holder.txtTruckRegNo.setText("" + itemList.get(position).getManufecturer());
        }

        if(itemList.get(position).getPercentProfile()!=null){
            holder.progressTruckProfile.setProgress(itemList.get(position).getPercentProfile());
        }

        String imageUri = "";
        if(itemList.get(position).getVehicleImage()!=null){
            imageUri= Constant.BASE_URL_IMAGE+ itemList.get(position).getVehicleImage();
            imageUri=imageUri.trim().replace(" ","%20");
        }else{
            imageUri = "drawable://" + R.drawable.default_pic;
        }
        imageLoader.displayImage(imageUri,holder.imgvwTruck, options);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
