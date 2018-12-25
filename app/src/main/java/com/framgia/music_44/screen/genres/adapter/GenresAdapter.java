package com.framgia.music_44.screen.genres.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.framgia.music_44.R;
import com.framgia.music_44.data.model.Genre;
import com.framgia.music_44.util.OnItemClickListener;
import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.ItemViewHolder> {
    private Context mContext;
    private List<Genre> mGenres;
    private OnItemClickListener mOnItemClickListener;

    public GenresAdapter(Context context, List<Genre> genres,
            OnItemClickListener onItemClickListener) {
        mContext = context;
        mGenres = genres;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_genres, viewGroup, false);
        return new ItemViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.bindData(mGenres.get(i));
    }

    @Override
    public int getItemCount() {
        return mGenres != null ? mGenres.size() : 0;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mTextViewGenres;
        private OnItemClickListener mListener;

        public ItemViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mListener = listener;
            mTextViewGenres = itemView.findViewById(R.id.textViewGenres);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition());
            }
        }

        private void bindData(Genre genre) {
            mTextViewGenres.setText(genre.getNameGenres());
        }
    }
}
