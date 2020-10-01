package ru.ray.jolly.Accountant;

public class Functions {
    private Functions(){}

    private static double fin0(double x){
        return 5*Math.sin(3*x);
    }
    private static double fin1(double x){
        return Math.log(x*5);
    }
    private static double fin2(double x){
        return 3*Math.pow(x, 3)+1;
    }
    private static double fin3(double x){
        return Math.pow(2, x+1);
    }
    private static double fin4(double x){
        return 2*x;
    }

    public static double valueFun(int id, double x){
        switch (id) {
            case 0: return Functions.fin0(x);
            case 1: return Functions.fin1(x);
            case 2: return Functions.fin2(x);
            case 3: return Functions.fin3(x);
            case 4: return Functions.fin4(x);
            default: return 0;
        }
    }
}
