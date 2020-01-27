package fran.martinez.flickrSearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fran.martinez.flickrSearch.Fragments.Recycler;
import fran.martinez.flickrSearch.model.MainModel;

//Clase principal de la aplicación
public class MainActivity extends AppCompatActivity {


    private MainModel model;
    private TextView tx_noFound;
    private ImageView im_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //model
        model = ViewModelProviders.of(this).get(MainModel.class);
        final Recycler recyclerFragment = new Recycler();
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //views
        tx_noFound = findViewById(R.id.tx_noFound);
        im_error = findViewById(R.id.im_error);
        im_error.setVisibility(View.GONE);

        //cargar fragment Recycler
        fragmentTransaction.add(R.id.container, recyclerFragment);
        fragmentTransaction.hide(recyclerFragment).commit();


        //observador que mira si han habido resultados en la búsqueda de imágenes
        final Observer<Integer> observer2 = new Observer<Integer>() {
            @Override
            public void onChanged(Integer total) {


                if(total>0){
                    //si la busqueda ha dado resultados, muestra el fragment
                    FragmentTransaction fr =getSupportFragmentManager().beginTransaction();
                    fr.show(recyclerFragment).commit();

                    tx_noFound.setText("");
                    im_error.setVisibility(View.GONE);


                }else{
                    //si no ha dado resultados, oculta el fragment
                    FragmentTransaction fr =getSupportFragmentManager().beginTransaction();
                    fr.hide(recyclerFragment).commit();

                    tx_noFound.setText(getString(R.string.no_results));
                    im_error.setVisibility(View.VISIBLE);

                }
            }


        };

        model.getTotal().observe(this,observer2);







    }




}
