//package  com.jayu.blog;
//
//import java.sql.Timestamp;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import com.jayu.common.util.JsonUtil;
//import com.jayu.exception.BusinessException;
//import  com.jayu.log.Log;
//import com.jayu.web.entity.OperatorLog;
//import com.jayu.web.spring.annotation.log.LogDB;
//
///**
// * 操作日志 数据库记录实现类 .
// * <P>
// * @author jayu
// * @version V1.0 2012-3-30
// * @createDate 2012-3-30 下午4:13:52
// * @modifyDate	 jayu 2012-3-30 <BR>
// */
//public class LogDbBmo implements LogDB {
//	Log log=Log.getLog(LogDbBmo.class);
//	@Resource(name="com.jayu.mall.sys.bmo.OperatorLogBmoImpl")
//	OperatorLogBmo operatorLogBmo;
//	/**
//	 * 操作日志记录
//	 * @param operatorLog  OperatorLog 操作日志对象
//	 */
//	public void addLog(OperatorLog operatorLog) {
//		com.jayu.mall.sys.model.OperatorLog operatorLogDb = new com.jayu.mall.sys.model.OperatorLog();
//		operatorLogDb.setIp(operatorLog.getIp());
//		operatorLogDb.setOperatorAction(operatorLog.getClassMethod());
//		operatorLogDb.setOperatorCode(operatorLog.getOptCode());
//		operatorLogDb.setOperatorDesc(operatorLog.getDesc());
//		if(operatorLog.getSessionData() !=null){
//			SessionAdmin sessionAdmin = (SessionAdmin)operatorLog.getSessionData();
//			operatorLogDb.setUserCode(sessionAdmin.getAccount());
//			operatorLogDb.setUserType(sessionAdmin.getAccountType());
//		}else{
//			if("logindo".equals(operatorLog.getOptCode())){
//				Map paramMap=JsonUtil.toObject(operatorLog.getInParam(), HashMap.class);
//				paramMap=(Map)paramMap.get("paramMap");
//				if(paramMap.containsKey("staffCode")){
//					operatorLogDb.setUserCode((String)paramMap.get("staffCode"));
//				}else{
//					operatorLogDb.setUserCode("0");
//				}
//			}
//			operatorLogDb.setUserType(-1);
//		}
//		try {
//			operatorLogBmo.insert(operatorLogDb);
//		} catch (BusinessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//}
