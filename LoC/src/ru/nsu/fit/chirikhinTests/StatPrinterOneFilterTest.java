package ru.nsu.fit.chirikhinTests;

import org.junit.Test;
import ru.nsu.fit.chirikhin.EmptyFilter;
import ru.nsu.fit.chirikhin.FileExtensionFilter;
import ru.nsu.fit.chirikhin.StatPrinterOneFilter;
import ru.nsu.fit.chirikhin.Statistics;

public class StatPrinterOneFilterTest {
    @Test
    public void testPrintStatistics() {
        StatPrinterOneFilter printer = new StatPrinterOneFilter();
        Statistics stat = new Statistics();
        stat.updateStatistics(new FileExtensionFilter("txt"), 50, 53);
        stat.updateStatistics(new FileExtensionFilter("txt"), 100, 55);
        stat.updateStatistics(new FileExtensionFilter("tx"), 50, 90);
        stat.updateStatistics(new FileExtensionFilter("bmp"), 20, 60);
        stat.updateStatistics(new EmptyFilter(), 100, 200);
        printer.printStatistics(stat);
    }
}
