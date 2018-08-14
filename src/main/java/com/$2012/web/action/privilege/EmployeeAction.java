package com.$2012.web.action.privilege;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.$2012.entity.privilege.Department;
import com.$2012.entity.privilege.Employee;
import com.$2012.entity.privilege.IDCard;
import com.$2012.entity.privilege.PrivilegeGroup;
import com.$2012.service.privilege.DepartmentService;
import com.$2012.service.privilege.EmployeeService;
import com.$2012.service.privilege.PrivilegeGroupService;
import com.$2012.utils.WebUtils;
import com.$2012.vo.FileContext;
import com.$2012.vo.Gender;
import com.$2012.vo.PageContext;
import com.$2012.vo.QueryResult;
import com.opensymphony.xwork2.ActionSupport;

@Component
@Scope("prototype")
public class EmployeeAction extends ActionSupport {
	private static final long serialVersionUID = -37446151411895905L;
	private Employee employee;
	private IDCard idCard;
	private List<Employee> employees = new ArrayList<Employee>();
	private EmployeeService employeeService;
	private PageContext<Employee> pageCtx;
	private FileContext fileCtx;
	private String query;
	private String gender;
	private String departmentId;
	private DepartmentService departmentService;
	
	private String name;//empName
	private String msg;
	
	private PageContext<Department> pageCtx2;
	private List<Department> departments = new ArrayList<Department>();
	
	private Set<PrivilegeGroup> empGroups = new HashSet<PrivilegeGroup>();
	private PrivilegeGroupService privilegeGroupService;
	private List<PrivilegeGroup> privilegeGroups = new ArrayList<PrivilegeGroup>();
	
	private String[] groupIds;
	
	private String visible;
	
	public String list() {
		pageCtx = new PageContext<Employee>(12, pageCtx.getCurrentPage());
		StringBuilder whereql = new StringBuilder("1=1");
		List<Object> params = new ArrayList<Object>();
		if ("true".equals(query)) {//执行查询
			String name = this.employee.getName();
			String realname = this.employee.getRealname();
			if (name != null && !"".equals(name.trim())) {
				whereql.append(" and o.name like ?");
				params.add("%" + name + "%");
			}
			if (realname != null && !"".equals(realname.trim())) {
				whereql.append(" and o.realname like ?");
				params.add("%" + realname + "%");
			}
			if (departmentId != null && !"".equals(departmentId.trim())) {
				whereql.append(" and o.department.departmentId=?");
				params.add(departmentId);
			}
			if (visible != null) {
				whereql.append(" and o.visible=?");
				params.add(Boolean.valueOf(visible));
			}
		}
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("name", "asc");
		QueryResult<Employee> qr = employeeService.getScrollData(pageCtx.getFirstResult(), pageCtx.getMaxResults(), whereql.toString(), params, orderby);
		employees = qr.getResultList();
		pageCtx.setQueryResult(qr);
		return SUCCESS;
	}
	
	/*
	 * 检测用户名是否已存在
	 */
	public String checkName() {
		if (employeeService.exists(name)) {
			msg = "该用户名已注册";
		} else {
			msg = "恭喜，该用户名可注册";
		}
		
		WebUtils.ajaxCallback(msg, "html");
		
		return null;
	}
	
	public String add() {
		if (gender != null) employee.setGender(Gender.valueOf(gender));
		if (departmentId != null && !"".equals(departmentId))   
			                        //也可直接构造new
			employee.setDepartment(this.departmentService.find(departmentId));
		//上传文件并返回保存路径
		String imagePath = fileCtx.makeUploadPath(fileCtx, 
				"\\images\\employee\\", 1024*1024*10);
		employee.setEmpImagePath(imagePath);
		if (idCard != null) employee.setIdCard(idCard);
		employees.add(employee);
		this.employeeService.save(employee);
		return SUCCESS;
	}
	
	public String showUpdateUI() {
		departments = departmentService.getScrollData().getResultList();
		employee = this.employeeService.find(name);
		idCard = employee.getIdCard();
		return SUCCESS;
	}
	
	public String update() {
		Employee emp = this.employeeService.find(name);
		emp.setDegree(this.employee.getDegree());
		if (null != departmentId) emp.setDepartment(this.departmentService.find(departmentId));
		emp.setEmail(this.employee.getEmail());
		String empImagePath = fileCtx.makeUploadPath(fileCtx, 
				"\\images\\employee\\", 1024*1024*10);
		if (empImagePath != null && !"".equals(empImagePath))
			emp.setEmpImagePath(empImagePath);
		emp.setGender(Gender.valueOf(gender));
		IDCard card = new IDCard();
		card.setAddress(this.idCard.getAddress());
		card.setBirthday(this.idCard.getBirthday());
		card.setCardNo(this.idCard.getCardNo());
		emp.setIdCard(card);
		emp.setPhone(this.employee.getPhone());
		emp.setRealname(this.employee.getRealname());
		emp.setSchool(this.employee.getSchool());
		this.employeeService.update(emp);
		return SUCCESS;
	}
	
	public String leave() {
		this.employeeService.delete(name);
		return SUCCESS;
	}
	
	public String showPrivilegeGroupSetUI() {
		this.privilegeGroups = this.privilegeGroupService.getScrollData().getResultList();
		Employee emp = this.employeeService.find(name);
		this.empGroups = emp.getGroups();
		return SUCCESS;
	}
	
	public String setPrivilegeGroup() {
		Employee emp = this.employeeService.find(name);
		emp.clearGroups();
		for (String id : groupIds) {
			PrivilegeGroup group = this.privilegeGroupService.find(id);
			emp.addGroups(group);
		}
		this.employeeService.update(emp);
		return SUCCESS;
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public PageContext<Employee> getPageCtx() {
		return pageCtx;
	}
	@Resource
	public void setPageCtx(PageContext<Employee> pageCtx) {
		this.pageCtx = pageCtx;
	}

	public FileContext getFileCtx() {
		return fileCtx;
	}
	@Resource
	public void setFileCtx(FileContext fileCtx) {
		this.fileCtx = fileCtx;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}
	@Resource
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}
	@Resource
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public IDCard getIdCard() {
		return idCard;
	}

	public void setIdCard(IDCard idCard) {
		this.idCard = idCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public PageContext<Department> getPageCtx2() {
		return pageCtx2;
	}
	@Resource
	public void setPageCtx2(PageContext<Department> pageCtx2) {
		this.pageCtx2 = pageCtx2;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Set<PrivilegeGroup> getEmpGroups() {
		return empGroups;
	}

	public void setEmpGroups(Set<PrivilegeGroup> empGroups) {
		this.empGroups = empGroups;
	}

	public PrivilegeGroupService getPrivilegeGroupService() {
		return privilegeGroupService;
	}
	@Resource
	public void setPrivilegeGroupService(PrivilegeGroupService privilegeGroupService) {
		this.privilegeGroupService = privilegeGroupService;
	}

	public List<PrivilegeGroup> getPrivilegeGroups() {
		return privilegeGroups;
	}

	public void setPrivilegeGroups(List<PrivilegeGroup> privilegeGroups) {
		this.privilegeGroups = privilegeGroups;
	}

	public String[] getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

}
