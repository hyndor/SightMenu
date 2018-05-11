package ru.hyndo.sightmenu.loader;

import com.google.common.collect.ForwardingMap;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.UnaryOperator;

public class ProxyHashMap<K, V> extends ForwardingMap<K, V> {

    private Map<K, V> delegate;

    private UnaryOperator<V> mapper;

    public ProxyHashMap(Map<K, V> delegate, UnaryOperator<V> mapper) {
        this.delegate = delegate;
        this.mapper = mapper;
    }

    @Override
    public V get(@Nullable Object key) {
        V v = delegate.get(key);
        if (v != null) {
            return mapper.apply(v);
        }
        return null;
    }

    public void setMapper(UnaryOperator<V> mapper) {
        this.mapper = mapper;
    }

    @Override
    protected Map<K, V> delegate() {
        return delegate;
    }
}
