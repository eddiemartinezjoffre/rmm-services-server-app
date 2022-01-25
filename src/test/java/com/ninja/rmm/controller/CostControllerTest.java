package com.ninja.rmm.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(CostController.class)
public class CostControllerTest {

  private static final String URI = "/customers/1/cost";

  @MockBean
  private CostController costController;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = standaloneSetup(costController).build();
  }

  @Test
  public void getCosts() throws Exception {
    mockMvc.perform(get(URI))
        .andExpect(status().isOk());
  }

}
