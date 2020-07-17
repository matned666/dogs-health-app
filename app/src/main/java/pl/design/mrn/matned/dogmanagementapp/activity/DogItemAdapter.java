package pl.design.mrn.matned.dogmanagementapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.DogModel;

public class DogItemAdapter extends RecyclerView.Adapter<DogItemAdapter.ViewHolder> {

    private Context context;
    private List<DogModel> dogs;

    DogItemAdapter(Context context, List<DogModel> dogs) {
        this.context = context;
        this.dogs = dogs;
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
        holder.dogImage.setImageDrawable(drawable);
        holder.dogName.setText(dog.getName());
        holder.holderButton.setOnClickListener(clickIt(holder));
    }

    private View.OnClickListener clickIt(@NonNull ViewHolder holder) {
        return v -> {
            holder.holderButton.setBackgroundResource(R.drawable.roundcornersrecyclerviewelementselected);
        };
    }


    @Override
    public int getItemCount() {
        if(dogs == null) return 0;
        else return dogs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout holderButton;
        private ImageView dogImage;
        private TextView dogName;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.holderButton = itemView.findViewById(R.id.holderButton);
            this.dogImage = itemView.findViewById(R.id.dog_item_image);
            this.dogName = itemView.findViewById(R.id.dog_item_name);
        }


    }

}

