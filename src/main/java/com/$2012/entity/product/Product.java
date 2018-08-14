package com.$2012.entity.product;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableComponent;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;

/*
 * 产品（书）
 */
@Entity 
/*@Searchable*/
public class Product implements java.io.Serializable {
	private static final long serialVersionUID = 6038809104006738452L;
	private Integer productId;
	/*货号*/
	private String code;
	/*产品名称*/
	private String name;
	/*产品类型*/
	private ProductType type;
	/*底价*/
	private Float basePrice;
	/*市场价*/
	private Float marketPrice;
	/*销售价*/
	private Float salePrice;
	/*简介*/
	private String description;
	/*简介(去掉html标签)*/
	private String description2;
	/*购买说明*/
	private String buyExplain;
	/*是否可见*/
	private Boolean visible = true;
	/*上架日期*/
	private Date showDate = new Date();
	/*销售数量*/
	private Integer saleCount = 0;//根据已收货订单统计
	/*人气指数-本项目中指点击购买的次数*/
	private Long clickCount = 0L;
	/*是否推荐*/
	private Boolean commend = false;
	/*产品样式*/
	private Set<ProductStyle> styles = new HashSet<ProductStyle>();
	/*品牌*/
	private Brand brand;
	
	@Id @GeneratedValue 
	/*@SearchableId*/
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	@Column(length=30)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}                                                      
	@Column(length=50,nullable=false) 
	/*@SearchableProperty(index=Index.ANALYZED, boost=2, name="productName")*/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@ManyToOne(cascade=CascadeType.REFRESH,optional=false)
	@JoinColumn(name="typeId")//在关系维护端设外键
	/*@SearchableComponent*/
	public ProductType getType() {
		return type;
	}
	public void setType(ProductType type) {
		this.type = type;
	}
	@Column(nullable=false)
	public Float getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Float basePrice) {
		this.basePrice = basePrice;
	}
	@Column(nullable=false) 
	/*@SearchableProperty(index=Index.NO)*/
	public Float getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Float marketPrice) {
		this.marketPrice = marketPrice;
	}
	@Column(nullable=false) 
	/*@SearchableProperty(index=Index.NO)*/
	public Float getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Float salePrice) {
		this.salePrice = salePrice;
	}
	@Transient
	public Float getSavedPrice() {
		return this.marketPrice - this.salePrice;
	}
	@Lob @Column(nullable=false)  
	/*@SearchableProperty*/
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(length=30) 
	/*@SearchableProperty(index=Index.NO)*/
	public String getBuyExplain() {
		return buyExplain;
	}
	public void setBuyExplain(String buyExplain) {
		this.buyExplain = buyExplain;
	}
	@Column(nullable=false)
	/*@SearchableProperty(index=Index.NO)*/
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	@Temporal(TemporalType.DATE)
	public Date getShowDate() {
		return showDate;
	}
	public void setShowDate(Date showDate) {
		this.showDate = showDate;
	}
	@Column(nullable=false)
	public Integer getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(Integer saleCount) {
		this.saleCount = saleCount;
	}
	@Column(nullable=false)
	public Long getClickCount() {
		return clickCount;
	}
	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}
	@Column(nullable=false)
	public Boolean getCommend() {
		return commend;
	}
	public void setCommend(Boolean commend) {
		this.commend = commend;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Product other = (Product) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}
	@OneToMany(cascade=CascadeType.ALL,mappedBy="product")
	@OrderBy("visible desc, styleId asc")
	/*@SearchableComponent*/
	public Set<ProductStyle> getStyles() {
		return styles;
	}
	public void setStyles(Set<ProductStyle> styles) {
		this.styles = styles;
	}
	/*
	 * 添加样式
	 */
	public void addStyle(ProductStyle style) {
		if (!this.styles.contains(style)) {
			style.setProduct(this);
			this.styles.add(style);
		}
	}
	/*
	 * 删除样式
	 */
	public void removeStyle(ProductStyle style) {
		if (this.styles.contains(style)) {
			style.setProduct(null);
			this.styles.remove(style);
		}
	}
	@Transient
	public String getDescription2() {
		return description2;
	}
	public void setDescription2(String description2) {
		this.description2 = description2;
	}
	@ManyToOne(cascade=CascadeType.REFRESH)//实际中很多产品未标明品牌
	@JoinColumn(name="brandId")
	/*@SearchableComponent*/
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
}
