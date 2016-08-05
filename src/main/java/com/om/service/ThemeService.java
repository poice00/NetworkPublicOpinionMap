package com.om.service;

import java.util.List;

import com.om.base.DaoSupport;
import com.om.domain.Theme;

public interface ThemeService extends DaoSupport<Theme> {

	public List<Theme> selectTheme();
	
}
