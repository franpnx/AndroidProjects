package com.example.rxjava;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
Observable que emite una lista de nombres de animales, filtrados por los que empiezan por 'm'
 **/

public class SimpleObserver {

    private static final String TAG = "Simple Observer";

    // observable
    Observable<String> animalsObservable ;

    // observer
    Observer<String> animalsObserver ;


    public SimpleObserver() {

        animalsObservable = getAnimalsObservable();
        animalsObserver = getAnimalsObserver();

        // observer que se suscribe al observable
        animalsObservable
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override // filtro que retorna solo los valores que empiezan por 'm'
                    public boolean test(String s) throws Exception {
                        return s.toLowerCase().startsWith("m");
                    }
                })
                .subscribe(animalsObserver);

    }

    //observer
    private Observer<String> getAnimalsObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.v("ESTADO", "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.v("Nombre",  s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("ERROR", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.v(TAG, "Todos los items emitidos!");
            }
        };
    }

    // el observable emite los datos
    private Observable<String> getAnimalsObservable() {

        return Observable.fromArray(
                "Hotmiga", "Mono",
                "Murciélago", "Abeja", "Oso", "Mariposa",
                "Gato", "Cangrejo", "Conejo",
                "Perro", "Cigueña",
                "Zorro", "Rana");
    }
}
