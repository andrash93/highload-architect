package ru.otus.architect.cache;

import org.springframework.lang.Nullable;

import java.util.List;

public interface Cache<K,V> {

    <T> T get(K key);

    void put(K key, @Nullable V val);

    void putAll(K key, List<V> val);

    long size(K key);
}
