package com.om.service;

import java.util.List;

import com.om.base.DaoSupport;
import com.om.domain.ClassifyType;

public interface ClassifyTypeService extends DaoSupport<ClassifyType> {

	ClassifyType getByName(String classifyName);
	
}
