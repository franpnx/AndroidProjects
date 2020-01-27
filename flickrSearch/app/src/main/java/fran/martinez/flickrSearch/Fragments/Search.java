package fran.martinez.flickrSearch.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

        //gestión del evento search del keyboard
        tx_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    //comprobar conectividad
                    if(connectivity(getActivity())){

                        //buscar imágenes
                        search();

                        // Ocultar el teclado
                        hideKeyboard(getActivity());

                    }else{
                        Toast.makeText(getActivity(),getString(R.string.connec_failed), Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
                return false;
            }
        });

        //gestión del evento onClick
        im_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //comprobar conectividad
                if(connectivity(getActivity())){

                    //buscar imágenes
                    search();

                    // Ocultar el teclado
                    hideKeyboard(getActivity());

                }else{
                    Toast.makeText(getActivity(),getString(R.string.connec_failed), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public boolean connectivity(Activity context){

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }

    public void search(){

        //obtiene datos introducidos por el usuario
        request = tx_search.getText().toString();

        if(request.equals("")){
            //si no se ha introducido ningún dato...
            Toast.makeText(getActivity(),getString(R.string.empty_field),Toast.LENGTH_SHORT).show();

        }
        //realizar tarea asíncrona para descargar datos
        GetContent gc = new GetContent();
        URL = "https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key="+getString(R.string.flickr_key)+"&text="+request+"&extras=owner_name,description&format=json&nojsoncallback=1";

        gc.execute(URL);

    }

    public void hideKeyboard(Activity context){

        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
            pDialog.setMessage(getString(R.string.async_task));
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

                            items.add(new ListItem(photo.getOwnername(),photo.getTitle(),imageUrl,photo.getDescription().get_content()));
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
