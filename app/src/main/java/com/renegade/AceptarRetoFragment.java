package com.renegade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renegade.databinding.FragmentAceptarRetoBinding;


public class AceptarRetoFragment extends BaseDialogFragment {

    private FragmentAceptarRetoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentAceptarRetoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.nombreRival1RetoEdit.setText(viewModel.nombreRival1LiveData.getValue());
        binding.nombreRival2RetoEdit.setText(viewModel.nombreRival2LiveData.getValue());
        binding.puntuacionRival1RetoEdit.setText(""+viewModel.puntuacionRival1LiveData.getValue());
        binding.puntuacionRival2RetoEdit.setText(""+viewModel.puntuacionRival2LiveData.getValue());
        binding.rangoHora1.setText(viewModel.hora1lLiveData.getValue());
        binding.rangoHora2.setText(viewModel.hora2LiveData.getValue());
        binding.diasDisponibles.setText(viewModel.diasSeleccionadosLiveData.getValue());

        binding.botonPlanearReto.setOnClickListener(v -> {
            db.collection(CollectionDB.ENCUENTROS)
                    .document(viewModel.idEncuentroLiveData.getValue())
                    .update("estado", "En proceso", "organizador", user.getDisplayName());

            nav.navigate(R.id.retosPendientesFragment);
        });
    }
}