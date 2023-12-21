package com.montesdiether.myapplication.ui.MusicLibrary;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.montesdiether.myapplication.R;
import com.montesdiether.myapplication.data.musicwishlist;

import java.util.List;


public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private List<musicwishlist> localDataSet;
    private final OnItemClickListener localListener;
    private final OnDeleteClickListener localListenerFave;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(musicwishlist item);
    }

    public interface OnDeleteClickListener {
        void onItemClick(musicwishlist item, int position);
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;
        private final CardView cardView;
        private final Button btnDelete;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.desc);
            cardView = view.findViewById(R.id.noteCardView);
            btnDelete = view.findViewById(R.id.btnDelete);
        }

        public TextView getTitle() {
            return title;
        }
        public TextView getDescription() {
            return description;
        }
        public CardView getCardView() {
            return cardView;
        }
        public Button getBtnDelete() {
            return btnDelete;
        }
    }

    public MusicAdapter(List<musicwishlist> dataSet, OnItemClickListener listener, OnDeleteClickListener listenerFave, Context context) {

        localDataSet = dataSet;
        localListener = listener;
        localListenerFave = listenerFave;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        viewHolder.getTitle().setText(localDataSet.get(position).musicName);
        viewHolder.getDescription().setText(localDataSet.get(position).musicAuthor);
        viewHolder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localListener.onItemClick(localDataSet.get(position));
            }
        });
        viewHolder.getBtnDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localListenerFave.onItemClick(localDataSet.get(position), position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

