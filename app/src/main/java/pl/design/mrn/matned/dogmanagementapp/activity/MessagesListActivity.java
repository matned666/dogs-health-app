package pl.design.mrn.matned.dogmanagementapp.activity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.design.mrn.matned.dogmanagementapp.R;
import pl.design.mrn.matned.dogmanagementapp.activity.adapters.MessageElementAdapter;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.Message;
import pl.design.mrn.matned.dogmanagementapp.dataBase.app.MessagesDao;

public class MessagesListActivity extends AppCompatActivity {

    private RecyclerView messageList;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.messages_activity);
        init();
        initRecycleView();
    }

    private void init() {
        messageList = findViewById(R.id.recyclerViewDataList);
        backBtn = findViewById(R.id.cancelDataList);
        backBtn.setOnClickListener(v -> finish());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initRecycleView() {
        RecyclerView.LayoutManager messageList_layoutManager = new LinearLayoutManager(this);
        messageList.setLayoutManager(messageList_layoutManager);
        messageList.setHasFixedSize(true);
        addNewDogList();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addNewDogList() {
        MessagesDao dao = new MessagesDao(this);
        List<Message> messages = dao.findAll();
        RecyclerView.Adapter<MessageElementAdapter.ViewHolder> dogAdapter = new MessageElementAdapter(this, messages);
        messageList.setAdapter(dogAdapter);
    }

}
