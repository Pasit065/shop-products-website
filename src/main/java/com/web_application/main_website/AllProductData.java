package com.web_application.main_website;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AllProductData {
    private JSONObject productsDetail;

    public void readProductsDataFileAndGetproductsJsonArrayData() {
        Object productsJsonArrayData = null;
        JSONParser parser = new JSONParser();

            try {
                productsJsonArrayData = parser.parse(new FileReader("./src/main/java/com/web_application/main_website/json/products_data.json"));
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }

            this.productsDetail = (JSONObject) productsJsonArrayData;
        }

    public JSONObject getProductsDetail() {
        return this.productsDetail;
    }

    public JSONObject getProductDetailBySpecificTitle(String productTitle) {
        Object productObject = this.productsDetail.get(productTitle);
        JSONObject product = (JSONObject) productObject;

        return product;
    }

    // Learn how to loop 
    public void showAllProducts() {
        Object productObj;

        System.out.println("We have the list of product heres: ");

        for (Iterator iterator = this.productsDetail.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
            
            // With json object and get will
            productObj = this.productsDetail.get(key);
            JSONObject product = (JSONObject) productObj;
            System.out.println("\nWe have " + product.get("name") + " with " + product.get("price") + " Baht.");
		}
    }
}
