package com.example.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Ejemplos básicos de RxJava
     * En RxJava, si hay cambios en los valores del observable, se actualizan automáticamente en el observer.
     */

    private Disposable disposable;
    private Button bt_simpleObs, bt_multipleObs, bt_objectObs;
    private SimpleObserver simpleObs;
    private MultipleObservers multipleObs;
    private ObjectObserver objectObs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instanciar Views
        bt_simpleObs = findViewById(R.id.bt_simpleObs);
        bt_simpleObs.setOnClickListener(this);
        bt_multipleObs = findViewById(R.id.bt_multipleObs);
        bt_multipleObs.setOnClickListener(this);
        bt_objectObs = findViewById(R.id.bt_objectObs);
        bt_objectObs.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {

        if (v == bt_simpleObs) {

            Toast.makeText(this,"mirar LOG",Toast.LENGTH_LONG).show();
            simpleObs = new SimpleObserver();

        } else if (v == bt_multipleObs) {

            Toast.makeText(this,"mirar LOG",Toast.LENGTH_LONG).show();
            multipleObs = new MultipleObservers();

        } else if (v == bt_objectObs) {

            Toast.makeText(this,"mirar LOG",Toast.LENGTH_LONG).show();
            objectObs = new ObjectObserver();


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // no envia eventos una vez que la activity haya sido destruida
        disposable.dispose();


        CompositeDisposable comp =multipleObs.getCompositeDisposable();
        if(comp!=null){

            comp.clear();
        }
    }
}
