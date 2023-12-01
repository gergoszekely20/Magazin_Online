package DAO;

import Connection.ConnectionFactory;

import javax.swing.table.DefaultTableModel;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final String DATABASE = "schooldb.";
    private String insertStatementString;
    private final String selectStatementString;
    private final String updateStatementString;
    private final String deleteStatementString;
    private final String findAllStatementString;
    private final String findLastIdStatementString;
    private final String selectNameStatementString;
    private final String selectEmailStatementString;
    private final String selectCategoryStatementString;
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        insertStatementString = "INSERT INTO " + DATABASE + type.getSimpleName().toLowerCase() + " VALUES (";
        for (int i = 0; i < type.getDeclaredFields().length - 1; i++) {
            insertStatementString += "?,";
        }
        insertStatementString += "?);";
        updateStatementString = "UPDATE " + DATABASE + type.getSimpleName() + " SET ";
        findAllStatementString = "SELECT * FROM " + DATABASE + type.getSimpleName() + ";";
        deleteStatementString = "DELETE FROM " + DATABASE + type.getSimpleName() + " WHERE id = ?;";
        findLastIdStatementString = "SELECT * FROM " + DATABASE + type.getSimpleName() + " ORDER BY id DESC LIMIT 1;";
        selectStatementString = "SELECT * FROM " + DATABASE + type.getSimpleName() + " WHERE id =?;";
        selectEmailStatementString = "SELECT * FROM " + DATABASE + type.getSimpleName() + " WHERE email =?;";
        selectNameStatementString = "SELECT * FROM " + DATABASE + type.getSimpleName() + " WHERE lower(name) LIKE ?;";
        selectCategoryStatementString = "SELECT * FROM " + DATABASE + type.getSimpleName() + " WHERE category =?;";
    }

    public int findLastId(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findLastIdStatementString);
            rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
//            LOGGER.log(Level.WARNING, type.getName() + "DAO:findLastId " + e.getMessage());
            return 0;
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findAllStatementString);
            rs = statement.executeQuery();
            return createObjects(rs);
        } catch (SQLException e) {
            return new ArrayList<>();
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(selectStatementString);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            List<T> list = createObjects(rs);
            if (list.isEmpty()){return null;}
            return list.get(0);
        } catch (SQLException e) {
            return null;
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public T findByEmail(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(selectEmailStatementString);
            statement.setString(1, email);
            rs = statement.executeQuery();
            List<T> list = createObjects(rs);
            if (list.isEmpty()){return null;}
            return list.get(0);
        } catch (SQLException e) {
            return null;
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public List<T> findByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(selectNameStatementString);
            statement.setString(1, "%" + name.toLowerCase() + "%");
            rs = statement.executeQuery();
            return createObjects(rs);
        } catch (SQLException e) {
            return new ArrayList<>();
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public List<T> findByCategory(String category) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(selectCategoryStatementString);
            statement.setString(1, category);
            rs = statement.executeQuery();
            return createObjects(rs);
        } catch (SQLException e) {
            return new ArrayList<>();
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public T insert(T t) throws Exception {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int index = 1;
        try {
            insertStatement = connection.prepareStatement(insertStatementString);
            for (Field field : type.getDeclaredFields()) {
                String fieldName = field.getName();
                PropertyDescriptor pd = new PropertyDescriptor(fieldName, type);
                Method getter = pd.getReadMethod();
                if (field.getType().getName().equals("int")){
                    insertStatement.setInt(index++, (int)getter.invoke(t));
                }else if (field.getType().getName().equals("float")){
                    insertStatement.setFloat(index++, (float)getter.invoke(t));
                }else{
                    insertStatement.setString(index++, (String)getter.invoke(t));
                }
            }
            insertStatement.executeUpdate();
        } catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new Exception(e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    public T update(T t) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        try {
            int id = -1;
            String updateString = updateStatementString;
            for (Field field : type.getDeclaredFields()) {
                String fieldName = field.getName();
                PropertyDescriptor pd = new PropertyDescriptor(fieldName, type);
                Method getter = pd.getReadMethod();
                if (fieldName.equals("id")){
                    id = (int)getter.invoke(t);
                    continue;
                }
                updateString += fieldName + " = '" + getter.invoke(t) + "', ";
            }
            updateString = updateString.substring(0, updateString.length() - 2);
            updateString += " WHERE id = " + id + ";";
            updateStatement = connection.prepareStatement(updateString);
            updateStatement.executeUpdate();
        } catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.WARNING, "ClioentDAO: update " + e.getMessage());
            return null;
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    public DefaultTableModel getTableData(List<T> obj){
        DefaultTableModel table = new DefaultTableModel();
        for (Field field : type.getDeclaredFields()){
            field.setAccessible(true);
            table.addColumn(field.getName());
        }
        for (T t: obj){
            Vector<Object> data = new Vector<>();
            for (Field field : type.getDeclaredFields()){
                field.setAccessible(true);
                Object value;
                try{
                    value = field.get(t);
                    data.add(value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            table.addRow(data);
        }
        return table;
    }

    public boolean delete(T t) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = connection.prepareStatement(deleteStatementString);
            for (Field field : type.getDeclaredFields()) {
                String fieldName = field.getName();
                if (fieldName.equals("id")){
                    PropertyDescriptor pd = new PropertyDescriptor(fieldName, type);
                    Method getter = pd.getReadMethod();
                    deleteStatement.setInt(1, (int)getter.invoke(t));
                    break;
                }
            }
            deleteStatement.executeUpdate();
        } catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.WARNING, "ClioentDAO: delete " + e.getMessage());
            return false;
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(connection);
        }
        return true;
    }

}
