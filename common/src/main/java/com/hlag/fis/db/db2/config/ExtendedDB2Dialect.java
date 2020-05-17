package com.hlag.fis.db.db2.config;

import org.hibernate.dialect.DB2Dialect;

public class ExtendedDB2Dialect extends DB2Dialect {

    @Override
    public String getQueryHintString(String query, String hintString) {
        if (!hintString.isEmpty() && !hintString.equals("null")) {
            String[] hints = hintString.split(",");
            for (String hint : hints) {
                query += " " + hint;
            }
        }
        return query;
    }
}
