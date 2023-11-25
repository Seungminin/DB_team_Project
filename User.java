class User {
	private String id;
    private String pw;
    private String name;
    private String phone; 
    private String Email;  
    private String birth;
    private String website;
    private String introduce;
    private String gender;

    public User(String id, String pw, String name, String phone, String Email, String birth, String website, String introduce, String gender) {
    	setId(id);
        setPw(pw);
        setName(name);
        setphone(phone);  
        setEmail(Email);  
        setbirth(birth);
        setwebsite(website);
        setintroduce(introduce);
        setGender(gender);
    }
    public User(String id) {
        setId(id);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPw() {
        return pw;
    }
    public void setPw(String pw) {
        this.pw = pw;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getphone() {
        return phone;
    }
    public void setphone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public String getbirth() {
        return birth;
    }
    public void setbirth(String birth) {
        this.birth = birth;
    }
    public String getwebsite() {
        return website;
    }
    public void setwebsite(String website) {
        this.website = website;
    }
    public String getintroduce() {
        return introduce;
    }
    public void setintroduce(String introduce) {
        this.introduce = introduce;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof User)) {
            return false;
        }
        User temp = (User)o;

        return id.equals(temp.getId());
    }

    @Override
    public String toString() {
        String info = "Id: " + id + "\n";
        info += "Pw: " + pw + "\n";
        info += "Name: " + name + "\n";
        info += "Phone: " + phone + "\n";
        info += "Email: " + Email + "\n";
        info += "Birth: " + birth + "\n";
        info += "Website: " + website + "\n";
        info += "Introduce: " + introduce + "\n";
        info += "gender: " + gender + "\n";
        return info;
    }
}