package ru.nsu.fit.chirikhinTests;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FileManagerTest {
    public FileManagerTest() {

    }

    @Test
    public void testIsAllFiles() throws IOException {
        File dir = new File("./NewDir");
        dir.mkdir();
        File txt = new File(dir, "me.txt");
        txt.createNewFile();
        File jpeg = new File(dir, "jpeg");
        jpeg.createNewFile();
        File dir2 = new File(dir, "dir2");
        dir2.mkdir();
        File another = new File(dir2, "another.png");
        another.createNewFile();

        another.delete();
        txt.delete();
        jpeg.delete();
        dir2.delete();
        dir.delete();
    }
}
