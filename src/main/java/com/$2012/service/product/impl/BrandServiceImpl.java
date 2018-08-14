package com.$2012.service.product.impl;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.$2012.dao.DaoSupport;
import com.$2012.entity.product.Brand;
import com.$2012.service.product.BrandService;

@Component("brandService")
@Transactional
public class BrandServiceImpl extends DaoSupport<Brand> implements BrandService {

	@Override
	public void save(Brand brand) {
		brand.setBrandId(UUID.randomUUID().toString());
		super.save(brand);
	}

}
