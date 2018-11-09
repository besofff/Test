package com.sergeybelkin.test;

public interface Observable {

    void addObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers(String keywords, String category);
}
