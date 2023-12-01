package BusinessLogic;

import DAO.OrderDAO;
import Model.Order;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {
    private final OrderDAO orderDAO;

    public OrderBLL(){
        orderDAO = new OrderDAO();
    }
    public Order findOrderById(int id) {
        Order Order = orderDAO.findById(id);
        if (Order == null) {
            throw new NoSuchElementException("The Order with id =" + id + " was not found!");
        }
        return Order;
    }

    public DefaultTableModel getTableData(){
        DefaultTableModel data = orderDAO.getTableData(findAllOrders());
        if (data == null) {
            throw new NoSuchElementException("The clients was not found!");
        }
        return data;
    }

    public List<Order> findAllOrders(){
        List<Order> list = orderDAO.findAll();
        if (list == null) {
            throw new NoSuchElementException("The orders was not found!");
        }
        return list;
    }

    public void insertOrder(Order p) throws Exception {
        if (orderDAO.insert(p) == null) {
            throw new NoSuchElementException("The Order was not inserted!");
        }
    }

    public void deleteOrder(Order p){
        if (!orderDAO.delete(p)) {
            throw new NoSuchElementException("The Order was not deleted!");
        }
    }

    public int findOrderLastId(){
        int id = orderDAO.findLastId();
        if (id == -1){
            throw new NoSuchElementException("The id was not found!");
        }
        return id;
    }

}
