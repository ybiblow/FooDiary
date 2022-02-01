package dev.jacob_ba.foodiary.models;

public class Dish {
    public static int id_of_next_dish = 0;
    private int restaurantId;
    private int id;
    private String name;
    private float rank;
    private String image_url;

    public Dish() {
        id_of_next_dish++;
    }

    public Dish(String dish_name, String image_url, float dish_rank) {
        this.name = dish_name;
        this.rank = dish_rank;
        this.image_url = image_url;
        this.id = id_of_next_dish;
        id_of_next_dish++;
    }

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
}
