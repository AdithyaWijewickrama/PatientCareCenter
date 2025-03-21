package com.pcc.PatientCareCenter.Views.Components.PccTable;

    import java.util.HashMap;
import java.util.Map;

    public class DynamicTableRow {
        private final Map<String, Object> data = new HashMap<>();

        public void addData(String columnName, Object value) {
            data.put(columnName, value);
        }

        public Object getData(String columnName) {
            return data.get(columnName);
        }

        public Map<String, Object> getData() {
            return data;
        }
    }

