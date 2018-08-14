package com.$2012.entity.product;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;

/*
 * 产品类别，理论上可以无限级
 *     （有点bbs评论的影子）
 */
@Entity 
/*@Searchable(root=false)*/
public class ProductType implements java.io.Serializable {
	private static final long serialVersionUID = -5587244514655694090L;
	/*类别id*/
	private Integer typeId;
	/*类别名称*/
	private String name;
	/*备注，用于google搜索页面描述*/
	private String note;
	/*是否可见*/
	private Boolean visible = true;
	/*子类别*/
	private Set<ProductType> childTypes = new HashSet<ProductType>();
	/*父类别*/
	private ProductType parent;
	
	private Set<Product> products = new HashSet<Product>();
	
	public ProductType() {}
	
	public ProductType(String name) {
		this.name = name;
	}
	@Id @GeneratedValue
	/*@SearchableProperty(index=Index.NO)*/
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	@Column(length=36,nullable=false)
	/*@SearchableProperty(index=Index.NO, name="typeName")*/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=200)
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Column(nullable=false,columnDefinition="bit(1) default true")
	public Boolean isVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	@OneToMany(cascade={CascadeType.REFRESH,CascadeType.REMOVE},mappedBy="parent")
	public Set<ProductType> getChildTypes() {
		return childTypes;
	}
	public void setChildTypes(Set<ProductType> childTypes) {
		this.childTypes = childTypes;
	}
	@ManyToOne(cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="parentid")
	public ProductType getParent() {
		return parent;
	}
	public void setParent(ProductType parent) {
		this.parent = parent;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeId == null) ? 0 : typeId.hashCode());
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
		final ProductType other = (ProductType) obj;
		if (typeId == null) {
			if (other.typeId != null)
				return false;
		} else if (!typeId.equals(other.typeId))
			return false;
		return true;
	}
	@OneToMany(mappedBy="type",cascade=CascadeType.REMOVE)
	public Set<Product> getProducts() {
		return products;
	}
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
}
