package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class VendorControllerTest {

  WebTestClient webTestClient;
  VendorRepository vendorRepository;
  VendorController vendorController;

  @Before
  public void setUp() throws Exception {
    vendorRepository = Mockito.mock(VendorRepository.class);
    vendorController = new VendorController(vendorRepository);
    webTestClient = WebTestClient.bindToController(vendorController).build();
  }

  @Test
  public void list() {
    when(vendorRepository.findAll()).thenReturn(Flux.just(
        Vendor.builder().firstName("joe").lastName("blow").build(),
        Vendor.builder().firstName("ricki").lastName("babi").build()
    ));

    webTestClient.get()
        .uri("/api/v1/vendors")
        .exchange()
        .expectBodyList(Vendor.class)
        .hasSize(2);
  }

  @Test
  public void getById() {
    when(vendorRepository.findById(anyString())).thenReturn(
        Mono.just(Vendor.builder().firstName("joe").lastName("blow").build())
    );

    webTestClient.get()
        .uri("/api/vq/vendor/1234")
        .exchange()
        .expectBody(Vendor.class);
  }
}