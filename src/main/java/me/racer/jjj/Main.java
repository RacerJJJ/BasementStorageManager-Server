package me.racer.jjj;


import static me.racer.jjj.utils.eanutils.getEANdata;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        /**
        Map<String, Float> testmap = Map.of("Coca-Cola", 1f);
        microsEAN(testmap);
         **/
        System.out.println(getEANdata("5449000000439"));
    }
}