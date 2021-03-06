package dao.interfaces;

import dto.DepartmentDto;
import dto.PartnerDto;
import exceptions.MalformedIbanException;

import java.util.ArrayList;

public interface PartnerDao {

  /**
   * Add partner to DB.
   *
   * @param partner DTO partner
   * @param departments ArrayList of Department DTO
   */
  void createPartner(PartnerDto partner, ArrayList<DepartmentDto> departments);

  /**
   * Get all partners created by one user.
   *
   * @param userId Id of user.
   * @param permission Type of permission (Teacher or Student).
   * @return An ArrayList with all partners.
   */
  ArrayList<PartnerDto> getPartnersMin(int userId, String permission);

  /**
   * Get a partner.
   *
   * @param id Id of partner.
   * @return A partnerDto.
   * @throws MalformedIbanException If the Iban is malformed.
   */
  PartnerDto getPartnerById(int id) throws MalformedIbanException;

  /**
   * Get all the partners of the database.
   * 
   * @return An ArrayList of PartnerDto
   * @throws MalformedIbanException If the Iban is malformed.
   */
  ArrayList<PartnerDto> getAllPartners() throws MalformedIbanException;

  /**
   * Update the partner details in the Database whith the informations in the dto.
   * 
   * @param partner the dto with the informations.
   * @param departments Array List of departmentDto.
   * @return the number of rows modified
   */
  int updatePartner(PartnerDto partner, ArrayList<DepartmentDto> departments);

  /**
   * Get all the partners of the database added by a teacher.
   * 
   * @return An arraylist of PartnerDto
   * @throws MalformedIbanException If the Iban is malformed.
   */
  ArrayList<PartnerDto> getTeacherPartners() throws MalformedIbanException;

  /**
   * Get partners who do not have mobility.
   * 
   * @return an ArrayList with partnerDto.
   */
  ArrayList<PartnerDto> getPartnersWithoutMobility();


  /**
   * Get deleted partners of the database
   * 
   * @return an ArrayList with PartnerDto
   * @throws MalformedIbanException If the Iban is malformed.
   */
  ArrayList<PartnerDto> getDeletedPartners() throws MalformedIbanException;

  /**
   * Set the field "deleted" of a partner.
   * 
   * @param partnerDto The partner dto containing the modification.
   * @return The number of row updated.
   */
  int setDeleted(PartnerDto partnerDto);

  /**
   * Get all partners of the database that a student may see.
   *
   * @param userId Id of user.
   * @return An ArrayList with all partners.
   */
  ArrayList<PartnerDto> getPartnersForStudentList(int userId);

  /**
   * Get all departments of partner.
   *
   * @param partnerId id of partner
   * @return ArrayList with departmentsDto
   */
  ArrayList<DepartmentDto> getAllPartnerDepartments(int partnerId);
}
