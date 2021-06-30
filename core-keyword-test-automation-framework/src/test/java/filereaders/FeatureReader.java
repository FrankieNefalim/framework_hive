package filereaders;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A wrapper class to be used to more simply read test feature files.
 *
 * @author  Jonathan Diaz
 * @version 1.0
 * @since   02/13/2019
 */
public class FeatureReader {

    private static final int ACTION = 0;
    private static final int OBJECT = 1;
    private static final int INPUT = 2;
    private static final int OUTPUT = 3;

    private static final int DEFAULT_SHEET_NUMBER = 0;

    private static final int FIRST_ROW_OF_SHEET = 1;

    private static final String PAGE_OBJECT_DELIMITER = "\\|";
    private static final int PAGE_INDEX = 0;
    private static final int OBJECT_INDEX = 1;

    private int currentStepNumber;
    private int stepCount;

    private List<String[]> stepValuesList;

    /**
     * Creates an object that holds the test steps imported from the specified excel spreadsheet.
     * @param featureFileName The name of the test file.
     * @param isCalledTo A boolean to adjust the current step value so that called to feature files can start execution on the correct step.
     */
    public FeatureReader(String featureFileName, boolean isCalledTo) throws Exception {
        String Feature_path = getFeaturePath(featureFileName);
        File file = (new File(Feature_path));
        FileInputStream excelFile = new FileInputStream(file);
        HSSFWorkbook workbook = new HSSFWorkbook(excelFile);

        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        Sheet featureSheet = workbook.getSheetAt(DEFAULT_SHEET_NUMBER);
        stepCount = featureSheet.getLastRowNum() + 1;
        if(isCalledTo){
            currentStepNumber = FIRST_ROW_OF_SHEET-1;
        }
        else {
            currentStepNumber = FIRST_ROW_OF_SHEET;
        }

        // Load all of the test step values into the the list of steps
        stepValuesList = new ArrayList<>(stepCount);
        for (int i = FIRST_ROW_OF_SHEET; i < stepCount; i++) {
            String[] stepValues = new String[4];

            if (featureSheet.getRow(i) != null) {
                if (featureSheet.getRow(i).getCell(ACTION) != null) {
                    stepValues[ACTION] = getCellValueString(featureSheet.getRow(i).getCell(ACTION), evaluator);
                } else {
                    stepValues[ACTION] = "";
                }

                if (featureSheet.getRow(i).getCell(OBJECT) != null) {
                    stepValues[OBJECT] = getCellValueString(featureSheet.getRow(i).getCell(OBJECT), evaluator);
                } else {
                    stepValues[OBJECT] = "";
                }

                if (featureSheet.getRow(i).getCell(INPUT) != null) {
                    stepValues[INPUT] = getCellValueString(featureSheet.getRow(i).getCell(INPUT), evaluator);
                } else {
                    stepValues[INPUT] = "";
                }

                if (featureSheet.getRow(i).getCell(OUTPUT) != null) {
                    stepValues[OUTPUT] = getCellValueString(featureSheet.getRow(i).getCell(OUTPUT), evaluator);
                } else {
                    stepValues[OUTPUT] = "";
                }

                stepValuesList.add(stepValues);
            } else {
                i = stepCount;
            }
        }
        // Close the connection to the excel workbook once all values have been loaded
        excelFile.close();
    }

    /**
     * Get string value of {@link Cell} object
     *
     * @param cell
     *          {@link Cell} object
     * @return String value of {@link Cell} object
     */
    private static String getCellValueString(Cell cell,FormulaEvaluator evaluator) {
        String value="";
        if (cell != null) {
            CellValue cellValue = evaluator.evaluate(cell);
            if (cellValue != null) {
                switch (cell.getCellType()) {
                    case BOOLEAN:
                        value = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case NUMERIC:
                        DataFormatter formatter = new DataFormatter();
                        value = formatter.formatCellValue(cell);
                        break;
                    case STRING:
                        value = String.valueOf(cellValue.getStringValue());
                        break;
                    case FORMULA:
                        value = cellValue.getStringValue();
                        break;
                    case BLANK:
                        value = "";
                        break;
                }
            }
        }
        return value;
    }

    /**
     * Get full path of specific test file name.
     * @param featureFileName The name of the test file.
     */
    private String getFeaturePath(String featureFileName) throws Exception {
        Path start = Paths.get("../keyword-test-automation-framework/src/test/resources/features/");
        Path startM = Paths.get("../mobile-keyword-test-automation-framework/src/test/resources/features/");

        List<String> collect;

        Stream<Path> streamWeb = Files.walk(start);
        Stream<Path> streamMobile = Files.walk(startM);
        Stream<Path> stream = Stream.concat(streamWeb,streamMobile);

        {
            collect = stream
                    .filter(s -> s.getFileName().toString().equals(featureFileName + ".xls"))
                    .map(String::valueOf)
                    .collect(Collectors.toList());
            if (collect.size() == 1) {
                return collect.get(0);
            } else {
                throw new Exception("More of one file with the same name: " + featureFileName);
            }
        }
    }

    /**
     * Creates an object that holds the test steps imported from the specified excel spreadsheet.
     * @param featureFileName The name of the test file.
     */
    public FeatureReader(String featureFileName) throws Exception {
        this(featureFileName, false);
    }

    /**
     * Will advance to the next test step that was in the feature file.
     * @return boolean Will return false once there are no more test steps.
     */
    public boolean nextStep(){
        currentStepNumber++;
        return currentStepNumber < stepCount;
    }

    /**
     * Gets the action value corresponding to the current test step.
     * @return String Will return the current step's action value.
     */
    public String getCurrentAction(){
        return stepValuesList.get(currentStepNumber-1)[ACTION];
    }

    /**
     * Gets the page value corresponding to the current test step.
     * @return String Will return the current step's page value (The name of the web page).
     */
    public String getCurrentPage(){
        return stepValuesList.get(currentStepNumber-1)[OBJECT].split(PAGE_OBJECT_DELIMITER)[PAGE_INDEX];
    }

    /**
     * Gets the object value corresponding to the current test step.
     * @return String Will return the current step's object value (The name of the object).
     */
    public String getCurrentObject(){
        String[] pageObjectInfo = stepValuesList.get(currentStepNumber-1)[OBJECT].split(PAGE_OBJECT_DELIMITER);
        // Only attempt to return the current object if the page object delimiter is used
        if(pageObjectInfo.length > 1){
            return pageObjectInfo[OBJECT_INDEX];
        }
        return "";
    }

    /**
     * Gets the input value corresponding to the current test step.
     * @return String Will return the current step's input value.
     */
    public String getCurrentInput(){
        return stepValuesList.get(currentStepNumber-1)[INPUT];
    }

    /**
     * Gets the output value corresponding to the current test step.
     * @return String Will return the current step's output value.
     */
    public String getCurrentOutput(){
        return stepValuesList.get(currentStepNumber-1)[OUTPUT];
    }

    /**
     * Gets the current test step number that is to be executed.
     * @return int Will return the current step number.
     */
    public int getCurrentStepNumber(){
        return currentStepNumber;
    }

    /**
     * Gets the list of test steps.
     * @return List<String[]> Will return the list of test steps.
     */
    public List<String[]> getStepValuesList(){
        return stepValuesList;
    }
}
