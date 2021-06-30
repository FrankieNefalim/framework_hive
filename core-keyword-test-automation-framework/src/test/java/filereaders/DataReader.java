package filereaders;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import utilities.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A class to make use of data repositories.
 * Will generate dataRepository based on the data definitions in the globalVariables and TCs Variable repo file.
 *
 * @author Santiago Passalacqua
 * @version 1.0
 * @since 10/04/2019
 */
public class DataReader {

    private final String GLOBAL_DATA_FILE_LOCATION = "src/test/resources/data/globalVariables.properties";
    private final String DATA_PATH = "src/test/resources/data/";
    private final String PAGE_OBJECT_DELIMITER = "\\|";
    private final int COLUMN_TO_START_TO_READ_VARIABLES = 3;

    private Properties properties;
    private int rowCount;
    private int columnCount;

    /**
     * Creates an object that holds the test data imported from the globalVariables.properties file.
     */
    public DataReader(Config configuration) {
        InputStream input = null;
        properties = new Properties();
        try {
            input = new FileInputStream(GLOBAL_DATA_FILE_LOCATION);
            properties.load(input);
            String env = configuration.getConfig("env");
            if (env != null){
                if (!env.equals("")) {
                    properties.setProperty("env", env);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Load into the data object all variables imported from the specified excel and dataId spreadsheet.
     *
     * @param dataFileName The name of the data file.
     * @param dataId       An id to indicate the row where the variables are for the current test.
     */
    public void loadTestCaseVariablesData(String dataFileName, String dataId) {
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File(DATA_PATH + dataFileName));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

        Sheet dataSheet = workbook.getSheet(getValueForEnvironment("Tab"));

        rowCount = dataSheet.getRows();

        for (int i = 0; i < rowCount; i++) {

            if (dataSheet.getCell(0, i).getContents().equals(dataId)) {

                columnCount = dataSheet.getRow(i).length;
                // Load all of the test variables values into the properties object
                for (int j = 0; j < columnCount - COLUMN_TO_START_TO_READ_VARIABLES; j++) {
                    String cellValues[] = dataSheet.getCell(j + COLUMN_TO_START_TO_READ_VARIABLES, i).getContents().split(PAGE_OBJECT_DELIMITER);
                    if (cellValues!=null && cellValues.length>=2) {
	                    String key = cellValues[0];
	                    String value = cellValues[1];
	                    this.setValueForEnvironment(key, value);
                    }
                }
                break;
            }
        }
        workbook.close();
    }

    /**
     * Add variables into the data repository without environment
     *
     * @param key   variable name
     * @param value variable value
     */
    public void setValue(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * Will get a variable value according to the variable name provided,
     * it returns the value when the variable doesn't has an Environment as prefix.
     *
     * @param key The variable name
     * @return The variable value
     */
    public String getValue(String key) {
        return properties.getProperty(key);
    }

    /**
     * Add variables into the data repository according to the environment defined into the Global Variables.
     *
     * @param key   variable name
     * @param value variable value
     */
    public void setValueForEnvironment(String key, String value) {
        String env = properties.getProperty("env");
        String envKey = env + "." + key;
        properties.setProperty(envKey, value);
    }

    /**
     * Will get a variable value according to the variable name provided,
     * it returns the value when the variable has an Environment as prefix.
     *
     * @param key The variable name without environment prefix
     * @return The variable value
     */
    public String getValueForEnvironment(String key) {
        String env = properties.getProperty("env");
        String envKey = env + "." + key;
        String value = getValue(envKey);
        return value != null && !value.equalsIgnoreCase("NULL") ? value : "";
    }
}