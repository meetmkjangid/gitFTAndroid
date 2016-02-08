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
import in.futuretrucks.RecyclerViewHolder;
import in.futuretrucks.model.Driver;


public class DriverRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).resetViewBeforeLoading(true).displayer(new RoundedBitmapDisplayer(1000)).showImageForEmptyUri(R.drawable.final_logo).showImageOnFail(R.drawable.final_logo).showImageOnLoading(R.drawable.final_logo).build();
    private List<Driver> itemList;
    private Context context;
    View.OnClickListener clickListener;

    public DriverRecyclerViewAdapter(Context context, List<Driver> itemList,View.OnClickListener clickListener) {
        this.itemList = itemList;
        this.context = context;
        this.clickListener=clickListener;
        imageLoader.destroy();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_driver_item, null);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.layoutInfo.setTag(String.valueOf(position));
        holder.txtDriverName.setTag(String.valueOf(position));
        holder.txtDriverDLNo.setTag(String.valueOf(position));
        holder.imgvwDriverProfilePic.setTag(String.valueOf(position));
        holder.progressDriverProfile.setTag(String.valueOf(position));

        holder.layoutInfo.setOnClickListener(clickListener);
        holder.txtDriverName.setOnClickListener(clickListener);
        holder.txtDriverDLNo.setOnClickListener(clickListener);
        holder.imgvwDriverProfilePic.setOnClickListener(clickListener);
        holder.progressDriverProfile.setOnClickListener(clickListener);


        if(itemList.get(position).getFirstName()!=null && itemList.get(position).getLastName()!=null){
            holder.txtDriverName.setText(itemList.get(position).getFirstName() + " " + itemList.get(position).getLastName());
        }else if(itemList.get(position).getLastName()!=null){
            holder.txtDriverName.setText(itemList.get(position).getFirstName());
        }
        if(itemList.get(position).getPercentProfile()!=null){
            holder.progressDriverProfile.setProgress(itemList.get(position).getPercentProfile());
        }

        if(itemList.get(position).getLicenseNo()!=null){
            holder.txtDriverDLNo.setText("DL No.- " + itemList.get(position).getLicenseNo());
        }

        String imageUri = "";
        if(itemList.get(position).getDriverImage()!=null){
            imageUri= Constant.BASE_URL_IMAGE+ itemList.get(position).getDriverImage();
            imageUri=imageUri.trim().replace(" ","%20");
        }else{
            imageUri = "drawable://" + R.drawable.default_pic;
        }
        imageLoader.displayImage(imageUri,holder.imgvwDriverProfilePic, options);


    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
