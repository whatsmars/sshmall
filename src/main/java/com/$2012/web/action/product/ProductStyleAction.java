package com.$2012.web.action.product;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.$2012.entity.product.Product;
import com.$2012.entity.product.ProductStyle;
import com.$2012.service.product.ProductService;
import com.$2012.service.product.ProductStyleService;
import com.$2012.vo.FileContext;
import com.opensymphony.xwork2.ActionSupport;

@Component  
@Scope("prototype")
public class ProductStyleAction extends ActionSupport {
	private static final long serialVersionUID = 4964451892301670982L;
	private ProductStyleService productStyleService;
	private List<ProductStyle> styles = new ArrayList<ProductStyle>();
	private ProductStyle style;
	private Integer[] styleIds;
	
	private Product product;
	private ProductService productService;
	
	private FileContext fileCtx;
	
	public String list() {
		List<Object> params = new ArrayList<Object>();
		params.add(product.getProductId());
		styles = this.productStyleService.getScrollData(-1, -1, "o.product.productId=?", params).getResultList();
		return SUCCESS;
	}
	
	public String visible() {
		productStyleService.setVisible(styleIds, true);
		return SUCCESS;
	}
	public String disvisible() {
		productStyleService.setVisible(styleIds, false);
		return SUCCESS;
	}
	
	public String add() {
		ProductStyle style = new ProductStyle();
		style.setName(this.style.getName());
		
		//上传文件并返回保存路径
		String imagePath = fileCtx.makeUploadPath(fileCtx, 
				"\\images\\product\\", 1024*1024*10);
		style.setProductImagePath(imagePath);
		Product product = new Product();
		product.setProductId(this.product.getProductId());
		style.setProduct(product);
		styles.add(style);
		this.productStyleService.save(style);
		return SUCCESS;
	}
	
	public String update() {
		ProductStyle style = this.productStyleService.find(this.style.getStyleId());
		style.setName(this.style.getName());
		String imagePath = fileCtx.makeUploadPath(fileCtx, 
				"\\images\\product\\", 1024*1024*10);
		if (imagePath != null && !"".equals(imagePath)) {
			style.setProductImagePath(imagePath);
		}
		this.productStyleService.update(style);
		return SUCCESS;
	}

	public ProductStyleService getProductStyleService() {
		return productStyleService;
	}
	@Resource
	public void setProductStyleService(ProductStyleService productStyleService) {
		this.productStyleService = productStyleService;
	}

	public List<ProductStyle> getStyles() {
		return styles;
	}

	public void setStyles(List<ProductStyle> styles) {
		this.styles = styles;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer[] getStyleIds() {
		return styleIds;
	}

	public void setStyleIds(Integer[] styleIds) {
		this.styleIds = styleIds;
	}

	public FileContext getFileCtx() {
		return fileCtx;
	}
	@Resource
	public void setFileCtx(FileContext fileCtx) {
		this.fileCtx = fileCtx;
	}

	public ProductStyle getStyle() {
		return style;
	}

	public void setStyle(ProductStyle style) {
		this.style = style;
	}

	public ProductService getProductService() {
		return productService;
	}
	@Resource
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

}
