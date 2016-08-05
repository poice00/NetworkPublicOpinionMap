package com.om.service;


import java.util.List;
import java.util.Set;

import com.om.base.DaoSupport;
import com.om.domain.DataSource;
import com.om.next.DataPoint;

public interface DataSourceNoTagService extends DaoSupport<DataSource> {

	List<DataSource> getByName(String name);

	List<DataSource> getByWebsiteName(String type);

	List<DataSource> getByClusterResult(String type);

	List<DataSource> getTopByHotNum(String hotNum, int num);

	List<DataSource> getByWriterId(String type,String writerFactorId);

	DataSource getByDocumentId(String datasourceId);
}
