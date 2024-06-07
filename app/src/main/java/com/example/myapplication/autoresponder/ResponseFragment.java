package com.example.myapplication.autoresponder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class ResponseFragment extends Fragment {

    private ListView responsesListView;  // afficher les réponses enregistrées
    private EditText newResponseEditText;  // ajouter une nouvelle réponse
    private Button addResponseButton;  // Bouton pour ajouter une nouvelle réponse
    private RadioGroup responseTypeGroup;  // boutons pour choisir le type de réponse
    private SharedPreferences sharedPreferences;  // Stockage des préférences partagées
    private ArrayAdapter<String> responsesAdapter;  // Adaptateur pour la ListView
    private Set<String> responsesSet;  // Ensemble de réponses enregistrées

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_responses, container, false);

        // Initialisation des composants
        responsesListView = view.findViewById(R.id.responsesListView);
        newResponseEditText = view.findViewById(R.id.newResponseEditText);
        addResponseButton = view.findViewById(R.id.addResponseButton);
        responseTypeGroup = view.findViewById(R.id.responseTypeGroup);
        sharedPreferences = getContext().getSharedPreferences("AutoResponseSMS", getContext().MODE_PRIVATE);

        // Chargement des réponses enregistrées
        loadResponses();

        // Ajout d'un écouteur sur le bouton d'ajout de réponse
        addResponseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newResponse = newResponseEditText.getText().toString();
                if (!newResponse.isEmpty()) {
                    responsesSet.add(newResponse);
                    saveResponses();
                    responsesAdapter.add(newResponse);
                    newResponseEditText.setText("");
                }
            }
        });

        return view;
    }


     //Charge les réponses enregistrées depuis les préférences partagées.

    private void loadResponses() {
        responsesSet = sharedPreferences.getStringSet("responses", new HashSet<>());
        responsesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_single_choice, new ArrayList<>(responsesSet));
        responsesListView.setAdapter(responsesAdapter);
    }


     // Sauvegarde les réponses dans les préférences partagées.

    private void saveResponses() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("responses", responsesSet);
        editor.apply();
    }
}
