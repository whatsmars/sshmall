package com.$2012.web.action.user;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

/*
 * 生成验证码
 */
@Component
@Scope("prototype")
public class RegCodeAction extends ActionSupport {
	private static final long serialVersionUID = 5064409403963643112L;
	private ByteArrayInputStream inputStream;
	
	public String execute() {
		// 清空缓存，对FF无效
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setDateHeader("expires", 0);
		
		BufferedImage bi = new BufferedImage(60, 20, BufferedImage.TYPE_INT_RGB);// 画板
		Graphics g = bi.getGraphics();// 画笔
		drawBackground(g);
		char[] c = getRandom();
		drawNum(g, c);
		g.dispose();
		
		ServletActionContext.getRequest().getSession().setAttribute("regCode", new String(c));

		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		try {
			ImageIO.write(bi, "JPEG", bo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] b = bo.toByteArray();
		try {
			bo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setInputStream(new ByteArrayInputStream(b));
		
		return SUCCESS;
	}
	
	private void drawNum(Graphics g, char[] c) {
		g.setColor(Color.WHITE);
		g.drawString("" + c[0], 1, 17);
		g.drawString("" + c[1], 15, 16);
		g.drawString("" + c[2], 31, 18);
		g.drawString("" + c[3], 46, 16);
	}

	private char[] getRandom() {
		String num = "1234567890";
		char[] c = new char[4];
		for (int i = 0; i < c.length; i++) {
			int position = (int) (Math.random() * 10);
			c[i] = num.charAt(position);
		}
		return c;
	}

	private void drawBackground(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 60, 20);
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

}
