package dev.jacob_ba.foodiary.models;

import java.util.ArrayList;

public class Restaurant {
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
}
