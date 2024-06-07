package com.example.myapplication.autoresponder;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.example.myapplication.R;


public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;  // Composant permettant le swipe entre les fragments
    private TabLayout tabLayout;   // Composant pour afficher les onglets

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des composants
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        // Configuration de l'adaptateur du ViewPager pour gérer les fragments
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle()));

        // Association du TabLayout avec le ViewPager pour synchroniser les onglets et les pages
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Contacts");  // onglet 1
                        break;
                    case 1:
                        tab.setText("Responses");  // onglet 2
                        break;
                    case 2:
                        tab.setText("Send");  // onglet 3
                        break;
                }
            }
        }).attach();
    }


    private static class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // Retourne le fragment approprié pour chaque position d'onglet
            switch (position) {
                case 0:
                    return new ContactFragment();  // Fragment pour les contacts
                case 1:
                    return new ResponseFragment();  // Fragment pour les réponses automatiques
                case 2:
                    return new SendFragment();  // Fragment pour envoyer des messages
            }
            return new Fragment();  // Un fragment vide par défaut
        }

        @Override
        public int getItemCount() {
            return 3;  // Nombre total d'onglets
        }
    }
}