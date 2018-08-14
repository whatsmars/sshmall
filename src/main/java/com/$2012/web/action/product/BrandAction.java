package com.$2012.web.action.product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.$2012.entity.product.Brand;
import com.$2012.entity.product.Product;
import com.$2012.service.product.BrandService;
import com.$2012.service.product.ProductService;
import com.$2012.vo.FileContext;
import com.$2012.vo.PageContext;
import com.$2012.vo.QueryResult;
import com.opensymphony.xwork2.ActionSupport;

@Component
@Scope("prototype")
public class BrandAction extends ActionSupport {
	private static final long serialVersionUID = -8662438350606370217L;
	private BrandService brandService;
	private List<Brand> brands = new ArrayList<Brand>();
	private Brand brand;
	private String listVar;
	private ProductService productService;
	private Product product;
	
	private PageContext<Brand> pageCtx;
	private FileContext fileCtx;
	
	private String query;
	
	public String list() {
		pageCtx = new PageContext<Brand>(12, pageCtx.getCurrentPage());
		StringBuilder whereql = new StringBuilder("o.visible=?");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		if ("true".equals(query)) {//执行查询
			String name = brand.getName();
			if (name != null && !"".equals(name.trim())) {
				whereql.append(" and o.name like ?");
				params.add("%" + name + "%");
			}
		}
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("name", "asc");
		QueryResult<Brand> qr = brandService.getScrollData(pageCtx.getFirstResult(), pageCtx.getMaxResults(), whereql.toString(), params, orderby);
		brands = qr.getResultList();
		pageCtx.setQueryResult(qr);
		return SUCCESS;
	}
	
	public String simpleList() {
		StringBuilder whereql = new StringBuilder("o.visible=?");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("name", "asc");
		QueryResult<Brand> qr = brandService.getScrollData(-1, -1, whereql.toString(), params, orderby);
		brands = qr.getResultList();
		if ("addProduct".equals(listVar)) return "addProduct";
		if ("updateProduct".equals(listVar)) {
			product = this.productService.find(product.getProductId());
			return "updateProduct";
		} 
		if ("queryProduct".equals(listVar)) return "queryProduct";
		return ERROR;
	}
	
	public String add() {
		Brand brand = new Brand();
		brand.setName(this.brand.getName());
		String logoPath = fileCtx.makeUploadPath(fileCtx, 
				"\\images\\brand\\", 1024*1024*10);
		brand.setLogoPath(logoPath);
		this.brandService.save(brand);
		brands.add(brand);
		return SUCCESS;
	}
	
	public String updateName() {
		Brand brand = brandService.find(this.brand.getBrandId());
		String name = this.brand.getName();
		if (name != null) {
			brand.setName(name);
		}
		brandService.update(brand);
		return SUCCESS;
	}
	
	public String updateLogo() {
		Brand brand = brandService.find(this.brand.getBrandId());
		String logoPath = fileCtx.makeUploadPath(fileCtx, 
				"\\images\\brand\\", 1024*1024*10);
		brand.setLogoPath(logoPath);
		brandService.update(brand);
		return SUCCESS;
	}

	public BrandService getBrandService() {
		return brandService;
	}
	@Resource
	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}

	public List<Brand> getBrands() {
		return brands;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	public PageContext<Brand> getPageCtx() {
		return pageCtx;
	}
	@Resource
	public void setPageCtx(PageContext<Brand> pageCtx) {
		this.pageCtx = pageCtx;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
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

	public String getListVar() {
		return listVar;
	}

	public void setListVar(String listVar) {
		this.listVar = listVar;
	}
	
	public ProductService getProductService() {
		return productService;
	}
	@Resource
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
