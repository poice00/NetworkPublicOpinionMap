package com.om.serviceImpl;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.om.base.DaoSupportImpl;
import com.om.domain.ClassifyType;
import com.om.domain.Theme;
import com.om.service.ClassifyTypeService;
import com.om.service.ThemeService;


@Service
@Transactional
@SuppressWarnings("unchecked")
public class ThemeServiceImpl extends DaoSupportImpl<Theme> implements ThemeService {

	@Override
	public List<Theme> selectTheme() {
		// TODO Auto-generated method stub
		return null;
	}
}
