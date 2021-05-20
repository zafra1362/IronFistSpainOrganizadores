package com.renegade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.UserProfileChangeRequest;
import com.renegade.databinding.FragmentRegistroBinding;

import java.util.UUID;


public class RegistroFragment extends BaseFragment {

    private FragmentRegistroBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRegistroBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.completarRegistro.setOnClickListener(v -> {


            String email = binding.correo.getText().toString();
            String password = binding.password.getText().toString();
            String name = binding.nombreOrganizacion.getText().toString();
            String enlace = binding.canalOrganizacion.getText().toString();


            boolean valid = true;

            if (email.isEmpty()) {
                binding.correo.setError("Required");
                valid = false;
            }
            if (password.isEmpty()) {
                binding.password.setError("Required");
                valid = false;
            }
            if (name.isEmpty()) {
                binding.nombreOrganizacion.setError("Required");
                valid = false;
            }
            if (enlace.isEmpty()) {
                binding.canalOrganizacion.setError("Required");
                valid = false;
            }


            if (valid) {
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                db.collection(CollectionDB.USUARIOS).document(email).set(new Organizador(name, enlace));

                                auth.getCurrentUser().updateProfile(
                                                new UserProfileChangeRequest.Builder()
                                                        .setDisplayName(name)
                                                        .build()
                                );

                                nav.navigate(R.id.notificacionesFragment);
                            } else {
                                Toast.makeText(requireContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

}