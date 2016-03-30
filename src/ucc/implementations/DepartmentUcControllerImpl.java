
package ucc.implementations;

import dal.DalServices;
import dao.interfaces.DepartmentDao;
import dto.DepartmentDto;
import ucc.interfaces.DepartmentUcController;

import java.util.ArrayList;

public class DepartmentUcControllerImpl implements DepartmentUcController {

  private DepartmentDao departmentDao;
  private DalServices dalServices;

  public DepartmentUcControllerImpl(DalServices dalServices, DepartmentDao departmentDao) {
    this.departmentDao = departmentDao;
    this.dalServices = dalServices;
  }


  @Override
  public ArrayList<DepartmentDto> getAllDepartments() {

    ArrayList<DepartmentDto> departments = departmentDao.getAllDepartments();

    return departments;
  }

  public DepartmentDto getDepartementsById(String id) {
    return departmentDao.getDepartementById(id);
  }

  @Override
  public DepartmentDto getDepartmentByLabel(String label) {
    return departmentDao.getDepartmentByLabel(label);
  }

}