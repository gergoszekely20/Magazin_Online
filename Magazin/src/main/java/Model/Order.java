package Model;

public class Order {
    private int id;
    private int idClient;
    private int idProduct;
    private int quantity;
    private float price;

    public Order(){}

    public Order(int id, int idClient, int idProduct, int quantity, float price){
        this.id = id;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getIdClient() {return idClient;}
    public void setIdClient(int idClient) {this.idClient = idClient;}
    public int getIdProduct() {return idProduct;}
    public void setIdProduct(int idProduct) {this.idProduct = idProduct;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public float getPrice() {return price;}
    public void setPrice(float price) {this.price = price;}

    public String toString(){
        return "id:  " + id + ";\nclient id:  " + idClient + ";\nproduct id:  " + idProduct + ";\nquantity:  " + quantity + ";\nprice:  " + price + ".\n";
    }
}
