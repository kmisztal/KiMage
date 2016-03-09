/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kimage.tools.executors.gui.helpers;

/**
 * @author Krzysztof
 */
class Pair<T, K> {
    T first;
    K second;

    public Pair(T first, K second) {
        this.first = first;
        this.second = second;
    }

    T getKey() {
        return first;
    }

    K getValue() {
        return second;
    }


}
