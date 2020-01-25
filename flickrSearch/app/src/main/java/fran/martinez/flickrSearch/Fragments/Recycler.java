package fran.martinez.flickrSearch.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fran.martinez.flickrSearch.model.MainModel;
import fran.martinez.flickrSearch.object.ListItem;
import fran.martinez.flickrSearch.R;
import fran.martinez.flickrSearch.adapters.RecyAdapter;


//Fragment que contiene y gestiona el recyclerView
public class Recycler extends Fragment {

    private RecyAdapter adapter;
    private MainModel model;
    private ArrayList<ListItem> list;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    // infla el layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


          //model
        model = ViewModelProviders.of(getActivity()).get(MainModel.class);

        //inicializar componentes
        list = new ArrayList<>();
        recyclerView = getView().findViewById(R.id.recyclerId);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //añadir adapter al recyclerView
        adapter = new RecyAdapter( getActivity(),list);
        recyclerView.setAdapter(adapter);


        // observador que mira si se ha actualizado la lista de fotos
        final Observer<ArrayList<ListItem>> observer = new Observer<ArrayList<ListItem>>() {
            @Override
            public void onChanged(ArrayList<ListItem> listItems) {

                if(listItems!=null){

                    //cargar lista cuando la lista se haya actualizado
                    list = listItems;
                    adapter = new RecyAdapter( getActivity(),list);
                    recyclerView.setAdapter(adapter);

                }


            }
        };

        //añadir observador
        model.getPhotosList().observe(this,observer);



    }
}
