package io.vicp.goradical.surveypark.struts2.action;

import io.vicp.goradical.surveypark.model.other.XinGe;
import io.vicp.goradical.surveypark.service.XinGeService;
import io.vicp.goradical.surveypark.util.ValidateUtil;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Date;

@Controller
@Scope("prototype")
public class FileUploadAction extends BaseAction<XinGe> implements ServletContextAware {

	@Autowired
	private XinGeService xinGeService;

	private Integer xid;

	//上传文件
	private File image;
	//文件名称
	private String imageFileName;

	private ServletContext sc;

	public String getInfo() {
		return "info";
	}

	public String getImageFile() {
		model = xinGeService.getEntity(xid);
		return "imageGetSuccess";
	}

	/**
	 * 实现图片文件上传
	 * @return
	 */
	public String uploadFile() {
		if (ValidateUtil.isValid(imageFileName)) {
			//1.实现文件上传
			// /upload文件夹真是路径
			String dir = sc.getRealPath("/xinge");
			//扩展名
			String ext = imageFileName.substring(imageFileName.lastIndexOf("."));
			long l = System.nanoTime();
			File newFile = new File(dir, l + ext);
			//文件另存为
			image.renameTo(newFile);

			model.setLongitude("1565.125");
			model.setLatitude("14564.16");
			model.setUploadTime(new Date());
			model.setImgPath("/xinge/" + l + ext);
			model.setInfo("图片上传测试");
			model.setExtraInfo1("info1");
			model.setExtraInfo2("info2");
			model.setExtraInfo3("info3");
			model.setExtraInfo4("info4");
			model.setExtraInfo5("info5");
//			2.保存路径
			xinGeService.saveEntity(model);
		}
		addFieldError(imageFileName, "上传成功");
		return "imageUploadSuccess";
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		sc = servletContext;
	}

	public Integer getXid() {
		return xid;
	}

	public void setXid(Integer xid) {
		this.xid = xid;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
}
