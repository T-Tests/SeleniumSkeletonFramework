package com.skeleton.utils;

import java.util.List;
import java.util.Map;

/** INTERFACE: lets tests depend on "something that reads data", not on Excel specifically. */
public interface TestDataReader {

    /** Each row becomes a Map of column header -> cell text. The first row must be the header. */
    List<Map<String, String>> readSheet(String sheetName);
}