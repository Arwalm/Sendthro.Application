package com.example.sendthro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class archivedmsg extends Fragment {
    private FirebaseAuth mAuth;
    ListView archivedListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_archivedmsg, container, false);

        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton4);
        FloatingActionButton smsfab = (FloatingActionButton) view.findViewById(R.id.smsfab);
        FloatingActionButton wtsfab = (FloatingActionButton) view.findViewById(R.id.wtsfab);
        FloatingActionButton emailfab = (FloatingActionButton) view.findViewById(R.id.emailfab);

        final RelativeLayout wtslayout = (RelativeLayout) view.findViewById(R.id.wtslayout);
        final RelativeLayout smslayout = (RelativeLayout) view.findViewById(R.id.smslayout);
        final RelativeLayout emaillayout = (RelativeLayout) view.findViewById(R.id.emaillayout);

        smslayout.setVisibility(View.INVISIBLE);
        emaillayout.setVisibility(View.INVISIBLE);
        wtslayout.setVisibility(View.INVISIBLE);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wtslayout.getVisibility() == View.VISIBLE && smslayout.getVisibility() == View.VISIBLE
                        && emaillayout.getVisibility() == View.VISIBLE) {

                    wtslayout.setVisibility(View.GONE);
                    smslayout.setVisibility(View.GONE);
                    emaillayout.setVisibility(View.GONE);

                } else {
                    wtslayout.setVisibility(View.VISIBLE);
                    smslayout.setVisibility(View.VISIBLE);
                    emaillayout.setVisibility(View.VISIBLE);
                }
            }
        });


        smsfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() == null) {
                    Intent NewUSer = new Intent(getActivity(), MainActivity.class);
                    startActivity(NewUSer);
                } else {
                    Intent Do = new Intent(getActivity(), CreateSmsScheduleActivity.class);
                    startActivity(Do);
                }
            }
        });


        wtsfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() == null) {
                    Intent NewUSer = new Intent(getActivity(), MainActivity.class);
                    startActivity(NewUSer);
                } else {
                    Intent Do = new Intent(getActivity(), Newmessage.class);
                    startActivity(Do);
                }
            }
        });


        emailfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() == null) {
                    Intent NewUSer = new Intent(getActivity(), MainActivity.class);
                    startActivity(NewUSer);
                } else {
                    Intent Do = new Intent(getActivity(), Newmessage.class);
                    startActivity(Do);
                }
            }
        });


//        Bundle bundle = getActivity().getIntent().getExtras();
//        String message1 = bundle.getString("msg");;
//        String number = bundle.getString("num");
//
//        archivedListView = view.findViewById(R.id.archivedListView);
//        MyAdapter adapter = new MyAdapter(this, message1, number);
//        archivedListView.setAdapter(adapter);
//
//        return view;
//    }
//
//    class MyAdapter extends ArrayAdapter<String> {
//
//        Context context;
//        String[] rMsg;
//        String[] rNum;
////        ListView[] rDate;
////        ListView[] rTime;
//
//        MyAdapter(archivedmsg c, String msgs, String nums) {
//            super(c, R.layout.archive_row, R.id.textViewMessage, message1);
//            this.context = c;
//            this.rMsg = msgs;
//            this.rNum = nums;
////            this.rDate = listViews;
////            this.rTime = listViews;
//        }
//
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            LayoutInflater layoutInflater = (LayoutInflater) getActivity()
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            View row = layoutInflater.inflate(R.layout.archive_row, parent, false);
//
//            TextView mMsg = row.findViewById(R.id.textViewMessage);
//            TextView mNum = row.findViewById(R.id.textViewNumber);
////            TextView mDate = row.findViewById(R.id.textViewTime);
////            TextView mTime = row.findViewById(R.id.textViewDate);
//
//            mMsg.setText(rMsg[position]);
//            mNum.setText(rNum[position]);
//
//            return row;
//        }
        return view;
    }
}
