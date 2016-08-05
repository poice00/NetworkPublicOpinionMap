package com.om.serviceImpl;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.om.base.DaoSupportImpl;
import com.om.domain.ClassifyType;
import com.om.service.ClassifyTypeService;


@Service
@Transactional
@SuppressWarnings("unchecked")
public class ClassifyTypeServiceImpl extends DaoSupportImpl<ClassifyType> implements ClassifyTypeService {

	public ClassifyType getByName(String classifyName) {
		return (ClassifyType) getSession()//
				.createQuery(//
				"FROM ClassifyType ct where ct.classifyName = ?")//
				.setParameter(0, classifyName)//
				.uniqueResult();
	}

}
