package dev.jacob_ba.foodiary.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Dish implements Parcelable {
    public static int id_of_next_dish = 0;
    private int restaurantId;
    private int id;
    private String name;
    private float rank;
    private ArrayList<String> attributes;
    private String image_url;

    public Dish() {
        id_of_next_dish++;
    }

    public Dish(String dish_name, String image_url, float dish_rank, ArrayList<String> attributes) {
        this.name = dish_name;
        this.rank = dish_rank;
        this.image_url = image_url;
        this.id = id_of_next_dish;
        this.attributes = attributes;
        id_of_next_dish++;
    }

    protected Dish(Parcel in) {
        restaurantId = in.readInt();
        id = in.readInt();
        name = in.readString();
        rank = in.readFloat();
        image_url = in.readString();
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getImage_url() {
        return image_url;
    }

    public float getRank() {
        return rank;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getId() {
        return id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(restaurantId);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeFloat(rank);
        dest.writeString(image_url);
    }
}
