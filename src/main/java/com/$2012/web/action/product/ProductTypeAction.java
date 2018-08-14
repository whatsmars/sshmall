package com.$2012.web.action.product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.$2012.entity.product.ProductType;
import com.$2012.service.product.ProductTypeService;
import com.$2012.vo.PageContext;
import com.$2012.vo.QueryResult;
import com.opensymphony.xwork2.ActionSupport;

@Component  //事实上更适合标注成@Controller
@Scope("prototype")
public class ProductTypeAction extends ActionSupport {
	private static final long serialVersionUID = -1271770418471621275L;
	private ProductTypeService productTypeService;
	private ProductType type;
	//属性传值和域模型传值有时混用更恰当,因为三级导航太长了，
	//单独出来也不失为一种选择，且可避免空指针异常（即可以传null）
	private Integer parentId;
	private String parentName;
	private String query;//查询标志
	
	private PageContext<ProductType> pageCtx;
	
	private List<ProductType> types = new ArrayList<ProductType>();
	
	public String list() {
		pageCtx = new PageContext<ProductType>(12, pageCtx.getCurrentPage());
		StringBuilder whereql = new StringBuilder("o.visible=?");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		if ("true".equals(query)) {//执行查询
			String name = type.getName();//事实上这里只是利用type这个属性，不代表将以全名查询
			if (name != null && !"".equals(name.trim())) {
				whereql.append(" and o.name like ?");
				params.add("%" + name + "%");
			}
		} else {
			if (parentId != null && parentId > 0) {
				whereql.append(" and o.parent.typeId=?");
				params.add(parentId);
			} else {
				whereql.append(" and o.parent is null");
			}
		}
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("typeId", "desc");
		QueryResult<ProductType> qr = productTypeService.getScrollData(pageCtx.getFirstResult(), pageCtx.getMaxResults(), whereql.toString(), params, orderby);
		types = qr.getResultList();
		pageCtx.setQueryResult(qr);
		return SUCCESS;
	}
	
	public String add() {
		ProductType subType = new ProductType();
		subType.setName(type.getName());
		subType.setNote(type.getNote());
		if (parentId != null && parentId > 0) {
			ProductType parentType = new ProductType();
			parentType.setTypeId(parentId);
			parentType.setName(parentName);
			subType.setParent(parentType);
		}
		productTypeService.save(subType);
		return SUCCESS;
	}
	
	public String update() {
		ProductType type = productTypeService.find(this.type.getTypeId());
		type.setName(this.type.getName());
		type.setNote(this.type.getNote());
		productTypeService.update(type);
		return SUCCESS;
	}

	public ProductTypeService getProductTypeService() {
		return productTypeService;
	}
	@Resource
	public void setProductTypeService(ProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}

	public List<ProductType> getTypes() {
		return types;
	}

	public void setTypes(List<ProductType> types) {
		this.types = types;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public PageContext<ProductType> getPageCtx() {
		return pageCtx;
	}
	@Resource  
	public void setPageCtx(PageContext<ProductType> pageCtx) {
		this.pageCtx = pageCtx;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
