package com.tech.airbnb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.tech.airbnb.Model.Model;
import com.tech.airbnb.R;

import java.util.ArrayList;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Model> models;

    public ItemAdapter(Context context, ArrayList<Model> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Model model = models.get(position);
        holder.location.setText(model.getLocation());
        holder.landmark.setText(model.getLocation());
        holder.cost.setText(model.getCost());
        holder.stars.setText(model.getStars());

        ArrayList<String> imageUrls = model.getImageUrls();
        holder.bindImages(imageUrls);

        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ViewPager2 viewPager;
        private ImageView roomImage;
        private ImageView favBtn;
        private TextView location;
        private TextView landmark;
        private TextView cost;
        private TextView stars;
        private RelativeLayout rootLayout;

        public ViewHolder(@NonNull View view) {
            super(view);

            roomImage = view.findViewById(R.id.room_image);
            favBtn = view.findViewById(R.id.favourite_btn);
            location = view.findViewById(R.id.location);
            landmark = view.findViewById(R.id.landmark);
            cost = view.findViewById(R.id.cost);
            stars = view.findViewById(R.id.ratings);
            rootLayout = view.findViewById(R.id.rootLayout);
            viewPager = view.findViewById(R.id.viewPager);


        }

        public void bindImages(ArrayList<String> imageUrls) {
            ImageAdapter imageAdapter = new ImageAdapter(context, imageUrls);
            viewPager.setAdapter(imageAdapter);
        }
    }

    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

        private Context context;
        private ArrayList<String> imageUrls;

        public ImageAdapter(Context context, ArrayList<String> imageUrls) {
            this.context = context;
            this.imageUrls = imageUrls;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            String imageUrl = imageUrls.get(position);
            Glide.with(context).load(imageUrl).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return imageUrls.size();
        }

        public class ImageViewHolder extends RecyclerView.ViewHolder {

            private ImageView imageView;

            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image_view);
            }
        }
    }

}
