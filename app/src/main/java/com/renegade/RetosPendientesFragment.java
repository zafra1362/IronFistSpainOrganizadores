package com.renegade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.renegade.databinding.FragmentRetosPendientesBinding;
import com.renegade.databinding.ViewholderNotificacionBinding;
import com.renegade.databinding.ViewholderRetoPendienteBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RetosPendientesFragment extends BaseFragment {

    private FragmentRetosPendientesBinding binding;

    RetosAdapter retosAdapter = new RetosAdapter();

    List<Encuentro> retos = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRetosPendientesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager verticalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.retosPendientesRecycler.setLayoutManager(verticalLayoutManager);
        binding.retosPendientesRecycler.setAdapter(retosAdapter);

        db.collection(CollectionDB.ENCUENTROS)
                .whereEqualTo("organizador", user.getDisplayName())
//                .orderBy("fechaEncuentro")
                .addSnapshotListener((value, error) -> {
                    retos.clear();
                    for (QueryDocumentSnapshot noti : value) {
                        if (noti != null) {

                            String id = noti.getId();
                            String uidLocal = noti.getString("uidLocal");
                            String uidVisitante = noti.getString("uidVisitante");
                            String rangoHoraMin = noti.getString("rangoHoraMin");
                            String rangoHoraMax = noti.getString("rangoHoraMax");
                            List<String> diasDisponibles = (List<String>) noti.get("diasSeleccionados");
                            String estado = noti.getString("estado");

                            retos.add(new Encuentro(estado, uidLocal, uidVisitante, diasDisponibles, rangoHoraMin, rangoHoraMax, id));
                            retosAdapter.notifyDataSetChanged();

                        }
                    }
                });


    }

    class RetosAdapter extends RecyclerView.Adapter<RetosViewHolder> {

        String nicknameRival1, nicknameRival2;
        Long puntuacionRival1, puntuacionRival2;

        @NonNull
        @Override
        public RetosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RetosViewHolder(ViewholderRetoPendienteBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RetosViewHolder holder, int position) {
            Encuentro encuentro = retos.get(position);

            db.collection(CollectionDB.USUARIOS)
                    .document(encuentro.uidLocal)
                    .get()
                    .addOnSuccessListener(doc -> {
                        nicknameRival1 = doc.getString("nickname");
                        puntuacionRival1 = doc.getLong("puntuacion");

                        holder.binding.nombreRival1RetoPendiente.setText(nicknameRival1);
                        db.collection(CollectionDB.USUARIOS)
                                .document(encuentro.uidVisitante)
                                .get()
                                .addOnSuccessListener(doc1 -> {
                                    nicknameRival2 = doc1.getString("nickname");
                                    puntuacionRival2 = doc1.getLong("puntuacion");

                                    holder.binding.nombreRival2RetoPendiente.setText(nicknameRival2);
                                });
                    });

            holder.binding.estadoRetoPendiente.setText(encuentro.estado);

            holder.itemView.setOnClickListener(v -> {

                db.collection(CollectionDB.USUARIOS).document(encuentro.uidLocal).get().addOnSuccessListener(documentSnapshot -> {
                    viewModel.puntuacionRival1LiveData.setValue(documentSnapshot.getLong("puntuacion"));
                });

                db.collection(CollectionDB.USUARIOS).document(encuentro.uidVisitante).get().addOnSuccessListener(documentSnapshot -> {
                    viewModel.puntuacionRival2LiveData.setValue(documentSnapshot.getLong("puntuacion"));
                });

                viewModel.estadoRetoLiveData.setValue(encuentro.estado);
                viewModel.idEncuentroLiveData.setValue(encuentro.id);

                viewModel.nombreRival1LiveData.setValue(holder.binding.nombreRival1RetoPendiente.getText().toString());
                viewModel.nombreRival2LiveData.setValue(holder.binding.nombreRival2RetoPendiente.getText().toString());


                viewModel.diasSeleccionadosLiveData.setValue(Arrays.toString(encuentro.diasSeleccionados.toArray()));
                viewModel.hora1lLiveData.setValue(encuentro.rangoHoraMin);
                viewModel.hora2LiveData.setValue(encuentro.rangoHoraMax);

                viewModel.horaEncuentro.setValue(encuentro.horaEncuentro);
                viewModel.fechaEncuentro.setValue(encuentro.fechaEncuentro);

                Log.e("ABCD", "nombre: " + viewModel.nombreRival1LiveData.getValue() + " nombne: " + viewModel.nombreRival2LiveData.getValue() + " nombne: " + viewModel.diasSeleccionadosLiveData.getValue() + " nombne: " + viewModel.hora1lLiveData.getValue());

                if (encuentro.estado.equals("En proceso")) nav.navigate(R.id.editarRetoFragment);
                else if (encuentro.estado.equals("Planificado"))nav.navigate(R.id.finalizarRetoFragment);
//                else nav.navigate(R.id.visualizarReto);
            });
        }

        @Override
        public int getItemCount() {
            return retos == null ? 10 : retos.size();
        }

    }

    static class RetosViewHolder extends RecyclerView.ViewHolder {
        ViewholderRetoPendienteBinding binding;
        public RetosViewHolder(@NonNull ViewholderRetoPendienteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
