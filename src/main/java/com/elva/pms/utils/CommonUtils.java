package com.elva.pms.utils;

import java.util.List;

public class CommonUtils {
    public static  <T> T getWithDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static String generateLimitQuery(Integer limit, Integer offset, List<Object> values) {
        if (limit != null && limit < 0) {
            return "";
        }
        String limitQuery = "LIMIT ? OFFSET ?";
        values.add(CommonUtils.getWithDefault(limit, 10));
        values.add(CommonUtils.getWithDefault(offset, 0));
        return limitQuery;
    }


}
