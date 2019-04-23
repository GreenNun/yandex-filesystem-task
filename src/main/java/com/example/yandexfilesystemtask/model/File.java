package com.example.yandexfilesystemtask.model;

import java.util.Objects;

/**
 * Created by GreenNun on 2019-04-23.
 */
public class File {
    private final String name;
    private final byte[] data;

    public File(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof File)) return false;
        File file = (File) o;
        return Objects.equals(name, file.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
