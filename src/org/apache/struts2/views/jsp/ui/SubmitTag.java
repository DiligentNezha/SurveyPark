//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.apache.struts2.views.jsp.ui;

import com.opensymphony.xwork2.util.ValueStack;
import io.vicp.goradical.surveypark.util.ValidateUtil;
import org.apache.struts2.components.Component;
import org.apache.struts2.components.Submit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

public class SubmitTag extends AbstractClosingTag {
	private static final long serialVersionUID = 2179281109958301343L;
	protected String action;
	protected String method;
	protected String align;
	protected String type;
	protected String src;

	public SubmitTag() {
	}

	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new Submit(stack, req, res);
	}

	protected void populateParams() {
		super.populateParams();
		Submit submit = (Submit)this.component;
		submit.setAction(this.action);
		submit.setMethod(this.method);
		submit.setAlign(this.align);
		submit.setType(this.type);
		submit.setSrc(this.src);
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	@Override
	public int doEndTag() throws JspException {
		return hasRight() ? super.doEndTag() : SKIP_BODY;
	}

	@Override
	public int doStartTag() throws JspException {
		return hasRight() ? super.doStartTag() : SKIP_BODY;
	}

	private boolean hasRight() {
		String ns = getFormNamespace();
		String actionName = getValidActionName();
		return ValidateUtil.hasRight(ns, actionName, (HttpServletRequest) pageContext.getRequest(), null);
	}

	/**
	 * 获得所在表单的命名空间
	 * @return
	 */
	private String getFormNamespace() {
		//获得上级标签
		Tag parent = getParent();
		while (parent != null) {
			if (parent instanceof FormTag) {
				return ((FormTag) parent).namespace;
			} else {
				parent = parent.getParent();
			}
		}
		return null;
	}

	/**
	 * 获得所在表单的action
	 * @return
	 */
	private String getValidActionName() {
		if (ValidateUtil.isValid(action)) {
			return action;
		}
		//获得上级标签
		Tag parent = getParent();
		while (parent != null) {
			if (parent instanceof FormTag) {
				return ((FormTag) parent).action;
			} else {
				parent = parent.getParent();
			}
		}
		return null;
	}
}
