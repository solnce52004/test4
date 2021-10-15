package dev.example.entities.filters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DynamicUserFilter {
    public static final String LIMIT_BY_IS_ACTUAL = "limitByIsActual";
    public static final String LIMIT_BY_IS_ACTUAL_PARAM_IS_ACTUAL = "IsActual";
    public static final String LIMIT_BY_IS_ACTUAL_PARAM_CREATED_AT = "createdAt";
}
