package ru.nsu.fit.chirikhin;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by cas on 25.02.16.
 */
public class FileManagerTest {
    public FileManagerTest() {

    }

    @Test (expected = IllegalArgumentException.class)
    public void isDirectoryTest() {
        FileManager.getFullListOfFilesInDirectory("Hello");
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

        LinkedList<File> files = FileManager.getFullListOfFilesInDirectory("./NewDir/");

        assertTrue(files.contains(txt));
        assertTrue(files.contains(jpeg));
        assertTrue(files.contains(another));
        assertFalse(files.contains(dir2));

        another.delete();
        txt.delete();
        jpeg.delete();
        dir2.delete();
        dir.delete();
    }
}
