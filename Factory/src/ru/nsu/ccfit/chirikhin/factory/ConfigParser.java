package ru.nsu.ccfit.chirikhin.factory;

import com.sun.xml.internal.ws.util.StringUtils;

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
    final static String LOG_STR = "Logging";

    private int workersCount;
    private int dealersCount;
    private int accessorySupplCount;
    private int carStorageSize;
    private int engineStorageSize;
    private int accessoryStorageSize;
    private int carBodyStorageSize;
    private boolean isLog;

    private boolean isNumber(String string) {
        return string.matches("\\d+");
    }

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
                    throw new InvalidConfigException("Config Parser: invalid config file. " + i + " strings has been found instead of " + COUNT_OF_LINES_IN_CONFIG);
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
                        throw new DeveloperBugException("Config Parser: can;t solve what string I should look for in config. Unexpected exception");

                }

                pattern = Pattern.compile("^" + lookedForString + "=(.+)$");

                Matcher matcher = pattern.matcher(configLine);
                if (!matcher.matches()) {
                    throw new InvalidConfigException("Config Parser: invalid config file. String " + configLine + " is invalid");
                }

                String arg = matcher.group(1);

                if (!isNumber(arg)) {
                    throw new InvalidConfigException("Config Parser: invalid config file. Not numbers have been found after " + lookedForString);
                }

                int argValue = Integer.parseInt(arg);

                switch(i) {
                    case 0:
                        carBodyStorageSize = argValue;
                        break;
                    case 1:
                        engineStorageSize = argValue;
                        break;
                    case 2:
                        accessoryStorageSize = argValue;
                        break;
                    case 3 :
                        carStorageSize = argValue;
                        break;
                    case 4:
                        accessorySupplCount = argValue;
                        break;
                    case 5:
                        workersCount = argValue;
                        break;
                    case 6:
                        dealersCount = argValue;
                        break;
                    case 7:
                        if (argValue > 0) {
                            isLog = true;
                        }
                        else {
                            isLog = false;
                        }
                        break;
                }
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
