package com.$2012.web.action.product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.$2012.entity.product.Brand;
import com.$2012.entity.product.Product;
import com.$2012.entity.product.ProductStyle;
import com.$2012.entity.product.ProductType;
import com.$2012.service.product.ProductService;
import com.$2012.service.product.ProductStyleService;
import com.$2012.service.product.ProductTypeService;
import com.$2012.utils.WebUtils;
import com.$2012.vo.PageContext;
import com.$2012.vo.QueryResult;
import com.opensymphony.xwork2.ActionSupport;

/*
 * 产品前台展示
 */
@Component
@Scope("prototype")
public class FrontProductAction extends ActionSupport {
	private static final long serialVersionUID = -1520613512879835719L;
	private ProductService productService;
	private List<Product> products = new ArrayList<Product>();
	
	private PageContext<Product> pageCtx;
	
	private String sort;//排序
	private ProductType type;
	private String showStyle;
	private ProductTypeService productTypeService;
	private List<ProductType> types = new ArrayList<ProductType>();
	
	private Integer typeId;
	private List<Product> topSale = new ArrayList<Product>();
	private List<Product> history = new ArrayList<Product>();
	private Integer productId;
	private Product product;
	private ProductStyle style;
	private Integer styleId;
	private List<ProductStyle> styles = new ArrayList<ProductStyle>();
	private ProductStyleService productStyleService;
	private String color;
	
	private String word;
	
	private List<Brand> brands = new ArrayList<Brand>();
	private String brandId;
	
	public String list() {
		pageCtx = new PageContext<Product>(2, 4, pageCtx.getCurrentPage());
		StringBuilder ql = new StringBuilder("o.visible=?");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		List<Integer> allTypeIds = this.productTypeService.getAllSubTypeIds(new ArrayList<Integer>(), new Integer[]{type.getTypeId()});
		allTypeIds.add(type.getTypeId());
		StringBuilder n = new StringBuilder();
		for (int i = 0; i < allTypeIds.size(); i++) {
			n.append("?,");
		}
		n.deleteCharAt(n.length() - 1);
		ql.append(" and o.type.typeId in(" + n.toString() + ")");
		params.addAll(allTypeIds);
		if (brandId!=null && !"".equals(brandId)) {
			ql.append(" and o.brand.brandId=?");
			params.add(brandId);
		}
		LinkedHashMap<String, String> orderby = this.buildOrder(sort);
		QueryResult<Product> qr = productService.getScrollData(pageCtx.getFirstResult(), pageCtx.getMaxResults(), ql.toString(), params, orderby);
		products = qr.getResultList();
		pageCtx.setQueryResult(qr);
		
		for (Product p : products) {//去除产品描述中的html标签
			p.setDescription2(WebUtils.HtmltoText(p.getDescription()));
			//p.setDescription(WebUtils.HtmltoText(p.getDescription()));
			//这种由于openSessionInView，会把修改后的数据同步回数据库,如果不想把数据同步回数据库,请在其后调用productService.clear();
		}
		//productService.clear();
		
		ProductType type = this.productTypeService.find(this.type.getTypeId());
		types.add(type);
		ProductType parent = type.getParent();
		while (parent != null) {
			types.add(parent);
			parent = parent.getParent();
		}
		
		brands = this.productService.findBrandsByTypeId(allTypeIds.toArray(new Integer[]{}));
		
		this.type = type;
		
		return this.getView(showStyle);
	}
	
	private LinkedHashMap<String, String> buildOrder(String orderFied){
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		if("saleCount_desc".equals(orderFied)){
			orderby.put("saleCount", "desc");
		}else if("salePrice_desc".equals(orderFied)){
			orderby.put("salePrice", "desc");
		}else if("salePrice_asc".equals(orderFied)){
			orderby.put("salePrice", "asc");
		}else{
			orderby.put("showDate", "desc");
		}
		return orderby;
	}
	
	/*
	 * 获取显示视图
	 */
	private String getView(String showStyle){
		if("imagetext".equalsIgnoreCase(showStyle)) return "list_imagetext";//图文版
		else return "list_image";//图片版		
	}
	
	/*
	 * 查找某类别下最畅销的10个产品
	 */
	public String findTopSale() {
		this.topSale = (this.productService.getTopSale(typeId, 10));
		return SUCCESS;
	}
	
