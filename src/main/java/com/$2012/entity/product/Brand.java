package com.$2012.entity.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;

@Entity
/*@Searchable(root=false)*/
public class Brand implements java.io.Serializable {
	private static final long serialVersionUID = -3382034833929511489L;
	private String brandId;
	private String name;
	private Boolean visible = true;
	private String logoPath;
	
	public Brand() {}
	
	public Brand(String name) {
		this.name = name;
	}
	
	@Id @Column(length=36)
	/*@SearchableProperty(index=Index.NO)*/
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	@Column(length=40,nullable=false)
	/*@SearchableProperty(name="brandName")*/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable=false)
	/*@SearchableProperty(index=Index.NO)*/
	public Boolean isVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	@Column(length=120)
	/*@SearchableProperty(index=Index.NO)*/
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brandId == null) ? 0 : brandId.hashCode());
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
		final Brand other = (Brand) obj;
		if (brandId == null) {
			if (other.brandId != null)
				return false;
		} else if (!brandId.equals(other.brandId))
			return false;
		return true;
	}
}
