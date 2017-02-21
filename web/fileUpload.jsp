<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>文件上传获取页面</title>
  <link rel="stylesheet" type="text/css" href='<s:url value="/styles.css" />'>
</head>
<body>
<table>
  <tr>
    <td class="tdHeader">文件上传:</td>
  </tr>
  <tr>
    <td style="vertical-align: top;">
      <table>
        <tr>
          <td>
            <s:form action="FileUploadAction_uploadFile" method="post" enctype="multipart/form-data">
              <table>
                <tr>
                  <td class="tdFormLabel">选择文件:</td>
                  <td class="tdFormControl">
                    <s:file name="image" cssClass="text"/>
                    <s:fielderror fieldName="image"></s:fielderror>
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
  <tr>
    <td><s:fielderror name="imageFileName"></s:fielderror></td>
  </tr>
</table>
</body>
</html>