package com.$2012.entity.product;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.SearchableReference;

/*
 * 产品样式
 */
@Entity
/*@Searchable(root=false) //Bug - 单纯增删改样式时，貌似没有自动更新索引
*/public class ProductStyle implements java.io.Serializable {
	private static final long serialVersionUID = 7721005950291955301L;
	private Integer styleId;
	private String name;//颜色
	private String productImagePath;
	private Boolean visible = true;
	private Product product; 
	
	@Id @GeneratedValue
	/*@SearchableProperty(index=Index.NO)*/
	public Integer getStyleId() {
		return styleId;
	}
	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}
	@Column(length=16,nullable=false)
	/*@SearchableProperty(index=Index.NO, name="styleName")*/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=120,nullable=false)
	/*@SearchableProperty(index=Index.NO)*/
	public String getProductImagePath() {
		return productImagePath;
	}
	public void setProductImagePath(String productImagePath) {
		this.productImagePath = productImagePath;
	}
	/*获得产品原尺寸图片路径，图片均由上传而来*/
	@Transient
	public String getProductPrototypeImagePath() {
		int i = this.productImagePath.lastIndexOf("/");
		StringBuilder sb = new StringBuilder(this.productImagePath);
		sb.insert(i, "/prototype");
		return sb.toString();
	}
	@Column(nullable=false)
	/*@SearchableProperty(index=Index.NO)*/
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((styleId == null) ? 0 : styleId.hashCode());
		return result;
	}
	/*一个产品可有多个样式，样式不同则产品不同*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ProductStyle other = (ProductStyle) obj;
		if (styleId == null) {
			if (other.styleId != null)
				return false;
		} else if (!styleId.equals(other.styleId))
			return false;
		return true;
	}
	@ManyToOne(cascade=CascadeType.REFRESH,optional=false)
	@JoinColumn(name="productId")
	/*@SearchableReference*/
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
}
