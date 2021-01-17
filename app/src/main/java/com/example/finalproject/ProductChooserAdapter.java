package com.example.finalproject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.model.Product;

import org.w3c.dom.Text;

public class ProductChooserAdapter extends RecyclerView.Adapter<ProductChooserAdapter.ViewHolder> {
    double price=0;
     boolean checkBoxesStatus[];

    Product products[];
    Context context;


    public ProductChooserAdapter(Context context,Product[]products) {
        this.context = context;

        this.products=products;
        this.checkBoxesStatus=new boolean[products.length];




    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_order_card,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView=holder.cardView;
        TextView nameTextView=(TextView) cardView.findViewById(R.id.productNameView);
        nameTextView.setText(products[position].getProductName());
        ImageView imageView=(ImageView) cardView.findViewById(R.id.imageView);
        Drawable dr= ContextCompat.getDrawable(cardView.getContext(),products[position].getImageId());
        imageView.setImageDrawable(dr);
        TextView priceTextView=(TextView) cardView.findViewById(R.id.priceView);
        priceTextView.setText("Price:"+products[position].getPrice()+"$");
        TextView typeTextView=(TextView) cardView.findViewById(R.id.typeView);
        typeTextView.setText(products[position].getType());
        final CheckBox checkBox=cardView.findViewById(R.id.orderCheckBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {



                if(checkBox.isChecked()){
                    price+=products[position].getPrice();
                    checkBoxesStatus[position]=true;

                    Toast.makeText(context, "Price:"+price+"$", Toast.LENGTH_SHORT).show();

                }else{
                    price-=products[position].getPrice();
                    checkBoxesStatus[position]=false;
                    Toast.makeText(context, "Price:"+price+"$", Toast.LENGTH_SHORT).show();
                }
//                priceView.setText("Price:"+price);
          }
        });

    }

    @Override
    public int getItemCount() {
       return products.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder (CardView cardView){
            super(cardView);
            this.cardView=cardView;
        }
    }
}
