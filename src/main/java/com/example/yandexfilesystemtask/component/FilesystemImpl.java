package com.example.yandexfilesystemtask.component;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * Created by GreenNun on 2019-04-23.
 */
@Component
public class FilesystemImpl implements Filesystem {
    private static final int MAX_FILES = 100_000;
    private static final int MAX_NAME_LENGTH = 50;
    private static final int NAME_LENGTH_BYTES = 1;
    private static final int NAME_SPACE_BYTES = 100;
    private static final int DATA_LENGTH_BYTES = 1;
    private static final int DATA_SPACE_BYTES = 10;
    private static final int FILE_SIZE_BYTES = NAME_LENGTH_BYTES + NAME_SPACE_BYTES + DATA_LENGTH_BYTES + DATA_SPACE_BYTES;
    private final byte[] storage = new byte[FILE_SIZE_BYTES * MAX_FILES];

    @Override
    public void write(String name, byte[] data) {
        Assert.isTrue(name.length() <= MAX_NAME_LENGTH, "Filename is too long");
        Assert.isTrue(data.length <= DATA_SPACE_BYTES, "File is too big");
        Assert.isTrue(data.length > 0, "File is empty");

        for (int index = 0; index < storage.length; index = index + FILE_SIZE_BYTES) {
            final byte nameLength = storage[index];

            if (nameLength == 0) {
                writeFileName(name, index);
                writeFileData(data, index);
                return;
            }
        }

        throw new RuntimeException("No free space");
    }

    @Override
    public byte[] read(String name) {
        Assert.isTrue(name.length() <= MAX_NAME_LENGTH, "Filename is too long");

        final byte[] nameBytes = name.getBytes();

        for (int index = 0; index < storage.length; index = index + FILE_SIZE_BYTES) {
            final byte nameLength = storage[index];

            if (nameLength > 0) {
                final byte[] fName = readFileName(index, nameLength);

                if (Arrays.equals(fName, nameBytes)) {
                    return readFileData(index);
                }
            }
        }

        return new byte[0];
    }

    @Override
    public boolean delete(String name) {
        Assert.isTrue(name.length() <= MAX_NAME_LENGTH, "Filename is too long");

        final byte[] nameBytes = name.getBytes();

        for (int i = 0; i < storage.length; i = i + FILE_SIZE_BYTES) {
            final byte nLength = storage[i];

            if (nLength == nameBytes.length) {
                final byte[] fName = readFileName(i, nLength);

                if (Arrays.equals(nameBytes, fName)) {
                    System.arraycopy(new byte[FILE_SIZE_BYTES], 0, storage, i, FILE_SIZE_BYTES);
                    return true;
                }
            }

        }

        return false;
    }

    private void writeFileData(byte[] data, int index) {
        storage[index + NAME_LENGTH_BYTES + NAME_SPACE_BYTES] = (byte) data.length;
        writeFromIndex(data, index + NAME_LENGTH_BYTES + NAME_SPACE_BYTES + DATA_LENGTH_BYTES);
    }

    private void writeFileName(String name, int index) {
        final byte[] fName = name.getBytes();
        storage[index] = (byte) fName.length;
        writeFromIndex(fName, index + NAME_LENGTH_BYTES);
    }

    private void writeFromIndex(byte[] data, int index) {
        System.arraycopy(data, 0, storage, index, data.length);
    }

    private byte[] readFileName(int index, byte nameLength) {
        final byte[] name = new byte[nameLength];
        System.arraycopy(storage, index + 1, name, 0, nameLength);
        return name;
    }

    private byte[] readFileData(int index) {
        final byte dLength = storage[index + NAME_LENGTH_BYTES + NAME_SPACE_BYTES];
        final byte[] fData = new byte[dLength];
        System.arraycopy(storage, index + NAME_LENGTH_BYTES + NAME_SPACE_BYTES + DATA_LENGTH_BYTES, fData, 0, dLength);
        return fData;
    }
}
