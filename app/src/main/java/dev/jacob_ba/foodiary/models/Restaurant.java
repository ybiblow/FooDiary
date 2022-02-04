package dev.jacob_ba.foodiary.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Restaurant implements Parcelable {
    public static int id_of_next_restaurant = 0;
    private int id;
    private String name;
    private String category;
    private String image_url;
    private float rank;
    private ArrayList<Integer> arrayList_dishes_id;

    public Restaurant() {
        id_of_next_restaurant++;
    }

    public Restaurant(String restaurant_name, String category, String image_url, float restaurant_rank) {
        this.name = restaurant_name;
        this.category = category;
        this.image_url = image_url;
        arrayList_dishes_id = new ArrayList<>();
        this.rank = restaurant_rank;
        this.id = id_of_next_restaurant;
        id_of_next_restaurant++;
    }

    protected Restaurant(Parcel in) {
        id = in.readInt();
        name = in.readString();
        category = in.readString();
        image_url = in.readString();
        rank = in.readFloat();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getName() {

        return name;
    }

    public String getCategory() {

        return category;
    }

    public String getImage_url() {
        return image_url;
    }

    public float getRank() {

        return rank;
    }

    public void addDish(Dish dish) {
        dish.setRestaurantId(this.id);
        arrayList_dishes_id.add(dish.getId());
    }

    public ArrayList<Integer> getArrayList_dishes_id() {
        return arrayList_dishes_id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(image_url);
        dest.writeFloat(rank);
    }
}
