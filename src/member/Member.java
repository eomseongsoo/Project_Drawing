package member;

public class Member {
private String id;
private String password;
private String name;
private String addr;
private String phone;
public Member() {
	// TODO Auto-generated constructor stub
}
public Member(String id,String password,String name,String addr,String phone) {
	// TODO Auto-generated constructor stub
	this.id=id;
	this.password=password;
	this.name=name;
	this.addr=addr;
	this.phone=phone;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getAddr() {
	return addr;
}
public void setAddr(String addr) {
	this.addr = addr;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}

}
