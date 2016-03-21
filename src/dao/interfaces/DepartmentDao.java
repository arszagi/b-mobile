
package dao.interfaces;

import dto.DepartmentDto;

import java.util.ArrayList;

public interface DepartmentDao {


  ArrayList<DepartmentDto> getAllDepartments();

  DepartmentDto getDepartementById(String id);

  DepartmentDto getDepartmentByLabel(String label);
}
