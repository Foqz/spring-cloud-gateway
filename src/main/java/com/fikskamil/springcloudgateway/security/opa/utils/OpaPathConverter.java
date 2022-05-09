package com.fikskamil.springcloudgateway.security.opa.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
public class OpaPathConverter {
    private static final String PATH_SEPARATOR = "/";

    public List<String> convert(String path) {
        return Stream.of(path.split(PATH_SEPARATOR))
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());
    }
}
