package com.skeleton.dataproviders;

import com.skeleton.utils.ExcelReader;
import com.skeleton.utils.TestDataReader;
import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Map;

/** Bridges Excel -> TestNG. One Excel row = one test execution. Sheet name = method intent. */
public class TestDataProviders {

    private static final TestDataReader READER = new ExcelReader();

    @DataProvider(name = "loginData")
    public static Object[][] loginData() {
        return toArray(READER.readSheet("Login"));
    }

    @DataProvider(name = "cartData")
    public static Object[][] cartData() {
        return toArray(READER.readSheet("Cart"));
    }

    private static Object[][] toArray(List<Map<String, String>> rows) {
        Object[][] data = new Object[rows.size()][1];
        for (int i = 0; i < rows.size(); i++) {
            data[i][0] = rows.get(i);
        }
        return data;
    }
}