package com.example.yandexfilesystemtask.service;

import com.example.yandexfilesystemtask.component.Filesystem;
import com.example.yandexfilesystemtask.model.File;
import org.springframework.stereotype.Service;

/**
 * Created by GreenNun on 2019-04-23.
 */
@Service
public class FilesystemServiceImpl implements FilesystemService {
    private final Filesystem filesystem;

    public FilesystemServiceImpl(Filesystem filesystem) {
        this.filesystem = filesystem;
    }

    @Override
    public void write(File file) {
        filesystem.write(file.getName(), file.getData());
    }

    @Override
    public File read(String name) {
        final byte[] data = filesystem.read(name);

        if (data.length == 0)
            return null;

        return new File(name, data);
    }

    @Override
    public boolean delete(String name) {
        return filesystem.delete(name);
    }
}
