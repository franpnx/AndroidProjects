package fran.martinez.flickrSearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fran.martinez.flickrSearch.Fragments.Details;
import fran.martinez.flickrSearch.R;
import fran.martinez.flickrSearch.model.MainModel;
import fran.martinez.flickrSearch.object.ListItem;

//Clase adapter para el RecyclerView
public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder> {


     private ArrayList<ListItem> mData;
     private LayoutInflater mInflater;
     private Context context;
     private MainModel model;

    // constructor
   public RecyAdapter(Context context, ArrayList<ListItem> data) {
        this.mData = data;
        this.context = context;
    }

    // infla el layout con el item
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.list_item, null, false);
        return new ViewHolder(view);
    }

    // actualiza los valores de los views
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //model
        model = ViewModelProviders.of((FragmentActivity) context).get(MainModel.class);

        final ListItem row = mData.get(position);
        holder.title.setText(row.getTitle());
        holder.author.setText(row.getAuthor());

        //añade una imagen con  Picasso dentro del imageView
        Picasso.with(context)
                .load(row.getImage())
                .error(R.drawable.ic_error)
                .fit()
                .centerInside()
                .into(holder.thumbnail);

        //implementar el onclick
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // actualiza el item en el model
                model.setItem(row);

                //cambia al fragment Details
                FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new Details());
                transaction.addToBackStack(null);//para que vuelva al fragment anterior de la pila al darle atrás
                transaction.commit();

            }
        });
    }

    // numero total de filas
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // gestiona los views
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,author;
        ImageView thumbnail;
        LinearLayout itemLayout;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.tx_title);
            author =(TextView)itemView.findViewById(R.id.tx_author);
            thumbnail = itemView.findViewById(R.id.im_thumbnail);
            itemLayout= (LinearLayout)itemView.findViewById(R.id.ly_ItemLayout);

        }


    }




}
