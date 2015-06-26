package com.innshine.orderhelp.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.base.log.Log;
import com.innshine.orderhelp.service.OrderhelpService;


@Controller
@RequestMapping("/orderhelp")
public class OrderhelpController {
	
	@Autowired
	private OrderhelpService orderhelpService;
	
	@Log(message = "上传了{0}文件成功。") 
	@RequiresPermissions("orderhelp:view")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<String> upload(@RequestParam MultipartFile[] files)
	{
		try{
			orderhelpService.helpOrder(files);
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	@RequiresPermissions("orderhelp:view")
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String list(){
		return "/scheduler/job/helporder";
	}
}
