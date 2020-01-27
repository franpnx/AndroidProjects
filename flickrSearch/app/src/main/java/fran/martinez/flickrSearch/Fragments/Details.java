package fran.martinez.flickrSearch.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fran.martinez.flickrSearch.R;
import fran.martinez.flickrSearch.model.MainModel;
import fran.martinez.flickrSearch.object.ListItem;


public class Details extends Fragment {

    private MainModel model;
    private ImageView image;
    private TextView title, author, description;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //model
        model = ViewModelProviders.of(getActivity()).get(MainModel.class);

        //views
        image = getView().findViewById(R.id.im_det_photo);
        author = getView().findViewById(R.id.tx_det_author);
        title = getView().findViewById(R.id.tx_det_title);
        description = getView().findViewById(R.id.tx_desc);

        // observador
        final Observer<ListItem> observerDetails = new Observer<ListItem>() {
            @Override
            public void onChanged(ListItem listItem) {

                author.setText(listItem.getAuthor());
                title.setText(listItem.getTitle());
                description.setText(listItem.getDescription());

                //añade una imagen con  Picasso dentro del imageView
                Picasso.with(getActivity())
                        .load(listItem.getImage())
                        .error(R.drawable.ic_error)
                        .fit()
                        .centerInside()
                        .into(image);


            }
        };

        //añadir observador
        model.getItem().observe(this,observerDetails);
    }
}
