package pl.design.mrn.matned.dogmanagementapp.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import pl.design.mrn.matned.dogmanagementapp.R;

public class MessagesListActivity extends AppCompatActivity {

    private RecyclerView messageList;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_activity);
        init();
    }

    private void init() {
        messageList = findViewById(R.id.recyclerViewDataList);
        backBtn = findViewById(R.id.cancelDataList);

    }

}
