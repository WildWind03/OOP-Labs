package ru.nsu.ccfit.chirikhin.factory;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigParser {
    final static int COUNT_OF_LINES_IN_CONFIG = 8;

    final static String WORKERS_COUNT_STR = "CountOfWorkers";
    final static String DEALERS_COUNT_STR = "CountOfDealers";
    final static String ACCESSORY_SUPPLIERS_COUNT_STR = "CountOfAccessorySuppliers";
    final static String CAR_STORAGE_SIZE_STR = "CarStorageSize";
    final static String ENGINE_STORAGE_SIZE_STR = "EngineStorageSize";
    final static String ACCESSORY_STORAGE_SIZE_STR = "AccessoryStorageSize";
    final static String CAR_BODY_STORAGE_SIZE_STR = "CarBodyStorageSize";
    final static String LOG_STR = "LOGGING";

    private int workersCount;
    private int dealersCount;
    private int accessorySupplCount;
    private int carStorageSize;
    private int engineStorageSize;
    private int accessoryStorageSize;
    private int carBodyStorageSize;
    private boolean isLog;

    public ConfigParser(String pathToConfig) throws IOException, InvalidConfigException, DeveloperBugException {
        if (null == pathToConfig) {
            throw new NullPointerException("Config Parser: can't parse file because null reference has been found!");
        }

        File file =  new File(pathToConfig);

        if (!file.exists()) {
            throw new FileNotFoundException("Config Parser: " + pathToConfig + " doesn't exist");
        }

        if (!file.isFile()) {
            throw new IllegalArgumentException("Config Parser: " + pathToConfig + " is not a file");
        }

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String configLine;

            for (int i = 0; i < COUNT_OF_LINES_IN_CONFIG; ++i) {
                configLine = bufferedReader.readLine();

                if (null == configLine) {
                    throw new InvalidConfigException("Config Parser: invalid config file. " + (i + 1) + " strings has been found instead of " + COUNT_OF_LINES_IN_CONFIG);
                }

                Pattern pattern;
                String lookedForString;

                switch(i) {
                    case 0:
                        lookedForString = CAR_BODY_STORAGE_SIZE_STR;
                        break;
                    case 1:
                        lookedForString = ENGINE_STORAGE_SIZE_STR;
                        break;
                    case 2:
                        lookedForString = ACCESSORY_STORAGE_SIZE_STR;
                        break;
                    case 3 :
                        lookedForString = CAR_STORAGE_SIZE_STR;
                        break;
                    case 4:
                        lookedForString = ACCESSORY_SUPPLIERS_COUNT_STR;
                        break;
                    case 5:
                        lookedForString = WORKERS_COUNT_STR;
                        break;
                    case 6:
                        lookedForString = DEALERS_COUNT_STR;
                        break;
                    case 7:
                        lookedForString = LOG_STR;
                        break;
                    default :
                        throw new DeveloperBugException("Config Parser: can;t solve what string i should look for in config. Unexpected exception");

                }

                pattern = Pattern.compile("^" + lookedForString + "=.+$");

                Matcher matcher = pattern.matcher(configLine);
                if (!matcher.matches()) {
                    throw new InvalidConfigException("Config Parser: invalid config file. Illegal format is string number " + (i + 1));
                }

                int lastIndexOfEquals =  configLine.lastIndexOf('=');
                String arg = configLine.substring(lastIndexOfEquals);
            }
        }
    }

    public int getWorkersCount() {
        return workersCount;
    }

    public int getDealersCount() {
        return dealersCount;
    }

    public int getAccessorySupplCount() {
        return accessorySupplCount;
    }

    public int getCarStorageSize() {
        return carStorageSize;
    }

    public int getEngineStorageSize() {
        return engineStorageSize;
    }

    public int getAccessoryStorageSize() {
        return accessoryStorageSize;
    }

    public int getCarBodyStorageSize() {
        return carBodyStorageSize;
    }

    public boolean isLog() {
        return isLog;
    }
}
