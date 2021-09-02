package com.example.prova1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prova1.R; //Mudar para o seu projeto

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Adapter para exibição de Contacts em listas de RecyclerView
 */
public class MesaAdapter extends RecyclerView.Adapter {

    private ArrayList<Mesa> mMesaList;
    SharedPreferences preferences;

    /**
     * @param mesaList lista em array de objetos Mesas
     */
    public MesaAdapter(ArrayList<Mesa> mesaList, SharedPreferences preferences) {
        this.mMesaList = mesaList;
        this.preferences = preferences;
    }

    /**
     * Constructor da classe
     * ViewHolder para RecyclerView que lista Mesas
     */
    public class MesaViewHolder extends RecyclerView.ViewHolder {

        //Views do botão que está criando
        Button reservar;
        TextView tvReservar;
        LinearLayout layoutMesa;

        /**
         * Constructor da classe
         * @param itemView view para exibição de dados de Contacts
         */
        MesaViewHolder(View itemView) {
            super(itemView);
            //Puxar do botão que está criando
            reservar = itemView.findViewById(R.id.btnReservar);
            tvReservar = itemView.findViewById(R.id.tvReservar);
            layoutMesa = itemView.findViewById(R.id.linearMesa);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mesa_layout, parent, false);
        return new MesaViewHolder(v);
    }

    /**
     * Método sobracarrecado para preenchimento da View com dados do objeto Mesa atual
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Mesa mesas = mMesaList.get(position);
        MesaViewHolder mesaVH = (MesaViewHolder) holder;
        final int posicao = position + 1;
        mesaVH.tvReservar.setText(String.valueOf(posicao));
        Context context = mesaVH.layoutMesa.getContext();

        if(mesas.getIsReserved()) {
            if(preferences.getString("corRes", "") == "") {
                mesaVH.tvReservar.setTextColor(context.getResources().getColor(R.color.red_google));
            } else {
                mesaVH.tvReservar.setTextColor(Color.parseColor(preferences.getString("corRes", "")));
            }
        } else {
            if(preferences.getString("corLivre", "") == "" ) {
                mesaVH.tvReservar.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                mesaVH.tvReservar.setTextColor(Color.parseColor(preferences.getString("corLivre", "")));
            }
        }

        //acessar as views agora e setar as coisas
        mesaVH.reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesas.setReserved(true);
                mesaVH.reservar.setClickable(false);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMesaList.size(); //retorna a quantidade de itens dentro do adapter com base no ArrayList de Contacts
    }
}
