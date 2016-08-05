package com.om.base;

import java.util.List;

import org.hibernate.Session;


public interface DaoSupport<T> {
	/**
	 * 保存实体
	 * @param entity
	 */
	void save(T entity);
	
	/**
	 * 删除实体
	 * @param entity
	 */
	void delete(Long id);
	/**
	 * 按ID查询
	 * @param id
	 * @return
	 */
	
	T getById(Long id);
	
	/**
	 * 更新实体
	 * @param entity
	 */
	void update(T entity);
	
	/**
	 * 按ID查询
	 * @param ids
	 * @return
	 */
	List<T> getByIds(Long[] ids);
	
	/**
	 * 查询所有
	 * @return
	 */
	List<T> findAll();

	
	/**
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param hql 查询数据列表的HQL
	 * @param parameters 查询列表 与HQL的？一一对应
	 * @return
	 */
	
	public Session getSession();
	

	
}
