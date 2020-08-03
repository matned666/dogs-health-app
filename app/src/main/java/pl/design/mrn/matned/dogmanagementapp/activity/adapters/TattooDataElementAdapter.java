package pl.design.mrn.matned.dogmanagementapp.activity.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.add.ChipActivityAdd;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.add.TattooActivityAdd;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit.ChipActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit.TattooActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.info.ChipActivityInfo;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.info.TattooActivityInfo;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Tattoo;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_ADD;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_EDIT;


public class TattooDataElementAdapter extends RecyclerView.Adapter<TattooDataElementAdapter.ViewHolder>  {

    private List<Tattoo> tattooList;
    private DataPositionListener dataPositionListener;
    private int selectedPosition;
    private String usage;
    private Context context;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public TattooDataElementAdapter(List<Tattoo> tattoos, String usage, Context context) {
        this.context = context;
        this.usage = usage;
        this.tattooList = tattoos;
        this.dataPositionListener = DataPositionListener.getInstance();
        this.selectedPosition = dataPositionListener.getPosition();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_tattoo_info, null);
        return new ViewHolder(view);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        notifyDataSetChanged();
    }

    public void refresh(){
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tattoo tattoo = tattooList.get(position);
        holder.tattooDescription.setText(generateHeader(tattoo.getDescription()));
        holder.dateFrom.setText(dateFormat.format(tattoo.getTattooDate()));
        if(selectedPosition == position) holder.holderButton.setBackgroundResource(R.drawable.roundcornersrecyclerviewelementselected);
        else holder.holderButton.setBackgroundResource(R.drawable.roundcornersrecyclerviewelement);

        holder.holderButton.setOnClickListener(v -> {
            selectedPosition = position;
            dataPositionListener.setPosition(position);
            dataPositionListener.setSelectedItemId(tattoo.getTattooId());
            notifyDataSetChanged();
            if (usage.equals(USAGE_EDIT) || usage.equals(USAGE_ADD)){
                Intent intent = new Intent(context, TattooActivityEdit.class);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(context, TattooActivityInfo.class);
                context.startActivity(intent);
            }
        });
    }

    private String generateHeader(String description) {
        if(description.length() > 25) return description.substring(0, 24).trim() + "...";
        else return description;
    }


    @Override
    public int getItemCount() {
        if(tattooList == null) return 0;
        else return tattooList.size();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout holderButton;
        private TextView tattooDescription;
        private TextView dateFrom;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.holderButton = itemView.findViewById(R.id.tattooItemButton);
            this.tattooDescription = itemView.findViewById(R.id.tattooItem_description);
            this.dateFrom = itemView.findViewById(R.id.tattooItem_date_of_injection);
        }


    }

}

