package fran.martinez.flickrSearch.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import fran.martinez.flickrSearch.MainActivity;
import fran.martinez.flickrSearch.R;
import fran.martinez.flickrSearch.http.HttpHandler;
import fran.martinez.flickrSearch.model.MainModel;
import fran.martinez.flickrSearch.object.ListItem;
import fran.martinez.flickrSearch.object.jsonContent;

//Fragment que se encarga de hacer la búsqueda de imágenes
public class Search extends Fragment {

    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private String response,request;
    private jsonContent jsContent;
    public String URL;

    private EditText tx_search;
    private ImageView im_search;
    private MainModel model;
    private ArrayList<jsonContent.photo> photosList;
    private ArrayList<ListItem> items;
    private int total;

    //infla el layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //modelo
        model = ViewModelProviders.of(getActivity()).get(MainModel.class);

        //views
        tx_search = getView().findViewById(R.id.tx_search);
        im_search = getView().findViewById(R.id.im_search);
        //gestión del evento onClick
        im_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //obtiene datos introducidos por el usuario
                request = tx_search.getText().toString();


                if(request.equals("")){
                    //si no se ha introducido ningún dato...
                    Toast.makeText(getActivity(),"Por favor, introduzca datos de búsqueda",Toast.LENGTH_SHORT).show();

                }
                    //realizar tarea asíncrona para descargar datos
                    GetContent gc = new GetContent();
                    URL = "https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=1da668b4f8de3499fd19a1f1fff06fe3&text="+request+"&extras=owner_name&per_page=10&format=json&nojsoncallback=1";

                    gc.execute(URL);






            }
        });
    }

    /**
     * Async task class to get json by making HTTP call
     * */

    private class GetContent extends AsyncTask<String, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Mostrar progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Cargando contenido...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... param) {


            //instanciar clase del post
            HttpHandler sh = new HttpHandler();

            //Hacer una petición a la url y recibir respuesta
            String jsonStr = sh.makeServiceCall(param[0]);
            if(jsonStr!=null){

                response =jsonStr;
                try{

                    //crea objeto a partir del json
                    jsContent = new Gson().fromJson(response, jsonContent.class);
                    //extrae la ArrayList de fotos
                    photosList = jsContent.getPhotos().getPhoto();
                    //crear lista de items
                    items = new ArrayList<>();

                    if(photosList!=null){

                        for(jsonContent.photo photo : photosList){

                            String imageUrl = "https://farm"+String.valueOf(photo.getFarm())+".staticflickr.com/"+photo.getServer()+"/"+photo.getId()+"_"+photo.getSecret()+".jpg";

                            items.add(new ListItem(photo.getOwnername(),photo.getTitle(),imageUrl));
                        }
                    }

                    //extrae el numero de resultados total
                    total = jsContent.getPhotos().getTotal();


                }catch(Exception e){
                    e.printStackTrace();
                }

            }

            Log.d(TAG, "Response from url: " + response);


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Cierra el progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();


            //envía los datos obtenidos en la respuesta al model
            model.setTotal(total);
            model.setPhotosList(items);





        }

    }




}
