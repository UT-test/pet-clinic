package bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.PetClinicApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = PetClinicApplication.class)
public class CucumberSpringConfiguration {

}
