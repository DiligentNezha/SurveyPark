package io.vicp.goradical.surveypark.model.other;

import io.vicp.goradical.surveypark.model.BaseEntity;

import java.util.Date;

public class XinGe extends BaseEntity {
	private static final long serialVersionUID = -162051258993169138L;

	private Integer id;
	private String longitude;//经度
	private String latitude;//纬度
	private Date uploadTime;//上传时间
	private String imgPath;//图片地址
	private String info;//信息
	private String extraInfo1;
	private String extraInfo2;
	private String extraInfo3;
	private String extraInfo4;
	private String extraInfo5;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getExtraInfo1() {
		return extraInfo1;
	}

	public void setExtraInfo1(String extraInfo1) {
		this.extraInfo1 = extraInfo1;
	}

	public String getExtraInfo2() {
		return extraInfo2;
	}

	public void setExtraInfo2(String extraInfo2) {
		this.extraInfo2 = extraInfo2;
	}

	public String getExtraInfo3() {
		return extraInfo3;
	}

	public void setExtraInfo3(String extraInfo3) {
		this.extraInfo3 = extraInfo3;
	}

	public String getExtraInfo4() {
		return extraInfo4;
	}

	public void setExtraInfo4(String extraInfo4) {
		this.extraInfo4 = extraInfo4;
	}

	public String getExtraInfo5() {
		return extraInfo5;
	}

	public void setExtraInfo5(String extraInfo5) {
		this.extraInfo5 = extraInfo5;
	}
}
