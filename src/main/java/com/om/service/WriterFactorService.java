package com.om.service;

import java.util.List;

import com.om.base.DaoSupport;
import com.om.domain.User;
import com.om.domain.Writer;
import com.om.domain.WriterFactor;

public interface WriterFactorService extends DaoSupport<WriterFactor> {

	List<WriterFactor> findByInfluence(int i);

	WriterFactor getByWriterfactorId(String writerFactorId);
	
	List<WriterFactor> findByActiveDegree(int i);

	List<WriterFactor> findByValue(int i);
	
}
