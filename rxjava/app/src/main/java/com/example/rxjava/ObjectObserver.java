package com.example.rxjava;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Observer que mira a un Observable que retorna una objeto de una lista
 */

public class ObjectObserver {

    private CompositeDisposable disposable;
    private static final String TAG = "Object Observer";


    public ObjectObserver() {
        this.disposable = new CompositeDisposable();
        disposable.add(getNotesObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Nota, Nota>() {
                    @Override
                    public Nota apply(Nota nota) throws Exception {
                        // cambiando la nota a mayúsculas
                        nota.setTexto(nota.getTexto().toUpperCase());
                        return nota;
                    }
                })
                .subscribeWith(getNotesObserver()));


    }

    //observer
    private DisposableObserver<Nota> getNotesObserver() {
        return new DisposableObserver<Nota>() {

            @Override
            public void onNext(Nota nota) {
                Log.v("Nota",  nota.getTexto());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("ERROR",  e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.v(TAG, "Todas las notas emitidas!");
            }
        };
    }

    //observable
    private Observable<Nota> getNotesObservable() {
        final List<Nota> notas = prepareNotes();

        return Observable.create(new ObservableOnSubscribe<Nota>() {
            @Override
            public void subscribe(ObservableEmitter<Nota> emitter) throws Exception {
                for (Nota nota : notas) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(nota);
                    }
                }

                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        });
    }

    //añadir datos a la lista
    private List<Nota> prepareNotes() {
        List<Nota> notes = new ArrayList<>();
        notes.add(new Nota(1, "comprar pasta de dientes!"));
        notes.add(new Nota(2, "llamar a tu hermano!"));
        notes.add(new Nota(3, "mirar Narcos esta noche!"));
        notes.add(new Nota(4, "pagar la cuenta!"));

        return notes;
    }

    //clase del objeto
    class Nota {
        int id;
        String texto;

        public Nota(int id, String texto) {
            this.id = id;
            this.texto = texto;
        }

        public int getId() {
            return id;
        }

        public String getTexto() {
            return texto;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setTexto(String texto) {
            this.texto = texto;
        }
    }

}
