package guru.springframework.spring5webfluxrest.bootstrap;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

  private final VendorRepository vendorRepository;

  public DataLoader(VendorRepository vendorRepository) {
    this.vendorRepository = vendorRepository;
  }

  @Override
  public void run(String... args) throws Exception {

    Long vendorCount = vendorRepository.count().block();
    System.out.println("##################################################");
    System.out.println("Count: " + vendorCount.toString());

    if (vendorCount == 0) {
      vendorRepository.save(Vendor.builder().firstName("Joe").lastName("Blow").build()).block();
      vendorRepository.save(Vendor.builder().firstName("Rick").lastName("Smith").build()).block();
      System.out.println("2 vendor created.");
    }
  }
}
