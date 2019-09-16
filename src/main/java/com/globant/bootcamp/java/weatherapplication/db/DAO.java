package com.globant.bootcamp.java.weatherapplication.db;

import java.util.List;

public interface DAO <E> {
	Boolean insert(E e);
	Boolean update(E e);
	Boolean delete(E e);
	List<E> selectAll();
	E selectOne(E e);
	
}
