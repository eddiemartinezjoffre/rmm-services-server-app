package com.ninja.rmm.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninja.rmm.model.Service;
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

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceController.class)
public class ServiceControllerTest {

  private static final String URI = "/customers/1/services";

  @MockBean
  private ServiceController serviceController;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = standaloneSetup(serviceController).build();
  }

  @Test
  public void getAllServices() throws Exception {
    mockMvc.perform(get(URI))
        .andExpect(status().isOk());
  }

  @Test
  public void createService() throws Exception {
    Service service = createService("Antivirus-Mac", "To have antivirus in their devices.", 7);
    performPost(URI, service)
        .andExpect(status().isCreated());
  }

  @Test
  public void updateDevice() throws Exception {
    Service service = createService("Antivirus-Win", "To have antivirus in their devices.", 5);
    performPut(URI + "/1", service)
        .andExpect(status().isOk());
  }

  @Test
  public void deleteDevice() throws Exception {
    mockMvc.perform(delete(URI + "/1"))
        .andExpect(status().isOk());
  }

  private Service createService(String name, String description, long cost) {
    Service service = new Service();
    service.setServiceName(name);
    service.setServiceDescription(description);
    service.setServiceCost(cost);
    return service;
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
