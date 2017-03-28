package formula1.standings.controller;

import com.formula1.standings.controller.ConstructorsStandingsController;
import com.formula1.standings.service.ConstructorsStandingService;
import com.formula1.standings.utils.RedisConstants;
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
 * Created by Oleksandr Ryzhkov on 26.03.2017.
 */
public class ConstructorsStandingsContollerTest {
    private MockMvc mockMvc;
    @Mock
    private ConstructorsStandingService mockService;
    @InjectMocks
    private ConstructorsStandingsController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getConstructorsStandingTest() throws Exception {
        when(mockService.getStandings(RedisConstants.CONSTRUCTORS)).thenReturn("\"test key\":\"test value\"");

        mockMvc.perform(get("/constructors/standings"))
                .andExpect(status().isOk())
                .andExpect(content().string("\"test key\":\"test value\""));

        verify(mockService, times(1)).getStandings(RedisConstants.CONSTRUCTORS);
    }

    @Test
    public void getConstructorsStandingFailTest() throws Exception {
        when(mockService.getStandings(RedisConstants.CONSTRUCTORS)).thenThrow(new RuntimeException("Method invocation failed"));
        mockMvc.perform(get("/constructors/standings"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Unexpected error occured while getting standing data: Method invocation failed"));
        verify(mockService, times(1)).getStandings(RedisConstants.CONSTRUCTORS);
    }

    @Test
    public void updateConstructorsStandingsTest() throws Exception {
        mockMvc.perform(post("/constructors/constructor/testConstructor")
                .content("\"test key\":\"test value\""))
                .andExpect(status().isOk());

        verify(mockService, times(1)).updateStandingsWithData("testConstructor", "\"test key\":\"test value\"");
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
        doThrow(new RuntimeException()).when(mockService).updateStandingsWithData("testConstructor", "\"test key\":\"test value\"");
        mockMvc.perform(post("/constructors/constructor/testConstructor")
                .content("\"test key\":\"test value\""))
                .andExpect(status().isInternalServerError());

        verify(mockService, times(1)).updateStandingsWithData("testConstructor", "\"test key\":\"test value\"");
    }

    @Test
    public void uploadConstructorsStandingsTest() throws Exception {
        mockMvc.perform(post("/constructors/constructor")
                .content("\"test key\":\"test value\""))
                .andExpect(status().isOk());

        verify(mockService, times(1)).updateStandingsWithData("\"test key\":\"test value\"");
    }

    @Test
    public void uploadConstructorsStandingsExceptionTest() throws Exception {
        doThrow(new RuntimeException("test exception")).when(mockService).updateStandingsWithData("\"test key\":\"test value\"");

        mockMvc.perform(post("/constructors/constructor")
                .content("\"test key\":\"test value\""))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Update of standing wasn't possible: test exception"));

        verify(mockService, times(1)).updateStandingsWithData("\"test key\":\"test value\"");
    }
}
