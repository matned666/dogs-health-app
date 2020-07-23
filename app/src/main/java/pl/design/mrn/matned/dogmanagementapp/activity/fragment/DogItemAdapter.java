package pl.design.mrn.matned.dogmanagementapp.activity.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.PositionListener;
import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogModel;

import static pl.design.mrn.matned.dogmanagementapp.ImageAdvancedFunction.setImage;


public class DogItemAdapter extends RecyclerView.Adapter<DogItemAdapter.ViewHolder>  {

    private Context context;
    private List<DogModel> dogs;
    private PositionListener positionListener;
    private int selectedPosition;
    private Resources resources;
    private Uri photoUri;

    public DogItemAdapter(Context context, List<DogModel> dogs, Resources resources) {
        this.context = context;
        this.dogs = dogs;
        this.positionListener = PositionListener.getInstance();
        this.selectedPosition = positionListener.getPosition();
        this.resources = resources;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_list_element, null);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DogModel dog = dogs.get(position);
        dogs.size();
        Drawable drawable;
        drawable = context.getResources().getDrawable(R.drawable.ic_stat_name);
        if (dog.getDogImage() != null)
        {
            setImage(holder.dogImage, dog.getDogImage(),context, photoUri, resources);
        }
        else
            holder.dogImage.setImageDrawable(drawable);

        holder.dogName.setText(dog.getName());
        if(selectedPosition == position) holder.holderButton.setBackgroundResource(R.drawable.roundcornersrecyclerviewelementselected);
        else holder.holderButton.setBackgroundResource(R.drawable.roundcornersrecyclerviewelement);

        holder.holderButton.setOnClickListener(v -> {
            selectedPosition = position;
            positionListener.setPosition(position);
            positionListener.setSelectedDogId(dog.getId());
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        if(dogs == null) return 0;
        else return dogs.size();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout holderButton;
        private ImageView dogImage;
        private TextView dogName;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.holderButton = itemView.findViewById(R.id.holderButton);
            this.dogImage = itemView.findViewById(R.id.dog_item_image);
            this.dogName = itemView.findViewById(R.id.data_item_owner_name);
        }


    }

}

