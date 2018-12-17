package com.framgia.music_44.screen.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.framgia.music_44.R;
import com.framgia.music_44.data.model.Songs;
import com.framgia.music_44.util.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.music_44.util.Constant.ONE;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ItemViewHolder> {
    private List<Songs> mSongs;
    private Context mContext;
    private OnItemClickListener mListener;

    public HomeAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
        mSongs = new ArrayList<>();
    }

    public void addSongs(List<Songs> songs) {
        mSongs.addAll(songs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_list, viewGroup, false);
        return new ItemViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.bindData(mSongs.get(i), mContext);
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private ImageView mImageViewSong;
        private TextView mTextViewNameSong, mTextViewNameArtist;
        private OnItemClickListener mListener;

        ItemViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mImageViewSong = itemView.findViewById(R.id.imageViewSongs);
            mTextViewNameSong = itemView.findViewById(R.id.textViewNameSong);
            mTextViewNameArtist = itemView.findViewById(R.id.textViewNameArtist);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        private void bindData(Songs songs, Context context) {
            Glide.with(context).load(songs.getImage()).into(mImageViewSong);
            mTextViewNameSong.setText(songs.getNameSong());
            mTextViewNameArtist.setText(songs.getNameArtist());
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
