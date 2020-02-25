package com.example.rxjava;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Múltiples observers que miran a un único observable
 */

public class MultipleObservers {



    private static final String TAG = "Multiple Observers";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    // observable
    Observable<String> animalsObservable ;

    // observers
    DisposableObserver<String> animalsObserver ;
    DisposableObserver<String> animalsObserverAllCaps;


    public MultipleObservers() {

        animalsObservable = getAnimalsObservable();
        animalsObserver = getAnimalsObserver();
        animalsObserverAllCaps = getAnimalsAllCapsObserver();

        compositeDisposable.add(
                // observer que retorna solo los valores que empiezan por 'm'
                animalsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) throws Exception {
                                return s.toLowerCase().startsWith("m");
                            }
                        })
                        .subscribeWith(animalsObserver));


        compositeDisposable.add(
                // observer que retorna solo los valores que empiezan por 'c' y los pone en mayúsculas
                animalsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) throws Exception {
                                return s.toLowerCase().startsWith("c");
                            }
                        })
                        // crea una función que retorna una string en mayúsculas
                        .map(new Function<String, String>() {
                            @Override
                            public String apply(String s) throws Exception {
                                return s.toUpperCase();
                            }
                        })
                        .subscribeWith(animalsObserverAllCaps));
    }



    //observer
    private DisposableObserver<String> getAnimalsObserver() {
        return new DisposableObserver<String>() {

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

    //observer
    private DisposableObserver<String> getAnimalsAllCapsObserver() {
        return new DisposableObserver<String>() {


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

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}
