package ru.nsu.fit.chirikhin;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.Charset.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by cas on 25.02.16.
 */
public class CounterTest {
    public CounterTest() {

    }

    @Test
    public void count() throws IOException {
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

        Statistics[] stat = Counter.getStatistics(new BaseFilter[]{new FileExtensionFilter("txt")}, new File[]{txt, jpeg, another});

        assertTrue(3 == stat[1].getNumOfFiles());
        assertTrue(0 == stat[1].getNumOfLines());
        assertTrue(1 == stat[0].getNumOfFiles());
        assertTrue(0 == stat[0].getNumOfLines());

        List<String> lines = Arrays.asList("1", "2", "3");
        Files.write(txt.toPath(), lines, forName("UTF-8"));
        Files.write(another.toPath(), lines, forName("UTF-8"));

        stat = Counter.getStatistics(new BaseFilter[]{new FileExtensionFilter("txt")}, new File[]{txt, jpeg, another});

        assertTrue(3 == stat[1].getNumOfFiles());
        assertTrue(6 == stat[1].getNumOfLines());
        assertTrue(1 == stat[0].getNumOfFiles());
        assertTrue(3 == stat[0].getNumOfLines());
    }
}
