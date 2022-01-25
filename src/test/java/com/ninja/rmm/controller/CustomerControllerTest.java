package com.ninja.rmm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninja.rmm.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

  private static final String URI = "/customers";

  @MockBean
  private CustomerController customerController;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = standaloneSetup(customerController).build();
  }

  @Test
  public void createCustomer() throws Exception {
    Customer customer = createCustomer("John Doe");
    performPost(URI, customer)
        .andExpect(status().isCreated());
  }

  @Test
  public void getCustomerById() throws Exception {
    mockMvc.perform(get(URI + "/1"))
        .andExpect(status().isOk());
  }

  @Test
  public void getAllCustomers() throws Exception {
    mockMvc.perform(get(URI))
        .andExpect(status().isOk());
  }

  @Test
  public void updateCustomer() throws Exception {
    Customer customer = createCustomer("John Doe");
    performPut(URI + "/1", customer)
        .andExpect(status().isOk());
  }

  @Test
  public void deleteCustomer() throws Exception {
    mockMvc.perform(delete(URI + "/1"))
        .andExpect(status().isOk());
  }

  private Customer createCustomer(String name) {
    Customer customer = new Customer();
    customer.setName(name);
    return customer;
  }

  private ResultActions performPost(String uri, Object payload) throws Exception {
    return mockMvc.perform(post(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(payload)));
  }

  private ResultActions performPut(String uri, Object payload) throws Exception {
    return mockMvc.perform(put(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(payload)));
  }

  private String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
