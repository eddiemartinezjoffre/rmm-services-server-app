package com.ninja.rmm.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninja.rmm.model.Device;
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
@WebMvcTest(DeviceController.class)
public class DeviceControllerTest {

  private static final String URI = "/customers/1/devices";

  @MockBean
  private DeviceController deviceController;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = standaloneSetup(deviceController).build();
  }

  @Test
  public void getAllDevices() throws Exception {
    mockMvc.perform(get(URI))
        .andExpect(status().isOk());
  }

  @Test
  public void createDevice() throws Exception {
    Device device = createDevice("WS-0001", "Windows Server", 4);
    performPost(URI, device)
        .andExpect(status().isCreated());
  }

  @Test
  public void updateDevice() throws Exception{
    Device device = createDevice("MAC-001", "Macintosh", 4);
    performPut(URI + "/1", device)
        .andExpect(status().isOk());
  }

  @Test
  public void deleteDevice() throws Exception {
    mockMvc.perform(delete(URI + "/1"))
        .andExpect(status().isOk());
  }


  private Device createDevice(String name, String type, long cost) {
    Device device = new Device();
    device.setSystemName(name);
    device.setDeviceType(type);
    device.setDeviceCost(cost);
    return device;
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
