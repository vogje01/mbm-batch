package com.hlag.fis.db.converter;

public interface PersistableEnum<T> {

	T getValue();

    T getNullValue();
}