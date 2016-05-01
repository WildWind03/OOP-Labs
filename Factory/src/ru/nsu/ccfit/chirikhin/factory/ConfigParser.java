package ru.nsu.ccfit.chirikhin.factory;


import java.io.*;
import java.util.Properties;

public class ConfigParser {

    private final static String WORKERS_COUNT_STR = "CountOfWorkers";
    private final static String DEALERS_COUNT_STR = "CountOfDealers";
    private final static String ACCESSORY_SUPPLIERS_COUNT_STR = "CountOfAccessorySuppliers";
    private final static String CAR_STORAGE_SIZE_STR = "CarStorageSize";
    private final static String ENGINE_STORAGE_SIZE_STR = "EngineStorageSize";
    private final static String ACCESSORY_STORAGE_SIZE_STR = "AccessoryStorageSize";
    private final static String CAR_BODY_STORAGE_SIZE_STR = "CarBodyStorageSize";
    private final static String LOG_STR = "Logging";

    private final int workersCount;
    private final int dealersCount;
    private final int accessorySupplCount;
    private final int carStorageSize;
    private final int engineStorageSize;
    private final int accessoryStorageSize;
    private final int carBodyStorageSize;
    private final boolean isLog;

    private boolean isNumber(String string) {
        if (null == string) {
            throw new NullPointerException("ConfigParser: trying to know if null is a number!");
        }
        return string.matches("\\d+");
    }

    public ConfigParser(String pathToConfig) throws IOException, InvalidConfigException {
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

            Properties properties = new Properties();
            properties.load(bufferedReader);

            String workersCountStr = properties.getProperty(WORKERS_COUNT_STR);
            String dealersCountStr = properties.getProperty(DEALERS_COUNT_STR);
            String accessorySuppliersCountStr = properties.getProperty(ACCESSORY_SUPPLIERS_COUNT_STR);
            String carStorageSizeStr = properties.getProperty(CAR_STORAGE_SIZE_STR);
            String engineStorageSizeStr = properties.getProperty(ENGINE_STORAGE_SIZE_STR);
            String accessoryStorageSizeStr = properties.getProperty(ACCESSORY_STORAGE_SIZE_STR);
            String carBodyStorageSizeStr = properties.getProperty(CAR_BODY_STORAGE_SIZE_STR);
            String logStr = properties.getProperty(LOG_STR);

            if (null == workersCountStr || null == dealersCountStr || null == accessorySuppliersCountStr || null == carStorageSizeStr
                    || null == engineStorageSizeStr || null == accessoryStorageSizeStr || null == carBodyStorageSizeStr || null == logStr) {
                throw new InvalidConfigException("Config Parser: invalid config file");
            }

            if (!isNumber(workersCountStr) || !isNumber(dealersCountStr) || !isNumber(accessoryStorageSizeStr) || !isNumber(carStorageSizeStr)
                    || !isNumber(engineStorageSizeStr) || !isNumber(accessoryStorageSizeStr) || !isNumber(carBodyStorageSizeStr) || !isNumber(logStr)) {
                throw new InvalidConfigException("Config Parser: not numbers have been found as values in config!");
            }

            accessorySupplCount = Integer.parseInt(accessorySuppliersCountStr);
            workersCount = Integer.parseInt(workersCountStr);
            dealersCount = Integer.parseInt(dealersCountStr);
            accessoryStorageSize = Integer.parseInt(accessoryStorageSizeStr);
            carStorageSize = Integer.parseInt(carStorageSizeStr);
            engineStorageSize = Integer.parseInt(engineStorageSizeStr);
            carBodyStorageSize = Integer.parseInt(carBodyStorageSizeStr);
            isLog = Integer.parseInt(logStr) > 0;
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
