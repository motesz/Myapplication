package com.montesdiether.myapplication.ui.MusicLibrary;

import android.os.Bundle;
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


public class AddNoteFragment extends Fragment {



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AddNoteFragment() {
        // Required empty public constructor
    }

    public static AddNoteFragment newInstance(String param1, String param2) {
        AddNoteFragment fragment = new AddNoteFragment();
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
        View root = inflater.inflate(R.layout.fragment_add_note, container, false);

        EditText tvTitle = root.findViewById(R.id.tvTitle);
        EditText tvDesc = root.findViewById(R.id.tvDesc);
        Button btnSave = root.findViewById(R.id.btnSave);
        Button btnCancel = root.findViewById(R.id.btnCancel);

        DatabaseHelper db = new DatabaseHelper(root.getContext());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvTitle.getText().toString().isEmpty() || tvDesc.getText().toString().isEmpty()){
                    Snackbar.make(root, "Please enter note title and/or description", Snackbar.LENGTH_SHORT).show();
                }else{
                    long result = db.addNote(tvTitle.getText().toString(), tvDesc.getText().toString());
                    if(result < 0){
                        Snackbar.make(root, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                    }else{
                        Snackbar.make(root, "Your new note has been saved.", Snackbar.LENGTH_SHORT).show();
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