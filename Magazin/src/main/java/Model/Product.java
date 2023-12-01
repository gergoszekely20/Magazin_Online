package Model;


public class Product {
    private int id;
    private String name;
    private float price;
    private float quantity;
    private int stock;
    private String category;
    private String expiration;
    private String image;

    public Product(){}

    public Product(int id, String name, float price, float quantity, int stock, String category, String expiration, String image){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.stock = stock;
        this.category = category;
        this.expiration = expiration;
        this.image = image;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public float getPrice() {return price;}
    public void setPrice(float price) {this.price = price;}
    public float getQuantity() {return quantity;}
    public void setQuantity(float quantity) {this.quantity = quantity;}
    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}
    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}
    public String getExpiration() {return expiration;}
    public void setExpiration(String expiration) {this.expiration = expiration;}
    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}


    public String toString(){
        return "Id produs:  " + id + ";\nNume:  " + name + ";\nPret:  " + price + ";\nCantitate:  "
                + quantity + ";\nStock:  " + stock + ";\nData de expirare:  " + expiration
                + ".";
    }

}
