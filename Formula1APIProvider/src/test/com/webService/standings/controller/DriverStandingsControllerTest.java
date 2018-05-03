package com.webService.standings.controller;

import com.businessModel.model.Constructor;
import com.businessModel.model.Driver;
import com.businessModel.service.DriversStandingsServiceImpl;
import com.businessModel.utils.RedisConstants;
import com.webService.standings.wrapper.ObjectWrapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
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
    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getConstructorsStandingTest() throws Exception {
        Driver d1 = new Driver("Michael Schumacher", "Germany", new Constructor("Ferrari"));
        Driver d2 = new Driver("Airton Senna", "Brazil", new Constructor("Williams"));
        List<Driver> mockedResult = Arrays.asList(d1, d2);

        when(mockService.getAllStandings()).thenReturn(mockedResult);

        mockMvc.perform(get("/drivers/standings"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"points\":0,\"name\":\"Michael Schumacher\",\"nationality\":\"n/a\",\"wins\":0,\"team\":{\"points\":0,\"title\":\"Ferrari\"}},{\"points\":0,\"name\":\"Airton Senna\",\"nationality\":\"n/a\",\"wins\":0,\"team\":{\"points\":0,\"title\":\"Williams\"}}]"));

        verify(mockService, times(1)).getAllStandings();
    }

    @Test
    public void getConstructorsStandingFailTest() throws Exception {
        thrownException.expect(NestedServletException.class);
        thrownException.expectMessage(equalTo("Request processing failed; nested exception is java.lang.RuntimeException: Method invocation failed"));

        when(mockService.getAllStandings()).thenThrow(new RuntimeException("Method invocation failed"));
        mockMvc.perform(get("/drivers/standings"));

        verify(mockService, times(1)).getAllStandings();
    }

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
                .content("\"name\":\"test value\""))
                .andExpect(status().isOk());

        verify(mockService, times(0)).updateStandingsWithData("testDriver", "");
        verify(mockService, times(0)).updateStandingsWithData("testDriver", "\"test key\":\"test value\"");
    }

    @Test
    public void updateConstructorsStandingsExceptionTest() throws Exception {
        thrownException.expect(NestedServletException.class);
        thrownException.expectMessage(equalTo("Request processing failed; nested exception is java.lang.RuntimeException"));

        doThrow(new RuntimeException()).when(mockService).updateStandingsWithData("testDriver", "\"test key\":\"test value\"");
        mockMvc.perform(post("/drivers/driver/testDriver")
                .content("\"test key\":\"test value\""))
                .andExpect(status().isInternalServerError());

        verify(mockService, times(1)).updateStandingsWithData("testDriver", "\"test key\":\"test value\"");
    }


}
