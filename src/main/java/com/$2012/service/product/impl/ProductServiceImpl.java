package com.$2012.service.product.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.compass.core.Compass;
import org.compass.core.CompassCallback;
import org.compass.core.CompassException;
import org.compass.core.CompassHits;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.$2012.dao.DaoSupport;
import com.$2012.entity.product.Brand;
import com.$2012.entity.product.Product;
import com.$2012.service.product.ProductService;
import com.$2012.service.product.ProductTypeService;
import com.$2012.vo.QueryResult;

@Component("productService")
@Transactional
public class ProductServiceImpl extends DaoSupport<Product> implements ProductService {
	private ProductTypeService productTypeService;
	
	/*private CompassTemplate compassTemplate;*/
	
	public ProductTypeService getProductTypeService() {
		return productTypeService;
	}
	@Resource
	public void setProductTypeService(ProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	
	/*@Resource
	public void setCompass(Compass compass) {
		this.compassTemplate = new CompassTemplate(compass);
	}*/
	
	/*
    //采用编码进行索引增加同步，本项目采用spring配置文件控制
	@Override
	public void save(Product entity) {
		super.save(entity);
		this.compassTemplate.save(entity);
	}*/

	public void setCommendStatus(Integer[] productIds, boolean status) {
		if (productIds != null && productIds.length > 0) {
			StringBuilder ql = new StringBuilder();
			int len = productIds.length;
			for (int i = 0; i < len; i++) {
				ql.append("?,");
			}
			ql.deleteCharAt(2 * len - 1);
			Object[] params = new Object[len+1];
			params[0] = status;
			for (int i = 0; i < len; i++) {
				params[i+1] = productIds[i];
			}
			this.hibernateTemplate.bulkUpdate("update Product o set o.commend = ? where o.productId in(" + ql.toString() + ")", params);
		}
	}

	public void setVisibleStatus(Integer[] productIds, boolean status) {
		if (productIds != null && productIds.length > 0) {
			for (int i = 0; i < productIds.length; i++) {
				Product product = (Product) super.hibernateTemplate.get(Product.class, productIds[i]);
				product.setVisible(status);
				super.hibernateTemplate.update(product);//为追求性能，应用hql语句
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<Product> getTopSale(final Integer typeId, final Integer maxResults) {
		return (List<Product>) super.hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List<Integer> allTypeIds = productTypeService.getAllSubTypeIds(new ArrayList<Integer>(), new Integer[]{typeId});
				allTypeIds.add(typeId);
				StringBuilder n = new StringBuilder();
				for (int i = 0; i < allTypeIds.size(); i++) {
					n.append(allTypeIds.get(i)).append(',');
				}
				n.deleteCharAt(n.length() - 1);
				Query query = session.createQuery("select o from Product o where o.commend=true and o.visible=true and o.type.typeId in(" + n.toString() + ") order by o.saleCount desc");
				query.setFirstResult(0).setMaxResults(maxResults);
				return (List<Product>) query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<Product> findScanHistory(final Integer[] productIds, final Integer maxResults) {
		List<Product> products = new ArrayList<Product>();
		for (Integer productId : productIds) {
			products.add(super.find(productId));
		}//select .. in(..)默认按id排序
		return products;
	}

	public void updateClickCount(Integer productId) {
		this.hibernateTemplate.bulkUpdate("update Product o set o.clickCount=o.clickCount+1 where o.productId=?", productId);
	}
	
	public void clearClickCount(Integer[] productIds) {
		for (Integer productId : productIds) {
			this.hibernateTemplate.bulkUpdate("update Product o set o.clickCount=0 where o.productId=?", productId);
		}
	}
	
	/*public QueryResult<Product> search(final String keyword, final int firstResult, final int maxResults) {
		final QueryResult<Product> qr = new QueryResult<Product>();
		return this.compassTemplate.execute(new CompassCallback<QueryResult<Product>>() {
			public QueryResult<Product> doInCompass(CompassSession session)
					throws CompassException {
				查询指定类别的匹配记录，并按position降序排序
				 CompassQueryBuilder queryBuilder = session.queryBuilder();
				 CompassHits hits = queryBuilder.bool()
				 	.addMust(queryBuilder.spanEq("typeid", typeid))
			 		.addMust(queryBuilder.queryString(keyword).toQuery())
			   		.toQuery().addSort("position", SortPropertyType.FLOAT, SortDirection.REVERSE)
			   		.hits();//sql: typeid=1 and (xxxx like ?) order by positoin desc
				 
				CompassHits hits = session.find(keyword) ;
				if (hits == null) return null;
				qr.setTotalRecords(hits.length());
				int length = firstResult + maxResults;
				if (length > hits.length()) length = hits.length();
				List<Product> products = new ArrayList<Product>();
				for (int i = firstResult; i < length; i++) {
					Product product = (Product) hits.data(i);//执行此方法时，才将数据加载进内存，之前只存储着索引
					if (hits.highlighter(i).fragment("productName") != null) {
						product.setName(hits.highlighter(i).fragment("productName"));
					}
					if (hits.highlighter(i).fragment("description") != null) {
						product.setDescription(hits.highlighter(i).fragment("description"));
					}
					if (hits.highlighter(i).fragment("brandName") != null) {
						product.setBrand(new Brand(hits.highlighter(i).fragment("brandName")));
					}
					if (product.getVisible()) {
						//删除下架的样式
						//...
						products.add(product);
					} 
				}
				qr.setResultList(products);
				return qr;
			}
		});
	}*/
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<Brand> findBrandsByTypeId(final Integer[] typeIds) {
		if(typeIds!=null && typeIds.length>0){
			StringBuffer ql = new StringBuffer();
			for(int i=0;i<typeIds.length;i++){
				ql.append("?,");
			}
			ql.deleteCharAt(ql.length()-1);	
			Object[] params = new Object[typeIds.length];
			for (int i = 0; i < typeIds.length; i++) {
				params[i] = typeIds[i];
			}
			return (List<Brand>) this.hibernateTemplate.find("select o from Brand o where o.brandId in(select p.brand.brandId from Product p where p.type.typeId in("+ ql.toString()+") group by p.brand.brandId)", params);
		}
		return null;
	}
	
}
