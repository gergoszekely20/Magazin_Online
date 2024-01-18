package BusinessLogic;

import BusinessLogic.Validators.*;
import DAO.ClientDAO;
import Model.Client;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private final ClientDAO clientDAO;
    private final List<Validator<Client>> validators;

    public ClientBLL(){
        clientDAO = new ClientDAO();
        validators = new ArrayList<>();
        validators.add(new IdValidator<>());
        validators.add(new NameValidator<>());
        validators.add(new PhoneValidator());
        validators.add(new EmailValidator());
        validators.add(new AddressValidator());
    }
    public Client findClientById(int id) {
        return clientDAO.findById(id);
    }

    public Client findClientByEmail(String email) {
        return clientDAO.findByEmail(email);
    }

    public List<Client> findAllClients(){
        List<Client> list = clientDAO.findAll();
        if (list == null) {
            throw new NoSuchElementException("The clients was not found!");
        }
        return list;
    }

    public DefaultTableModel getTableData(){
        List<Client> clientList = findAllClients();
        clientList.remove(0);
        DefaultTableModel data = clientDAO.getTableData(clientList);
        if (data == null) {
            throw new NoSuchElementException("The clients was not found!");
        }
        return data;
    }

    public void insertClient(Client c) throws Exception{
        try {
            for (Validator<Client> v : validators) {
                v.validate(c);
            }if (clientDAO.insert(c) == null) {
                throw new NoSuchElementException("The client was not inserted!");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void updateClient(Client c) throws Exception{
        try {
            for (Validator<Client> v : validators) {
                v.validate(c);
            }if (clientDAO.update(c) == null) {
                throw new NoSuchElementException("The client was not updated!");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void deleteClient(Client c){
        if (!clientDAO.delete(c)) {
            throw new NoSuchElementException("The client was not deleted!");
        }
    }

    public int findClientLastId(){
        int id = clientDAO.findLastId();
        if (id == -1){
            throw new NoSuchElementException("The id was not found!");
        }
        return id;
    }

}
