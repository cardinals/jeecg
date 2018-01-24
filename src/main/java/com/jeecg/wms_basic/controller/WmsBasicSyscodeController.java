package com.jeecg.wms_basic.controller;
import com.jeecg.wms_basic.entity.WmsBasicSyscodeEntity;
import com.jeecg.wms_basic.service.WmsBasicSyscodeServiceI;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**   
 * @Title: Controller  
 * @Description: wms_basic_Syscode
 * @author onlineGenerator
 * @date 2018-01-22 17:29:06
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wmsBasicSyscodeController")
public class WmsBasicSyscodeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WmsBasicSyscodeController.class);

	@Autowired
	private WmsBasicSyscodeServiceI wmsBasicSyscodeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * wms_basic_Syscode列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/jeecg/wms_basic/wmsBasicSyscodeList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(WmsBasicSyscodeEntity wmsBasicSyscode,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WmsBasicSyscodeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmsBasicSyscode, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.wmsBasicSyscodeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除wms_basic_Syscode
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WmsBasicSyscodeEntity wmsBasicSyscode, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wmsBasicSyscode = systemService.getEntity(WmsBasicSyscodeEntity.class, wmsBasicSyscode.getId());
		message = "wms_basic_Syscode删除成功";
		try{
			wmsBasicSyscodeService.delete(wmsBasicSyscode);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "wms_basic_Syscode删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除wms_basic_Syscode
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "wms_basic_Syscode删除成功";
		try{
			for(String id:ids.split(",")){
				WmsBasicSyscodeEntity wmsBasicSyscode = systemService.getEntity(WmsBasicSyscodeEntity.class, 
				id
				);
				wmsBasicSyscodeService.delete(wmsBasicSyscode);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "wms_basic_Syscode删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加wms_basic_Syscode
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WmsBasicSyscodeEntity wmsBasicSyscode, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "wms_basic_Syscode添加成功";
		try{
			wmsBasicSyscodeService.save(wmsBasicSyscode);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "wms_basic_Syscode添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新wms_basic_Syscode
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WmsBasicSyscodeEntity wmsBasicSyscode, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "wms_basic_Syscode更新成功";
		WmsBasicSyscodeEntity t = wmsBasicSyscodeService.get(WmsBasicSyscodeEntity.class, wmsBasicSyscode.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wmsBasicSyscode, t);
			wmsBasicSyscodeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "wms_basic_Syscode更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * wms_basic_Syscode新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WmsBasicSyscodeEntity wmsBasicSyscode, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmsBasicSyscode.getId())) {
			wmsBasicSyscode = wmsBasicSyscodeService.getEntity(WmsBasicSyscodeEntity.class, wmsBasicSyscode.getId());
			req.setAttribute("wmsBasicSyscodePage", wmsBasicSyscode);
		}
		return new ModelAndView("com/jeecg/wms_basic/wmsBasicSyscode-add");
	}
	/**
	 * wms_basic_Syscode编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WmsBasicSyscodeEntity wmsBasicSyscode, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wmsBasicSyscode.getId())) {
			wmsBasicSyscode = wmsBasicSyscodeService.getEntity(WmsBasicSyscodeEntity.class, wmsBasicSyscode.getId());
			req.setAttribute("wmsBasicSyscodePage", wmsBasicSyscode);
		}
		return new ModelAndView("com/jeecg/wms_basic/wmsBasicSyscode-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","wmsBasicSyscodeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WmsBasicSyscodeEntity wmsBasicSyscode,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WmsBasicSyscodeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wmsBasicSyscode, request.getParameterMap());
		List<WmsBasicSyscodeEntity> wmsBasicSyscodes = this.wmsBasicSyscodeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"wms_basic_Syscode");
		modelMap.put(NormalExcelConstants.CLASS,WmsBasicSyscodeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("wms_basic_Syscode列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,wmsBasicSyscodes);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WmsBasicSyscodeEntity wmsBasicSyscode,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"wms_basic_Syscode");
    	modelMap.put(NormalExcelConstants.CLASS,WmsBasicSyscodeEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("wms_basic_Syscode列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<WmsBasicSyscodeEntity> listWmsBasicSyscodeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WmsBasicSyscodeEntity.class,params);
				for (WmsBasicSyscodeEntity wmsBasicSyscode : listWmsBasicSyscodeEntitys) {
					wmsBasicSyscodeService.save(wmsBasicSyscode);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<WmsBasicSyscodeEntity> list() {
		List<WmsBasicSyscodeEntity> listWmsBasicSyscodes=wmsBasicSyscodeService.getList(WmsBasicSyscodeEntity.class);
		return listWmsBasicSyscodes;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		WmsBasicSyscodeEntity task = wmsBasicSyscodeService.get(WmsBasicSyscodeEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody WmsBasicSyscodeEntity wmsBasicSyscode, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmsBasicSyscodeEntity>> failures = validator.validate(wmsBasicSyscode);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wmsBasicSyscodeService.save(wmsBasicSyscode);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = wmsBasicSyscode.getId();
		URI uri = uriBuilder.path("/rest/wmsBasicSyscodeController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody WmsBasicSyscodeEntity wmsBasicSyscode) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<WmsBasicSyscodeEntity>> failures = validator.validate(wmsBasicSyscode);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			wmsBasicSyscodeService.saveOrUpdate(wmsBasicSyscode);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		wmsBasicSyscodeService.deleteEntityById(WmsBasicSyscodeEntity.class, id);
	}
}
