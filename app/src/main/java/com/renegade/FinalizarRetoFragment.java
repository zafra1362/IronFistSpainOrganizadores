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
                ganador = Integer.parseInt(binding.puntuacionRival1RetoEdit.getText().toString());
                perdedor = Integer.parseInt(binding.puntuacionRival2RetoEdit.getText().toString());
            } else if (resultadoLocal < resultadoVisitante) {
                ganador = Integer.parseInt(binding.puntuacionRival2RetoEdit.getText().toString());
                perdedor = Integer.parseInt(binding.puntuacionRival1RetoEdit.getText().toString());
            } else {
                binding.resultadoLocal.setError("");
                binding.resultadoVisitante.setError("Los resultados no pueden ser iguales");
            }

            TekkenFT calcularELO = new TekkenFT();
            difP = (int) calcularELO.start(ganador, perdedor, Integer.parseInt(binding.firstTo.getText().toString()));

            db.collection(CollectionDB.ENCUENTROS).document(viewModel.idEncuentroLiveData.getValue()).get().addOnSuccessListener(doc -> {
                        uidLocal = doc.getString("uidLocal");
                        uidVisitante = doc.getString("uidVisitante");
                        // buscar la los uid del local y visitante

                        if (resultadoLocal > resultadoVisitante) {
                            resultadoLocal = Math.round(resultadoLocal + difP);
                            resultadoVisitante = Math.round(resultadoVisitante - difP);

                            db.collection(CollectionDB.USUARIOS).document(uidLocal).update("puntuacion", resultadoLocal);
                            db.collection(CollectionDB.USUARIOS).document(uidVisitante).update("puntuacion", resultadoVisitante);
                        } else {
                            resultadoLocal = Math.round(resultadoLocal - difP);
                            resultadoVisitante = Math.round(resultadoVisitante + difP);

                            db.collection(CollectionDB.USUARIOS).document(uidLocal).update("puntuacion", resultadoLocal);
                            db.collection(CollectionDB.USUARIOS).document(uidVisitante).update("puntuacion", resultadoVisitante);
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