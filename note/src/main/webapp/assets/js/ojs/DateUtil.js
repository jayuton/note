/**
 * 扩展Date的format方法 
 * @author jayu
 */
DateUtil=(function(){
	Date.prototype.format =function(format){
		var o = {
	            "M+": this.getMonth() + 1,
	            "d+": this.getDate(),
	            "h+": this.getHours(),
	            "m+": this.getMinutes(),
	            "s+": this.getSeconds(),
	            "q+": Math.floor((this.getMonth() + 3) / 3),
	            "S": this.getMilliseconds()
	        };
	        if (/(y+)/.test(format)) {
	            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	        }
	        for (var k in o) {
	            if (new RegExp("(" + k + ")").test(format)) {
	                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
	            }
	        }
	    return format;
	};
	
    /**  
     *转换日期对象为日期字符串  
     * @param date 日期对象  
     * @param isFull 是否为完整的日期数据,  
     *               为true时, 格式如"2000-03-05 01:05:04"  
     *               为false时, 格式如 "2000-03-05"  
     * @return 符合要求的日期字符串  
     */  
     function _getSmpFormatDate(date, isFull) {
         var pattern = "";
         if (isFull == true || isFull == undefined) {
             pattern = "yyyy-MM-dd hh:mm:ss";
         } else {
             pattern = "yyyy-MM-dd";
         }
         return _getFormatDate(date, pattern);
     }
     /**  
     *转换当前日期对象为日期字符串  
     * @param date 日期对象  
     * @param isFull 是否为完整的日期数据,  
     *               为true时, 格式如"2000-03-05 01:05:04"  
     *               为false时, 格式如 "2000-03-05"  
     * @return 符合要求的日期字符串  
     */  

     function _getSmpFormatNowDate(isFull) {
         return _getSmpFormatDate(new Date(), isFull);
     }
     /**  
     *转换long值为日期字符串  
     * @param l long值  
     * @param isFull 是否为完整的日期数据,  
     *               为true时, 格式如"2000-03-05 01:05:04"  
     *               为false时, 格式如 "2000-03-05"  
     * @return 符合要求的日期字符串  
     */  

     function _getSmpFormatDateByLong(l, isFull) {
         return _getSmpFormatDate(new Date(l), isFull);
     }
     /**  
     *转换long值为日期字符串  
     * @param l long值  
     * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss  
     * @return 符合要求的日期字符串  
     */  

     function _getFormatDateByLong(l, pattern) {
         return _getFormatDate(new Date(l), pattern);
     }
     /**  
     *转换日期对象为日期字符串  
     * @param l long值  
     * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss  
     * @return 符合要求的日期字符串  
     */  
     function _getFormatDate(date, pattern) {
         if (typeof date == "undefined") {
             date = new Date();
         }
         if (typeof pattern == "undefined") {
             pattern = "yyyy-MM-dd hh:mm:ss";
         }
         try{
        	 return date.format(pattern);
         }catch(e){
        	 return "";
         }
     }
     
     return {
    	 getSD:_getSmpFormatDate,
    	 getSND:_getSmpFormatNowDate,
    	 getSDByLong:_getFormatDateByLong,
    	 getFDate:_getFormatDate
     };
})();   
