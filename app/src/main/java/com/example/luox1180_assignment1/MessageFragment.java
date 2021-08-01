package com.example.luox1180_assignment1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MessageFragment extends Fragment {

    TextView messageView,idView;
    View inf;
    String msg;
    int ID;
    Button btnDelete;
    ChatWindow window;

    public MessageFragment(){}

    public MessageFragment(ChatWindow c){
        window = c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle b = this.getArguments();
        msg = b.getString("message");
        ID = b.getInt("id");
        inf = inflater.inflate(R.layout.message_fragment, container, false);
        findComponents();
        messageView.setText(msg);
        idView.setText(String.valueOf(ID));
        return inf;
    }

    void findComponents(){
        messageView = inf.findViewById(R.id.txtFragmentMsg);
        idView = inf.findViewById(R.id.txtFragmentID);
        btnDelete = inf.findViewById(R.id.FragnmentDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.DeleteMsg(Integer.parseInt(idView.getText().toString()));
            }
        });
    }

}
