<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>文件获取页面</title>
  <link rel="stylesheet" type="text/css" href='<s:url value="/styles.css" />'>
</head>
<body>

<table>
  <tr>
    <td class="tdHeader">图片获取:</td>
  </tr>
  <tr>
    <td style="vertical-align: top;">
      <table>
        <tr>
          <td>
            <s:form action="FileUploadAction_getImageFile" method="post">
              <table>
                <tr>
                  <td class="tdFormLabel">图片id:</td>
                  <td class="tdFormControl">
                    <s:textfield  name="xid" cssClass="text"></s:textfield>
                  </td>
                </tr>
                <tr>
                  <td class="tdFormLabel"></td>
                  <td class="tdFormControl"><s:submit value="%{'确定'}" cssClass="btn"/></td>
                </tr>
              </table>
            </s:form>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<s:if test="xid != null">
  <img src="<s:url value="%{model.imgPath}" />" height="300px" width="500px">
</s:if>
</body>
</html>