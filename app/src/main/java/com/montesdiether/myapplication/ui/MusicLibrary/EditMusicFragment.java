package com.montesdiether.myapplication.ui.MusicLibrary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.montesdiether.myapplication.R;
import com.montesdiether.myapplication.data.DatabaseHelper;
import com.montesdiether.myapplication.data.musicwishlist;


public class EditMusicFragment extends Fragment {



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public EditMusicFragment() {

    }



    public static EditMusicFragment newInstance(String param1, String param2) {
        EditMusicFragment fragment = new EditMusicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_edit_note, container, false);

        EditText tvTitle = root.findViewById(R.id.musicTitle);
        EditText tvDesc = root.findViewById(R.id.tvDesc);
        Button btnUpdate = root.findViewById(R.id.btnUpdate);
        Button btnCancel = root.findViewById(R.id.btnCancel);

        DatabaseHelper db = new DatabaseHelper(root.getContext());

        musicwishlist note = db.getNote(getArguments().getInt("musicID"));

        if (note != null && note.musicName != null) {

            String musicName = note.musicName;
            String musicAuthor = note.musicAuthor;

            tvTitle.setText(musicName);
            tvDesc.setText(musicAuthor);

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tvTitle.getText().toString().isEmpty() || tvDesc.getText().toString().isEmpty()){
                        Snackbar.make(root, "Please enter note Music Name and/or Music Author", Snackbar.LENGTH_SHORT).show();
                    }else{
                        long result = db.updateNote(getArguments().getInt("musicID"), tvTitle.getText().toString(), tvDesc.getText().toString());
                        if(result < 0){
                            Snackbar.make(root, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                        }else{
                            Snackbar.make(root, "Your music has been updated.", Snackbar.LENGTH_SHORT).show();
                            Navigation.findNavController(root).navigateUp();
                        }

                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(root).navigateUp();
                }
            });
        } else {

            Log.i("ROROORR","WAWAWA");
        }

        tvTitle.setText(note.musicName);
        tvDesc.setText(note.musicAuthor);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvTitle.getText().toString().isEmpty() || tvDesc.getText().toString().isEmpty()){
                    Snackbar.make(root, "Please enter note Music Name and/or Music Author", Snackbar.LENGTH_SHORT).show();
                }else{
                    long result = db.updateNote(getArguments().getInt("musicID"), tvTitle.getText().toString(), tvDesc.getText().toString());
                    if(result < 0){
                        Snackbar.make(root, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                    }else{
                        Snackbar.make(root, "Your music has been updated.", Snackbar.LENGTH_SHORT).show();
                        Navigation.findNavController(root).navigateUp();
                    }

                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigateUp();
            }
        });

        return root;
    }
}