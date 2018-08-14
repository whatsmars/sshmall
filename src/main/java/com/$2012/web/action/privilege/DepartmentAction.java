package com.$2012.web.action.privilege;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.$2012.entity.privilege.Department;
import com.$2012.service.privilege.DepartmentService;
import com.$2012.vo.PageContext;
import com.$2012.vo.QueryResult;
import com.opensymphony.xwork2.ActionSupport;

@Component
@Scope("prototype")
public class DepartmentAction extends ActionSupport {
	private static final long serialVersionUID = 7375042317073309266L;
	private String departmentId;
	private String name;
	private DepartmentService departmentService;
	private List<Department> departments = new ArrayList<Department>();
	private PageContext<Department> pageCtx;
	
	public String list() {
		pageCtx = new PageContext<Department>(12, pageCtx.getCurrentPage());
		QueryResult<Department> qr = departmentService.getScrollData(pageCtx.getFirstResult(), pageCtx.getMaxResults());
		departments = qr.getResultList();
		pageCtx.setQueryResult(qr);
		return SUCCESS;
	}
	
	public String add() {
		this.departmentService.save(new Department(name));
		return SUCCESS;
	}
	
	public String update() {
		Department department = this.departmentService.find(departmentId);
		department.setName(name);
		this.departmentService.update(department);
		return SUCCESS;
	}
	
	public String delete() {
		this.departmentService.delete(departmentId);
		return SUCCESS;
	}
	
	public DepartmentService getDepartmentService() {
		return departmentService;
	}
	@Resource
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public PageContext<Department> getPageCtx() {
		return pageCtx;
	}
	@Resource
	public void setPageCtx(PageContext<Department> pageCtx) {
		this.pageCtx = pageCtx;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
