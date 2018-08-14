package com.$2012.web.action.user;

import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.$2012.entity.user.User;
import com.$2012.service.user.UserService;
import com.$2012.utils.*;
import com.$2012.web.action.BaseAction;
import com.$2012.web.action.shopping.CartAction;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import net.sf.json.JSONObject;

/*
 * 注册登录及找回密码
 */
@Component
@Scope("prototype")
public class FrontUserAction extends BaseAction implements SessionAware {
	private static final long serialVersionUID = 5058953534890081662L;
	
	private UserService userService;
	private User user;
	
	private String msg;//错误提示信息
	
	private Map<String, Object> session;
	
	//找回密码相关参数
	private String username;
	private String validateCode;//用MD5编码username+password
	private long time;//找回密码有时间限制
	private String password;
	
	private String regCode;//注册时用的验证码
	
	private String toUI;
	private String xiaoshuoUI;
	
	private CartAction cartAction;
	
	Map<String, Object> json;
	
	public Map<String, Object> getJson() {
		return json;
	}

	/*
	 * 检测用户名是否已存在     如果要求在服务端也进行输入校验，可用validateXxx()---xml
	 */
	public String checkName() throws Exception {
		if (userService.exists(username)) {
			msg = "该用户名已注册";
		} else {
			msg = "恭喜，该用户名可注册";
		}
		
		WebUtils.ajaxCallback(msg, "html");
		
		
		return null;
	}
	
	/*
	 * 检测校验码
	 */
	public String checkCode() {
		if (!regCode.equals(WebUtils.getCode(ServletActionContext.getRequest()))) {
			msg = "输入验证码错误，请重新输入";
		} else {
			msg = "输入验证码正确";
		}
		
		WebUtils.ajaxCallback(msg, "html");
		
		return null;
	}
	
	/*
	 * 注册
	 */
	public String register() {
		userService.save(user);
		return SUCCESS;
	}
	/*
	 * 登录
	 */
	public String login() {
		if (userService.validate(user.getName().trim(), user.getPassword().trim())) {
			session.put("user", userService.find(user.getName()));//将登录用户存入session中
			//cartUI--参见com.hongxi.web.filter.UserLoginFilter
			if("cartUI".equals(toUI)) {
				cartAction.setAmount();
				return "toCartUI";
			} else if("myCenterUI".equals(toUI)) {
				return "toMyCenterUI";
			} else return SUCCESS;
		} else {
			this.addFieldError("login", "用户名或密码错误");
			return "nameorpass";
		} 
	}
	/*
	 * 退出登录
	 */
	public String logout() {
		WebUtils.deleteUser(ServletActionContext.getRequest());
		return SUCCESS;
	}
	
	
	/*
	 * 找回密码之发送邮件
	 */
	public String sendMail() throws Exception {
		if (user.getName() != null && !"".equals(user.getName().trim())) {
			if (userService.exists(user.getName().trim())) {
				user = userService.find(user.getName().trim());
				Template template = Velocity.getTemplate("email.html");
				VelocityContext context = new VelocityContext();
				context.put("username", user.getName());
				context.put("validateCode", MD5.MD5Encode(user.getName()
						+ user.getPassword()));
				context.put("time", new Date().getTime());
				StringWriter writer = new StringWriter();
				template.merge(context, writer);
				String emailContent = writer.toString();
				EmailSender.send(user.getEmail(), "当当网--找回密码邮件", emailContent, "text/html");
			}
		} 
		return SUCCESS; 
	}
	/*假设发送成功-发送邮件涉及到一些邮件发送知识有待学习掌握*/
	public String xx() {
		if (user.getName() != null && !"".equals(user.getName().trim())) {
			if (userService.exists(user.getName().trim())) {
				user = userService.find(user.getName().trim());
				username = user.getName();
				validateCode = MD5.MD5Encode(user.getName() + user.getPassword());
				time = new Date().getTime();
			}
		} 
		return SUCCESS;
	}
	
	/*
	 * 找回密码之显示修改密码界面
	 */
	public String showChangePasswordUI() {
		if((new Date().getTime() - time) <= 24*60*60*1000) {
			if (username != null && !"".equals(username)) {
				if (userService.exists(username)) {
					user = userService.find(username);
					String code = MD5.MD5Encode(user.getName() + user.getPassword());
					if (code.equals(validateCode)) return SUCCESS;
				}
			}
		} else {
			return "error";
		}
		return "error"; 
	}
	
	/*
	 * 找回密码之修改密码
	 */
	public String changePassword() {
		if (username != null && !"".equals(username)) {
			if (userService.exists(username)) {
				user = userService.find(username);
				String code = MD5.MD5Encode(user.getName() + user.getPassword());
				if (code.equals(validateCode)) {
					userService.updatePassword(username, password);
					message = "修改密码成功";
					urladdress = "/page/user/front/login.jsp";
					return SUCCESS;
				}
			}
		}
		return "error"; 
	}

	public UserService getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getXiaoshuoUI() {
		return xiaoshuoUI;
	}

	public void setXiaoshuoUI(String xiaoshuoUI) {
		this.xiaoshuoUI = xiaoshuoUI;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToUI() {
		return toUI;
	}

	public void setToUI(String toUI) {
		this.toUI = toUI;
	}
	
	public CartAction getCartAction() {
		return cartAction;
	}
	@Resource
	public void setCartAction(CartAction cartAction) {
		this.cartAction = cartAction;
	}

}
