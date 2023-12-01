package BusinessLogic;

import BusinessLogic.Validators.*;
import DAO.ProductDAO;
import Model.Product;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private final ProductDAO productDAO;
    private final List<Validator<Product>> validators;

    public ProductBLL(){
        productDAO = new ProductDAO();
        validators = new ArrayList<>();
        validators.add(new IdValidator<>());
        validators.add(new NameValidator<>());
        validators.add(new PriceValidator());
        validators.add(new QuantityValidator());
    }
    public Product findProductById(int id) {
        return productDAO.findById(id);
    }

    public List<Product> findProductByCategory(String category){
        List<Product> list = productDAO.findByCategory(category);
        if (list == null) {
            throw new NoSuchElementException("The products was not found!");
        }
        return list;
    }

    public List<Product> findProductByName(String name){
        List<Product> list = productDAO.findByName(name);
        if (list == null) {
            throw new NoSuchElementException("The products was not found!");
        }
        return list;
    }

    public List<Product> findAllProducts(){
        List<Product> list = productDAO.findAll();
        if (list == null) {
            throw new NoSuchElementException("The products was not found!");
        }
        return list;
    }

    public void insertProduct(Product p) throws Exception {
        try {
            for (Validator<Product> v : validators) {
                v.validate(p);
            }
            if (productDAO.insert(p) == null) {
                throw new NoSuchElementException("The product was not inserted!");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void updateProduct(Product p) throws Exception {
        try {
            for (Validator<Product> v : validators) {
                v.validate(p);
            }
            if (productDAO.update(p) == null) {
                throw new NoSuchElementException("The product was not updated!");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void deleteProduct(Product p){
        if (!productDAO.delete(p)) {
            throw new NoSuchElementException("The product was not deleted!");
        }
    }

    public DefaultTableModel getTableData(){
        DefaultTableModel data = productDAO.getTableData(findAllProducts());
        if (data == null) {
            throw new NoSuchElementException("The product was not found!");
        }
        return data;
    }

    public int findProductLastId(){
        int id = productDAO.findLastId();
        if (id == -1){
            throw new NoSuchElementException("The id was not found!");
        }
        return id;
    }

}
