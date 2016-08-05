package com.om.serviceImpl;



import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.om.base.DaoSupportImpl;
import com.om.domain.DataSource;
import com.om.service.DataSourceNoTagService;


@Service
@Transactional
@SuppressWarnings("unchecked")
public class DataSourceNoTagServiceImpl extends DaoSupportImpl<DataSource> implements DataSourceNoTagService {
	public List<DataSource> getByName(String name) {
		return (List<DataSource>) getSession()//
				.createQuery(//
				"FROM DataSource ds where ds.ssyClassifyResult = ?")//
				.setParameter(0, name)//
				.list();
	}

	public List<DataSource> getByWebsiteName(String type) {
		return (List<DataSource>) getSession()//
				.createQuery(//
				"FROM DataSource ds where ds.websiteSection = ?")//
				.setParameter(0, type)//
				.list();
	}

	public List<DataSource> getByClusterResult(String type) {
		return (List<DataSource>) getSession()//
				.createQuery(//
				"FROM DataSource ds where ds.clusterResult = ?")//
				.setParameter(0, type)//
				.list();
	}

	@Override
	public List<DataSource> getTopByHotNum(String type,int num) {
		return (List<DataSource>) getSession()//
				.createQuery(//
				"FROM DataSource ds where ds.clusterResult = ? ORDER BY ds.hotNum DESC")//
				.setFirstResult(0)//
				.setMaxResults(num)//
				.setParameter(0, type)//
				.list();
	}

	@Override
	public List<DataSource> getByWriterId(String type,String writerFactorId) {
		return (List<DataSource>) getSession()//
				.createQuery(//
				"FROM DataSource ds where ds.writer.writerId = ? AND ssyClassifyResult = ? ")//
				.setParameter(0, writerFactorId)//
				.setParameter(1, type)//
				.list();
	}

	@Override
	public DataSource getByDocumentId(String dataSourceId) {
		return (DataSource) getSession()//
				.createQuery(//
				"FROM DataSource ds where ds.dataSourceId = ? ")//
				.setParameter(0, dataSourceId)//
				.uniqueResult();
	}

}
