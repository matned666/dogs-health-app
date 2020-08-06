package pl.design.mrn.matned.dogmanagementapp.activity.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.Message;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.MessageStatus;
import pl.design.mrn.matned.dogmanagementapp.listeners.DataPositionListener;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT_MESSAGE;


public class MessageElementAdapter extends RecyclerView.Adapter<MessageElementAdapter.ViewHolder>  {

    private List<Message> messages;
    private DataPositionListener dataPositionListener;
    private int selectedPosition;
    private String usage;
    private Context context;

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_MESSAGE);

    public MessageElementAdapter(List<Message> messages, String usage, Context context) {
        this.context = context;
        this.usage = usage;
        this.messages = messages;
        this.dataPositionListener = DataPositionListener.getInstance();
        this.selectedPosition = dataPositionListener.getPosition();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_message, null);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.messageTitle.setText(message.getSubject().name());
        holder.message.setText(message.getMessage());
        holder.message.setText(dateFormat.format(message.getMessageDateTime()));
        if (message.getStatus() == MessageStatus.READ) {
            holder.messageIcon.setCompoundDrawablesRelative(Drawable.createFromPath("@drawable/ic_message"),null,null,null);
        }else{
            holder.messageIcon.setCompoundDrawablesRelative(Drawable.createFromPath("@drawable/ic_message_incoming"),null,null,null);
        }

        holder.holderButton.setOnClickListener(v -> {
            message.setStatus(MessageStatus.READ);
            Toast.makeText(context,"Jeszcze nie działa", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, ChipActivityEdit.class);
//                context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        if(messages == null) return 0;
        else return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout holderButton;
        private TextView messageTitle;
        private TextView message;
        private TextView messageDateTime;
        private TextView messageIcon;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.holderButton = itemView.findViewById(R.id.messageButton);
            this.messageTitle = itemView.findViewById(R.id.messageTitleTextView);
            this.message = itemView.findViewById(R.id.messageTextView);
            this.messageDateTime = itemView.findViewById(R.id.messageDateTime);
            this.messageIcon = itemView.findViewById(R.id.messageIcon);
        }


    }

}

