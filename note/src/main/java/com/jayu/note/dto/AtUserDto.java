package com.jayu.note.dto;

import java.util.ArrayList;
import java.util.List;

import com.jayu.note.dao.model.ArticeAtUser;
import com.jayu.note.dao.model.User;
import com.jayu.note.domain.SysConstant;

public class AtUserDto {

	private String name;
	private long id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public static AtUserDto of(String name,long id){
		AtUserDto atUserDto = new AtUserDto();
		atUserDto.setId(id);
		atUserDto.setName(name);
		return atUserDto;
	}
	public static List<AtUserDto> convertFromUser(List<User> userList){
		 List<AtUserDto> atUserDtoList = convertFromUserDefault();
		 if(null != userList && userList.size() > 0){
			 for(User user:userList){
				 atUserDtoList.add(AtUserDto.of(user.getAccount(),user.getUserId()));
			 }
		 }
		 return atUserDtoList;
	}
	public static List<AtUserDto> convertFromAtUser(List<ArticeAtUser> articeAtUserList){
		 List<AtUserDto> atUserDtoList = new ArrayList();
		 if(null != articeAtUserList && articeAtUserList.size() > 0){
			 for(ArticeAtUser articeAtUser:articeAtUserList){
				 atUserDtoList.add(AtUserDto.of(articeAtUser.getAccount(),articeAtUser.getBizId()));
			 }
		 }
		 
		 return atUserDtoList;
	}
	
	public static boolean isContains(ArticeAtUser dbArticeAtUser,List<ArticeAtUser> articeAtUserList){
		 if(null != articeAtUserList && articeAtUserList.size() > 0 && null !=dbArticeAtUser){
			 for(ArticeAtUser articeAtUser:articeAtUserList){
				 if(dbArticeAtUser.getBizId().longValue()==articeAtUser.getBizId()
					&& dbArticeAtUser.getArticleId().longValue() == articeAtUser.getArticleId()){
					 return true;
				 }
			 }
		 }
		 return false;
	}
	
	public static String convertToAccountStr(List<AtUserDto> atUserDtoList){
		StringBuilder str = new StringBuilder();
		 if(null != atUserDtoList && atUserDtoList.size() > 0){
			 for(AtUserDto atUserDto:atUserDtoList){
				 str.append(atUserDto.getName());
				 str.append(",");
			 }
		 }
		 String result = str.toString();
		 if(result.length()>1)return result.substring(0,result.length()-1);
		 return result;
	}
	
	public static List<AtUserDto> convertFromUserDefault(){
		 List<AtUserDto> atUserDtoList = new ArrayList();
		 atUserDtoList.add(AtUserDto.of("all", SysConstant.ALL_MEMBER));
		 atUserDtoList.add(AtUserDto.of("guest",SysConstant.ALL_GUEST));
		 return atUserDtoList;
	}
	
}
