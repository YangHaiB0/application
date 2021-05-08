package com.yanghb.application.utils;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class BeanUtil extends BeanUtils {
    /**
     * org.springframework.beans.BeanUtils copy 属性时忽略null
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        String[] nullProperties = Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null).toArray(String[]::new);
        List<String> properties = new ArrayList<>(Arrays.asList(nullProperties));
        BeanUtils.copyProperties(source, target, properties.toArray(new String[0]));
    }

    /**
     * org.springframework.beans.BeanUtils copy 属性时忽略null和指定的属性
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        String[] nullProperties = Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null).toArray(String[]::new);
        List<String> properties = new ArrayList<>();
        if (ignoreProperties != null) {
            properties.addAll(Arrays.asList(ignoreProperties));
        }
        properties.addAll(Arrays.asList(nullProperties));
        BeanUtils.copyProperties(source, target, properties.toArray(new String[0]));
    }

}
