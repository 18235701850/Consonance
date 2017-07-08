package com.example.sql;

public class login {
private String use;
private String pwd;
public login(){}
public login(String use,String pwd){
	this.use = use;
	this.pwd = pwd;
}
public String getUse() {
	return use;
}
public void setUse(String use){
	this.use = use;
}
public String getPwd() {
	return pwd;
}
public void setPwd(String pwd) {
	this.pwd = pwd;
}
}
