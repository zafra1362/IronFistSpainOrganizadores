package com.renegade;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renegade.databinding.FragmentEditarRetoBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class EditarRetoFragment extends BaseDialogFragment {

    private FragmentEditarRetoBinding binding;
    Date dateHoraEncuentro, dateFechaEncuentro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentEditarRetoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.nombreRival1RetoEdit.setText(viewModel.nombreRival1LiveData.getValue());
        binding.nombreRival2RetoEdit.setText(viewModel.nombreRival2LiveData.getValue());
        binding.puntuacionRival1RetoEdit.setText(""+viewModel.puntuacionRival1LiveData.getValue());
        binding.puntuacionRival2RetoEdit.setText(""+viewModel.puntuacionRival2LiveData.getValue());
        binding.rangoHora1.setText(viewModel.hora1lLiveData.getValue());
        binding.rangoHora2.setText(viewModel.hora2LiveData.getValue());
        binding.diasDisponibles.setText(viewModel.diasSeleccionadosLiveData.getValue());

        binding.buttonHoraEncuentro.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getContext(),
                    (view2, hourOfDay, minute) -> {
                        String tiempo = hourOfDay + ":" + minute;
                        SimpleDateFormat f24horas = new SimpleDateFormat("HH:mm");
                        try {
                            dateHoraEncuentro = f24horas.parse(tiempo);
                            binding.buttonHoraEncuentro.setText(f24horas.format(dateHoraEncuentro));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    },12,0,true
            );
            timePickerDialog.show();
        });

        binding.buttonFechaEncuentro.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        String fecha = dayOfMonth + "/" + monthOfYear + "/" + year;
                        SimpleDateFormat fDiaMes = new SimpleDateFormat("dd/MM/yyyy");

                        try {
                            dateFechaEncuentro = fDiaMes.parse(fecha);
                            binding.buttonFechaEncuentro.setText(fDiaMes.format(dateFechaEncuentro));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        binding.botonPlanearReto.setOnClickListener(v -> {

            db.collection(CollectionDB.ENCUENTROS)
                    .document(viewModel.idEncuentroLiveData.getValue())
                    .update("estado", "Planeado",
                            "fechaEncuentro", Arrays.asList(binding.buttonHoraEncuentro.getText(), binding.buttonFechaEncuentro.getText()));

            nav.popBackStack();
        });
    }
}