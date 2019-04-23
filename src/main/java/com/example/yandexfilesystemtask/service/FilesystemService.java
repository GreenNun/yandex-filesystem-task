package com.example.yandexfilesystemtask.service;

import com.example.yandexfilesystemtask.model.File;

/**
 * Created by GreenNun on 2019-04-23.
 */
public interface FilesystemService {

    void write(File file);

    File read(String name);

    boolean delete(String name);
}
