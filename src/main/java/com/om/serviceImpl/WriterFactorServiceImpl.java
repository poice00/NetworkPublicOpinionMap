package com.om.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.om.base.DaoSupportImpl;
import com.om.domain.User;
import com.om.domain.Writer;
import com.om.domain.WriterFactor;
import com.om.service.UserService;
import com.om.service.WriterFactorService;
import com.om.service.WriterService;


@Service
@Transactional
@SuppressWarnings("unchecked")
public class WriterFactorServiceImpl extends DaoSupportImpl<WriterFactor>implements WriterFactorService {

	public List<WriterFactor> findByInfluence(int i) {
		return getSession().createQuery(//
				"FROM WriterFactor wf ORDER BY wf.authorInfluence DESC")//
				.setFirstResult(0)//
				.setMaxResults(i)//
				.list();
	}

	public WriterFactor getByWriterfactorId(String writerFactorId) {
		return (WriterFactor) getSession().get(WriterFactor.class, writerFactorId);
	}

	public List<WriterFactor> findByActiveDegree(int i) {
		return getSession().createQuery(//
				"FROM WriterFactor wf ORDER BY wf.authorActiveDegree DESC")//
				.setFirstResult(0)//
				.setMaxResults(i)//
				.list();
	}

	public List<WriterFactor> findByValue(int i) {
		return getSession().createQuery(//
				"FROM WriterFactor wf ORDER BY wf.authorInfluence2 ASC")//
				.setFirstResult(0)//
				.setMaxResults(i)//
				.list();
	}

}
