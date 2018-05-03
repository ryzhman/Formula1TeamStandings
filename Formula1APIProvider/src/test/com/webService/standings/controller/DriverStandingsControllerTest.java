package com.webService.standings.controller;

import com.businessModel.service.DriversStandingsServiceImpl;
import com.businessModel.utils.RedisConstants;
import com.webService.standings.wrapper.ObjectWrapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Oleksandr Ryzhkov on 27.03.2017.
 */
public class DriverStandingsControllerTest {
    private MockMvc mockMvc;
    @Mock
    private DriversStandingsServiceImpl mockService;
    @InjectMocks
    private DriversStandingsController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

//    @Test
//    public void getConstructorsStandingTest() throws Exception {
//        when(mockService.getStandings(RedisConstants.DRIVERS)).thenReturn(new ObjectWrapper("\"test key\":\"test value\""));
//
//        mockMvc.perform(get("/drivers/standings"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("\"test key\":\"test value\""));
//
//        verify(mockService, times(1)).getStandings(RedisConstants.DRIVERS);
//    }
//
//    @Test
//    public void getConstructorsStandingFailTest() throws Exception {
//        when(mockService.getStandings(RedisConstants.DRIVERS)).thenThrow(new RuntimeException("Method invocation failed"));
//        mockMvc.perform(get("/drivers/standings"))
//                .andExpect(status().isInternalServerError())
//                .andExpect(content().string("Unexpected error occured while getting standing data: Method invocation failed"));
//        verify(mockService, times(1)).getStandings(RedisConstants.DRIVERS);
//    }

    @Test
    public void updateConstructorsStandingsTest() throws Exception {
        mockMvc.perform(post("/drivers/driver/testDriver")
                .content("\"test key\":\"test value\""))
                .andExpect(status().isOk());

        verify(mockService, times(1)).updateStandingsWithData("testDriver", "\"test key\":\"test value\"");
    }

    @Test
    public void updateConstructorsStandingsIncorrectValueTest() throws Exception {
        mockMvc.perform(post("/drivers/driver/incorrectTestDriver")
                .content("\"test key\":\"test value\""))
                .andExpect(status().isOk());

        verify(mockService, times(0)).updateStandingsWithData("testDriver", "");
        verify(mockService, times(0)).updateStandingsWithData("testDriver", "\"test key\":\"test value\"");
    }

    @Test
    public void updateConstructorsStandingsExceptionTest() throws Exception {
        doThrow(new RuntimeException()).when(mockService).updateStandingsWithData("testDriver", "\"test key\":\"test value\"");
        mockMvc.perform(post("/drivers/driver/testDriver")
                .content("\"test key\":\"test value\""))
                .andExpect(status().isInternalServerError());

        verify(mockService, times(1)).updateStandingsWithData("testDriver", "\"test key\":\"test value\"");
    }


}
