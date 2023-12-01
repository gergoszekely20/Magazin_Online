package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.ProductBLL;
import Model.Client;
import Model.Product;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class Controller {
    private final View view;
    private final ProductBLL productBll;
    private final ClientBLL clientBll;
    private int logged = 0;

    public Controller(View view){
        this.view = view;
        this.productBll = new ProductBLL();
        this.clientBll = new ClientBLL();

        view.addAccBtnListener(new AccBtnListener());
        view.addSearchListener(new SearchListener());
        view.addLogoBtnListener(new LogoBtnListener());
        view.addCartBtnListener(new CartBtnListener());
        view.addSellBtnListener(new SellBtnListener());
        view.addCategoryListener(new CategoryListener());
        view.addLoginBtnListener(new LoginBtnListener());
        view.addSignUpBtnListener(new SignBtnListener());
        view.addSearchBtnListener(new SearchBtnListener());
        view.addLogoffBtnListener(new LogoffBtnListener());
        view.addSellLabelListener(new SellLabelListener());
        view.addBuyBtnListener(new BuyBtnListener());
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
                String category = view.getCategory();
                String expiration = view.getExpirationInput();
                String image = view.getImageInput();

                if (name.equals("") || price.equals("") || quantity.equals("") || stock.equals("")
                        || category.equals("") || image.equals("")) {
                    throw new Exception("Please complete the fields");
                } else {
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

    public static void main(String[] args) {
        View view = new View();
        new Controller(view);
    }
}

