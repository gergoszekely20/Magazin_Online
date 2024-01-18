package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.ProductBLL;
import Model.Client;
import Model.Product;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.time.LocalDate;


public class View extends JFrame{
    private static float totalCost = 0;
    private final JTextField searchTextField = new JTextField(25);
    private JComboBox<String> categories;
    private JComboBox<String> productsCategories;
    private final JPanel contentPanel;
    private final Font fo;
    private final Font fo2;
    private final JButton logoffBtn = new JButton("  Deconectare  ");
    private final JButton buyBtn = new JButton("  Comanda  ");
    public final JLabel priceProductsLabel = new JLabel("");
    public final JLabel moneyLabel = new JLabel("");


    private JDialog dialog;
    private final JDialog productInfo = new JDialog(this, "Info");
    private final JDialog billInfo = new JDialog(this, "Info");

    private JLabel searchLabel;
    private JLabel contLabel;
    private JLabel sellLabel;
    private JLabel cartLabel;
    private JLabel logoLabel;
    private JLabel adminLabel;
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JButton loginButton;// = new JButton("LOGIN");
    private final JButton signButton = new JButton("SIGN UP");
    private final JPanel categoryPanel = new JPanel();

    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField addrTextField;
    private JTextField telTextField;

    private final JButton sellBtn = new JButton("      Vinde produs      ");
    private final JTextField tName = new JTextField(25);
    private final JTextField tPrice = new JTextField(25);
    private final JTextField tQuantity = new JTextField(25);
    private final JTextField tStock = new JTextField(25);
    private final JTextField tExpiration = new JTextField(25);
    private final JTextField tImage = new JTextField(25);

    private JTable clientTable;
    private JTable productTable;
    private final JButton selectBtn = new JButton("...");
    private final JButton deleteClientBtn = new JButton("   Delete client   ");
    private final JButton deleteProductBtn = new JButton("   Delete product   ");

    private final ProductBLL productBll = new ProductBLL();
    private final ClientBLL clientBll = new ClientBLL();
    private final HashMap<Integer, Integer> cartList = new HashMap<Integer, Integer>();

    private final String[] categoryList = { "Produse", "Fructe", "Legume", "Lactate", "Mezeluri", "Dulciuri", "Bauturi" };

