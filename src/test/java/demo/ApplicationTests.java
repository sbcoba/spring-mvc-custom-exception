package demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTests {
    @Autowired
    WebApplicationContext wac;
    MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(print())
                .alwaysExpect(handler().handlerType(TestController.class))
                .build();
    }

	@Test
	public void exceptionRedirectTest() throws Exception {
		mockMvc.perform(get("/test"))
		.andExpect(handler().methodName("test"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/exception"));
	}

	@Test
	public void exceptionAjaxTest() throws Exception {
        mockMvc.perform(get("/test2"))
        		.andExpect(content().string("{\"status\":\"500\",\"message\":\"예외가 발생했습니다. ajax\"}"))
                .andExpect(handler().methodName("test2"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

}
