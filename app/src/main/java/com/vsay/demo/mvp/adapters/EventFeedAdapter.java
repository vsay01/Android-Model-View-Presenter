package com.vsay.demo.mvp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vsay.demo.mvp.R;
import com.vsay.demo.mvp.listener.OnHolderClickListener;
import com.vsay.demo.mvp.listener.OnRecyclerViewClickListener;
import com.vsay.demo.mvp.models.Event;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vsaya on 2/11/17.
 */
public class EventFeedAdapter extends RecyclerView.Adapter<EventFeedAdapter.FeedViewHolder> implements OnHolderClickListener {
    private List<Event> events;
    private Context mContext;
    private OnRecyclerViewClickListener clickListener;

    public EventFeedAdapter(Context context, List<Event> events, OnRecyclerViewClickListener clickListener) {
        this.mContext = context;
        this.events = events;
        this.clickListener = clickListener;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        return new FeedViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(final FeedViewHolder holder, int position) {
        holder.currentEvent = events.get(position);
        holder.holderPosition = position;
        holder.mTitle.setText(holder.currentEvent.getTitle());
        holder.mLocationLine1.setText(String.format(mContext.getString(R.string.locationLine), holder.currentEvent.getLocationline1(), holder.currentEvent.getLocationline2()));
        holder.mDescription.setText(holder.currentEvent.getDescription());
        Picasso.with(mContext).load(holder.currentEvent.getImage())
                .error(R.drawable.placeholder_nomoon)
                .placeholder(R.drawable.placeholder_nomoon)
                .into(holder.mImage);
        holder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTheDetail(holder.currentEvent);
            }
        });
    }

    private void shareTheDetail(Event city) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, city.getDescription());
        sendIntent.setType("text/plain");
        mContext.startActivity(Intent.createChooser(sendIntent, mContext.getResources().getText(R.string.send_to)));
    }

    @Override
    public void onHolderClicked(int holderPosition, View clickedImage) {
        clickListener.onRecyclerViewClick(events.get(holderPosition), clickedImage);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setPosts(List<Event> posts) {
        this.events = posts;
    }

    public void clearData() {
        events.clear();
    }

    public void addAllData(List<Event> posts) {
        this.events.addAll(posts);
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {
        //public View view;
        private Event currentEvent;
        private int holderPosition;
        private OnHolderClickListener clickListener;

        @BindView(R.id.image)
        CircleImageView mImage;

        @BindView(R.id.title)
        TextView mTitle;

        @BindView(R.id.locationLine)
        TextView mLocationLine1;

        @BindView(R.id.description)
        TextView mDescription;

        @BindView(R.id.share)
        TextView mShare;

        public FeedViewHolder(View view, OnHolderClickListener clickListener) {
            super(view);
            this.clickListener = clickListener;
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.card_view)
        public void onClick() {
            if (clickListener != null) {
                clickListener.onHolderClicked(holderPosition, mImage);
            }
        }
    }
}
