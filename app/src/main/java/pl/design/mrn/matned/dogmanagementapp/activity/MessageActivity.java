package pl.design.mrn.matned.dogmanagementapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.Message;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.MessageStatus;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.MessagesDao;

import static pl.design.mrn.matned.dogmanagementapp.Statics.DATE_FORMAT_MESSAGE;
import static pl.design.mrn.matned.dogmanagementapp.Statics.MESSAGE;

public class MessageActivity extends AppCompatActivity {

    Button backBtn;
    Button deleteBtn;
    Button setUnreadButton;
    TextView subjectTV;
    TextView messageTV;
    TextView dateTV;
    MessagesDao dao;
    Message message;

    private DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_MESSAGE);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        init();
        getCurrentMessage();
        initTextViews();
        initClickListeners();
    }

    private void init() {
        backBtn = findViewById(R.id.messageBackBtn);
        deleteBtn = findViewById(R.id.messageDeleteBtn);
        setUnreadButton = findViewById(R.id.messageSelectBtn);
        subjectTV = findViewById(R.id.messageSubject);
        messageTV = findViewById(R.id.messageMessage);
        dateTV = findViewById(R.id.messageDate);
        dao = new MessagesDao(this);

    }

    private void getCurrentMessage() {
        message = dao.findById(getIntent().getIntExtra(MESSAGE, 0));
    }

    private void initTextViews() {
        subjectTV.setText(message.getSubject().name());
        messageTV.setText(message.getMessage());
        dateTV.setText(dateFormat.format(message.getMessageDateTime()));
    }

    private void initClickListeners() {
        backBtn.setOnClickListener(v -> finish());
        deleteBtn.setOnClickListener(deleteClickListener());
        setUnreadButton.setOnClickListener(setUnread());
    }

    private View.OnClickListener setUnread() {
        return v -> {
            message.setStatus(MessageStatus.NOT_READ);
            dao.update(message);
        };
    }

    private View.OnClickListener deleteClickListener() {
        return v -> {
            dao.remove(message);
            finish();
        };
    }
}
