package com.treinamento.framework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CollectionsUtil {
    private CollectionsUtil() { }

    public static <T> Collection<T> unmodifiable(Collection<T> collection) {
        return collection == null ? Collections.emptyList() : Collections.unmodifiableCollection(collection);
    }

    public static <T> List<T> unmodifiable(List<T> collection) {
        return collection == null ? Collections.emptyList() : Collections.unmodifiableList(collection);
    }

    public static <T> Set<T> unmodifiable(Set<T> collection) {
        return collection == null ? Collections.emptySet() : Collections.unmodifiableSet(collection);
    }

    public static <K, V> Map<K, V> unmodifiable(Map<K, V> map) {
        return map == null ? Collections.emptyMap() : Collections.unmodifiableMap(map);
    }

    public static <T> List<T> unmodifiable(T[] array) {
        return array == null ? Collections.emptyList() : List.of(array);
    }

    public static <T> List<T> arrayListNullabeSafe(List<T> list) {
        return list == null ? new ArrayList<>() : list;
    }

    public static <T> Set<T> hashSetNullableSafe(Set<T> set) {
        return set == null ? new HashSet<>() : set;
    }
}

