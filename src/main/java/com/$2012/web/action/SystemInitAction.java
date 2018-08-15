package com.$2012.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import com.$2012.entity.privilege.Employee;
import com.$2012.entity.privilege.IDCard;
import com.$2012.entity.privilege.PrivilegeGroup;
import com.$2012.entity.privilege.SystemPrivilege;
import com.$2012.entity.product.ProductType;
import com.$2012.service.privilege.EmployeeService;
import com.$2012.service.privilege.PrivilegeGroupService;
import com.$2012.service.privilege.SystemPrivilegeService;
import com.$2012.service.product.ProductTypeService;
import com.$2012.vo.Gender;

import org.apache.struts2.ServletActionContext;

/*
 * 系统初始化（初始化数据库）
 * 虽然系统初始化的工作可以直接在相关bean的init-method方法里进行，但这样
 * 系统重启熟读会较慢，故单独初始化较好（服务器启动后，访问http://localhost/system/init）
 * 另外，初始化工作（方法）较多且相互间有联系时，也适合集中管理
 */
@Component
public class SystemInitAction extends BaseAction {
	private static final long serialVersionUID = -5947901814469803284L;
	private SystemPrivilegeService systemPrivilegeService;
	private PrivilegeGroupService privilegeGroupService;
	private EmployeeService employeeService;
	private ProductTypeService productTypeService;
	
	public String execute() {
		initPrivileges();//初始化系统权限
		initPrivilegeGroups();//初始化权限组
		initAdmin();//初始化系统管理员
		initProductType();//其他初始化
		message = "系统初始化成功";
		urladdress = "/employee/go";
		return SUCCESS;
	}
	
	/*
	 * 初始化系统权限
	 * 根据后台业务和公司各部门管理需求，将后台各模块里涉及到的相关操作封装成系统权限对象，然后存入数据库
	 * 这里只对部分模块进行了权限处理，日后项目真正部署运营时，再具体对所有相关模块进行权限处理
	 */
	private void initPrivileges() {
		if (this.systemPrivilegeService.getCount() == 0) {
			List<SystemPrivilege> privileges = new ArrayList<SystemPrivilege>();
			privileges.add(new SystemPrivilege("department", "add", "部门添加"));
			privileges.add(new SystemPrivilege("department", "list", "部门查看"));
			privileges.add(new SystemPrivilege("department", "update", "部门更新"));
			privileges.add(new SystemPrivilege("department", "delete", "部门删除"));
			
			privileges.add(new SystemPrivilege("employee", "add", "员工添加"));
			privileges.add(new SystemPrivilege("employee", "list", "员工查看"));
			privileges.add(new SystemPrivilege("employee", "update", "员工更新"));
			privileges.add(new SystemPrivilege("employee", "delete", "员工离职"));
			privileges.add(new SystemPrivilege("employee", "privilegeSet", "员工权限分配"));
			privileges.add(new SystemPrivilege("employee", "query", "员工查询"));
			
			privileges.add(new SystemPrivilege("privilegegroup", "add", "权限组添加"));
			privileges.add(new SystemPrivilege("privilegegroup", "list", "权限组查看"));
			privileges.add(new SystemPrivilege("privilegegroup", "update", "权限组更新"));
			privileges.add(new SystemPrivilege("privilegegroup", "delete", "权限组删除"));
			
			privileges.add(new SystemPrivilege("user", "list", "用户查看"));
			privileges.add(new SystemPrivilege("user", "delete", "用户禁用"));
			privileges.add(new SystemPrivilege("user", "enable", "用户启用"));
			
			privileges.add(new SystemPrivilege("order", "unlockAll", "订单批量解锁"));
			
			this.systemPrivilegeService.batchSave(privileges);
		}
	}
	
	/*
	 * 新建“系统权限组”，将系统的所有权限加入其中
	 */
	private void initPrivilegeGroups() {
		if (this.privilegeGroupService.getCount() == 0) {
			PrivilegeGroup group = new PrivilegeGroup("系统权限组");
			group.getPrivileges().addAll(this.systemPrivilegeService.getScrollData().getResultList());
			this.privilegeGroupService.save(group);
		}
	}
	
	/*
	 * 后台必须要登录了才可进去，故要初始化一个系统管理员，为其设置账号、密码和权限组等
	 */
	private void initAdmin() {
		if (this.employeeService.getCount() == 0) {
			Employee emp = new Employee();
			emp.setName("admin");
			emp.setPassword("admin");
			emp.setRealname("系统管理员");
			emp.setGender(Gender.MAN);
			emp.setIdCard(new IDCard("530005199004104987", "恒州", new Date()));
			emp.getGroups().addAll(this.privilegeGroupService.getScrollData().getResultList());
			this.employeeService.save(emp);
		}
	}
	
	/*
	 * 初始化产品类型
	 * 从web.xml里取得相关常量，存入数据库
	 */
	private void initProductType() {
		if (this.productTypeService.getCount() == 0) {
			ServletContext application = ServletActionContext.getServletContext();
			for (int i = 1; i <= Integer.parseInt(application.getInitParameter("typeCount")); i++) {
				this.productTypeService.save(new ProductType(application.getInitParameter("type"+i)));
			}
		}
	}
	
	public SystemPrivilegeService getSystemPrivilegeService() {
		return systemPrivilegeService;
	}
	@Resource
	public void setSystemPrivilegeService(
			SystemPrivilegeService systemPrivilegeService) {
		this.systemPrivilegeService = systemPrivilegeService;
	}

	public PrivilegeGroupService getPrivilegeGroupService() {
		return privilegeGroupService;
	}
	@Resource
	public void setPrivilegeGroupService(PrivilegeGroupService privilegeGroupService) {
		this.privilegeGroupService = privilegeGroupService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}
	@Resource
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public ProductTypeService getProductTypeService() {
		return productTypeService;
	}
	@Resource
	public void setProductTypeService(ProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}

}
