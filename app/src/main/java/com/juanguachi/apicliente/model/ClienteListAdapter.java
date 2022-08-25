package com.juanguachi.apicliente.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.juanguachi.apicliente.R;

import java.util.ArrayList;

public class ClienteListAdapter extends ArrayAdapter<Cliente> {
    private static final String TAG ="ClienteListAdapter";
    private Context mContext;
    int mResource;

    public ClienteListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Cliente> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String id=getItem(position).getId();
        String nombre=getItem(position).getNombre();
        String apellido=getItem(position).getApellido();
        String email=getItem(position).getEmail();

        Cliente cliente=new Cliente(id, nombre, apellido, email);
        LayoutInflater inflater=LayoutInflater.from(mContext);

        convertView=inflater.inflate(mResource,parent,false);

        TextView tnombre=(TextView) convertView.findViewById(R.id.txtnombre);
        TextView tapellido=(TextView) convertView.findViewById(R.id.txtapellido);
        TextView tid=(TextView) convertView.findViewById(R.id.txtid);
        TextView temail=(TextView) convertView.findViewById(R.id.txtemail);

        tid.setText(id);
        tnombre.setText(nombre);
        tapellido.setText(apellido);
        temail.setText(email);

        return convertView;
    }
}
