package com.deena.justdonate.HelperClasses.HomeAdapter;

public class CategoriesHelperClass {
    int image;
    String title;
    String Gradient;

    public CategoriesHelperClass(int image, String title) {
        this.image = image;
        this.title = title;
        this.Gradient = Gradient;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getGradient(int color_background) {
        return Gradient;
    }



}
