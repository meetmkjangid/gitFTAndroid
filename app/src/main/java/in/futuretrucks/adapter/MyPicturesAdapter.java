package in.futuretrucks.adapter;

/*
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import in.futuretrucks.R;
import in.futuretrucks.UploadsHandler;
import in.futuretrucks.model.UserImage;

public class MyPicturesAdapter extends BaseAdapter {

    private static final int ITEM_TYPE_ONLINE = 0;
    private static final int ITEM_TYPE_DISK = 1;

    private List<UserImage> userImages;
    private Context context;
    private OnActionListener onActionListener;

    private LayoutInflater inflater;

    public MyPicturesAdapter(Context context) {
        super();
        this.context = context;
        this.userImages = new ArrayList<UserImage>();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return userImages.size();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).status >= 0 ? ITEM_TYPE_ONLINE : ITEM_TYPE_DISK;
    }

    @Override
    public UserImage getItem(int position) {
        return userImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userImages.get(position).image.hashCode();
    }

    public void clear() {
        this.userImages.clear();
    }

    public void addAll(Collection<UserImage> images) {
        if (images != null) {
            this.userImages.addAll(images);
        }
    }

    public void remove(UserImage userImage) {
        this.userImages.remove(userImage);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            int itemType = getItemViewType(position);
            if (itemType == ITEM_TYPE_ONLINE)
                convertView = inflater.inflate(R.layout.item_my_pictures_image, parent, false);
            else
                convertView = inflater.inflate(R.layout.item_my_pictures_upload, parent, false);
            holder = new ViewHolder(convertView);
            if (itemType == ITEM_TYPE_DISK) {
                holder.cancelButtonListener = new CancelButtonListener();
                holder.statusIcon.setOnClickListener(holder.cancelButtonListener);
            }
            convertView.setTag(holder);
        }

        final UserImage userImage = getItem(position);

        if (getItemViewType(position) == ITEM_TYPE_DISK) {

            Picasso.with(holder.avatar.getContext())
                    .load(Uri.fromFile(new File(UploadsHandler.getInstance().getImagePath(userImage.image))))
                    .noFade()
                    .fit()
                    .into(holder.avatar);

            userImage.status = UploadsHandler.getInstance().getUploadStatus(userImage.image);

            holder.cancelButtonListener.updatePosition(position);

            switch (userImage.status) {
                case UploadsHandler.UPLOAD_LOADING:
                    holder.uploadProgress.setVisibility(View.VISIBLE);
                    UploadsHandler.getInstance().setProgressBar(userImage.image, holder.uploadProgress);
                    holder.statusText.setVisibility(View.GONE);
                    holder.statusIcon.setImageResource(R.drawable.icon_mb_cancelupload);
                    break;
                case UploadsHandler.UPLOAD_ERROR:
                    holder.uploadProgress.setVisibility(View.GONE);
                    holder.statusText.setVisibility(View.VISIBLE);
                    holder.statusText.setText(R.string.picture_upload_error);
                    holder.statusIcon.setImageResource(R.drawable.icon_mb_errorupload);
                    break;
                case UploadsHandler.UPLOAD_ABORTED:
                    holder.uploadProgress.setVisibility(View.GONE);
                    holder.statusText.setVisibility(View.VISIBLE);
                    holder.statusText.setText(R.string.picture_upload_aborted);
                    holder.statusIcon.setImageResource(R.drawable.icon_mb_refreshupload);
                    break;
            }

            holder.statusIndicator.setBackgroundColor(Color.TRANSPARENT);
            holder.uploadProgress.setProgress(UploadsHandler.getInstance().getProgress(userImage.image));
        } else {

            switch (userImage.status) {
                case 0:
                    holder.statusIndicator.setBackgroundColor(context.getResources().getColor(R.color.my_pictures_check_divider));
                    holder.statusIcon.setImageResource(R.drawable.icon_mb_check);
                    holder.actionIcon.setImageResource(R.drawable.icon_mb_settings);
                    break;
                // etc
            }

            holder.statusText.setText(userImage.description);

            UIUtils.loadImage(context, holder.avatar, userImage.image);

        }

        return convertView;
    }

    public void add(UserImage image) {
        this.userImages.add(image);
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }

    static class ViewHolder {
        @InjectView(R.id.user_image_avatar)
        ImageView avatar;

        @InjectView(R.id.user_image_status_indicator)
        View statusIndicator;

        @InjectView(R.id.user_image_status_icon)
        ImageView statusIcon;

        @InjectView(R.id.user_image_status_text)
        TextView statusText;

        @Optional @InjectView(R.id.user_image_upload_progress)
        ProgressBar uploadProgress;

        @Optional @InjectView(R.id.user_image_action)
        ImageView actionIcon;

        CancelButtonListener cancelButtonListener;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public interface OnActionListener {
        public void onAction(int position);
    }

    private class CancelButtonListener implements View.OnClickListener {

        private int position;

        private void updatePosition(int pos){
            position = pos;
        }

        @Override
        public void onClick(View v) {
            if (onActionListener != null)
                onActionListener.onAction(position);

        }
    }
}*/