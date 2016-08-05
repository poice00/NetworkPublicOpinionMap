package com.om.service;

import java.util.List;

import com.om.base.DaoSupport;
import com.om.domain.DataSource;
import com.om.domain.Datasource1;

public interface DataSourceService extends DaoSupport<Datasource1> {

	List<Datasource1> getByName(String name);
	
	/**
	 * @desc 获取指定年份和月份的帖子
	 * @param year 年份
	 * @param month 月份
	 * @return 帖子集合，DataSource类型
	 * @author yanbaobin@yeah.net
	 * @date Nov 29, 2015 3:42:38 PM
	 */
	List<DataSource> getByYearAndMonth(int year,int month);
	
	
}
 