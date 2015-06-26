/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.base.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletRequest;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.base.service.BrandService;
import com.base.util.dwz.AjaxObject;
import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;
import com.base.entity.main.Brand;
import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls; 

@Controller
@RequestMapping("/management/security/brand")
public class BrandController {
    private static final Logger log = LoggerFactory.getLogger(BrandController.class);
	@Autowired
	private BrandService brandService;
	
	private static final String CREATE = "/management/security/brand/create";
	private static final String UPDATE = "/management/security/brand/update";
	private static final String LIST = "/management/security/brand/list";
	private static final String VIEW = "/management/security/brand/view";
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, 
				new CustomDateEditor(df, true));
	}
	
	@RequiresPermissions("Brand:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		return CREATE;
	}
	
	@Log(message="添加了id={0}Brand。")
	@RequiresPermissions("Brand:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid Brand brand) {
		brandService.saveOrUpdate(brand);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{brand.getId()}));
		return AjaxObject.newOk("品牌添加成功！").toString();
	}
	
	@ModelAttribute("preloadBrand")
	public Brand preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Brand brand = brandService.get(id);
			return brand;
		}
		return null;
	}
	
	@RequiresPermissions("Brand:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		Brand brand = brandService.get(id);
		map.put("brand", brand);
		return UPDATE;
	}
	
	@Log(message="修改了id={0}Brand的信息。")
	@RequiresPermissions("Brand:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadBrand")Brand brand) {
		brandService.saveOrUpdate(brand);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{brand.getId()}));
		return AjaxObject.newOk("品牌修改成功！").toString();
	}

	@Log(message="删除了id={0}Brand。")
	@RequiresPermissions("Brand:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		brandService.delete(id);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{id}));
		return AjaxObject.newOk("品牌删除成功！").setCallbackType("").toString();
	}
	
	@Log(message="批量删除了id={0}Brand。")
	@RequiresPermissions("Brand:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			Brand brand = brandService.get(ids[i]);
			brandService.delete(brand.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(ids)}));
		return AjaxObject.newOk("品牌删除成功！").setCallbackType("").toString();
	}

	@RequiresPermissions("Brand:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<Brand> specification = DynamicSpecifications.bySearchFilter(request, Brand.class);
		List<Brand> brands = brandService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("brands", brands);

		return LIST;
	}
	
	@RequiresPermissions("Brand:view")
	@RequestMapping(value="/view/{id}", method={RequestMethod.GET})
	public String view(@PathVariable Long id, Map<String, Object> map) {
		Brand brand = brandService.get(id);
		map.put("brand", brand);
		return VIEW;
	}
}
