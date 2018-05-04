package com.webService.standings.controller;

import com.businessModel.model.Constructor;
import com.businessModel.service.ConstructorsStandingService;
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
 * Created by Oleksandr Ryzhkov on 26.03.2017.
 */
public class ConstructorsStandingsContollerTest {
    private MockMvc mockMvc;
    @Mock
    private ConstructorsStandingService mockService;
    @InjectMocks
    private com.webService.standings.controller.ConstructorsStandingsController controller;
    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getConstructorsStandingTest() throws Exception {
        Constructor c1 = new Constructor("Ferrari test");
        c1.setPoints(10);
        Constructor c2 = new Constructor("Redbull test");
        c2.setPoints(22);
        List<Constructor> mockedResult = Arrays.asList(c1, c2);

        when(mockService.getAllStandings()).thenReturn(mockedResult);

        mockMvc.perform(get("/constructors/standings"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"points\":10,\"title\":\"Ferrari test\"},{\"points\":22,\"title\":\"Redbull test\"}]"));

        verify(mockService, times(1)).getAllStandings();
    }

    @Test
    public void getConstructorsStandingFailTest() throws Exception {
        thrownException.expect(NestedServletException.class);
        thrownException.expectMessage(equalTo("Request processing failed; nested exception is java.lang.RuntimeException: Method invocation failed"));

        when(mockService.getAllStandings()).thenThrow(new RuntimeException("Method invocation failed"));
        mockMvc.perform(get("/constructors/standings"));
        verify(mockService, times(1)).getAllStandings();
    }

    @Test
    public void updateConstructorsStandingsTest() throws Exception {
        mockMvc.perform(post("/constructors/constructor/testConstructor")
                .content("{\"test key\":\"test value\"}"))
                .andExpect(status().isOk());

        verify(mockService, times(1)).updateStandingsWithData("testConstructor", "{\"test key\":\"test value\"}");
    }

    @Test
    public void updateConstructorsStandingsIncorrectValueTest() throws Exception {
        mockMvc.perform(post("/constructors/constructor/inCorrectTestConstructor")
                .content("\"test key\":\"test value\""))
                .andExpect(status().isOk());

        verify(mockService, times(0)).updateStandingsWithData("testConstructor", "");
        verify(mockService, times(0)).updateStandingsWithData("testConstructor", "\"test key\":\"test value\"");
    }

    @Test
    public void updateConstructorsStandingsExceptionTest() throws Exception {
        thrownException.expect(NestedServletException.class);
        thrownException.expectMessage(equalTo("Request processing failed; nested exception is java.lang.RuntimeException"));

        doThrow(new RuntimeException()).when(mockService).updateStandingsWithData("testFerrari", "\"test key\":\"test value\"");
        mockMvc.perform(post("/constructors/constructor/{title}", "testFerrari")
                .content("\"test key\":\"test value\""));

        verify(mockService, times(1)).updateStandingsWithData("testFerrari", "\"test key\":\"test value\"");
    }

    @Test
    public void uploadConstructorsStandingsTest() throws Exception {
        mockMvc.perform(post("/constructors/constructor/{title}", "Ferrari")
                .content("{\"title\":\"test value\"}"))
                .andExpect(status().isOk());

        verify(mockService, times(1)).updateStandingsWithData("Ferrari", "{\"title\":\"test value\"}");
    }
}
