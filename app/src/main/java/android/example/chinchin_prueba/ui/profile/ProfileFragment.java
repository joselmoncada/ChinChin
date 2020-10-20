package android.example.chinchin_prueba.ui.profile;

import android.example.chinchin_prueba.MainActivity;
import android.example.chinchin_prueba.ui.appUtils.SharedPreferenceUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.example.chinchin_prueba.R;

public class ProfileFragment extends Fragment {




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView name = root.findViewById(R.id.nameTextView);
        final TextView email = root.findViewById(R.id.emailTextView);
        final Button logoutButton = root.findViewById(R.id.logoutButton);
        name.setText(SharedPreferenceUtils.getUserNames());
        email.setText(SharedPreferenceUtils.getEmail());
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).logout();
            }
        });

        return root;
    }



}