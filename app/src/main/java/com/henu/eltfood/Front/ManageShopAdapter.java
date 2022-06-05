package com.henu.eltfood.Front;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.henu.eltfood.DataClass.Shop;
import com.henu.eltfood.R;

import java.util.List;

public class ManageShopAdapter extends BaseAdapter {
    List<Shop> list;
    Context context;
    ManageShop manageShop;

    public ManageShopAdapter(List<Shop> list, Context context, ManageShop manageShop) {
        this.list = list;
        this.context = context;
        this.manageShop = manageShop;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Shop getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Shop shop = getItem(i);
        View v = null;
        ViewHold viewHold;
        if(view == null) {
            v = LayoutInflater.from(context).inflate(R.layout.shop_item, null);
            viewHold = new ViewHold();

            viewHold.shopImageView = v.findViewById(R.id.manageShopImg);
            viewHold.shopName = v.findViewById(R.id.manageShopName);
            viewHold.shopComments = v.findViewById(R.id.manageShopGrade);
            viewHold.shopPriceInf = v.findViewById(R.id.manageShopPrice);
            viewHold.deleteTextView = v.findViewById(R.id.delete_this_shop);

            v.setTag(viewHold);
        }
        else {
            v = view;
            viewHold = (ViewHold) v.getTag();
        }

        viewHold.shopImageView.setImageBitmap(shop.image);
        viewHold.shopName.setText(shop.ShopName);
        viewHold.shopComments.setText(shop.ShopGrade);
        viewHold.shopPriceInf.setText(shop.ShopPriceInf);
        viewHold.deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    manageShop.deleteShop(getItem(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return v;
    }

    private class ViewHold{
        ImageView shopImageView;
        TextView shopName;
        TextView shopComments;
        TextView shopPriceInf;
        TextView deleteTextView;
    }
}
