package com.$2012.web.action.privilege;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.$2012.entity.privilege.PrivilegeGroup;
import com.$2012.entity.privilege.SystemPrivilege;
import com.$2012.entity.privilege.SystemPrivilegePK;
import com.$2012.service.privilege.PrivilegeGroupService;
import com.$2012.service.privilege.SystemPrivilegeService;
import com.$2012.vo.PageContext;
import com.$2012.vo.QueryResult;
import com.opensymphony.xwork2.ActionSupport;

/*
 * 权限组管理
 */
@Component
@Scope("prototype")
public class PrivilegeGroupAction extends ActionSupport {
	private static final long serialVersionUID = 5243747639270156936L;
	private PrivilegeGroup privilegeGroup;
	private String name;//groupName
	private String groupId;
	private PrivilegeGroupService privilegeGroupService;
	private List<PrivilegeGroup> privilegeGroups = new ArrayList<PrivilegeGroup>();
	private PageContext<PrivilegeGroup> pageCtx;
	
	private List<SystemPrivilege> privileges = new ArrayList<SystemPrivilege>();
	private Set<SystemPrivilege> selectedPrivileges = new HashSet<SystemPrivilege>();
	private SystemPrivilegeService systemPrivilegeService;
	
	private String[] ids;
	
	public String list() {
		pageCtx = new PageContext<PrivilegeGroup>(12, pageCtx.getCurrentPage());
		QueryResult<PrivilegeGroup> qr = privilegeGroupService.getScrollData(pageCtx.getFirstResult(), pageCtx.getMaxResults());
		privilegeGroups = qr.getResultList();
		pageCtx.setQueryResult(qr);
		return SUCCESS;
	}
	
	public String showAddUI() {
		privileges = systemPrivilegeService.getScrollData().getResultList();
		return SUCCESS;
	}
	
	public String add() {
		privilegeGroup = new PrivilegeGroup(name);
		for (String id : ids) {
			//没定义转换器，而是采取直接解析字符串
			privilegeGroup.addPrivilege(new SystemPrivilege(new SystemPrivilegePK(id.split(",")[0], id.split(",")[1])));
		}
		this.privilegeGroupService.save(privilegeGroup);
		privilegeGroups.add(privilegeGroup);
		return SUCCESS;
	}
	
	public String showUpdateUI() {
		privileges = systemPrivilegeService.getScrollData().getResultList();
		this.privilegeGroup = this.privilegeGroupService.find(groupId);
		selectedPrivileges = this.privilegeGroup.getPrivileges();
		return SUCCESS;
	}
	
	public String update() {
		PrivilegeGroup group = this.privilegeGroupService.find(groupId);
		group.setName(name);
		group.clear();
		for (String id : ids) {
			group.addPrivilege(new SystemPrivilege(new SystemPrivilegePK(id.split(",")[0], id.split(",")[1])));
		}
		this.privilegeGroupService.update(group);
		return SUCCESS;
	}
	
	public String delete() {
		this.privilegeGroupService.delete(groupId);
		return SUCCESS;
	}
	
	public PrivilegeGroup getPrivilegeGroup() {
		return privilegeGroup;
	}

	public void setPrivilegeGroup(PrivilegeGroup privilegeGroup) {
		this.privilegeGroup = privilegeGroup;
	}

	public PrivilegeGroupService getPrivilegeGroupService() {
		return privilegeGroupService;
	}
	@Resource
	public void setPrivilegeGroupService(PrivilegeGroupService privilegeGroupService) {
		this.privilegeGroupService = privilegeGroupService;
	}

	public PageContext<PrivilegeGroup> getPageCtx() {
		return pageCtx;
	}
	@Resource
	public void setPageCtx(PageContext<PrivilegeGroup> pageCtx) {
		this.pageCtx = pageCtx;
	}

	public List<PrivilegeGroup> getPrivilegeGroups() {
		return privilegeGroups;
	}

	public void setPrivilegeGroups(List<PrivilegeGroup> privilegeGroups) {
		this.privilegeGroups = privilegeGroups;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SystemPrivilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<SystemPrivilege> privileges) {
		this.privileges = privileges;
	}

	public SystemPrivilegeService getSystemPrivilegeService() {
		return systemPrivilegeService;
	}
	@Resource
	public void setSystemPrivilegeService(
			SystemPrivilegeService systemPrivilegeService) {
		this.systemPrivilegeService = systemPrivilegeService;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public Set<SystemPrivilege> getSelectedPrivileges() {
		return selectedPrivileges;
	}

	public void setSelectedPrivileges(Set<SystemPrivilege> selectedPrivileges) {
		this.selectedPrivileges = selectedPrivileges;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
