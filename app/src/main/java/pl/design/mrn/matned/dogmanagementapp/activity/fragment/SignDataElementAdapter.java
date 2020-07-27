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
import pl.design.mrn.matned.dogmanagementapp.dataBase.dog.additionalData.SpecialSign;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT;


public class SignDataElementAdapter extends RecyclerView.Adapter<SignDataElementAdapter.ViewHolder>  {

    private List<SpecialSign> signList;
    private DataPositionListener dataPositionListener;
    private int selectedPosition;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public SignDataElementAdapter(List<SpecialSign> signs) {
        this.signList = signs;
        this.dataPositionListener = DataPositionListener.getInstance();
        this.selectedPosition = dataPositionListener.getPosition();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_sign_info, null);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpecialSign sign = signList.get(position);
        holder.signDesc.setText(generateHeader(sign.getDescription()));
        holder.date.setText(dateFormat.format(sign.getDate()));
        if(selectedPosition == position) holder.holderButton.setBackgroundResource(R.drawable.roundcornersrecyclerviewelementselected);
        else holder.holderButton.setBackgroundResource(R.drawable.roundcornersrecyclerviewelement);

        holder.holderButton.setOnClickListener(v -> {
            selectedPosition = position;
            dataPositionListener.setPosition(position);
            dataPositionListener.setSelectedItemId(sign.getSignId());
            notifyDataSetChanged();
        });
    }

    private String generateHeader(String description) {
        return description.substring(0, 25).trim() + "...";
    }


    @Override
    public int getItemCount() {
        if(signList == null) return 0;
        else return signList.size();
    }




    static class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout holderButton;
        private TextView signDesc;
        private TextView date;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.holderButton = itemView.findViewById(R.id.signItem_Button);
            this.signDesc = itemView.findViewById(R.id.signItem_sign_name);
            this.date = itemView.findViewById(R.id.signItem_dateToSign);
        }


    }

}
