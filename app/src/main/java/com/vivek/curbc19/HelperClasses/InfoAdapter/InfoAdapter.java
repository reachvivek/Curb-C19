package com.vivek.curbc19.HelperClasses.InfoAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivek.curbc19.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoViewHolder> {

    private List<InfoItem> infoItems;

    public InfoAdapter(List<InfoItem> infoItems) {
        this.infoItems = infoItems;
    }

    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InfoViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.info_container, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder holder, int position) {
        holder.setInfoData(infoItems.get(position));
    }

    @Override
    public int getItemCount() {
        return infoItems.size();
    }

    class InfoViewHolder extends RecyclerView.ViewHolder{
        private TextView textTitle;
        private TextView textDescription;
        private ImageView infoImage;


        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            infoImage = itemView.findViewById(R.id.imageInfo);
        }

        void setInfoData(InfoItem infoItem) {
            textTitle.setText(infoItem.getTitle());
            textDescription.setText(infoItem.getDescription());
            infoImage.setImageResource(infoItem.getImage());
        }

    }

}
