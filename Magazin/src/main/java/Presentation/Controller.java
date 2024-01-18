package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.ProductBLL;
import BusinessLogic.OrderBLL;
import Model.Client;
import Model.Order;
import Model.Product;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static javax.swing.JOptionPane.showConfirmDialog;

public class Controller {
    private final View view;
    private final ProductBLL productBll;
    private final ClientBLL clientBll;
    private final OrderBLL orderBll;
    private int logged = 0;

    public Controller(View view){
        this.view = view;
        this.productBll = new ProductBLL();
        this.clientBll = new ClientBLL();
        this.orderBll = new OrderBLL();

        view.addAccBtnListener(new AccBtnListener());
        view.addSearchListener(new SearchListener());
        view.addBuyBtnListener(new BuyBtnListener());
        view.addLogoBtnListener(new LogoBtnListener());
        view.addCartBtnListener(new CartBtnListener());
        view.addSellBtnListener(new SellBtnListener());
        view.addCategoryListener(new CategoryListener());
        view.addLoginBtnListener(new LoginBtnListener());
        view.addAdminBtnListener(new AdminBtnListener());
        view.addSignUpBtnListener(new SignBtnListener());
        view.addSearchBtnListener(new SearchBtnListener());
        view.addLogoffBtnListener(new LogoffBtnListener());
        view.addSellLabelListener(new SellLabelListener());
        view.addSelectBtnListener(new SelectBtnListener());
        view.addDeleteClientListener(new DeleteClientBtnListener());
        view.addDeleteProductListener(new DeleteProductBtnListener());
    }

    class SearchListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            List<Product> productsList;
            String name = view.getSearchName();
            productsList = productBll.findProductByName(name);
            view.createProductsPanel(view.getContentPanel(), productsList);
        }
    }

    class LogoBtnListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            List<Product> productsList;
            productsList = productBll.findAllProducts();
            view.createProductsPanel(view.getContentPanel(), productsList);
        }
    }

    class AdminBtnListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            view.createAdminPanel(view.getContentPanel());
        }
    }

    class AccBtnListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (logged == 0){
                view.getDialog().setVisible(true);
            } else {
                view.createAccInfoPanel(view.getContentPanel(), clientBll.findClientById(logged));
            }
        }
    }

    class SignBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String firstName = view.getFirstNameInput();
                String lastName = view.getLastNameInput();
                String address = view.getAddrInput();
                String email = view.getEmailInput();
                String password = view.getPassInput();
                String tel = view.getTelInput();
                if (firstName.equals("") || address.equals("") || email.equals("") || lastName.equals("") || password.equals("") || tel.equals("")) {
                    throw new Exception("Please complete the fields");
                } else {
                    int clientId = clientBll.findClientLastId() + 1;
                    Client client = new Client(clientId, firstName, lastName, password, tel, email, address);
                    clientBll.insertClient(client);
                    logged = clientId;

                    List<Product> productsList;
                    productsList = productBll.findAllProducts();
                    view.createProductsPanel(view.getContentPanel(), productsList);
                }
            }
            catch(Exception exx){
                view.showMessageError(exx.getMessage());
            }
        }
    }

    class BuyBtnListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(logged != 0){
                Client c = clientBll.findClientById(logged);
                view.showBillInfo(c);
                HashMap<Integer, Integer> cart = view.getCartList();
                for (int id : cart.keySet()) {
                    Product pr = productBll.findProductById(id);
                    Order ord = new Order(orderBll.findOrderLastId() + 1, c.getId(), id,
                            cart.get(id), (float)cart.get(id) * pr.getPrice());
                    try {
                        orderBll.insertOrder(ord);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }else{
                view.showMessageError("Nu sunteti conectat!");
            }
        }
    }

    class LoginBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String email = view.getEmailInput();
                String password = view.getPassInput();
                if (email.equals("") || password.equals("")) {
                    throw new Exception("Please complete the fields");
                } else {
                    Client c = clientBll.findClientByEmail(email);
                    if (c == null){
                        throw new Exception("Contul nu este inregistrat");
                    } else if (!c.getPassword().equals(password)){
                        throw new Exception("Parola incorecta");
                    } else{
                        logged = c.getId();
                        if (logged == 1)
                            view.setAdminLabelVisible(true);
                        List<Product> productsList;
                        productsList = productBll.findAllProducts();
                        view.createProductsPanel(view.getContentPanel(), productsList);
                    }
                }
            }
            catch(Exception exx){
                view.showMessageError(exx.getMessage());
            }
        }
    }

    class LogoffBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            logged = 0;
            view.setAdminLabelVisible(false);
            view.createLoginPanel(view.getContentPanel());
        }
    }

    class CartBtnListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            view.createCartPanel(view.getContentPanel());
        }
    }

    class SellLabelListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (logged == 0){
                view.showMessageError("Nu sunteti conectat!");
            } else{
                view.createSellPanel(view.getContentPanel());
            }
        }
    }

    class SearchBtnListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            List<Product> productsList;
            String name = view.getSearchName();
            productsList = productBll.findProductByName(name);
            view.createProductsPanel(view.getContentPanel(), productsList);
        }
    }

    class CategoryListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            List<Product> productsList;
            String category = view.getCategory().toLowerCase();

            if (category.equals("produse"))
                productsList = productBll.findAllProducts();
            else
                productsList = productBll.findProductByCategory(category.toLowerCase());
            view.createProductsPanel(view.getContentPanel(), productsList);
        }
    }

    class SellBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String name = view.getNameInput();
                String price = view.getPriceInput();
                String quantity = view.getQuantityInput();
                String stock = view.getStockInput();
                String category = view.getCategoryInput();
                String expiration = view.getExpirationInput();
                String image = view.getImageTextField().getText();

                if (name.equals("") || price.equals("") || quantity.equals("") || stock.equals("")) {
                    throw new Exception("Please complete the fields");
                } else {
                    if (image.equals(""))
                        image = "noproduct.png";
                    int id = productBll.findProductLastId() + 1;
                    Product p = new Product(id, name, Float.parseFloat(price), Float.parseFloat(quantity),
                            Integer.parseInt(stock), category, expiration, image);
                    productBll.insertProduct(p);

                    List<Product> productsList;
                    productsList = productBll.findAllProducts();
                    view.createProductsPanel(view.getContentPanel(), productsList);
                }
            }
            catch(Exception exx){
                view.showMessageError(exx.getMessage());
            }

        }
    }

    class SelectBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                view.getImageTextField().setText(fileChooser.getSelectedFile().getName());
            }
        }
    }

    class DeleteClientBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int row = view.getClientTable().getSelectedRow();
                if (row == -1)
                    throw new Exception("No client selected!");
                if (showConfirmDialog(view, "Are you sure you want to delete the client?") == 0){
                    clientBll.deleteClient(clientBll.findClientById(Integer.parseInt("" +
                            view.getClientTable().getValueAt(row, 0))));
                    view.createAdminPanel(view.getContentPanel());
                }
            } catch (Exception ex){
                view.showMessageError(ex.getMessage());
            }
        }
    }

    class DeleteProductBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int row = view.getProductTable().getSelectedRow();
                if (row == -1)
                    throw new Exception("No product selected!");
                if (showConfirmDialog(view, "Are you sure you want to delete this product?") == 0){
                    productBll.deleteProduct(productBll.findProductById(Integer.parseInt("" +
                            view.getProductTable().getValueAt(row, 0))));
                    view.createAdminPanel(view.getContentPanel());
                }
            } catch (Exception ex){
                view.showMessageError(ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        View view = new View();
        new Controller(view);
    }
}

