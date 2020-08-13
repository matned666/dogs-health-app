package pl.design.mrn.matned.dogmanagementapp.activity.adapters.health;

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
import pl.design.mrn.matned.dogmanagementapp.activity.health.TeethControlActivity;
import pl.design.mrn.matned.dogmanagementapp.dataBase.health.TeethControl;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;


public class TeethControlAdapter extends RecyclerView.Adapter<TeethControlAdapter.ViewHolder>  {

    private List<TeethControl> teethControlList;
    private DataPositionListener dataPositionListener;
    private int selectedPosition;
    private String usage;
    private Context context;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public TeethControlAdapter(List<TeethControl> teethControlList, Context context) {
        this.context = context;
        this.usage = usage;
        this.teethControlList = teethControlList;
        this.dataPositionListener = DataPositionListener.getInstance();
        this.selectedPosition = dataPositionListener.getPosition();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_teeth, null);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeethControl teethControl = teethControlList.get(position);
        holder.dateOtControl.setText(dateFormat.format(teethControl.getDateOfControl()));
        if(selectedPosition == position) holder.holderButton.setBackgroundResource(R.drawable.roundcornersrecyclerviewelementselected);
        else holder.holderButton.setBackgroundResource(R.drawable.roundcornersrecyclerviewelement);

        holder.holderButton.setOnClickListener(v -> {
            selectedPosition = position;
            dataPositionListener.setPosition(position);
            dataPositionListener.setSelectedItemId(teethControl.getId());
            notifyDataSetChanged();
                Intent intent = new Intent(context, TeethControlActivity.class);
                context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        if(teethControlList == null) return 0;
        else return teethControlList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout holderButton;
        private TextView dateOtControl;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.holderButton = itemView.findViewById(R.id.teeth_control_item_button);
            this.dateOtControl = itemView.findViewById(R.id.teeth_control_item_date);
        }


    }

}

