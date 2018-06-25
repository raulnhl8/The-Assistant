package com.example.pm.assistant;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pm.assistant.data.Contato;
import com.example.pm.assistant.data.Cuidador;
import com.example.pm.assistant.data.Usuario;
import com.example.pm.assistant.data.myDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RvAdapter adapter;
    private List<Contato> contatos;
    myDatabase db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard

        db = myDatabase.getsInstance(getActivity());
        contatos = db.dao().getAllContatos();
        Log.i("NUMERO", contatos.size()+"");
        View layout = inflater.inflate(R.layout.contacts_fragment, null);
        recyclerView = (RecyclerView) layout.findViewById(R.id.contatosList);
        adapter = new RvAdapter(getActivity(), contatos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }


}

