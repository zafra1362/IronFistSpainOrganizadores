package com.renegade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.renegade.databinding.FragmentLoginBinding;


public class LoginFragment extends BaseFragment {

    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentLoginBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.registro.setOnClickListener(v -> nav.navigate(R.id.action_loginFragment_to_registroFragment));

        binding.login.setOnClickListener(v -> {
            String email = binding.editTextCorreo.getText().toString();
            String password = binding.editTextPassword.getText().toString();

            boolean valid = true;

            if (email.isEmpty()) {
                binding.editTextCorreo.setError("Obligatorio");
                valid = false;
            }

            if (password.isEmpty()) {
                binding.editTextPassword.setError("Obligatorio");
                valid = false;
            }

            if (valid) {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                nav.navigate(R.id.action_loginFragment_to_notificacionesFragment);
                            } else Toast.makeText(requireContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}

