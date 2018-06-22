package com.example.pm.assistant;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pm.assistant.data.Usuario;
import com.example.pm.assistant.data.myDatabase;

import java.util.List;
import java.util.concurrent.TimeoutException;

public class UserProfileFragment extends Fragment {

    TextView nameText;
    TextView genderText;
    TextView birthText;
    TextView addressText;
    TextView tipText;

    private static myDatabase db;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard

        final View RootView = inflater.inflate(R.layout.user_profile_fragment, null);

//        db = Room.databaseBuilder(getContext(), myDatabase.class, "myDatabase").build();

        db = myDatabase.getsInstance(getActivity());

        nameText = (TextView) RootView.findViewById(R.id.nameTextView);
        genderText = (TextView) RootView.findViewById(R.id.genderTextView);
        birthText = (TextView) RootView.findViewById(R.id.birthTextView);
        addressText = (TextView) RootView.findViewById(R.id.addressTextView);
        tipText = (TextView) RootView.findViewById(R.id.tipTextView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (db.dao().getUsuario() == null) {
                    Log.i("NULO", "ta nulo na fragment");
                }
            }
        }).start();

        new UpdateUI(db, nameText, genderText, birthText, addressText, tipText).execute();

        return RootView;
    }

    @Override
    public void onStart() {
        super.onStart();



    }

}

class UpdateUI extends AsyncTask<Void, Void, Usuario> {

    private myDatabase db;
    private TextView name;
    private TextView gender;
    private TextView birth;
    private TextView address;
    private TextView tip;

    public UpdateUI(myDatabase myDb, TextView pName, TextView pGender, TextView pBirth ,TextView pAddress, TextView pTip) {
        this.db = myDb;
        this.name = pName;
        this.gender = pGender;
        this.birth = pBirth;
        this.address = pAddress;
        this.tip = pTip;
    }

    @Override
    protected Usuario doInBackground(Void... voids) {

        if (db == null) {
            Log.i("USER", "db ta nulo");
        }

        return db.dao().getUsuario();
    }

    @Override
    protected void onPostExecute(Usuario usuario) {
        super.onPostExecute(usuario);

        if(usuario == null) {
            Log.i("USER", "nulo");
        }

        name.setText(usuario.getUsuario_nome());
        if (usuario.isUsuario_sexo()) {
            gender.setText("Masculino");
        } else {
            gender.setText("Feminino");
        }
        birth.setText(usuario.getUsuario_dataDeNascimento());

        address.setText(usuario.getUsuario_endereco());

        if (usuario.isDicaAtiv()) {
            tip.setText("Ativado");
        } else {
            tip.setText("Desativado");
        }
    }

}