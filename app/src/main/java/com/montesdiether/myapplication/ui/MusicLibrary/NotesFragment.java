package com.montesdiether.myapplication.ui.MusicLibrary;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.montesdiether.myapplication.R;
import com.montesdiether.myapplication.data.DatabaseHelper;
import com.montesdiether.myapplication.data.musicwishlist;

import java.util.List;


public class NotesFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MusicAdapter notesAdapter = null;


    private String mParam1;
    private String mParam2;

    List<musicwishlist> data;

    public NotesFragment() {

    }


    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
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
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.rvNotes);
        DatabaseHelper db = new DatabaseHelper(root.getContext());
        data = db.getAllNotes();
        notesAdapter = new MusicAdapter(
                data,
                new MusicAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(musicwishlist item) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("musicID", item.musicID);
                        Navigation.findNavController(root).navigate(R.id.nav_edit_note, bundle);
                    }
                },
                new MusicAdapter.OnDeleteClickListener() {
                    @Override
                    public void onItemClick(musicwishlist item, int position) {
                        db.deleteNote(item.musicID);
                        data.remove(position);
                        notesAdapter.notifyDataSetChanged();
                        Snackbar.make(root, item.musicName + " has been deleted.", Snackbar.LENGTH_LONG).show();
                    }
                },
                root.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(notesAdapter);

        FloatingActionButton fabAddNote = root.findViewById(R.id.fabAddNote);
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.nav_add_note);
            }
        });

        //SEARCH
        EditText searchBar = root.findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() == 0){

                    data.clear();
                    data.addAll(db.getAllNotes());
                    notesAdapter.notifyDataSetChanged();
                }else{

                    String input = s.toString();

                    data.clear();
                    data.addAll(db.getNotesWithMatches(input));
                    notesAdapter.notifyDataSetChanged();

                }
            }


        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}