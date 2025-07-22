package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.ClientDao;
import model.entities.Account;
import model.entities.Client;
import model.enums.ClientGender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ClientDaoJDBC implements ClientDao {
    private Connection conn;

    public ClientDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Client obj) {

    }

    @Override
    public void update(Client obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Client findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            //refazer So precisa retornar o CLient
            st = conn.prepareStatement("Select clients.*, accounts.Account_number as AccountNumber " +
                    "FROM clients INNER JOIN accounts " +
                    "ON accounts.Client_id = clients.Id " +
                    "where clients.Id = ?");

            st.setInt(1,id);
            rs = st.executeQuery();

            if (rs.next()){
                Client client = new Client(rs.getString("Name"),rs.getString("Cpf"), ClientGender.valueOf(rs.getString("Gender")), rs.getDate("BirthDate").toLocalDate());
                return client;
            }
            return null;
        }
        catch (SQLException e ){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);

        }

    }

    @Override
    public List<Client> findAll() {
        return List.of();
    }

    @Override
    public List<Client> findByAccountNumber(Account account) {
        return List.of();
    }
}
