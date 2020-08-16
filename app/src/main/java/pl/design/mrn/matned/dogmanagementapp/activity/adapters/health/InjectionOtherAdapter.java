package pl.design.mrn.matned.dogmanagementapp.activity.adapters.health;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.edit.ChipActivityEdit;
import pl.design.mrn.matned.dogmanagementapp.activity.dataactivity.info.ChipActivityInfo;
import pl.design.mrn.matned.dogmanagementapp.activity.health.InjectionsOtherActivity;
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.Chip;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionOther;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_ADD;
import static pl.design.mrn.matned.dogmanagementapp.Statics.USAGE_EDIT;


public class InjectionOtherAdapter extends RecyclerView.Adapter<InjectionOtherAdapter.ViewHolder>  {

    private List<InjectionOther> injectionOtherList;
    private DataPositionListener dataPositionListener;
    private int selectedPosition;
    private String usage;
    private Context context;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public InjectionOtherAdapter(List<InjectionOther> injectionOtherList, Context context) {
        this.context = context;
        this.usage = usage;
        this.injectionOtherList = injectionOtherList;
        this.dataPositionListener = DataPositionListener.getInstance();
        this.selectedPosition = dataPositionListener.getPosition();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_injection, null);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InjectionOther injectionOther = injectionOtherList.get(position);
        holder.medicine.setText(injectionOther.getMedicine());
        holder.date.setText(dateFormat.format(injectionOther.getTreatmentDate()));
        holder.nextDate.setText(dateFormat.format(injectionOther.getNextTreatment()));
        if (injectionOther.isActive()) {
            holder.isActive.setTextColor(Color.GREEN);
            holder.isActive.setText("Aktywny");
        }else{
            holder.isActive.setTextColor(Color.RED);
            holder.isActive.setText("Nieaktywny");
        }

        holder.holderButton.setOnClickListener(v -> {
            selectedPosition = position;
            dataPositionListener.setPosition(position);
            dataPositionListener.setSelectedItemId(injectionOther.getId());
            notifyDataSetChanged();
                Intent intent = new Intent(context, InjectionsOtherActivity.class);
                context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(injectionOtherList == null) return 0;
        else return injectionOtherList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout holderButton;
        private TextView medicine;
        private TextView date;
        private TextView nextDate;
        private TextView isActive;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.holderButton = itemView.findViewById(R.id.injection_item_chipItemButton);
            this.medicine = itemView.findViewById(R.id.injection_item_injectionMedicine);
            this.date = itemView.findViewById(R.id.injection_item_injectionDate);
            this.nextDate = itemView.findViewById(R.id.injection_item_injectionExpDate);
            this.isActive = itemView.findViewById(R.id.data_item_isActiveInfoItem);
        }

    }

}

