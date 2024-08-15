package me.racer.jjj.utils;

import pl.coderion.model.Product;
import pl.coderion.model.ProductResponse;
import pl.coderion.service.OpenFoodFactsWrapper;
import pl.coderion.service.impl.OpenFoodFactsWrapperImpl;

import java.util.ArrayList;
import java.util.Arrays;

public class eanutils {
    public static ArrayList<String> getEANdata(String EAN) {
        ArrayList<String> EANdata = new ArrayList<>();
        OpenFoodFactsWrapper wrapper = new OpenFoodFactsWrapperImpl();
        ProductResponse productResponse = wrapper.fetchProductByCode(EAN);


        Product product = productResponse.getProduct();


        EANdata.add(product.getProductName());
        EANdata.add(product.getGenericName());
        EANdata.add(product.getImageUrl());
        EANdata.add("---");
        EANdata.add(product.getCategories());
        EANdata.add("---");
        EANdata.add(product.getQuantity());

        return EANdata;
    }
}
