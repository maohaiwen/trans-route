package com.trans.route.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trans.route.model.TransStationsEntity;
import com.trans.route.service.ITransStationsService;

@Controller
public class TestController {
	
	@Resource
	private ITransStationsService transStationsService;

	@ResponseBody
	@RequestMapping("test")
	public List<TransStationsEntity> test() {
		List<TransStationsEntity> list = transStationsService.getStationList();
		return list;
	}
	
}
