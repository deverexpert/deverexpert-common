/*******************************************************************************
 * Copyright(c) 2014 deverexpert. All rights reserved.
 * This software is the proprietary information of deverexpert.
 *******************************************************************************/
package kr.pe.deverexpert.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 요청된 Object 객체를 기준으로 특정 Type 의 Class Object 를 생성/복사하는 유틸리티
 * 값을 전달할 목적이 되는 대상 source DTO 를 기준으로 대상 Type 의 class 형식을 입력하면  대상 DTO를 반환한다.
 * 모든 변환은 source DTO 의 field 의 이름을 기준이며 field 갯수가 다른 경우 같은 field 만 mapping 대상이 된다.
 *
 * @author <a href=mailto:deverexpert@gmail.com>이재형</a>
 * @since 2014. 7. 3.
 */
public class TranslateUtil {

    /**
     * source DTO 값을 이용하여 특정 DTO 객체를 생성
     * [주의사항] 변환 조건
     * 1) 두 Class 의 Field 이름이 동일해야 합니다.
     * 2) List 의 경우에는 element 의 형을 지정해야 합니다 (i.e, List<AObj>, List<long>
     * 3) Map 형식은 지원하지 않습니다 .
     * 4) primitive 형식(int, long,...)과 Interger, Long 과는 호환되지 않습니다.
     * 5) 변환될 destination class는 생성자를 선언하지 않거나 기본생성자가 존재해야 합니다.
     *
     * [변환 예외 사항] 다음의 경우에는 변환되지 않습니다.
     * 1) List또는 Array의 element 형식이 List, Collection, Map, Array, 인 경우
     *
     * @param sourceDto : source DTO, destClass : Destination class type
     * @return <T> T : Destination object
     */
    public static <T> T translateDto(Object sourceDto, Class<T> destClass) {
        if (sourceDto == null || destClass == null) throw new RuntimeException("translateDto parameter error");

        TranslateUtil util = new TranslateUtil();
        T destDto = util.createClassInstance(destClass);
        util.mappingDto(sourceDto, destDto);
        return destDto;
    }

