package in.futuretrucks.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

import in.futuretrucks.LoadBoardRecyclerViewHolder;
import in.futuretrucks.R;
import in.futuretrucks.TruckBoardRecyclerViewHolder;
import in.futuretrucks.listener.OnLoadMoreListener;
import in.futuretrucks.model.LoadBoard;
import in.futuretrucks.model.TruckBoard;

/**
 * Created by futuretrucks on 28/12/15.
 */
public class TruckBoardRecyclerViewAdapter extends RecyclerView.Adapter<TruckBoardRecyclerViewHolder>{

    private List<TruckBoard> itemList;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).resetViewBeforeLoading(true).displayer(new RoundedBitmapDisplayer(1000)).showImageForEmptyUri(R.drawable.final_logo).showImageOnFail(R.drawable.final_logo).showImageOnLoading(R.drawable.final_logo).build();
    private Context context;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    RecyclerView mRecyclerView;
    private View.OnClickListener clickListener;

    public TruckBoardRecyclerViewAdapter(Context context, RecyclerView recyclerView,final List<TruckBoard> itemList,View.OnClickListener clickListener) {
        this.itemList = itemList;
        this.context = context;
        mRecyclerView=recyclerView;
        this.clickListener=clickListener;
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(itemList.size()>0){
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public TruckBoardRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_truck_board_item, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            if(itemList.size()>0){
                View view = LayoutInflater.from(context).inflate(R.layout.layout_load_more_progressbar, parent, false);
                return new LoadingViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(TruckBoardRecyclerViewHolder holder, int position) {

        if (holder instanceof UserViewHolder) {
            TruckBoard truckBoard = itemList.get(position);
            if(holder!=null){

                try{

                    holder.txtBlocked.setVisibility(View.INVISIBLE);
                    holder.txtCall.setTag(String.valueOf(position));
                    holder.txtSMS.setTag(String.valueOf(position));
                    holder.txtCall.setOnClickListener(clickListener);
                    holder.txtSMS.setOnClickListener(clickListener);
                    holder.txtVehicleType.setVisibility(View.GONE);

                    if(truckBoard.getExpectedPrice()!=null){
                        String exp_price=String.valueOf(truckBoard.getExpectedPrice());
                        holder.txtExpPriceLoadBoard.setText("");
                        holder.txtExpPriceLoadBoard.setText(R.string.Rupees_symbol);
                        if(!exp_price.trim().equals("null")){
                            holder.txtExpPriceLoadBoard.setText("Expected Price: "+holder.txtExpPriceLoadBoard.getText().toString()+" "+truckBoard.getExpectedPrice());
                        }else{
                            holder.txtExpPriceLoadBoard.setText("Expected Price: N/A");
                        }
                    }else{
                        holder.txtExpPriceLoadBoard.setText("Expected Price: N/A");
                    }

                    if(truckBoard.getWeight()!=null){
                        String weight=String.valueOf(truckBoard.getWeight());
                        if(!weight.trim().equals("null")){
                            holder.txtWeight.setText(""+truckBoard.getWeight()+" KG");
                        }else{
                            holder.txtWeight.setText("N/A");
                        }
                    }

                    if(truckBoard.getVerified()!=null){
                        if(!truckBoard.getVerified().trim().equals("null")){
                            holder.txtVerifiedFT.setText("Status: " + truckBoard.getVerified());
                        }
                    }

                    if(truckBoard.getPostingId()!=null){
                        if(!truckBoard.getPostingId().trim().equals("null")){
                            holder.txtID.setText("ID: "+truckBoard.getPostingId());
                        }
                    }

                    if(truckBoard.getPostOwnerCompany()!=null){
                        if(!truckBoard.getPostOwnerCompany().trim().equals("null")){
                            holder.txtPostBy.setText("Posted By: "+truckBoard.getPostOwnerCompany());
                        }
                    }

                    if(truckBoard.getDate()!=null){
                        if(truckBoard.getDate().getFrom()!=null){
                            holder.txtENDTimeLoadBoard.setText(truckBoard.getDate().getFrom().toString().split("T")[0]);
                        }
                    }

                    holder.txtCatapitir.setText("Not available");

                    if(!truckBoard.getSource().getC().trim().equals("null")){
                        if(truckBoard.getSource().getC().trim().equals(truckBoard.getSource().getD().trim())){
                            holder.txtsourceCity.setText(""+truckBoard.getSource().getC()+", "+truckBoard.getSource().getS());
                        }else{
                            holder.txtsourceCity.setText(""+truckBoard.getSource().getC()+", "+truckBoard.getSource().getD());
                        }
                    }

                    if(truckBoard.getDestination()!=null){
                        if(truckBoard.getDestination().size()>0){
                            if(truckBoard.getDestination().get(0).getC().trim().equals(truckBoard.getDestination().get(0).getD().trim())){
                                holder.txtdestinationCity.setText("" + truckBoard.getDestination().get(0).getC()+", "+truckBoard.getDestination().get(0).getS());
                            }else{
                                holder.txtdestinationCity.setText("" + truckBoard.getDestination().get(0).getC()+", "+truckBoard.getDestination().get(0).getD());
                            }
                        }else{

                        }
                    }

                    /*if (!truckBoard.getMaterialType().getMaterialType().equals("null")){
                        holder.txtCatapitir.setText(truckBoard.getMaterialType().getMaterialType());
                    }else{
                        holder.txtCatapitir.setText("Not available");
                    }*/
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        } else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    static class UserViewHolder extends TruckBoardRecyclerViewHolder {
        // CustomTextView txtEstPrice;
        public UserViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class LoadingViewHolder extends TruckBoardRecyclerViewHolder{
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
}
