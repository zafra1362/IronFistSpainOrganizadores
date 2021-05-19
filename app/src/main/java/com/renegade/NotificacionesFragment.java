package com.renegade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.renegade.databinding.FragmentNotificacionesBinding;
import com.renegade.databinding.ViewholderNotificacionBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NotificacionesFragment extends BaseFragment {

    private FragmentNotificacionesBinding binding;

    List<Encuentro> notificaciones = new ArrayList<>();
    NotificacionesAdapter notificacionesAdapter = new NotificacionesAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentNotificacionesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager verticalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.notificacionesRecycler.setLayoutManager(verticalLayoutManager);
        binding.notificacionesRecycler.setAdapter(notificacionesAdapter);

        db.collection(CollectionDB.ENCUENTROS)
                .whereEqualTo("estado", "Aceptado")
                .addSnapshotListener((value, error) -> {
                    notificaciones.clear();
                    for (QueryDocumentSnapshot noti : value) {
                        if (noti != null) {

                            String id = noti.getId();
                            String uidLocal = noti.getString("uidLocal");
                            String uidVisitante = noti.getString("uidVisitante");
                            String rangoHoraMin = noti.getString("rangoHoraMin");
                            String rangoHoraMax = noti.getString("rangoHoraMax");
                            List<String> diasDisponibles = (List<String>) noti.get("diasSeleccionados");

                            db.collection(CollectionDB.USUARIOS)
                                    .document(uidLocal)
                                    .get()
                                    .addOnSuccessListener(doc -> {
                                        String nicknameRival1 = doc.getString("nickname");


                                        db.collection(CollectionDB.USUARIOS)
                                                .document(uidVisitante)
                                                .get()
                                                .addOnSuccessListener(doc1 -> {
                                                    String nicknameRival2 = doc1.getString("nickname");

                                                    Log.e("ABCD", "Nickname Rival1: " + nicknameRival1  + "Nickname Rival2: " + nicknameRival2  + " - Hora minima: " + rangoHoraMin + " - Hora maxima: " + rangoHoraMax + " - Dias Seleccionados: " + diasDisponibles);

                                                    notificaciones.add(new Encuentro(nicknameRival1, nicknameRival2, rangoHoraMin, rangoHoraMax, diasDisponibles, uidLocal, uidVisitante, id));
                                                    notificacionesAdapter.notifyDataSetChanged();
                                                });

                                    });


                        }
                    }
                });


    }

    class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionViewHolder> {

        @NonNull
        @Override
        public NotificacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NotificacionesFragment.NotificacionViewHolder(ViewholderNotificacionBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull NotificacionViewHolder holder, int position) {
            Encuentro encuentro = notificaciones.get(position);

            holder.binding.nombreRival1Notificacion.setText(encuentro.nicknameRival1);
            holder.binding.nombreRival2Notificacion.setText(encuentro.nicknameRival2);
            holder.binding.rangoHora1.setText(encuentro.rangoHoraMin);
            holder.binding.rangoHora2.setText(encuentro.rangoHoraMax);
            holder.binding.diasDisponibles.setText(Arrays.toString(encuentro.diasDisponibles.toArray()));

            holder.itemView.setOnClickListener(v -> {

                viewModel.idEncuentroLiveData.setValue(encuentro.id);
                viewModel.nombreRival1LiveData.setValue(encuentro.nicknameRival1);
                viewModel.nombreRival2LiveData.setValue(encuentro.rangoHoraMin);
                viewModel.hora1lLiveData.setValue(encuentro.rangoHoraMin);
                viewModel.hora2LiveData.setValue(encuentro.rangoHoraMax);
                viewModel.diasSeleccionadosLiveData.setValue(Arrays.toString(encuentro.diasDisponibles.toArray()));
                viewModel.uidRival1LiveData.setValue(encuentro.uidRival1);

                Log.e("ABCD", "nombre: " + viewModel.nombreRival1LiveData.getValue() + " nombne: " + viewModel.nombreRival2LiveData.getValue() + " nombne: " + viewModel.diasSeleccionadosLiveData.getValue() + " nombne: " + viewModel.hora1lLiveData.getValue());
                nav.navigate(R.id.action_notificacionesFragment_to_editarRetoFragment);
            });
        }

        @Override
        public int getItemCount() {
            return notificaciones == null ? 10 : notificaciones.size();
        }

    }

    static class NotificacionViewHolder extends RecyclerView.ViewHolder {
        ViewholderNotificacionBinding binding;
        public NotificacionViewHolder(@NonNull ViewholderNotificacionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
