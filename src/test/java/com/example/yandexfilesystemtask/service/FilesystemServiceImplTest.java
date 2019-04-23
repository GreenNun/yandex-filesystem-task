package com.example.yandexfilesystemtask.service;

import com.example.yandexfilesystemtask.component.FilesystemImpl;
import com.example.yandexfilesystemtask.model.File;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by GreenNun on 2019-04-23.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {FilesystemImpl.class, FilesystemServiceImpl.class})
public class FilesystemServiceImplTest {
    @Autowired
    private FilesystemService service;

    @Test
    public void test() {
        final String fileName = "someName";
        final File file = new File(fileName, "someData".getBytes());

        service.write(file);

        final File read = service.read(fileName);
        assertEquals("read", file, read);

        final boolean delete = service.delete(fileName);
        assertTrue("deleted", delete);

        final File empty = service.read(fileName);
        assertNull("no file data", empty);
    }
}