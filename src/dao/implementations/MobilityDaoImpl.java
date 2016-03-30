package dao.implementations;

import bizz.interfaces.BizzFactory;
import dal.DalBackendServices;
import dao.interfaces.MobilityDao;
import dto.MobilityDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MobilityDaoImpl implements MobilityDao {

  private DalBackendServices dalBackendServices;
  private BizzFactory factory;

  public MobilityDaoImpl(DalBackendServices dalBackendServices, BizzFactory bizzFactory) {
    this.dalBackendServices = dalBackendServices;
    this.factory = bizzFactory;
  }

  @Override
  public void createMobility(MobilityDto mobilityDto) {
    String query =
        "INSERT INTO bmobile.mobilities VALUES (DEFAULT,?,?,NULL,?,?,?,?,?,NULL,FALSE,FALSE,FALSE,FALSE,FALSE,"
            + "FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,NULL,NULL,0)";
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = dalBackendServices.prepare(query);
      preparedStatement.setInt(1, mobilityDto.getStudentDto().getId());
      preparedStatement.setInt(2, mobilityDto.getProgramDto().getId());
      preparedStatement.setString(3, mobilityDto.getType());
      preparedStatement.setInt(4, mobilityDto.getPreferenceOrder());
      preparedStatement.setString(5, mobilityDto.getCountryDto().getIso());
      preparedStatement.setString(6, mobilityDto.getDepartementDto().getId());
      preparedStatement.setInt(7, mobilityDto.getQuadrimester());
      try {
        preparedStatement.executeUpdate();

      } catch (Exception exc) {
        exc.printStackTrace();
      }

    } catch (SQLException exc) {
      exc.printStackTrace();
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
  public ArrayList<MobilityDto> getAllMobilities() {
    String query =
        "SELECT id, id_student, id_program, id_partner, type, preference_order, country, id_department, "
            + "quadrimester, status, canceled, departure_grant_contract, departure_convention_internship_schoolarship,"
            + " departure_student_convention, departure_erasmus_language_test, departure_doc_aggreement, "
            + "depart_doc_sent_highschool, software_proeco, software_mobility_tools, software_mobi, return_residence_cert, "
            + "return_transcript, return_internship_cert, return_final_report, return_erasmus_language_test, "
            + "return_doc_sent_highschool, cancelation_reason, academic_year, ver_nr "
            + "FROM bmobile.mobilities";
    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = dalBackendServices.prepare(query);
      ArrayList<MobilityDto> mobilities = new ArrayList<MobilityDto>();
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          mobilities.add(fillDto(resultSet));
        }
        return mobilities;
      } catch (SQLException exc2) {
        exc2.printStackTrace();
        return null;
      }
    } catch (SQLException exc) {
      exc.printStackTrace();
      return null;
    }
  }

  @Override
  public ArrayList<MobilityDto> getFullConfirmedMobilities() {
    String query =
        "SELECT m.id, m.id_student, m.id_program, m.id_partner, m.type, m.preference_order, m.country, m.id_department, "
            + "m.quadrimester, m.status, m.canceled, m.departure_grant_contract, m.departure_convention_internship_schoolarship, "
            + "m.departure_student_convention, m.departure_erasmus_language_test, m.departure_doc_aggreement, "
            + "m.depart_doc_sent_highschool, m.software_proeco, m.software_mobility_tools, m.software_mobi, m.return_residence_cert, "
            + "m.return_transcript, m.return_internship_cert, m.return_final_report, m.return_erasmus_language_test, "
            + "m.return_doc_sent_highschool, m.cancelation_reason, m.academic_year, m.ver_nr, "

            + "u.id, u.id_department, u.pseudo, u.name, u.firstname, u.email, u.registration_date, u.permissions, "
            + "u.birth_date, u.street, u.house_number, u.mailbox, u.zip, u.city, u.country, u.tel, u.gender, "
            + "u.successfull_year_in_college, u.iban, u.bic, u.account_holder, u.bank_name, u.ver_nr, "

            + "pro.id, pro.name, pro.description, pro.ver_nr, "

    // + "par.id, par.id_user, par.legal_name, par.business_name, par.full_name, par.department,
    // par.type,"
    // + "par.nb_employees, par.street, par.number, par.mailbox, par.zip, par.city, par.state,"
    // + "par.country, par.email, par.website, par.exists, par.ver_nr,"

            + "co.iso, co.name_en, co.name_fr, co.id_program," + "d.id, d.label, d.ver_nr "
            // + ",c.id, c.reason, c.responsible, c.ver_nr "

            + "FROM bmobile.mobilities m, bmobile.users u, bmobile.programs pro, "
            // + "bmobile.partners par, "
            + "bmobile.countries co, bmobile.departments d "
            // + ", bmobile.cancelations c "

            + "WHERE (m.id_student = u.id) AND (m.id_program = pro.id) "
            // + "AND (m.id_partner = par.id) "
            + "AND (m.country = co.iso) AND (m.id_department = d.id) AND"
            // + "AND (m.cancelation_reason = c.id) AND "
            + "(m.status != 'PENDING' OR m.canceled = 'false')";

    PreparedStatement preparedStatement = null;
    try {
      preparedStatement = dalBackendServices.prepare(query);
      ArrayList<MobilityDto> mobilities = new ArrayList<MobilityDto>();
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          mobilities.add(fillFullDto(resultSet));
        }
        return mobilities;
      } catch (SQLException exc2) {
        exc2.printStackTrace();
        return null;
      }
    } catch (SQLException exc) {
      exc.printStackTrace();
      return null;
    }

  }

  private MobilityDto fillDto(ResultSet resultSet) throws SQLException {
    MobilityDto mobilitydto = factory.getMobilityDto();
    mobilitydto.setId(resultSet.getInt(1));
    mobilitydto.setIdStudent(resultSet.getInt(2));
    mobilitydto.setIdProgram(resultSet.getInt(3));
    mobilitydto.setIdPartner(resultSet.getInt(4));
    mobilitydto.setType(resultSet.getString(5));
    mobilitydto.setPreferenceOrder(resultSet.getInt(6));
    mobilitydto.setIsoCountry(resultSet.getString(7));
    mobilitydto.setIdDepartment(resultSet.getString(8));
    mobilitydto.setQuadrimester(resultSet.getInt(9));
    mobilitydto.setStatus(resultSet.getString(10));
    mobilitydto.setCanceled(resultSet.getBoolean(11));
    mobilitydto.setDepartureGrantContract(resultSet.getBoolean(12));
    mobilitydto.setDepartureConventionInternshipSchoolarship(resultSet.getBoolean(13));
    mobilitydto.setDepartureStudentConvention(resultSet.getBoolean(14));
    mobilitydto.setDepartureErasmusLanguageTest(resultSet.getBoolean(15));
    mobilitydto.setDepartureDocAggreement(resultSet.getBoolean(16));
    mobilitydto.setDepartDocSentHighschool(resultSet.getBoolean(17));
    mobilitydto.setSoftwareProeco(resultSet.getBoolean(18));
    mobilitydto.setSoftwareMobilityTools(resultSet.getBoolean(19));
    mobilitydto.setSoftwareMobi(resultSet.getBoolean(20));
    mobilitydto.setReturnResidenceCert(resultSet.getBoolean(21));
    mobilitydto.setReturnTranscript(resultSet.getBoolean(22));
    mobilitydto.setReturnInternshipCert(resultSet.getBoolean(23));
    mobilitydto.setReturnFinalReport(resultSet.getBoolean(24));
    mobilitydto.setReturnErasmusLanguageTest(resultSet.getBoolean(25));
    mobilitydto.setReturnDocSentHighschool(resultSet.getBoolean(26));
    mobilitydto.setCancelationReason(resultSet.getInt(27));
    mobilitydto.setAcademicYear(resultSet.getString(28));
    mobilitydto.setVerNr(resultSet.getInt(29));
    return mobilitydto;
  }

  private MobilityDto fillFullDto(ResultSet resultSet) throws SQLException {

    MobilityDto mobilitydto = fillDto(resultSet);
    mobilitydto.setStudentDto(factory.getUserDto());
    mobilitydto.getStudentDto().setId(resultSet.getInt(30));
    mobilitydto.getStudentDto().setIdDepartment(resultSet.getInt(31));
    mobilitydto.getStudentDto().setPseudo(resultSet.getString(32));
    // mobilitydto.getStudentDto().setPassword(resultSet.getString(33));
    mobilitydto.getStudentDto().setName(resultSet.getString(33));
    mobilitydto.getStudentDto().setFirstname(resultSet.getString(34));
    mobilitydto.getStudentDto().setEmail(resultSet.getString(35));

    Timestamp registrationDate = resultSet.getTimestamp(36);
    if (null != registrationDate) {
      mobilitydto.getStudentDto()
          .setRegistrationDate(registrationDate.toLocalDateTime().toLocalDate());
    }
    mobilitydto.getStudentDto().setPermissions(resultSet.getString(37));
    Timestamp birthdate = resultSet.getTimestamp(38);
    if (null != birthdate) {
      mobilitydto.getStudentDto().setBirthDate(birthdate.toLocalDateTime().toLocalDate());
    }
    mobilitydto.getStudentDto().setStreet(resultSet.getString(39));
    mobilitydto.getStudentDto().setHouseNumber(resultSet.getString(40));
    mobilitydto.getStudentDto().setMailBox(resultSet.getString(41));
    mobilitydto.getStudentDto().setZip(resultSet.getString(42));
    mobilitydto.getStudentDto().setCity(resultSet.getString(43));
    mobilitydto.getStudentDto().setCountry(resultSet.getString(44));
    mobilitydto.getStudentDto().setTel(resultSet.getString(45));
    mobilitydto.getStudentDto().setGender(resultSet.getString(46));
    mobilitydto.getStudentDto().setSuccessfullYearInCollege(resultSet.getInt(47));
    mobilitydto.getStudentDto().setIban(resultSet.getString(48));
    mobilitydto.getStudentDto().setBic(resultSet.getString(49));
    mobilitydto.getStudentDto().setAccountHolder(resultSet.getString(50));
    mobilitydto.getStudentDto().setBankName(resultSet.getString(51));
    mobilitydto.getStudentDto().setVerNr(resultSet.getInt(52));

    mobilitydto.setProgramDto(factory.getProgramDto());
    mobilitydto.getProgramDto().setId(resultSet.getInt(53));
    mobilitydto.getProgramDto().setName(resultSet.getString(54));
    mobilitydto.getProgramDto().setDescription(resultSet.getString(55));
    mobilitydto.getProgramDto().setVerNr(resultSet.getInt(56));
    /*
     * mobilitydto.setPartnerDto(factory.getPartnerDto());
     * mobilitydto.getPartnerDto().setId(resultSet.getInt(58));
     * mobilitydto.getPartnerDto().setIdUser(resultSet.getInt(59));
     * mobilitydto.getPartnerDto().setLegalName(resultSet.getString(60));
     * mobilitydto.getPartnerDto().setBusiness(resultSet.getString(61));
     * mobilitydto.getPartnerDto().setFullName(resultSet.getString(62));
     * mobilitydto.getPartnerDto().setDepartment(resultSet.getString(63));
     * mobilitydto.getPartnerDto().setType(resultSet.getString(64));
     * mobilitydto.getPartnerDto().setNbEmployees(resultSet.getInt(65));
     * mobilitydto.getPartnerDto().setStreet(resultSet.getString(66));
     * mobilitydto.getPartnerDto().setNumber(resultSet.getString(67));
     * mobilitydto.getPartnerDto().setMailbox(resultSet.getString(68));
     * mobilitydto.getPartnerDto().setZip(resultSet.getString(69));
     * mobilitydto.getPartnerDto().setCity(resultSet.getString(70));
     * mobilitydto.getPartnerDto().setState(resultSet.getString(71));
     * mobilitydto.getPartnerDto().setCountry(resultSet.getString(72));
     * mobilitydto.getPartnerDto().setEmail(resultSet.getString(73));
     * mobilitydto.getPartnerDto().setWebsite(resultSet.getString(74));
     * mobilitydto.getPartnerDto().setExists(resultSet.getBoolean(75));
     * mobilitydto.getPartnerDto().setVerNr(resultSet.getInt(76));
     */
    mobilitydto.setCountryDto(factory.getCountryDto());
    mobilitydto.getCountryDto().setIso(resultSet.getString(57));
    mobilitydto.getCountryDto().setNameEn(resultSet.getString(58));
    mobilitydto.getCountryDto().setNameFr(resultSet.getString(59));
    mobilitydto.getCountryDto().setIdProgram(resultSet.getInt(60));

    mobilitydto.setDepartementDto(factory.getDepartmentDto());
    mobilitydto.getDepartementDto().setId(resultSet.getString(61));
    mobilitydto.getDepartementDto().setLabel(resultSet.getString(62));
    mobilitydto.getDepartementDto().setVerNr(resultSet.getInt(63));
    /*
     * mobilitydto.setCancelationDto(factory.getCancelationDto());
     * mobilitydto.getCancelationDto().setId(resultSet.getInt(84));
     * mobilitydto.getCancelationDto().setReason(resultSet.getString(85));
     * mobilitydto.getCancelationDto().setResponsible(resultSet.getString(86));
     * mobilitydto.getCancelationDto().setVerNr(resultSet.getInt(87));
     */
    return mobilitydto;
  }

}