package com.example.yandexfilesystemtask.component;

/**
 * Created by GreenNun on 2019-04-23.
 */
public interface Filesystem {

    void write(String name, byte[] fData);

    byte[] read(String name);

    boolean delete(String name);
}
