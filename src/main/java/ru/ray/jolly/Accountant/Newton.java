package ru.ray.jolly.Accountant;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;

public class Newton {
    private static ArrayList<Double> separateDiff = new ArrayList<Double>();

    public static void fillDiff(LinkedList<Pair<Double, Double>> index){
        separateDiff = new ArrayList<Double>();
        LinkedList<Pair<Double, Double>> list = new LinkedList<Pair<Double, Double>>();
        for (Pair<Double, Double> doublePair : index) {
            list.add(doublePair);
            separateDiff.add(makerDiff(list));
        }
    }

    private static double makerDiff(LinkedList<Pair<Double, Double>> list){
        if (list.size() > 2){
            LinkedList<Pair<Double, Double>> left = new LinkedList<Pair<Double, Double>>(list), right = new LinkedList<Pair<Double, Double>>(list);
            left.removeFirst();
            right.removeLast();
            return (makerDiff(left)-makerDiff(right))/(list.getLast().getKey() - list.getFirst().getKey());
        } else if(list.size() == 2){
            return (list.getLast().getValue() - list.getFirst().getValue())/(list.getLast().getKey() - list.getFirst().getKey());
        } else {
            return list.getFirst().getValue();
        }
    }

    public static double getPol(double x, LinkedList<Pair<Double, Double>> list){

        double sum = separateDiff.get(0);
        double koef = 1;
        for (int i = 1; i < list.size(); i++){
            koef *= (x - list.get(i-1).getKey());
            sum += koef * separateDiff.get(i);
        }
        return sum;
    }
}
