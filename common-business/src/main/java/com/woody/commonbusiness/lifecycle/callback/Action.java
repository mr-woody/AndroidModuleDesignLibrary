package com.woody.commonbusiness.lifecycle.callback;

/**
 * 返回对象事件
 * @param <T>
 */
public interface Action<T> {
    void call(T item);
}
