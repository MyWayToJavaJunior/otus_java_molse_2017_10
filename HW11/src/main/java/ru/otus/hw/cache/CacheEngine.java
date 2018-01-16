package ru.otus.hw.cache;

import java.lang.ref.SoftReference; /**
 * Created by tully.
 */
public interface CacheEngine<K, V> {

    void put(MyElement<K, V> element);

    MyElement<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();

    void clean();

}
