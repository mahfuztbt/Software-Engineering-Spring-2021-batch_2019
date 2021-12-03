package com.mitonal.edu.service;

import com.mitonal.edu.common.service.AbstractTypedService;
import com.mitonal.edu.dao.SelectionDao;
import com.mitonal.edu.dao.TClassDao;
import com.mitonal.edu.entity.Selection;
import com.mitonal.edu.entity.TClass;
import org.springframework.stereotype.Service;

@Service
public class TClassService extends AbstractTypedService<TClass, Long> {

	public TClassService(TClassDao dao){
		this.dataContext=dao;
	}
}
