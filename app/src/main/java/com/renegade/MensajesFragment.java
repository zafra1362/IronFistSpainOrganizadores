package com.renegade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.renegade.databinding.FragmentMensajesBinding;
import com.renegade.databinding.ViewholderMensajeBinding;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class MensajesFragment extends BaseFragment {

    private FragmentMensajesBinding binding;

    private List<Mensaje> chat = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMensajesBinding.inflate(inflater,container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ChatAdapter chatAdapter = new ChatAdapter();
        binding.chat.setAdapter(chatAdapter);

        binding.enviar.setOnClickListener(v -> {
            String email = auth.getCurrentUser().getEmail();
            String nombre = auth.getCurrentUser().getDisplayName();
//            String foto = auth.getCurrentUser().getPhotoUrl().toString();
            String mensaje = binding.mensaje.getText().toString();
            String fecha = LocalDateTime.now().toString();

            db.collection(CollectionDB.MENSAJES)
                    .add(new Mensaje(email, nombre, mensaje, fecha));

            binding.mensaje.setText("");
        });

        db.collection(CollectionDB.MENSAJES).orderBy("fecha").addSnapshotListener((value, error) -> {
            chat.clear();
            for (QueryDocumentSnapshot m: value){

                String email = m.getString("autorEmail");
                String nombre = m.getString("autorNombre");
                String foto = m.getString("autorFoto");
                String texto = m.getString("mensaje");
                String fecha = m.getString("fecha");

                Mensaje mensaje = new Mensaje(email, nombre, texto, fecha);
                chat.add(mensaje);
            }
            chatAdapter.notifyDataSetChanged();
            binding.chat.scrollToPosition(chat.size() - 1);

        });
    }

    class ChatAdapter extends RecyclerView.Adapter<MensajeViewHolder>{

        @NonNull
        @Override
        public MensajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MensajeViewHolder(ViewholderMensajeBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MensajeViewHolder holder, int position) {
            Mensaje mensaje = chat.get(position);

            if(mensaje.autorEmail != null && mensaje.autorEmail.equals(user.getEmail())){
                holder.binding.todo.setGravity(Gravity.END);
            } else {
                holder.binding.todo.setGravity(Gravity.START);
            }
            holder.binding.autor.setText(mensaje.autorNombre);

            holder.binding.mensaje.setText(mensaje.mensaje);
        }

        @Override
        public int getItemCount() {
            return chat.size();
        }
    }

    static class MensajeViewHolder extends RecyclerView.ViewHolder{
        ViewholderMensajeBinding binding;
        public MensajeViewHolder(@NonNull ViewholderMensajeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}