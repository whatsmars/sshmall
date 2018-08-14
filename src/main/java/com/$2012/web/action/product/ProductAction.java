package com.$2012.web.action.product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.$2012.entity.product.Brand;
import com.$2012.entity.product.Product;
import com.$2012.entity.product.ProductStyle;
import com.$2012.entity.product.ProductType;
import com.$2012.service.product.BrandService;
import com.$2012.service.product.ProductService;
import com.$2012.service.product.ProductTypeService;
import com.$2012.vo.FileContext;
import com.$2012.vo.PageContext;
import com.$2012.vo.QueryResult;
import com.opensymphony.xwork2.ActionSupport;

@Component
@Scope("prototype")
public class ProductAction extends ActionSupport {
	private static final long serialVersionUID = -9201826024334336741L;
	private ProductService productService;
	private List<Product> products = new ArrayList<Product>();
	private Product product;
	private Integer[] productIds;
	
	private String query;
	private Float minBasePrice;
	private Float maxBasePrice;
	private Float minSalePrice;
	private Float maxSalePrice;
	
	private PageContext<Product> pageCtx;
	private FileContext fileCtx;
	
	private ProductTypeService productTypeService;
	private List<ProductType> types = new ArrayList<ProductType>();
	private ProductType type;
	private List<ProductType> menuTypes = new ArrayList<ProductType>();
	
	private ProductStyle style;
	private String size;
	
	private BrandService brandService;
	private Brand brand;
	
	/*
	 * 产品列表显示
	 */
	public String list() {
		pageCtx = new PageContext<Product>(12, pageCtx.getCurrentPage());
		StringBuilder whereql = new StringBuilder("1=1");
		List<Object> params = new ArrayList<Object>();
		if ("true".equals(query)) {//执行查询
			String name = this.product.getName();
			Integer typeId = this.type.getTypeId();
			String code = this.product.getCode();
			String brandId = this.brand.getBrandId();
			if (name != null && !"".equals(name.trim())) {
				whereql.append(" and o.name like ?");
				params.add("%" + name + "%");
			}
			if (typeId != null && typeId > 0) {
				whereql.append(" and o.type.typeId=?");
				params.add(typeId);
			}
			if (name != null && !"".equals(name.trim())) {
				whereql.append(" and o.name like ?");
				params.add("%" + name + "%");
			}
			if (code != null && !"".equals(code.trim())) {
				whereql.append(" and o.code=?");
				params.add(code);
			}
			if (minBasePrice != null && minBasePrice > 0) {
				whereql.append(" and o.basePrice>=?");
				params.add(minBasePrice);
			}
			if (maxBasePrice != null && maxBasePrice > 0) {
				whereql.append(" and o.basePrice<=?");
				params.add(maxBasePrice);
			}
			if (minSalePrice != null && minSalePrice > 0) {
				whereql.append(" and o.basePrice>=?");
				params.add(minSalePrice);
			}
			if (maxSalePrice != null && maxSalePrice > 0) {
				whereql.append(" and o.basePrice<=?");
				params.add(maxSalePrice);
			}
			if (brandId != null && !"".equals(brandId.trim())) {
				whereql.append(" and o.brand.brandId=?");
				params.add(brandId);
			}
		}
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("name", "asc");
		QueryResult<Product> qr = productService.getScrollData(pageCtx.getFirstResult(), pageCtx.getMaxResults(), whereql.toString(), params, orderby);
		products = qr.getResultList();
		pageCtx.setQueryResult(qr);
		return SUCCESS;
	}
	
	/*
	 * 选择产品类别
	 */
	public String selectType() {
		String whereql = "o.parent is null";
		List<Object> params = new ArrayList<Object>();
		Integer typeId = type.getTypeId();
		if (typeId != null && typeId > 0) {
			whereql = "o.parent.typeId=?";
			params.add(typeId);
			
			ProductType type = this.productTypeService.find(typeId);
			ProductType parent = type.getParent();
			menuTypes.add(type);
			while (parent != null) {
				menuTypes.add(parent);
				parent = parent.getParent();
			}
		}
		types = productTypeService.getScrollData(-1, -1, whereql, params).getResultList();
		return SUCCESS;
	}
	
