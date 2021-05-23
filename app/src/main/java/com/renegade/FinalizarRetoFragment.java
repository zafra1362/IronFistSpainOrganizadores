package com.renegade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renegade.databinding.FragmentFinalizarRetoBinding;


public class FinalizarRetoFragment extends BaseDialogFragment {

    private FragmentFinalizarRetoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentFinalizarRetoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.nombreRival1RetoEdit.setText(viewModel.nombreRival1LiveData.getValue());
        binding.nombreRival2RetoEdit.setText(viewModel.nombreRival2LiveData.getValue());
        binding.puntuacionRival1RetoEdit.setText(""+viewModel.puntuacionRival1LiveData.getValue());
        binding.puntuacionRival2RetoEdit.setText(""+viewModel.puntuacionRival2LiveData.getValue());
        binding.horaEncuentro.setText(viewModel.horaEncuentro.getValue());
        binding.fechaEncuentro.setText(viewModel.fechaEncuentro.getValue());

        binding.botonFinalizarReto.setOnClickListener(v -> {
//            TekkenFT difP = new TekkenFT();
//            difP.calcularPuntos(Integer.parseInt(binding.puntuacionRival1RetoEdit.toString()), Integer.parseInt(binding.puntuacionRival2RetoEdit.toString()), Integer.parseInt(binding.firstTo.toString()));

            db.collection(CollectionDB.ENCUENTROS)
                    .document(viewModel.idEncuentroLiveData.getValue())
                    .update("resultadoLocal", Integer.parseInt(binding.resultadoLocal.getText().toString()),
                            "resultadoVisitante", Integer.parseInt(binding.resultadoVisitante.getText().toString()),
                            "enlace", binding.enlaceReto.getText().toString(),
                            "estado", "Finalizado",
                            "firstTo", Integer.parseInt(binding.firstTo.getText().toString()));


            nav.popBackStack();
        });

    }
}