package fran.martinez.flickrSearch.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import fran.martinez.flickrSearch.object.ListItem;

//Clase ViewModel para intercambiar datos entre activities y fragments
public class MainModel extends AndroidViewModel {
    public MainModel(@NonNull Application application) {
        super(application);
    }

    //variables
    private MutableLiveData<Integer> total = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ListItem>> photosList = new MutableLiveData<>();
    private MutableLiveData<ListItem> item = new MutableLiveData<>();



    /////////////////////////////////  getters and setters  ////////////////////////////////////////////////////



    public void setTotal(int total){
        this.total.setValue(total);

    }

    public MutableLiveData<Integer> getTotal(){
        return total;
    }

    public void setPhotosList(ArrayList<ListItem> list){
        photosList.setValue(list);
    }

    public MutableLiveData<ArrayList<ListItem>> getPhotosList(){
        return photosList;
    }

    public void setItem(ListItem item){

        this.item.setValue(item);
    }

    public MutableLiveData<ListItem> getItem() {
        return item;
    }
}
