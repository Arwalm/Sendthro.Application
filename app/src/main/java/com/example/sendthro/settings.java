package com.example.sendthro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class settings extends Fragment {

    FirebaseFirestore firestore;
    TextView emailedit, phoneedit , useredit;
    ImageView signout;
    Button chngpass , signin;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String userId;

//    private Switch darkmode;
public settings(){
    //Required empty Constructor
}
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sttings, container, false);
        firestore = FirebaseFirestore.getInstance();
        emailedit = view.findViewById(R.id.emailedit);
        phoneedit = view.findViewById(R.id.phoneedit);
        useredit = view.findViewById(R.id.useredit);
        chngpass = view.findViewById(R.id.chngpass);
        signin = view.findViewById(R.id.signin);
        signin.setVisibility(View.INVISIBLE);
        signout = view.findViewById(R.id.signout);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
//        darkmode= view.findViewById(R.id.darkmod);
//
//
//        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
//            getActivity().setTheme(R.style.darktheme);
//        }
//        else getActivity().setTheme(R.style.AppTheme);
//
//
//        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
//            darkmode.setChecked(true);
//        }
//
//        darkmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    restartApp();
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    restartApp();
//                }
//            }
//        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent signoutint = new Intent(getActivity(), MainActivity.class);
                startActivity(signoutint);
                getActivity().finish();
            }
        });

        if (firebaseAuth.getCurrentUser() != null) {
            userId = firebaseAuth.getCurrentUser().getUid();

            DocumentReference documentReference = fStore.collection("users").document(userId);
            documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    phoneedit.setText(documentSnapshot.getString("Phone Number"));
                    useredit.setText(documentSnapshot.getString("Username"));
                    emailedit.setText(documentSnapshot.getString("Email Address"));
                }
            });

        }

            if (firebaseAuth.getCurrentUser() == null) {
                chngpass.setVisibility(View.INVISIBLE);
                signin.setVisibility(View.VISIBLE);
                signout.setVisibility(View.INVISIBLE);
            }

            chngpass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), ChangePassword.class);
                    startActivity(i);
                }
            });

            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });
            return view;
        }

//        public void restartApp(){
//                Intent restart = new Intent(getActivity(), settings.class);
//                startActivity(restart);
//                getActivity().finish();
//        }

}