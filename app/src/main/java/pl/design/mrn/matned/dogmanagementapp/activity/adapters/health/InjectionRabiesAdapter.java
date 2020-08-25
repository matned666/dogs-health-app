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
import pl.design.mrn.matned.dogmanagementapp.activity.health.InjectionsRabidActivity;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.InjectionRabid;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;

public class InjectionRabiesAdapter extends RecyclerView.Adapter<InjectionRabiesAdapter.ViewHolder>  {

    private List<InjectionRabid> injectionRabidList;
    private DataPositionListener dataPositionListener;
    private int selectedPosition;
    private Context context;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public InjectionRabiesAdapter(List<InjectionRabid> injectionRabidList, Context context) {
        this.context = context;
        this.injectionRabidList = injectionRabidList;
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
        InjectionRabid injectionRabid = injectionRabidList.get(position);
        holder.medicine.setText(injectionRabid.getMedicine());
        holder.date.setText(dateFormat.format(injectionRabid.getTreatmentDate()));
        holder.nextDate.setText(dateFormat.format(injectionRabid.getNextTreatment()));
        if (injectionRabid.isActive()) {
            holder.isActive.setTextColor(Color.GREEN);
            holder.isActive.setText("Aktywny");
        }else{
            holder.isActive.setTextColor(Color.RED);
            holder.isActive.setText("Nieaktywny");
        }

        holder.holderButton.setOnClickListener(v -> {
            selectedPosition = position;
            dataPositionListener.setPosition(position);
            dataPositionListener.setSelectedItemId(injectionRabid.getId());
            notifyDataSetChanged();
                Intent intent = new Intent(context, InjectionsRabidActivity.class);
                context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(injectionRabidList == null) return 0;
        else return injectionRabidList.size();
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

