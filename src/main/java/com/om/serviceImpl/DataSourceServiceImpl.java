package com.om.serviceImpl;


import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.om.base.DaoSupportImpl;
import com.om.domain.DataSource;
import com.om.domain.Datasource1;
import com.om.service.DataSourceService;


@Service
@Transactional
@SuppressWarnings("unchecked")
public class DataSourceServiceImpl extends DaoSupportImpl<Datasource1> implements DataSourceService {

	public List<Datasource1> getByName(String name) {
		return (List<Datasource1>) getSession()//
				.createQuery(//
				"FROM Datasource1 ds where ds.classify = ?")//
				.setParameter(0, name)//
				.list();
	}

	@Override
	public List<DataSource> getByYearAndMonth(int year, int month) {
		
		return getSession().createQuery(
				"FROM DataSource WHERE publishTime BETWEEN ? AND ?")
				.setParameter(0, new Date(year - 1900,month -1,1))
				.setParameter(1, new Date(year - 1900,month -1,31))
				.list();
	}

}
