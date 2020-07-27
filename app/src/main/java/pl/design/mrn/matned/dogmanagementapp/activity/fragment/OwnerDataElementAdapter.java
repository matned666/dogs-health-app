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

import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;
import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Owner;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;


public class OwnerDataElementAdapter extends RecyclerView.Adapter<OwnerDataElementAdapter.ViewHolder>  {

    private List<Owner> ownersList;
    private DataPositionListener dataPositionListener;
    private int selectedPosition;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public OwnerDataElementAdapter(List<Owner> owners) {
        ownersList = owners;
        this.dataPositionListener = DataPositionListener.getInstance();
        this.selectedPosition = dataPositionListener.getPosition();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_owner_info, null);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Owner owner = ownersList.get(position);
        holder.ownerNameAndSurname.setText(owner.getName() + " " + owner.getSurname());
        holder.dateTo.setText(dateFormat.format(owner.getDateTo()));
        holder.dateFrom.setText(dateFormat.format(owner.getDateFrom()));
        if(selectedPosition == position) holder.holderButton.setBackgroundResource(R.drawable.roundcornersrecyclerviewelementselected);
        else holder.holderButton.setBackgroundResource(R.drawable.roundcornersrecyclerviewelement);

        holder.holderButton.setOnClickListener(v -> {
            selectedPosition = position;
            dataPositionListener.setPosition(position);
            dataPositionListener.setSelectedItemId(owner.getId());
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
        private TextView ownerNameAndSurname;
        private TextView dateFrom;
        private TextView dateTo;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.holderButton = itemView.findViewById(R.id.itemOwnerButton);
            this.ownerNameAndSurname = itemView.findViewById(R.id.data_item_owner_name);
            this.dateFrom = itemView.findViewById(R.id.dateFromOwner);
            this.dateTo = itemView.findViewById(R.id.dateToOwner);
        }


    }

}

