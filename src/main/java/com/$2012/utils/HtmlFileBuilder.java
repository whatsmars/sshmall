package com.$2012.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.$2012.entity.product.Product;

public class HtmlFileBuilder {
	
	public static void createProductHtml(Product product, File saveDir){
		try {
			if(!saveDir.exists()) saveDir.mkdirs();
			VelocityContext context = new VelocityContext();
			context.put("product", product);
			Template template = Velocity.getTemplate("product/productDetail.html");
			FileOutputStream outStream = new FileOutputStream(new File(saveDir, product.getProductId()+".shtml"));
			OutputStreamWriter writer =  new OutputStreamWriter(outStream,"GBK");
			BufferedWriter sw = new BufferedWriter(writer);
			template.merge(context, sw);
			sw.flush();
			sw.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
