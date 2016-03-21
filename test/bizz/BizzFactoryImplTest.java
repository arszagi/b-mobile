package bizz;

import static org.junit.Assert.assertEquals;

import bizz.implementations.BizzFactoryImpl;
import bizz.implementations.CountryImpl;
import bizz.implementations.UserImpl;
import bizz.interfaces.BizzFactory;
import dto.CountryDto;
import dto.UserDto;

import org.junit.Before;
import org.junit.Test;

public class BizzFactoryImplTest {

  private BizzFactory bizzFactory;
  private UserDto userDto;
  private CountryDto countryDto;

  @Before
  public void setUp() {
    bizzFactory = new BizzFactoryImpl();
    userDto = new UserImpl();
    countryDto = new CountryImpl();
  }

  @Test
  public void testGetUserDto() {
    assertEquals(userDto, bizzFactory.getUserDto());
  }

  @Test
  public void testGetCountryDto() {
    assertEquals(countryDto, bizzFactory.getCountryDto());
  }

}
