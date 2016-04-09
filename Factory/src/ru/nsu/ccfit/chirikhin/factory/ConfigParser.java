package ru.nsu.ccfit.chirikhin.factory;


import java.io.*;
import java.util.Properties;

public class ConfigParser {

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
        if (null == string) {
            throw new NullPointerException("ConfigParser: trying to know if null is a number!");
        }
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
            accessoryStorageSize = Integer.parseInt(accessoryStorageSizeStr);
            carBodyStorageSize = Integer.parseInt(carBodyStorageSizeStr);
            if (Integer.parseInt(logStr) <= 0) {
                isLog = false;
            }
            else {
                isLog = true;
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
