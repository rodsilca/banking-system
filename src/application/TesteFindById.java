package application;

import model.dao.ClientDao;
import model.dao.DaoFactory;
import model.entities.Client;

public class TesteFindById {
    public static void main(String[] args) {
        ClientDao clientDao = DaoFactory.createClientDao();

        Client client = clientDao.findById(1);

        System.out.println(client.toString());
    }
}
