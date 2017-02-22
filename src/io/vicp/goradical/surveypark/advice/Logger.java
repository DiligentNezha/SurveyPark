package io.vicp.goradical.surveypark.advice;

import com.opensymphony.xwork2.ActionContext;
import io.vicp.goradical.surveypark.model.Log;
import io.vicp.goradical.surveypark.model.User;
import io.vicp.goradical.surveypark.service.LogService;
import io.vicp.goradical.surveypark.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class Logger {

	@Autowired
	private LogService logService;

	/**
	 * 记录
	 *
	 * @param pjp
	 * @return
	 */
	public Object record(ProceedingJoinPoint pjp) {
		Log log = new Log();
		try {
			ActionContext ac = ActionContext.getContext();
			//设置操作人
			if (ac != null) {
				Map<String, Object> session = ac.getSession();
				if (session != null) {
					User user = (User) session.get("user");
					if (user != null) {
						log.setOperator("" + user.getId() + ":" + user.getEmail());
					}
				}
			}
			//操作名称
			String mname = pjp.getSignature().getName();
			log.setOperName(mname);
			//操作参数
			Object[] params = pjp.getArgs();
			log.setOperParams(StringUtil.arr2Str(params));
			//调用目标对象的方法
			Object ret = pjp.proceed();
			//设置操作结果
			log.setOperResult("success");
			//设置结果消息
			if (ret != null) {
				log.setResultMsg(ret.toString());
				return ret;
			}
		} catch (Throwable e) {
			log.setOperResult("failure");
			log.setResultMsg(e.getMessage());
		} finally {
			logService.saveEntity(log);
		}
		return null;
	}

	public Object recordback(ProceedingJoinPoint pjp) {
		Log log = new Log();
		try {
			ActionContext ac = ActionContext.getContext();
			//设置操作人
			if (ac != null) {
				Map<String, Object> session = ac.getSession();
				if (session != null) {
					User user = (User) session.get("user");
					if (user != null) {
						log.setOperator(user.getId() + ":" + user.getEmail());
					}
				}
			}
			//操作名称
			String mname = pjp.getSignature().getName();
			log.setOperName(mname);
			//操作参数
			Object[] params = pjp.getArgs();
			log.setOperParams(StringUtil.arr2Str(params));
			//调用目标对象的方法
			Object ret = pjp.proceed();
			//设置操作结果
			log.setOperResult("success");
			//设置结果消息
			if (ret != null) {
				log.setResultMsg(ret.toString());
				return ret;
			}
		} catch (Throwable throwable) {
			log.setOperResult("failure");
			log.setResultMsg(throwable.getMessage());
		} finally {
			logService.saveEntity(log);
		}
		return null;
	}
}
