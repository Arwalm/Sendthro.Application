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
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent signoutint = new Intent(getActivity(), WelcomeActivity.class);
                startActivity(signoutint);
                getActivity().finish();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(firebaseAuth.getCurrentUser() != null ) {
            userId = firebaseAuth.getCurrentUser().getUid();

            DocumentReference documentReference = fStore.collection("users").document(userId);
            documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                    phoneedit.setText(documentSnapshot.getString("Phone Number"));
                    useredit.setText(documentSnapshot.getString("Username"));
                    emailedit.setText(documentSnapshot.getString("Email Address"));
                }
            });
        }

        if(firebaseAuth.getCurrentUser() == null ) {
            chngpass.setVisibility(View.INVISIBLE);
            signin.setVisibility(View.VISIBLE);
        }

        chngpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), ChangePassword.class);
                startActivity(i);
                getActivity().finish();
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getActivity() , WelcomeActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }


}
