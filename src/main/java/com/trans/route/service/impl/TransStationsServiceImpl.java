package com.trans.route.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.trans.route.dao.TransStationsDao;
import com.trans.route.model.TransStationsEntity;
import com.trans.route.service.ITransStationsService;
@Service
public class TransStationsServiceImpl implements ITransStationsService{
	
	@Resource
	private TransStationsDao transStationsDao;

	@Override
	public List<TransStationsEntity> getStationList() {
		return transStationsDao.getStationList();
	}

}
