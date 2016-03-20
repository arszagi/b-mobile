package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bizz.BizzFactory;
import dal.DalBackendServices;
import dto.UserDto;

public class UserDaoImpl implements UserDao {

  private DalBackendServices dalBackendServices;
  private BizzFactory factory;

  public UserDaoImpl(DalBackendServices dalBackendServices, BizzFactory bizzFactory) {
    this.dalBackendServices = dalBackendServices;
    this.factory = bizzFactory;
  }

  @Override
  public boolean createUser(UserDto userdto) {
    // TODO(fany) values en fonction de la db
    String query = "INSERT INTO bmobile.users VALUES (DEFAULT,?,?,?,?,?)";
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = dalBackendServices.prepare(query);
      preparedStatement.setString(1, userdto.getPseudo());
      preparedStatement.setString(2, userdto.getPassword());
      preparedStatement.setString(3, userdto.getName());
      preparedStatement.setString(4, userdto.getFirstname());
      preparedStatement.setString(5, userdto.getEmail());
      try (ResultSet res = preparedStatement.executeQuery()) {
        return true;

      } catch (Exception exc) {
        return false;
      }

    } catch (SQLException exc) {
      exc.printStackTrace();
      return false;
    }
  }

  @Override
  public void read() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean update() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean delete() {
    // TODO Auto-generated method stub
    return false;
  }


  @Override
  public UserDto findByUserName(String username) {
    String query = "SELECT id, id_department, pseudo, password, name, firstname, email, "
        + "registration_date, permission, birth_date, street, "
        + "house_number, mailbox, zip, city, country, tel, gender, successfully_year_in_college, "
        + "iban, bic, account_holder, bank_name, ver_nr FROM bmobile.users WHERE pseudo=?";
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = dalBackendServices.prepare(query);
      preparedStatement.setString(1, username);
      return fillDto(preparedStatement);
    } catch (SQLException exc) {
      exc.printStackTrace();
      return null;
    }
  }

  @Override
  public UserDto findById(int id) {
    String query = "SELECT id, id_department, pseudo, password, name, firstname, email, "
        + "registration_date, permission, birth_date, street, "
        + "house_number, mailbox, zip, city, country, tel, gender, successfully_year_in_college, "
        + "iban, bic, account_holder, bank_name, ver_nr FROM bmobile.users WHERE id=?";
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = dalBackendServices.prepare(query);
      preparedStatement.setInt(1, id);
      return fillDto(preparedStatement);
    } catch (SQLException exc) {
      exc.printStackTrace();
      return null;
    }
  }

  private UserDto fillDto(PreparedStatement preparedStatement) {
    UserDto user = factory.getUserDto();
    try (ResultSet resultSet = preparedStatement.executeQuery()) {
      if (resultSet.next()) {
        user.setId(resultSet.getInt(1));
        user.setIdDepartment(resultSet.getInt(2));
        user.setPseudo(resultSet.getString(3));
        user.setPassword(resultSet.getString(4));
        user.setName(resultSet.getString(5));
        user.setFirstname(resultSet.getString(6));
        user.setEmail(resultSet.getString(7));
        user.setRegistrationDate(resultSet.getDate(8).toLocalDate());
        user.setPermissions(resultSet.getString(9));
        user.setBirthDate(resultSet.getDate(10).toLocalDate());
        user.setStreet(resultSet.getString(11));
        user.setHouseNumber(resultSet.getString(12));
        user.setMailBox(resultSet.getString(13));
        user.setZip(resultSet.getString(14));
        user.setCity(resultSet.getString(15));
        user.setCountry(resultSet.getString(16));
        user.setTel(resultSet.getString(17));
        user.setGender(resultSet.getString(18));
        user.setSuccessfullYearInCollege(resultSet.getInt(19));
        user.setIban(resultSet.getString(20));
        user.setBic(resultSet.getString(21));
        user.setAccountHolder(resultSet.getString(21));
        user.setBankName(resultSet.getString(22));
        user.setVerNr(resultSet.getInt(22));
      } else {
        return null;
      }
      return user;
    } catch (SQLException exc2) {
      exc2.printStackTrace();
      return null;
    }


  }

}
