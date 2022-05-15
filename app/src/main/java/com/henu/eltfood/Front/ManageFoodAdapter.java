//package com.henu.eltfood.Front;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.henu.eltfood.R;
//
//import java.util.List;
//
//public class ManageFoodAdapter extends BaseAdapter {
//    private List<FoodInformation> list;
//    private Context context;
//
//    public ManageFoodAdapter(Context context, List<FoodInformation> list) {
//        this.list = list;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public FoodInformation getItem(int i) {
//        return list.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        FoodInformation food = (FoodInformation) getItem(i); //获取第 i 个item的数据
//        View v;
//        FoodAdapter.ViewHold viewHold;
//        if(view == null) {
//            v = LayoutInflater.from(context).inflate(R.layout.food_information, null);
//            viewHold = new ManageFoodAdapter().ViewHold();
//
//            viewHold.foodImage = v.findViewById(R.id.food_img);
//            viewHold.foodCategory = v.findViewById(R.id.food_category);
//            viewHold.foodName = v.findViewById(R.id.food_name);
//            viewHold.price = v.findViewById(R.id.front_food_price);
//            viewHold.imageButton = v.findViewById(R.id.select_add);
//
//            v.setTag(viewHold);
//        }
//        else {
//            v = view;
//            viewHold = (FoodAdapter.ViewHold) v.getTag();
//        }
//        viewHold.foodImage.setImageResource(food.getImg());
//        viewHold.foodCategory.setText(food.getCategory());
//        viewHold.foodName.setText(food.getName());
//        viewHold.price.setText(food.getPrice());
//        viewHold.imageButton.setOnClickListener(new View.OnClickListener() { //点击对应的item中的+号，产生相应的事件
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "已添加商品:" + getItem(i).name, Toast.LENGTH_SHORT).show();
//            }
//        });
//        return v;
//    }
//    class ViewHold {
//        ImageView foodImage;
//        TextView foodName;
//        TextView foodCategory;
//        TextView price;
//        ImageButton imageButton;
//    }
//}
