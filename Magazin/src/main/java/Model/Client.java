package Model;

public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;
    private String email;
    private String address;

    public Client(){}

    public Client(int id, String firstName, String lastName, String password, String phone, String email, String address){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public String toString(){
        return "client id:  " + id + ";\nfirst name:  " + firstName + ";\nlast name:  " + lastName
                + ";\nparola: " + password + ";\nphone:  " + phone + ";\nemail:  " + email
                + ";\naddress:  " + address + ".\n";
    }
}