    public View() {
        fo = new Font(searchTextField.getFont().toString(), Font.BOLD, 16);
        fo2 = new Font(searchTextField.getFont().toString(), Font.BOLD, 20);

        contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(1525, 730));
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

//        createProductsPanel(contentPanel, productBll.findAllProducts());
        createSellPanel(contentPanel);

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(1550, 830));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        createMeniu(mainPanel);
        createCategoryPanel(mainPanel);
        mainPanel.add(contentPanel);

        this.setVisible(true);
        this.setContentPane(mainPanel);
        this.setSize(1540, 830);
        this.setTitle("Shop");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        showPopUpDialog();
    }

    private void createMeniu(JPanel mainPanel){
        ImageIcon icon = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\logo.png"))).getImage().getScaledInstance(100, 50,  java.awt.Image.SCALE_SMOOTH));
        logoLabel = new JLabel(icon);
        logoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ImageIcon icon2 = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\cart.png"))).getImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH));
        cartLabel = new JLabel("Cosul meu", icon2, SwingConstants.RIGHT);
        cartLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ImageIcon icon3 = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\sell.png"))).getImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH));
        sellLabel = new JLabel("Vinde produs", icon3, SwingConstants.RIGHT);
        sellLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ImageIcon icon4 = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\user.png"))).getImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH));
        contLabel = new JLabel("Contul meu", icon4, SwingConstants.RIGHT);
        contLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ImageIcon icon5 = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\admin.png"))).getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH));
        adminLabel = new JLabel("Admin", icon5, SwingConstants.RIGHT);
        adminLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        adminLabel.setFont(new Font(contLabel.getFont().toString(), Font.BOLD, 13));
        JPanel auxAdminPanel = new JPanel();
        auxAdminPanel.add(adminLabel);
        auxAdminPanel.setBackground(Color.white);
        auxAdminPanel.setPreferredSize(new Dimension(70, 25));
        auxAdminPanel.setLayout(new BoxLayout(auxAdminPanel, BoxLayout.Y_AXIS));
        adminLabel.setVisible(false);

        ImageIcon icon6 = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\search.png"))).getImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH));
        searchLabel = new JLabel(icon6);
        searchLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        searchTextField.setFont(fo);
        searchTextField.setPreferredSize(new Dimension(0, 30));

        JPanel meniu = new JPanel();
        meniu.add(logoLabel);
        meniu.add(Box.createHorizontalStrut(20));
        meniu.add(searchTextField);
        meniu.add(searchLabel);
        meniu.add(Box.createHorizontalStrut(195));
        meniu.add(auxAdminPanel);
        meniu.add(Box.createHorizontalStrut(20));
        meniu.add(contLabel);
        meniu.add(Box.createHorizontalStrut(20));
        meniu.add(sellLabel);
        meniu.add(Box.createHorizontalStrut(20));
        meniu.add(cartLabel);
        meniu.add(Box.createHorizontalStrut(150));
        meniu.setPreferredSize(new Dimension(1525, 50));
        meniu.setBackground(Color.white);
        meniu.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        JPanel linePanel = new JPanel();
        linePanel.setPreferredSize(new Dimension(1525, 5));
        linePanel.setBackground(new Color(0x3c7eda));

        mainPanel.add(meniu);
        mainPanel.add(linePanel);
    }

    public void createProductsPanel(JPanel contentPanel, List<Product> productsList){
        categoryPanel.setVisible(true);
        List<JPanel> products = new ArrayList<>();

        for (int i = 0; i < 7; i++){
            products.add(new JPanel());
            products.get(i).setBackground(Color.white);
            products.get(i).setPreferredSize(new Dimension(225, 350));
        }

        JPanel productsPanel = new JPanel();
        productsPanel.setPreferredSize(new Dimension(1250, 1600));
        productsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        displayProducts(productsPanel, productsList);
        for (JPanel p : products) productsPanel.add(p);

        JScrollPane scrollFrame = new JScrollPane(productsPanel);
        scrollFrame.setPreferredSize(new Dimension( 1250, 650));
        scrollFrame.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollFrame.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollFrame.setBorder(BorderFactory.createEmptyBorder());

        contentPanel.setVisible(false);
        contentPanel.removeAll();
        contentPanel.add(scrollFrame);
        contentPanel.setVisible(true);
    }

    public void createCategoryPanel(JPanel contentPanel){
        categories = new JComboBox<>(categoryList);
        categories.setBackground(Color.white);
        categories.setSize(new Dimension(200, 40));

        JPanel sizeComboBox = new JPanel();
        sizeComboBox.add(categories);
        sizeComboBox.setBackground(Color.white);
        sizeComboBox.setPreferredSize(new Dimension(150, 30));
        sizeComboBox.setLayout(new BoxLayout(sizeComboBox, BoxLayout.X_AXIS));

        categoryPanel.add(Box.createHorizontalStrut(140));
        categoryPanel.add(sizeComboBox);
        categoryPanel.setPreferredSize(new Dimension(1525, 40));
        categoryPanel.setBackground(new Color(0x3c7eda));
        categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JPanel linePanel = new JPanel();
        linePanel.setPreferredSize(new Dimension(1525, 5));
        linePanel.setBackground(new Color(0x3c7eda));

        contentPanel.add(categoryPanel);
        contentPanel.add(linePanel);
        contentPanel.add(Box.createVerticalStrut(50));
    }

    private void displayProducts(JPanel productsPanel, List<Product> productsList){

        for (Product p: productsList){
            JPanel productPanel = new JPanel();
            productPanel.setPreferredSize(new Dimension(205, 330));
            productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
            productPanel.setBackground(Color.white);
            ImageIcon icon5;
            try{
                icon5 = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\" + p.getImage()))).getImage().getScaledInstance(205, 200,  java.awt.Image.SCALE_SMOOTH));
            }catch (NullPointerException nll){
                icon5 = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\noproduct.png"))).getImage().getScaledInstance(205, 200,  java.awt.Image.SCALE_SMOOTH));
            }
            JLabel picLabel = new JLabel(icon5);
            picLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            picLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    showProductInfo(p);
                }
            });

            JLabel nameLabel = new JLabel(p.getName());
            nameLabel.setFont(fo);

            JLabel priceLabel = new JLabel(String.valueOf(p.getPrice()) + " LEI");
            priceLabel.setFont(fo);
            priceLabel.setForeground(Color.red);

            JButton cartBtn= new JButton("       Adauga in cos      ");
            cartBtn.setBackground(new Color(0x3c7eda));
            cartBtn.setForeground(Color.white);
            cartBtn.setFont(fo);
            cartBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!cartList.containsKey(p.getId())){
                        cartList.put(p.getId(), 1);
                    }
                    cartBtn.setEnabled(false);
                }
            } );
            if (cartList.containsKey(p.getId())){
                cartBtn.setEnabled(false);
            }

            productPanel.add(picLabel);
            productPanel.add(productPanel.add(Box.createVerticalStrut(20)));
            productPanel.add(nameLabel);
            productPanel.add(productPanel.add(Box.createVerticalStrut(30)));
            productPanel.add(priceLabel);
            productPanel.add(productPanel.add(Box.createVerticalStrut(5)));
            productPanel.add(cartBtn);

            JPanel proBck = new JPanel();
            proBck.add(productPanel);
            proBck.setBackground(Color.white);
            proBck.setPreferredSize(new Dimension(225, 350));
            proBck.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

            productsPanel.add(proBck);

        }
    }

    private void showPopUpDialog(){
        JPanel panel = new JPanel(new GridLayout(0, 1));
        loginButton = new JButton("LOG IN");
        // = new JButton("SIGN UP");
        JButton signUpButton = new JButton("SIGN UP");
        JButton guestButton = new JButton("CONTINUE AS GUEST");

        JButton loginBtn = new JButton("LOGIN");
//        loginBtn.setBorder(BorderFactory.createLineBorder(Color.black));
        loginBtn.setBackground(new Color(0x58ACFF));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(fo);

//        signUpButton.setBorder(BorderFactory.createLineBorder(Color.black));
        signUpButton.setBackground(new Color(0x58ACFF));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(fo);

//        guestButton.setBorder(BorderFactory.createLineBorder(Color.black));
        guestButton.setBackground(new Color(0x58ACFF));
        guestButton.setForeground(Color.WHITE);
        guestButton.setFont(fo);

        panel.add(loginBtn);
        panel.add(signUpButton);
        panel.add(guestButton);


        dialog = new JDialog(this, "Welcome", true);

        dialog.getContentPane().add(panel);
        dialog.setSize(400,200);
        //  dialog.pack();
        dialog.setLocationRelativeTo(this); // Center on screen


        loginBtn.addActionListener(e -> {
            createLoginPanel(contentPanel);
            dialog.setVisible(false);
        });
        signUpButton.addActionListener(e -> {
            createSignUpPanel(contentPanel);
            dialog.setVisible(false);
        });
        guestButton.addActionListener(e -> {

            dialog.setVisible(false);
        });

        // Show the dialog
        dialog.setVisible(true);

    }

    public void showBillInfo(Client c){
        ImageIcon icon = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\bill.png"))).getImage().getScaledInstance(205, 200,  java.awt.Image.SCALE_SMOOTH));
        JLabel picLabel = new JLabel(icon);

        List<JLabel> labelList = new ArrayList<>();
        labelList.add(new JLabel("Nume:"));
        labelList.add(new JLabel("Prenume:"));
        labelList.add(new JLabel("Adresa:"));
        labelList.add(new JLabel("Pret total:"));
        labelList.add(new JLabel("Data livrare:"));


        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.white);
        leftPanel.add(Box.createVerticalStrut(40));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        for (JLabel l: labelList){
            l.setFont(fo);
            leftPanel.add(l);
            leftPanel.add(Box.createVerticalStrut(15));
        }

        LocalDate deliveryDate = LocalDate.now().plusDays(2);

        labelList.clear();
        labelList.add(new JLabel("" + c.getLastName()));
        labelList.add(new JLabel(""+ c.getFirstName()));
        labelList.add(new JLabel("" + c.getAddress()));
        labelList.add(new JLabel("" + totalCost + "LEI"));
        labelList.add(new JLabel("" + deliveryDate));


        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.white);
        rightPanel.add(Box.createVerticalStrut(40));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        for (JLabel l: labelList){
            l.setFont(fo);
            rightPanel.add(l);
            rightPanel.add(Box.createVerticalStrut(15));
        }

        JPanel dataPanel = new JPanel();
        dataPanel.add(leftPanel);
        dataPanel.add(rightPanel);
        dataPanel.setBackground(Color.white);

        JPanel infoPanel = new JPanel();
        infoPanel.add(picLabel);
        infoPanel.add(dataPanel);
        infoPanel.setBackground(Color.white);

        billInfo.getContentPane().removeAll();
        billInfo.add(infoPanel);
        billInfo.setSize(320,520);
        billInfo.setLocationRelativeTo(this);
        billInfo.setVisible(true);
    }

    private void showProductInfo(Product p){
        ImageIcon icon = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\" + p.getImage()))).getImage().getScaledInstance(205, 200,  java.awt.Image.SCALE_SMOOTH));
        JLabel picLabel = new JLabel(icon);

        List<JLabel> labelList = new ArrayList<>();
        labelList.add(new JLabel("Id:"));
        labelList.add(new JLabel("Nume:"));
        labelList.add(new JLabel("Pret:"));
        labelList.add(new JLabel("Cantitate"));
        labelList.add(new JLabel("Stoc:"));
        labelList.add(new JLabel("Categorie:"));
        labelList.add(new JLabel("Data de expirare:"));

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.white);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        for (JLabel l: labelList){
            l.setFont(fo);
            leftPanel.add(l);
            leftPanel.add(Box.createVerticalStrut(15));
        }

        labelList.clear();
        labelList.add(new JLabel("" + p.getId()));
        labelList.add(new JLabel(p.getName()));
        labelList.add(new JLabel("" + p.getPrice()));
        labelList.add(new JLabel("" + p.getQuantity()));
        labelList.add(new JLabel("" + p.getStock()));
        labelList.add(new JLabel(p.getCategory()));
        labelList.add(new JLabel(p.getExpiration() == null ? "     -" : p.getExpiration()));

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.white);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        for (JLabel l: labelList){
            l.setFont(fo);
            rightPanel.add(l);
            rightPanel.add(Box.createVerticalStrut(15));
        }

        JPanel dataPanel = new JPanel();
        dataPanel.add(leftPanel);
        dataPanel.add(rightPanel);
        dataPanel.setBackground(Color.white);

        JPanel infoPanel = new JPanel();
        infoPanel.add(picLabel);
        infoPanel.add(dataPanel);
        infoPanel.setBackground(Color.white);

        productInfo.getContentPane().removeAll();
        productInfo.add(infoPanel);
        productInfo.setSize(320,520);
        productInfo.setLocationRelativeTo(this);
        productInfo.setVisible(true);

    }

    public void createLoginPanel(JPanel contentPanel) {
        dialog.setVisible(false);
        categoryPanel.setVisible(false);

        emailTextField = new JTextField(20);
        emailTextField.setPreferredSize(new Dimension(300,50));
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(300,50));
        loginButton.setFont(fo);
        loginButton.setPreferredSize(new Dimension(50,50));

        ImageIcon icon = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\email.png"))).getImage().getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH));
        JLabel emailLabel = new JLabel(icon);
        ImageIcon icon2 = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\password.png"))).getImage().getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH));
        JLabel passLabel = new JLabel(icon2);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.add(emailLabel);
        panel1.add(Box.createRigidArea(new Dimension(50, 100))); // Spacing between components
        panel1.add(passLabel);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.add(emailTextField);
        panel2.add(Box.createRigidArea(new Dimension(50, 100))); // Spacing between components
        panel2.add(passwordField);

        JPanel panel3 = new JPanel();
        panel3.add(panel1);
        panel3.add(panel2);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.add(panel3);
        loginButton.setBackground(new Color(0x3c7eda));
        loginButton.setFont(fo);
        loginPanel.add(loginButton);

        contentPanel.setVisible(false);
        contentPanel.removeAll();
        contentPanel.add(loginPanel);
        contentPanel.setVisible(true);

    }

    public void createSignUpPanel(JPanel contentPanel) {
        dialog.setVisible(false);
        categoryPanel.setVisible(false);

        firstNameTextField = new JTextField(50);
        firstNameTextField.setPreferredSize(new Dimension(300,30));
        lastNameTextField = new JTextField(50);
        lastNameTextField.setPreferredSize(new Dimension(300,30));
        addrTextField = new JTextField(50);
        addrTextField.setPreferredSize(new Dimension(300,30));
        emailTextField = new JTextField(50);
        emailTextField.setPreferredSize(new Dimension(300,30));
        passwordField = new JPasswordField(50);
        passwordField.setPreferredSize(new Dimension(300,30));
        telTextField = new JPasswordField(50);
        telTextField.setPreferredSize(new Dimension(300,30));
        signButton.setBackground(new Color(0x66B2FF));
        signButton.setFont(fo);
        signButton.setPreferredSize(new Dimension(50,50));


        ImageIcon icon = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\email.png"))).getImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH));
        JLabel emailLabel = new JLabel( icon);
        ImageIcon icon2 = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\password.png"))).getImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH));
        JLabel passLabel = new JLabel( icon2);
        ImageIcon icon3 = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\adr.jpg"))).getImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH));
        JLabel adrLabel = new JLabel( icon3);
        ImageIcon icon4 = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\tel.png"))).getImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH));
        JLabel telLabel = new JLabel( icon4);

        JLabel firstNameLabel = new JLabel("PRENUME:");
        JLabel lastNameLabel = new JLabel("NUME:");

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.add(lastNameLabel);
        panel1.add(Box.createRigidArea(new Dimension(50, 70))); // Spacing between components
        panel1.add(firstNameLabel);
        panel1.add(Box.createRigidArea(new Dimension(50, 70))); // Spacing between components
        panel1.add(adrLabel);
        panel1.add(Box.createRigidArea(new Dimension(50, 70))); // Spacing between components
        panel1.add(telLabel);
        panel1.add(Box.createRigidArea(new Dimension(50, 70))); // Spacing between components
        panel1.add(emailLabel);
        panel1.add(Box.createRigidArea(new Dimension(50, 70))); // Spacing between components
        panel1.add(passLabel);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.add(lastNameTextField);
        panel2.add(Box.createRigidArea(new Dimension(50, 70))); // Spacing between components
        panel2.add(firstNameTextField);
        panel2.add(Box.createRigidArea(new Dimension(50, 70))); // Spacing between components
        panel2.add(addrTextField);
        panel2.add(Box.createRigidArea(new Dimension(50, 70))); // Spacing between components
        panel2.add(telTextField);
        panel2.add(Box.createRigidArea(new Dimension(50, 70))); // Spacing between components
        panel2.add(emailTextField);
        panel2.add(Box.createRigidArea(new Dimension(50, 70))); // Spacing between components
        panel2.add(passwordField);

        JPanel panel3 = new JPanel();
        // panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel3.add(panel1);
        panel3.add(panel2);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.add(panel3);
        loginPanel.add(signButton);

        contentPanel.setVisible(false);
        contentPanel.removeAll();
        contentPanel.add(loginPanel);
        contentPanel.setVisible(true);
    }

    public void createAccInfoPanel(JPanel contentPanel, Client c){
        categoryPanel.setVisible(false);

        Font fo2 = new Font(searchTextField.getFont().toString(), Font.BOLD, 20);
        List<JLabel> labelList = new ArrayList<>();
        labelList.add(new JLabel("Prenume: "));
        labelList.add(new JLabel("Nume: "));
        labelList.add(new JLabel("Nr. telefon: "));
        labelList.add(new JLabel("Email: "));
        labelList.add(new JLabel("Adresa: "));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        for(JLabel l: labelList){
            l.setFont(fo2);
            leftPanel.add(l);
            leftPanel.add(Box.createVerticalStrut(30));
        }

        labelList.clear();
        labelList.add(new JLabel(c.getFirstName() != null ? c.getFirstName() : " "));
        labelList.add(new JLabel(c.getLastName() != null ? c.getLastName() : " "));
        labelList.add(new JLabel(c.getPhone() != null ? c.getPhone() : " "));
        labelList.add(new JLabel(c.getEmail() != null ? c.getEmail() : " "));
        labelList.add(new JLabel(c.getAddress() != null ? c.getAddress() : " "));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        for(JLabel l: labelList){
            l.setFont(fo2);
            rightPanel.add(l);
            rightPanel.add(Box.createVerticalStrut(30));
        }

        JPanel upPanel = new JPanel();
        upPanel.add(leftPanel);
        upPanel.add(Box.createHorizontalStrut(50));
        upPanel.add(rightPanel);

        logoffBtn.setFont(fo2);
        logoffBtn.setForeground(Color.white);
        logoffBtn.setBackground(new Color(0x3c7eda));

        JPanel btnPanel = new JPanel();
        btnPanel.add(logoffBtn);

        JPanel infoPanel = new JPanel();
        infoPanel.add(Box.createVerticalStrut(110));
        infoPanel.add(upPanel);
        infoPanel.add(Box.createVerticalStrut(30));
        infoPanel.add(btnPanel);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        contentPanel.setVisible(false);
        contentPanel.removeAll();
        contentPanel.add(infoPanel);
        contentPanel.setVisible(true);
    }

    public void createCartPanel(JPanel contentPanel){
        categoryPanel.setVisible(false);

        List<JPanel> products = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            products.add(new JPanel());
            products.get(i).setBackground(Color.white);
            products.get(i).setPreferredSize(new Dimension(925, 200));
        }

        JPanel productsPanel = new JPanel();
        productsPanel.setPreferredSize(new Dimension(965, 1600)); //1250
        productsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        displayCartProducts(productsPanel, cartList);
        for (JPanel p : products) productsPanel.add(p);

        JLabel sumarLabel = new JLabel("Sumar comanda");
        sumarLabel.setFont(fo2);

        Font fo3 = new Font(searchTextField.getFont().toString(), Font.PLAIN, 16);
        Font fo4 = new Font(searchTextField.getFont().toString(), Font.BOLD, 24);
        JLabel costProductsLabel = new JLabel("Cost produse:");
        costProductsLabel.setFont(fo3);

        JLabel costTransportLabel = new JLabel("Cost livrare:");
        costTransportLabel.setFont(fo3);

        priceProductsLabel.setFont(fo3);

        JLabel priceTransportLabel = new JLabel("        20.00 LEI");
        priceTransportLabel.setFont(fo3);

        JLabel totalLabel = new JLabel("Total:                                    ");
        totalLabel.setFont(fo2);

        moneyLabel.setFont(fo4);

        JPanel costPanel = new JPanel();
        costPanel.add(costProductsLabel);
        costPanel.add(Box.createVerticalStrut(50));
        costPanel.add(costTransportLabel);
        costPanel.setBackground(Color.white);
        costPanel.setPreferredSize(new Dimension(125, 100));
        costPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        JPanel moneyPanel = new JPanel();
        moneyPanel.add(priceProductsLabel);
        moneyPanel.add(Box.createVerticalStrut(50));
        moneyPanel.add(priceTransportLabel);
        moneyPanel.setBackground(Color.white);
        moneyPanel.setPreferredSize(new Dimension(125, 100));
        moneyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

        JPanel auxPanel = new JPanel();
        auxPanel.add(costPanel);
        auxPanel.add(moneyPanel);
        auxPanel.setBackground(Color.white);

        buyBtn.setBackground(new Color(0x3c7eda));
        buyBtn.setForeground(Color.white);
        buyBtn.setFont(fo2);
        buyBtn.setPreferredSize(new Dimension(265, 40));

        updateCostLabel();

        JPanel pricePanel = new JPanel();
        pricePanel.add(sumarLabel);
        pricePanel.add(auxPanel);
        pricePanel.add(totalLabel);
        pricePanel.add(moneyLabel);
        pricePanel.add(buyBtn);
        pricePanel.setBackground(Color.white);
        pricePanel.setPreferredSize(new Dimension(285, 360));
        pricePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));

        JPanel rightPanel = new JPanel();
        rightPanel.add(pricePanel);
        rightPanel.setPreferredSize(new Dimension(285, 1600));
        rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 20));

        JPanel bigPanel = new JPanel();
        bigPanel.add(productsPanel);
        bigPanel.add(rightPanel);
        bigPanel.setPreferredSize(new Dimension(1250, 1600));
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.X_AXIS));

        JScrollPane scrollFrame = new JScrollPane(bigPanel);
        scrollFrame.setPreferredSize(new Dimension( 1250, 650));
        scrollFrame.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollFrame.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollFrame.setBorder(BorderFactory.createEmptyBorder());

        contentPanel.setVisible(false);
        contentPanel.removeAll();
        contentPanel.add(scrollFrame);
        contentPanel.setVisible(true);
    }

    private void displayCartProducts(JPanel productsPanel, HashMap<Integer, Integer> productsIdList){

        for (int id: productsIdList.keySet()){
            Product p = productBll.findProductById(id);
            JPanel productPanel = new JPanel();
            productPanel.setPreferredSize(new Dimension(905, 180));
            productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.X_AXIS));
            productPanel.setBackground(Color.white);

            ImageIcon icon = new ImageIcon(new ImageIcon(Objects.requireNonNull(View.class.getResource("..\\" + p.getImage()))).getImage().getScaledInstance(180, 180,  java.awt.Image.SCALE_SMOOTH));
            JLabel picLabel = new JLabel(icon);

            JLabel nameLabel = new JLabel(p.getName());
            nameLabel.setFont(fo2);
            JPanel namePanel = new JPanel();
            namePanel.add(nameLabel);
            namePanel.setBackground(Color.white);
            namePanel.setPreferredSize(new Dimension(500, 180));
            namePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

            JLabel priceLabel = new JLabel("" + p.getPrice() + " LEI");
            priceLabel.setFont(fo2);

            JTextField quantityTextField = new JTextField(2);
            quantityTextField.setText("1");
            quantityTextField.setEditable(false);
            quantityTextField.setBackground(Color.white);
            quantityTextField.setHorizontalAlignment(SwingConstants.CENTER);
            quantityTextField.setFont(new Font(searchTextField.getFont().toString(), Font.BOLD, 20));

            JButton plusBtn= new JButton("+");
            plusBtn.setBackground(new Color(0x3c7eda));
            plusBtn.setForeground(Color.white);
            plusBtn.setFont(fo);
            plusBtn.setPreferredSize(new Dimension(45, 30));
            plusBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int value = cartList.get(p.getId()) + 1;
                    if (value <= p.getStock()) {
                        cartList.replace(p.getId(), value);
                        quantityTextField.setText("" + value);
                        updateCostLabel();
                    }
                }
            } );

            JButton minusBtn= new JButton("-");
            minusBtn.setBackground(new Color(0x3c7eda));
            minusBtn.setForeground(Color.white);
            minusBtn.setFont(fo);
            minusBtn.setPreferredSize(new Dimension(45, 30));
            minusBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int value = cartList.get(p.getId()) - 1;
                    if (value > 0) {
                        cartList.replace(p.getId(), value);
                        quantityTextField.setText("" + value);
                        updateCostLabel();
                    }
                }
            } );

            JButton deleteBtn= new JButton("Sterge");
            deleteBtn.setBackground(new Color(0x3c7eda));
            deleteBtn.setForeground(Color.white);
            deleteBtn.setFont(fo2);
            deleteBtn.setPreferredSize(new Dimension(145, 30));
            deleteBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cartList.remove(p.getId());
                    refreshCartPanel();
                }
            } );

            JPanel textFieldPanel = new JPanel();
            textFieldPanel.add(quantityTextField);
            textFieldPanel.setBackground(Color.white);;
            textFieldPanel.setPreferredSize(new Dimension(20, 20));
            textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));

            JPanel quantityPanel = new JPanel();
            quantityPanel.add(minusBtn);
            quantityPanel.add(quantityTextField);
            quantityPanel.add(plusBtn);
            quantityPanel.setBackground(Color.white);

            JPanel controlPanel = new JPanel();
            controlPanel.add(priceLabel);
            controlPanel.add(quantityPanel);
            controlPanel.add(deleteBtn);
            controlPanel.setBackground(Color.white);
            controlPanel.setPreferredSize(new Dimension(225, 180));
            controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
            controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20));

            productPanel.add(picLabel);
            productPanel.add(namePanel);
            productPanel.add(controlPanel);

            JPanel proBck = new JPanel();
            proBck.add(productPanel);
            proBck.setBackground(Color.white);
            proBck.setPreferredSize(new Dimension(925, 200));
            proBck.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

            productsPanel.add(proBck);
        }
    }

    public void createSellPanel(JPanel contentPanel){
        categoryPanel.setVisible(false);

        JLabel name = new JLabel("Nume produs: ");
        JLabel price = new JLabel("Pret produs: ");
        JLabel quantity = new JLabel("Cantitate produs: ");
        JLabel stock = new JLabel("Stock produs: ");
        JLabel category = new JLabel("Categorie produs: ");
        JLabel expiration = new JLabel("Expirare produs: ");
        JLabel image = new JLabel("Imagine produs: ");

        JPanel label2 = new JPanel();
        label2.setLayout(new GridLayout(2, 4, 3, 3));
        label2.add(name);
        label2.add(tName);

        JPanel label3 = new JPanel();
        label3.setLayout(new GridLayout(2, 4, 3, 3));
        label3.add(price);
        label3.add(tPrice);

        JPanel label4 = new JPanel();
        label4.setLayout(new GridLayout(2, 4, 3, 3));
        label4.add(quantity);
        label4.add(tQuantity);

        JPanel label5 = new JPanel();
        label5.setLayout(new GridLayout(2, 4, 3, 3));
        label5.add(stock);
        label5.add(tStock);

        productsCategories = new JComboBox<>(categoryList);
        productsCategories.setBackground(Color.white);
        productsCategories.setSize(new Dimension(200, 40));

        JPanel label6 = new JPanel();
        label6.setLayout(new GridLayout(2, 4, 3, 3));
        label6.add(category);
        label6.add(productsCategories);

        Properties prop = new Properties();
        prop.put("text.today", "Today");
        prop.put("text.month", "Month");
        prop.put("text.year", "Year");
        JDatePickerImpl datePicker = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), prop),
                new JFormattedTextField.AbstractFormatter() {
            @Override
            public Object stringToValue(String text) throws ParseException {
                return "";
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                if (value != null)
                    return new SimpleDateFormat("dd-MM-yyyy").format(((Calendar) value).getTime());
                return "";
            }
        });

        JPanel label7 = new JPanel();
        label7.setLayout(new GridLayout(2, 4, 3, 3));
        label7.add(expiration);
        label7.add(datePicker);

        selectBtn.setPreferredSize(new Dimension(27, 20));
        JPanel auxLabel8 = new JPanel();
        auxLabel8.add(tImage);
        auxLabel8.add(selectBtn);
        auxLabel8.setLayout(new BoxLayout(auxLabel8, BoxLayout.X_AXIS));
        JPanel label8 = new JPanel();
        label8.setLayout(new GridLayout(2, 4, 3, 3));
        label8.add(image);
        label8.add(auxLabel8);

        sellBtn.setBackground(new Color(0x3c7eda));
        sellBtn.setForeground(Color.white);
        sellBtn.setFont(fo);
        JPanel btnPanel = new JPanel();
        btnPanel.add(sellBtn);


        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.add(label2);
        verticalPanel.add(label3);
        verticalPanel.add(label4);
        verticalPanel.add(label5);
        verticalPanel.add(label6);
        verticalPanel.add(label7);
        verticalPanel.add(label8);
        verticalPanel.add(Box.createVerticalStrut(30));
        verticalPanel.add(btnPanel);
        verticalPanel.setPreferredSize(new Dimension(500, 500));

        contentPanel.setVisible(false);
        contentPanel.removeAll();
        contentPanel.add(verticalPanel);
        contentPanel.setVisible(true);
    }

    public void createAdminPanel(JPanel contentPanel){
        clientTable = new JTable();
        clientTable.setFont(fo);
        clientTable.setRowHeight(20);
        clientTable.setBackground(Color.white);
        clientTable.setRowSelectionAllowed(true);
        clientTable.setModel(clientBll.getTableData());
        clientTable.setPreferredSize(new Dimension(550, 300));
        clientTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        clientTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        clientTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        clientTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        clientTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        clientTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        clientTable.getColumnModel().getColumn(6).setPreferredWidth(100);

        productTable = new JTable();
        productTable.setFont(fo);
        productTable.setRowHeight(20);
        productTable.setBackground(Color.white);
        productTable.setRowSelectionAllowed(true);
        productTable.setModel(productBll.getTableData());
        productTable.setPreferredSize(new Dimension(550, 300));
        productTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        productTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        productTable.getColumnModel().getColumn(4).setPreferredWidth(50);
        productTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        productTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        productTable.getColumnModel().getColumn(7).setPreferredWidth(80);

        JScrollPane clientScrollPane = new JScrollPane(clientTable);
        clientScrollPane.setBackground(Color.white);
        clientScrollPane.setPreferredSize(new Dimension(550, 200));
        clientScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        JScrollPane productScrollPane = new JScrollPane(productTable);
        productScrollPane.setBackground(Color.white);
        productScrollPane.setPreferredSize(new Dimension(550, 200));
        productScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        deleteClientBtn.setBackground(new Color(0x3c7eda));
        deleteClientBtn.setForeground(Color.white);
        deleteClientBtn.setFont(fo);

        deleteProductBtn.setBackground(new Color(0x3c7eda));
        deleteProductBtn.setForeground(Color.white);
        deleteProductBtn.setFont(fo);

        Font titleFont = new Font(deleteProductBtn.getFont().toString(), Font.BOLD, 24);
        JLabel clientLabel = new JLabel("CLIENTS");
        clientLabel.setFont(titleFont);
        JLabel productLabel = new JLabel("PRODUCTS");
        productLabel.setFont(titleFont);

        JPanel clientTitle = new JPanel();
        clientTitle.add(clientLabel);
        clientTitle.setPreferredSize(new Dimension(550, 40));
        clientTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        JPanel productTitle = new JPanel();
        productTitle.add(productLabel);
        productTitle.setPreferredSize(new Dimension(550, 40));
        productTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        JPanel clientPanel = new JPanel();
        clientPanel.add(Box.createVerticalStrut(100));
        clientPanel.add(clientTitle);
        clientPanel.add(clientScrollPane);
        clientPanel.add(Box.createVerticalStrut(200));
        clientPanel.add(deleteClientBtn);
        clientPanel.setPreferredSize(new Dimension(550, 650));

        JPanel productPanel = new JPanel();
        productPanel.add(Box.createVerticalStrut(100));
        productPanel.add(productTitle);
        productPanel.add(productScrollPane);
        productPanel.add(Box.createVerticalStrut(200));
        productPanel.add(deleteProductBtn);
        productPanel.setPreferredSize(new Dimension(550, 650));

        JPanel adminPanel = new JPanel();
        adminPanel.add(clientPanel);
        adminPanel.add(Box.createHorizontalStrut(100));
        adminPanel.add(productPanel);
        adminPanel.setPreferredSize(new Dimension( 1200, 450));
        adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.X_AXIS));

        contentPanel.setVisible(false);
        contentPanel.removeAll();
        contentPanel.add(adminPanel);
        contentPanel.setVisible(true);
    }

    public void updateCostLabel(){
        DecimalFormat dfrmt = new DecimalFormat();
        dfrmt.setMaximumFractionDigits(2);

        float cost = 0;

        for (int id: cartList.keySet()){
            cost += productBll.findProductById(id).getPrice() * cartList.get(id);
        }
        priceProductsLabel.setText(dfrmt.format(cost) + " LEI");
        if (cost == 0){
            moneyLabel.setText("0 LEI");
            totalCost = 0;
        }else {
            moneyLabel.setText(dfrmt.format(cost + 20) + " LEI");
            totalCost = cost+20;
        }

    }
    public void refreshCartPanel(){createCartPanel(contentPanel);}
    public String getCategory(){return (String) categories.getSelectedItem();}
    public JPanel getContentPanel(){return contentPanel;}
    public String getSearchName(){return searchTextField.getText();}

    public String getFirstNameInput(){return this.firstNameTextField.getText();}
    public String getEmailInput(){return this.emailTextField.getText();}
    public String getLastNameInput(){return this.lastNameTextField.getText();}
    public String getAddrInput(){return this.addrTextField.getText();}
    public String getPassInput(){return this.passwordField.getText();}
    public String getTelInput(){return this.telTextField.getText();}


    public String getNameInput(){return this.tName.getText();}
    public String getPriceInput(){return this.tPrice.getText();}
    public String getQuantityInput(){return this.tQuantity.getText();}
    public String getStockInput(){return this.tStock.getText();}
    public String getCategoryInput(){return this.productsCategories.getSelectedItem() + "";}
    public String getExpirationInput(){return this.tExpiration.getText();}
    public JTextField getImageTextField(){return this.tImage;}

    public JTable getClientTable(){return clientTable;}
    public JTable getProductTable(){return productTable;}

    public void addLogoBtnListener(MouseAdapter l){logoLabel.addMouseListener(l);}
    public void addAdminBtnListener(MouseAdapter l){adminLabel.addMouseListener(l);}
    public void addAccBtnListener(MouseAdapter l){contLabel.addMouseListener(l);}
    public void addCartBtnListener(MouseAdapter l){cartLabel.addMouseListener(l);}
    public void addCategoryListener(ActionListener l){categories.addActionListener(l);}
    public void addSearchListener(ActionListener l){searchTextField.addActionListener(l);}
    public void addSearchBtnListener(MouseAdapter l){searchLabel.addMouseListener(l);}
    public void addSignUpBtnListener(ActionListener l){signButton.addActionListener(l);}
    public void addLoginBtnListener(ActionListener l){loginButton.addActionListener(l);}
    public void addLogoffBtnListener(ActionListener l){logoffBtn.addActionListener(l);}
    public void addSellLabelListener(MouseAdapter l){sellLabel.addMouseListener(l);}
    public void addSellBtnListener(ActionListener l){sellBtn.addActionListener(l);}
    public void addBuyBtnListener(ActionListener l){buyBtn.addActionListener(l);}
    public void addSelectBtnListener(ActionListener l){selectBtn.addActionListener(l);}
    public void addDeleteClientListener(ActionListener l){deleteClientBtn.addActionListener(l);}
    public void addDeleteProductListener(ActionListener l){deleteProductBtn.addActionListener(l);}

    public void setAdminLabelVisible(boolean bool){adminLabel.setVisible(bool);}
    public JDialog getDialog() {
        return dialog;
    }
    public void showMessageError(String ms){JOptionPane.showMessageDialog(this, ms);}
    public HashMap<Integer, Integer> getCartList(){return cartList;}

}
