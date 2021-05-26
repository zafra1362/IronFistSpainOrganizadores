package com.renegade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renegade.databinding.FragmentFinalizarRetoBinding;


public class FinalizarRetoFragment extends BaseDialogFragment {

    private FragmentFinalizarRetoBinding binding;

    int resultadoLocal;
    int resultadoVisitante;
    int ganador, perdedor;
    int difP;
    String uidLocal, uidVisitante;

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

            resultadoLocal = Integer.parseInt(binding.resultadoLocal.getText().toString());
            resultadoVisitante = Integer.parseInt(binding.resultadoVisitante.getText().toString());

            if (resultadoLocal > resultadoVisitante) {
                ganador = Math.toIntExact(viewModel.puntuacionRival1LiveData.getValue());
                perdedor = Math.toIntExact(viewModel.puntuacionRival2LiveData.getValue());
            } else if (resultadoLocal < resultadoVisitante) {
                ganador = Math.toIntExact(viewModel.puntuacionRival2LiveData.getValue());
                perdedor = Math.toIntExact(viewModel.puntuacionRival1LiveData.getValue());
            } else {
                binding.resultadoLocal.setError("");
                binding.resultadoVisitante.setError("Los resultados no pueden ser iguales");
                return;
            }

            TekkenFT calcularELO = new TekkenFT();
            difP = (int) calcularELO.start(ganador, perdedor, Integer.parseInt(binding.firstTo.getText().toString()));

            db.collection(CollectionDB.ENCUENTROS).document(viewModel.idEncuentroLiveData.getValue()).get().addOnSuccessListener(doc -> {
                        uidLocal = doc.getString("uidLocal");
                        uidVisitante = doc.getString("uidVisitante");

                        if (resultadoLocal > resultadoVisitante) {
                            ganador = Math.round(ganador + difP);
                            perdedor = Math.round(perdedor - difP);

                            db.collection(CollectionDB.USUARIOS).document(uidLocal).update("puntuacion", ganador);
                            db.collection(CollectionDB.USUARIOS).document(uidVisitante).update("puntuacion", perdedor);
                        } else {
                            perdedor = Math.round(perdedor - difP);
                            ganador = Math.round(ganador + difP);

                            db.collection(CollectionDB.USUARIOS).document(uidLocal).update("puntuacion", perdedor);
                            db.collection(CollectionDB.USUARIOS).document(uidVisitante).update("puntuacion", ganador);
                        }

                    });

            db.collection(CollectionDB.ENCUENTROS)
                    .document(viewModel.idEncuentroLiveData.getValue())
                    .update("resultadoLocal", Integer.parseInt(binding.resultadoLocal.getText().toString()),
                            "resultadoVisitante", Integer.parseInt(binding.resultadoVisitante.getText().toString()),
//                            "enlace", binding.enlaceReto.getText().toString(),
                            "estado", "Finalizado",
                            "firstTo", Integer.parseInt(binding.firstTo.getText().toString())
                    );

            nav.popBackStack();
        });

    }
}