package com.example.yandexfilesystemtask.component;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by GreenNun on 2019-04-23.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {FilesystemImpl.class})
public class FilesystemImplTest {
    @Autowired
    private Filesystem fileSystem;

    @Test
    public void testFileSystem() {
        final String fileName = "someName";
        final String fileData = "someData";

        fileSystem.write(fileName, fileData.getBytes());

        final byte[] read = fileSystem.read(fileName);
        assertEquals("read", fileData, new String(read));

        final boolean delete = fileSystem.delete(fileName);
        assertTrue("deleted", delete);

        final byte[] empty = fileSystem.read(fileName);
        assertEquals("no file data", "", new String(empty));
    }
}