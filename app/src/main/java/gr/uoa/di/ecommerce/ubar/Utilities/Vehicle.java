package gr.uoa.di.ecommerce.ubar.Utilities;

import org.json.JSONException;
import org.json.JSONObject;


public class Vehicle {
    private String model, manufacturer, year, color, plate, type;


    public Vehicle(String mo, String ma, String y, String c, String p, String t) {
        model = mo;
        manufacturer = ma;
        year = y;
        color = c;
        plate = p;
        type = t;
    }

    public Vehicle() {}

    public void set(String mo, String ma, String y, String c, String p, String t) {
        model = mo;
        manufacturer = ma;
        year = y;
        color = c;
        plate = p;
        type = t;
    }

    public String mapJSON(JSONObject jobj) throws JSONException {

        jobj.put("id", 1); //TODO
        jobj.put("licensePlate", plate);
        jobj.put("manufacturer", manufacturer);
        jobj.put("year", year);
        jobj.put("color", color);
        jobj.put("model", model);
        jobj.put("type", type);

        return jobj.toString();
    }

    public boolean empty() {
        if ("".equals(model) ||
                "".equals(manufacturer) ||
                "".equals(year) ||
                "".equals(color) ||
                "".equals(plate) ||
                "".equals(type))
            return true;
        return false;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