	/*
	 * 获取产品浏览历史（5个产品）
	 */
	public String findScanHistory() {
		String cookieValue = WebUtils.getCookieByName(ServletActionContext.getRequest(), "scanHistory");
		//注意：若数据库重新建立过，访问小说模块而未浏览小说时，硬盘里的没过期的cookie会使浏览历史里有商品
		//String cookieValue = "1-2-3";
		if (cookieValue != null && !"".equals(cookieValue.trim())) {
			String[] ids = cookieValue.split("-");
			Integer[] productIds = new Integer[ids.length];
			for (int i = 0; i < ids.length; i++) {
				productIds[i] = new Integer(ids[i]);
			}
			this.history = this.productService.findScanHistory(productIds, productIds.length);
		}
		return SUCCESS;
	}
	
	/*
	 * 产品详细展示（点击图片）
	 */
	public String detailShow() {
		//点击产品图片即将产品存入浏览历史中30天
		WebUtils.addCookie(ServletActionContext.getResponse(), "scanHistory", 
				this.buildScanHistory(ServletActionContext.getRequest(), productId), 30*24*60*60);
		
		this.product = this.productService.find(productId);
		
		ProductType parent = this.product.getType();
		while (parent != null) {
			types.add(parent);
			parent = parent.getParent();
		}
		
		StringBuilder ql = new StringBuilder("o.visible=? and o.product.productId="+this.productId);
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		this.styles = this.productStyleService.getScrollData(-1, -1, ql.toString(), params).getResultList();
		if (color != null) {
			ql.append(" and o.name=?");
			params.add(color);
			
			styleId = this.productStyleService.findByName(color).getStyleId();
		}
		List<ProductStyle> styles = this.productStyleService.getScrollData(-1, -1, ql.toString(), params).getResultList();
		style = styles.get(0);
		
		return SUCCESS;
	}
	
	//将浏览过的产品的id序列如2-12-6-30，保存进Cookie
	//1.如果当前浏览的产品已经在浏览历史里了,我们要把它移到最前面
	//2.如果浏览历史里产品数达到5个,我们需要把最先进入的元素删除 
	private String buildScanHistory(HttpServletRequest request, Integer currentProductId) {
		String cookieValue = WebUtils.getCookieByName(request, "scanHistory");
		LinkedList<Integer> productIds = new LinkedList<Integer>();
		if(cookieValue!=null && !"".equals(cookieValue.trim())){
			String[] ids = cookieValue.split("-");			
			for(String id : ids){
				productIds.add(new Integer(id.trim()));
			}
			if(productIds.contains(currentProductId)) {
				productIds.remove(currentProductId);
			} 
			if(productIds.size()>=5) productIds.poll();
		}
		productIds.add(0, currentProductId);//offer(e) - addToTail
		
		StringBuffer out = new StringBuffer();
		for(Integer id : productIds){
			out.append(id).append('-');
		}
		out.deleteCharAt(out.length()-1);
		System.out.println(out.toString());
		return out.toString();
	}
	
	/*
	 * 产品搜索
	 */
	/*public String search() {
		pageCtx = new PageContext<Product>(2, 4, pageCtx.getCurrentPage());
		QueryResult<Product> qr = this.productService.search(word, pageCtx.getFirstResult(), pageCtx.getMaxResults());
		if (qr == null) return "noResult";
		products = qr.getResultList();
		pageCtx.setQueryResult(qr);
		return SUCCESS;
	}*/
	
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

	public PageContext<Product> getPageCtx() {
		return pageCtx;
	}
	@Resource
	public void setPageCtx(PageContext<Product> pageCtx) {
		this.pageCtx = pageCtx;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
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

	public void setTopSale(List<Product> topSale) {
		this.topSale = topSale;
	}
	
	public List<Product> getTopSale() {
		return topSale;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public List<Product> getHistory() {
		return history;
	}

	public void setHistory(List<Product> history) {
		this.history = history;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductStyleService getProductStyleService() {
		return productStyleService;
	}
	@Resource
	public void setProductStyleService(ProductStyleService productStyleService) {
		this.productStyleService = productStyleService;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<ProductStyle> getStyles() {
		return styles;
	}

	public void setStyles(List<ProductStyle> styles) {
		this.styles = styles;
	}

	public ProductStyle getStyle() {
		return style;
	}

	public void setStyle(ProductStyle style) {
		this.style = style;
	}

	public Integer getStyleId() {
		return styleId;
	}

	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getShowStyle() {
		return showStyle;
	}

	public void setShowStyle(String showStyle) {
		this.showStyle = showStyle;
	}

	public List<Brand> getBrands() {
		return brands;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	
}
