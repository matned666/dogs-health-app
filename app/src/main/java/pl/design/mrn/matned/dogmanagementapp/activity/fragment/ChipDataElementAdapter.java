package pl.design.mrn.matned.dogmanagementapp.activity.fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Chip;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;


public class ChipDataElementAdapter extends RecyclerView.Adapter<ChipDataElementAdapter.ViewHolder>  {

    private List<Chip> ownersList;
    private DataPositionListener dataPositionListener;
    private int selectedPosition;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public ChipDataElementAdapter(List<Chip> owners) {
        ownersList = owners;
        this.dataPositionListener = DataPositionListener.getInstance();
        this.selectedPosition = dataPositionListener.getPosition();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_chip_info, null);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chip chip = ownersList.get(position);
        holder.chipNumber.setText(chip.getChipNumber());
        if (chip.isActive()) {
            holder.isActive.setTextColor(Color.GREEN);
            holder.isActive.setText("Aktywny");
        }else{
            holder.isActive.setTextColor(Color.RED);
            holder.isActive.setText("Nieaktywny");
        }
        if(selectedPosition == position) holder.holderButton.setBackgroundResource(R.drawable.roundcornersrecyclerviewelementselected);
        else holder.holderButton.setBackgroundResource(R.drawable.roundcornersrecyclerviewelement);

        holder.holderButton.setOnClickListener(v -> {
            selectedPosition = position;
            dataPositionListener.setPosition(position);
            dataPositionListener.setSelectedItemId(chip.getChipId());
            notifyDataSetChanged();
        });
    }


    @Override
    public int getItemCount() {
        if(ownersList == null) return 0;
        else return ownersList.size();
    }




    static class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout holderButton;
        private TextView chipNumber;
        private TextView isActive;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.holderButton = itemView.findViewById(R.id.data_item_chipItemButton);
            this.chipNumber = itemView.findViewById(R.id.data_item_chip_name);
            this.isActive = itemView.findViewById(R.id.data_item_isActiveInfoItem);
        }


    }

}

