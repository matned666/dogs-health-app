package pl.design.mrn.matned.dogmanagementapp.activity.fragment;

import android.annotation.SuppressLint;
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
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Tattoo;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;


public class TattooDataElementAdapter extends RecyclerView.Adapter<TattooDataElementAdapter.ViewHolder>  {

    private List<Tattoo> tattooList;
    private DataPositionListener dataPositionListener;
    private int selectedPosition;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public TattooDataElementAdapter(List<Tattoo> tattoos) {
        tattooList = tattoos;
        this.dataPositionListener = DataPositionListener.getInstance();
        this.selectedPosition = dataPositionListener.getPosition();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_tattoo_info, null);
        return new ViewHolder(view);
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
        });
    }

    private String generateHeader(String description) {
        return description.substring(0, 25).trim() + "...";
    }


    @Override
    public int getItemCount() {
        if(tattooList == null) return 0;
        else return tattooList.size();
    }




    static class ViewHolder extends RecyclerView.ViewHolder{

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

