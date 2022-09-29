package com.cognixia.jump.jdbc.dao;

import java.io.Serializable;
import java.util.List;

public interface DAO<T extends Serializable>{

	T findById(long id);
	
	boolean update(T entity);
	boolean delete(T entity);
	boolean create(T entity);
}
