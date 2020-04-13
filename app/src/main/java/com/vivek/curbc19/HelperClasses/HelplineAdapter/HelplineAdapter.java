package com.vivek.curbc19.HelperClasses.HelplineAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vivek.curbc19.Common.HelplineActivity;
import com.vivek.curbc19.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class HelplineAdapter extends RecyclerView.Adapter<HelplineAdapter.NewsViewHolder> implements Filterable {


    Context mContext;
    List<HelplineItem> mData ;
    List<HelplineItem> mDataFiltered ;
    boolean isDark = false;
    private HelplineAdapterEvents helplineAdapterEvents;


    public HelplineAdapter(HelplineAdapterEvents helplineAdapterEvents, Context mContext, List<HelplineItem> mData, boolean isDark) {
        this.helplineAdapterEvents=helplineAdapterEvents;
        this.mContext = mContext;
        this.mData = mData;
        this.isDark = isDark;
        this.mDataFiltered = mData;
    }

    public HelplineAdapter(Context mContext, List<HelplineItem> mData, boolean isDark) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;

    }


    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_helpline,viewGroup,false);
        NewsViewHolder viewHolder = new NewsViewHolder(layout);

        return new NewsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int position) {

        // bind data here

        // we apply animation to views here
        // first lets create an animation for user photo
        newsViewHolder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
        newsViewHolder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));



        newsViewHolder.tv_title.setText(mDataFiltered.get(position).getTitle());
        newsViewHolder.tv_content.setText(mDataFiltered.get(position).getContent());
        newsViewHolder.callico.setImageResource(mDataFiltered.get(position).getCallPhoto());
        newsViewHolder.img_user.setImageResource(mDataFiltered.get(position).getUserPhoto());



    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {

                    mDataFiltered = mData ;

                }
                else {
                    List<HelplineItem> lstFiltered = new ArrayList<>();
                    for (HelplineItem row : mData) {

                        if (row.getTitle().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }

                    }

                    mDataFiltered = lstFiltered;

                }


                FilterResults filterResults = new FilterResults();
                filterResults.values= mDataFiltered;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                mDataFiltered = (List<HelplineItem>) results.values;
                notifyDataSetChanged();

            }
        };




    }



    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_title,tv_content;
        ImageView img_user, callico;
        RelativeLayout container;


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_description);
            callico = itemView.findViewById(R.id.callico);
            img_user = itemView.findViewById(R.id.img_user);

            callico.setOnClickListener(this);

            if (isDark) {
                setDarkTheme();
            }

        }

        private void setDarkTheme() {
            container.setBackgroundResource(R.drawable.card_bg_dark);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Toast.makeText(v.getContext(), "Redirecting You To Dialer...\nPlease Tap On Call Button!\nCalling: "+(mData.get(position).getContent()), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + (mData.get(position).getContent())));
            mContext.startActivity(intent);

        }
    }

    public interface HelplineAdapterEvents{
        void onNumberClicked(HelplineItem helplineItem);
    }


}
