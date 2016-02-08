package in.futuretrucks.model;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by mahadev on 9/16/15.
 */
public class SubCatSelectionModel implements Serializable{
    String cat_code;
    String truck_type;
    String category;
    String capacity;
    boolean selected = false;
    Context appContext;

    public void CatSelectionModel(Context context){
        appContext=context;
    }


    public void setTruckType(String truck_type){
        this.truck_type=truck_type;
    }

    public void setCatName(String cat_name){
        this.category=cat_name;
    }

    public void setCatCode(String cat_code){
        this.cat_code=cat_code;
    }

    public void setCatCapacity(String capacity){
        this.capacity=capacity;
    }

    public String getTruckType(){
        return truck_type;
    }

    public String getCatName(){
        return category;
    }

    public String getCatCode(){
        return cat_code;
    }

    public String getCatCapacity(){
        return capacity;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