	/*
	 * 增加产品
	 */
	public String add() {
		Product product = new Product();
		product.setName(this.product.getName());
		product.setBasePrice(this.product.getBasePrice());
		product.setSalePrice(this.product.getSalePrice());
		product.setMarketPrice(this.product.getMarketPrice());
		
		String brandId = this.brand.getBrandId();
		if (brandId != null && !"".equals(brandId.trim())) {
			Brand brand = brandService.find(brandId);
			product.setBrand(brand);
		}
		
		product.setBuyExplain(this.product.getBuyExplain());
		product.setCode(this.product.getCode());
		product.setDescription(this.product.getDescription());
		
		product.setType(productTypeService.find(this.type.getTypeId()));
		
		ProductStyle style = new ProductStyle();
		style.setName(this.style.getName());
		String productImagePath = fileCtx.makeUploadPath(fileCtx, 
				"\\images\\product\\", 1024*1024*10);
		style.setProductImagePath(productImagePath);
		product.addStyle(style);
		
		this.productService.save(product);
		products.add(product);
		
		//生成的文件存放在网站根目录的html/product/类别id/
		//File saveDir = new File(ServletActionContext.getServletContext().getRealPath("/html/product/"+product.getType().getTypeId()));
		//HtmlFileBuilder.createProductHtml(product, saveDir);
		return SUCCESS;
	}
	
	/*
	 * 更新产品
	 */
	public String update() {
		Integer productId = this.product.getProductId();
		Product product = productService.find(productId);
		
		product.setName(this.product.getName());
		product.setBasePrice(this.product.getBasePrice());
		product.setSalePrice(this.product.getSalePrice());
		product.setMarketPrice(this.product.getMarketPrice());
		
		String brandId = this.brand.getBrandId();
		if (brandId != null && !"".equals(brandId.trim())) {
			Brand brand = brandService.find(brandId);
			product.setBrand(brand);
		}
		
		product.setBuyExplain(this.product.getBuyExplain());
		product.setCode(this.product.getCode());
		product.setDescription(this.product.getDescription());
		product.setType(productTypeService.find(this.type.getTypeId()));
		
		productService.update(product);
		
		//File saveDir = new File(ServletActionContext.getServletContext().getRealPath("/html/product/"+product.getType().getTypeId()));
		//HtmlFileBuilder.createProductHtml(product, saveDir);
		return SUCCESS;
	}
	
	/*
	 * 产品上架
	 */
	public String visible() {
		productService.setVisibleStatus(productIds, true);
		return SUCCESS;
	}
	
	/*
	 * 产品下架
	 */
	public String disvisible() {
		productService.setVisibleStatus(productIds, false);
		productService.clearClickCount(productIds);
		return SUCCESS;
	}
	
	/*
	 * 产品推荐
	 */
	public String commend() {
		productService.setCommendStatus(productIds, true);
		return SUCCESS;
	}
	
	/*
	 * 产品不推荐
	 */
	public String uncommend() {
		productService.setCommendStatus(productIds, false);
		return SUCCESS;
	}
	
	public ProductService getProductService() {
		return productService;
	}
	@Resource
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public PageContext<Product> getPageCtx() {
		return pageCtx;
	}
	@Resource
	public void setPageCtx(PageContext<Product> pageCtx) {
		this.pageCtx = pageCtx;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public FileContext getFileCtx() {
		return fileCtx;
	}
	@Resource
	public void setFileCtx(FileContext fileCtx) {
		this.fileCtx = fileCtx;
	}

	public List<ProductType> getTypes() {
		return types;
	}

	public void setTypes(List<ProductType> types) {
		this.types = types;
	}

	public ProductTypeService getProductTypeService() {
		return productTypeService;
	}
	@Resource
	public void setProductTypeService(ProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}

	public ProductType getType() {
		return type;
	}
	
	public void setType(ProductType type) {
		this.type = type;
	}

	public List<ProductType> getMenuTypes() {
		return menuTypes;
	}

	public void setMenuTypes(List<ProductType> menuTypes) {
		this.menuTypes = menuTypes;
	}

	public ProductStyle getStyle() {
		return style;
	}

	public void setStyle(ProductStyle style) {
		this.style = style;
	}

	public Float getMinBasePrice() {
		return minBasePrice;
	}

	public void setMinBasePrice(Float minBasePrice) {
		this.minBasePrice = minBasePrice;
	}

	public Float getMaxBasePrice() {
		return maxBasePrice;
	}

	public void setMaxBasePrice(Float maxBasePrice) {
		this.maxBasePrice = maxBasePrice;
	}

	public Float getMinSalePrice() {
		return minSalePrice;
	}

	public void setMinSalePrice(Float minSalePrice) {
		this.minSalePrice = minSalePrice;
	}

	public Float getMaxSalePrice() {
		return maxSalePrice;
	}

	public void setMaxSalePrice(Float maxSalePrice) {
		this.maxSalePrice = maxSalePrice;
	}

	public Integer[] getProductIds() {
		return productIds;
	}

	public void setProductIds(Integer[] productIds) {
		this.productIds = productIds;
	}

	public BrandService getBrandService() {
		return brandService;
	}
	@Resource
	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