    /**
     * @param sourceObj : source DTO, destObj : Destination DTO
     * @return
     */
    private void mappingDto(Object sourceObj, Object destObj) {

        if (sourceObj == null || destObj == null) return;

        try {

            HashMap<String, Field> destFieldMap = getDestinationFieldMap(destObj);

            // Source Object 의 필드별로 Destination Object 의 동일 이름의 Field 탐색
            for (Field srcField : sourceObj.getClass().getDeclaredFields()) {

                // static 제외
                if(Modifier.isStatic(srcField.getModifiers())) continue;

                Field destField = destFieldMap.get(srcField.getName());
                if (destField == null) continue;

                Class<?> srcFieldType = srcField.getType();
                srcField.setAccessible(true);
                destField.setAccessible(true);

                // Source object check
                Object srcFieldObj = srcField.get(sourceObj);
                if (srcFieldObj == null) continue;

                // Do not change sequence
                if (isPrimitive(srcFieldType) || srcFieldType == String.class) {
                    mappingPrimitiveAndString(sourceObj, destObj, srcField, destField);
                } else if (srcFieldType.isArray()) {
                    mappingArray(sourceObj, destObj, srcField, destField);
                } else if (srcFieldType.isEnum()) {
                    mappingEnum(sourceObj, destObj, srcField, destField);
                } else if (srcFieldType == Map.class) {
                    // Unsupported type
                } else if (srcFieldType == Collection.class || srcFieldType == List.class) {
                    mappingCollection(sourceObj, destObj, srcField, destField);
                } else {
                    // Object
                    mappingObject(sourceObj, destObj, srcField, destField);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("mappingDto : mapping error : " + e.getMessage(), e);
        }
    }

    /**
     * @param destObj ; 대상 Object
     * @return 대상 Object 의 필드명과 필드 Object 의 HashMap
     */
    private HashMap<String, Field> getDestinationFieldMap(Object destObj) {
        // Destination 의 같은 이름의 Field 탐색
        Field[] fields = destObj.getClass().getDeclaredFields();
        HashMap<String, Field> fieldMap = new HashMap<String, Field>(fields.length);
        for (Field f : fields) {
            fieldMap.put(f.getName(), f);
        }
        return fieldMap;
    }

    /**
     * @param
     * @throws IllegalAccessException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void mappingCollection(Object source, Object dest, Field srcField, Field destField) throws IllegalAccessException {
        Class<?> srcFieldType = srcField.getType();
        Class<?> destFieldType = destField.getType();

        // Check Type
        if (srcFieldType == destFieldType) {
            Collection<Object> sourceColl = (Collection<Object>) srcField.get(source);

            ParameterizedType srcGenericType = (ParameterizedType) srcField.getGenericType();
            ParameterizedType destGenericType = (ParameterizedType) destField.getGenericType();
            if (srcGenericType.getActualTypeArguments().length == 0 ||
                    destGenericType.getActualTypeArguments().length == 0) {
                throw new RuntimeException("Need generic typed Collection or List");
            }
            Class<?> srcElementType = (Class<?>) srcGenericType.getActualTypeArguments()[0];
            Class<?> destElementType = (Class<?>) destGenericType.getActualTypeArguments()[0];
            Collection colls = new ArrayList(sourceColl.size());
            destField.set(dest, colls);

            for (Object unitSrcObj : sourceColl) {

                if (isPrimitive(srcElementType) || srcElementType == String.class) {
                    // Check same type
                    if (srcElementType == destElementType) {
                        Object unitDestObj = unitSrcObj;
                        colls.add(unitDestObj);
                    }
                } else if (srcElementType.isArray() || srcElementType == Map.class) {
                    // Unsupported List Element Type
                } else if (srcElementType == Collection.class || srcElementType == List.class) {
                    // Unsupported List Element Type
                } else {
                    if (srcElementType.isEnum()) {
                        // Enum
                        Enum<?> destEnum = convertEnum(unitSrcObj, destElementType);
                        if (destEnum != null)
                            colls.add(destEnum);
                    } else {
                        // Object
                        Object unitDestObj = createClassInstance(destElementType);
                        mappingDto(unitSrcObj, unitDestObj);
                        colls.add(unitDestObj);
                    }
                }
            }
        }
    }

    /**
     * @param
     * @throws IllegalAccessException
     */
    private void mappingObject(Object source, Object dest, Field srcField, Field destField) throws IllegalAccessException {
        Object unitDestObj = createClassInstance(destField.getType());
        destField.set(dest, unitDestObj);
        mappingDto(srcField.get(source), unitDestObj);
    }

    /**
     * @param
     * @throws IllegalAccessException
     */
    private void mappingEnum(Object source, Object dest, Field srcField, Field destField) throws IllegalAccessException {

        Enum<?> destEnum = convertEnum(srcField.get(source), destField.getType());
        if (destEnum != null) destField.set(dest, destEnum);
    }

    @SuppressWarnings("unchecked")
    private Enum<?> convertEnum(Object sourceEnum, Class<?> destEnumType) throws IllegalAccessException {
        Enum<?> srcEnumInstance = (Enum<?>) sourceEnum;
        if (destEnumType.getEnumConstants().length != 0) {
            Enum<?> destUnitEnum1 = (Enum<?>) destEnumType.getEnumConstants()[0];
            Enum<?> valueOfDestEnum = (Enum<?>) Enum.valueOf(destUnitEnum1.getClass(), srcEnumInstance.name());
            return valueOfDestEnum;
        }
        return null;
    }

    /**
     * @param
     * @throws IllegalAccessException
     */
    private void mappingPrimitiveAndString(Object source, Object dest, Field srcField, Field destField) throws IllegalAccessException {
        // Check Same Type
        if (srcField.getType() == destField.getType()) {
            destField.set(dest, srcField.get(source));
        }
    }

    /**
     * @param
     * @throws IllegalAccessException
     */
    private void mappingArray(Object source, Object dest, Field srcField, Field destField) throws IllegalAccessException {

        Class<?> srcElementType = srcField.getType().getComponentType();
        Class<?> destElementType = destField.getType().getComponentType();

        if (isPrimitive(srcElementType) || srcElementType == String.class) {

            // only for same type
            if (srcElementType == destElementType) {


                Object srcArray = srcField.get(source);
                int arraySize = Array.getLength(srcArray);

                Object destArray = Array.newInstance(srcElementType, arraySize);
                System.arraycopy(srcArray, 0, destArray, 0, arraySize);
                destField.set(dest, destArray);
            }
        } else if (srcElementType.isArray() || srcElementType == Map.class) {
            // Not Supported Array Type
        } else if (srcElementType == Collection.class || srcElementType == List.class) {
            // Not Supported Array Type
        } else {
            // Object
            Object[] srcArray = (Object[]) srcField.get(source);
            Object[] destArray = (Object[]) Array.newInstance(destElementType, srcArray.length);
            destField.set(dest, destArray);
            for (int i = 0; i < destArray.length; i++) {
                if (srcArray[i] != null) {
                    if (srcElementType.isEnum()) {
                        // Enum
                        Enum<?> destEnum = convertEnum(srcArray[i], destElementType);
                        if (destEnum != null) destArray[i] = destEnum;
                    } else {
                        // Object
                        Object unitDestObj = createClassInstance(destElementType);
                        mappingDto(srcArray[i], unitDestObj);
                        destArray[i] = unitDestObj;
                    }
                }
            }
        }
    }

    private boolean isPrimitive(Class<?> classType) {
        if (classType.isPrimitive()) return true;
        if (classType == Integer.class || classType == Byte.class || classType == Short.class || classType == Boolean.class
                || classType == Long.class || classType == Float.class || classType == Double.class || classType == Character.class) {
            return true;
        }
        return false;
    }

    /**
     * private constructor
     */
    private TranslateUtil() {
    }

    private <T> T createClassInstance(Class<T> destinationClass) {
        try {
            Constructor<T> constructor = destinationClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception exception) {
            throw new RuntimeException("createClassInstance error : " + destinationClass.getName(), exception);
        }
    }

}
