package com.mitonal.edu.service;

import com.mitonal.edu.common.service.AbstractTypedService;
import com.mitonal.edu.dao.SelectionDao;
import com.mitonal.edu.dao.StudentDao;
import com.mitonal.edu.entity.Selection;
import com.mitonal.edu.entity.Student;
import org.springframework.stereotype.Service;

@Service
public class SelectionService extends AbstractTypedService<Selection, Long> {

	public SelectionService(SelectionDao dao){
		this.dataContext=dao;
	}
}
