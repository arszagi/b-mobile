package dao.interfaces;

import dto.MobilityDto;
import exceptions.MalformedIbanException;
import exceptions.NoMobilityException;

import java.util.ArrayList;

public interface MobilityDao {

  void createMobility(MobilityDto mobilityDto);

  /**
   * Return an ArrayList containing all the mobilities stored in database.
   *
   * @return an ArrayList of MobilityDto.
   */
  ArrayList<MobilityDto> getAllMobilities();

  /**
   * Return an ArrayList containing all the mobilities of one departements stored in database.
   *
   * @return an ArrayList of MobilityDto.
   * @throws MalformedIbanException If the Iban is malformed.
   */

  ArrayList<MobilityDto> getFullMobilities() throws MalformedIbanException;

  /**
   * Return an ArrayList containing all the confirmed mobilities stored in database.
   *
   * @return an ArrayList of MobilityDto.
   * @throws MalformedIbanException If the Iban is malformed.
   */
  ArrayList<MobilityDto> getFullConfirmedMobilities() throws MalformedIbanException;

  /**
   * Return an ArrayList containing all the mobilities of one user stored in database.
   *
   * @param user Pseudo of user who want to see his mobilities
   * @return an ArrayList of MobilityDto.
   * @throws MalformedIbanException If the Iban is malformed.
   */

  ArrayList<MobilityDto> getFullMyMobilities(String user) throws MalformedIbanException;

  /**
   * Return an ArrayList containing all the academic years stored in database.
   *
   * @return an ArrayList with the academic years.
   */
  ArrayList<String> getAllAcademicYears();

  /**
   * Return an ArrayList containing all the payments matching with the academicYear.
   *
   * @return an ArrayList with the academic years.
   * @throws MalformedIbanException If the Iban is malformed.
   */
  ArrayList<MobilityDto> getFullPayments() throws MalformedIbanException;

  /**
   * Return basic information of Mobility DTO based on an id.
   *
   * @param id Nr Id of mobility.
   * @return Mobility DTO.
   * @throws NoMobilityException If no mobility is matching with the id.
   * @throws MalformedIbanException If the Iban is malformed.
   */
  MobilityDto getMobilityById(int id) throws NoMobilityException, MalformedIbanException;

  /**
   * Cancels the mobility matching with the id.
   * 
   * @param idMobility the id of the mobility.
   * @param verNr The version number before cancelation.
   * @param idCancelation the id of the cancellation.
   * @return the number of rows modified
   */
  int cancelMobility(int idMobility, int idCancelation, int verNr);

  /**
   * Update mobility to join whith the partner id.
   * 
   * @param mobilityDto mobility DTO.
   * @return the number of rows modified
   */
  int confirmPartner(MobilityDto mobilityDto);

  /**
   * Update the mobility details in the Database whith the informations in the dto.
   * 
   * @param mobility the dto with the informations.
   * @return the number of rows modified
   */
  int updateMobilityDetails(MobilityDto mobility);
}
