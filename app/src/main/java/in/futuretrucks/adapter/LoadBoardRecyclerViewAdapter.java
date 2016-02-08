package in.futuretrucks.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import in.futuretrucks.listener.OnLoadMoreListener;
import in.futuretrucks.model.LoadBoard;

/**
 * Created by futuretrucks on 28/12/15.
 */
public class LoadBoardRecyclerViewAdapter extends RecyclerView.Adapter<LoadBoardRecyclerViewHolder>{

    private List<LoadBoard> itemList;
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


    public LoadBoardRecyclerViewAdapter(Context context, RecyclerView recyclerView,final List<LoadBoard> itemList,View.OnClickListener clickListener) {
        this.itemList = itemList;
        this.context = context;
        mRecyclerView=recyclerView;
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        this.clickListener=clickListener;

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
    public LoadBoardRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_load_board_item, parent, false);
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
    public void onBindViewHolder(LoadBoardRecyclerViewHolder holder, int position) {

        if (holder instanceof UserViewHolder) {
            LoadBoard loadBoard = itemList.get(position);
            if(holder!=null){

                try{

                    holder.txtBlocked.setVisibility(View.INVISIBLE);
                    holder.txtVehicleType.setVisibility(View.GONE);
                    holder.txtCall.setTag(String.valueOf(position));
                    holder.txtSMS.setTag(String.valueOf(position));
                    holder.txtCall.setOnClickListener(clickListener);
                    holder.txtSMS.setOnClickListener(clickListener);

                    if(loadBoard.getExpectedPrice()!=null){
                        String exp_price=String.valueOf(loadBoard.getExpectedPrice());
                        holder.txtExpPriceLoadBoard.setText("");
                        holder.txtExpPriceLoadBoard.setText(R.string.Rupees_symbol);
                        if(!exp_price.trim().equals("null")){
                            holder.txtExpPriceLoadBoard.setText("Expected Price: "+holder.txtExpPriceLoadBoard.getText().toString()+" "+loadBoard.getExpectedPrice());
                        }else{
                            holder.txtExpPriceLoadBoard.setText("Expected Price: N/A");
                        }
                    }else{
                        holder.txtExpPriceLoadBoard.setText("Expected Price: N/A");
                    }


                    if(loadBoard.getSource()!=null){
                        if(!loadBoard.getSource().getC().equals("null")){
                            if(loadBoard.getSource().getC().trim().equals(loadBoard.getSource().getD().trim())){
                                holder.txtsourceCity.setText(""+loadBoard.getSource().getC()+" , "+loadBoard.getSource().getS());
                            }else{
                                holder.txtsourceCity.setText(""+loadBoard.getSource().getC()+" , "+loadBoard.getSource().getD());
                            }
                        }
                    }

                    if(loadBoard.getWeight()!=null){
                        if(!loadBoard.getWeight().trim().equals("null")){
                            holder.txtWeight.setText(""+loadBoard.getWeight()+" KG");
                        }else{
                            holder.txtWeight.setText("0 KG");
                        }
                    }

                    if(loadBoard.getVerified()!=null){
                        if(!loadBoard.getVerified().trim().equals("null")){
                            holder.txtVerifiedFT.setText("Status: "+loadBoard.getVerified());
                        }
                    }

                    if(loadBoard.getPostingId()!=null){
                        if(!loadBoard.getPostingId().trim().equals("null")){
                            holder.txtID.setText("ID: "+loadBoard.getPostingId());
                        }
                    }


                    if(loadBoard.getPostOwnerCompany()!=null){
                        if(!loadBoard.getPostOwnerCompany().trim().equals("null")){
                            holder.txtPostBy.setText("Posted By: "+loadBoard.getPostOwnerCompany());
                        }
                    }

                    if(loadBoard.getDate()!=null){
                        if(loadBoard.getDate().getFrom()!=null){
                            holder.txtENDTimeLoadBoard.setText(loadBoard.getDate().getFrom().toString().split("T")[0]);
                        }
                    }

                    if(loadBoard.getDestination()!=null){
                        if(!loadBoard.getDestination().get(0).getC().equals("null")){
                            if(loadBoard.getDestination().get(0).getC().trim().equals(loadBoard.getDestination().get(0).getD().trim())){
                                holder.txtdestinationCity.setText(""+loadBoard.getDestination().get(0).getC()+","+loadBoard.getDestination().get(0).getS());
                            }else{
                                holder.txtdestinationCity.setText(""+loadBoard.getDestination().get(0).getC()+","+loadBoard.getDestination().get(0).getD());
                            }
                        }
                    }

                    if(loadBoard.getMaterialType()!=null){
                        if(!loadBoard.getMaterialType().getMaterialType().trim().equals("null")){
                            holder.txtCatapitir.setText(loadBoard.getMaterialType().getMaterialType());
                        }else{
                            holder.txtCatapitir.setText("Not available");
                        }
                    }

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

    static class UserViewHolder extends LoadBoardRecyclerViewHolder {
        // CustomTextView txtEstPrice;
        public UserViewHolder(View itemView) {
            super(itemView);

        }
    }

    static class LoadingViewHolder extends LoadBoardRecyclerViewHolder{
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
}
