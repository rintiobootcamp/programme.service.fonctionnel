package Controllers;

import com.bootcamp.application.Application;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.controllers.ProgrammeController;
import com.bootcamp.crud.ProgrammeCRUD;
import com.bootcamp.entities.Programme;
import com.bootcamp.services.ProgrammeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Archange on 17/12/2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = ProgrammeController.class, secure = false)
@ContextConfiguration(classes = {Application.class})
public class ProgrammeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProgrammeService programmeService;

    
    @Test
    public void deleteProgramme() throws Exception {
        int id = 1;
        when(programmeService.exist(id)).thenReturn(true);
        when(programmeService.delete(id)).thenReturn(true);
        RequestBuilder requestBuilder
                = delete("/programmes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        System.out.println(response.getContentAsString());
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for delete programme  controller done *******************");
    }
    
    @Test
    public void createProgramme() throws Exception {
        List<Programme> programmes = getPreferenceFromJson();
        Programme programme = programmes.get(0);

        when(programmeService.read(0)).thenReturn(programme);
        when(programmeService.create(programme)).thenReturn(programme);
        RequestBuilder requestBuilder
                = post("/programmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(programme));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

        System.out.println(response.getContentAsString());

        System.out.println("*********************************Test for create programme controller done *******************");
    }

    @Test
    public void updateProgramme() throws Exception {
        int id = 1;
        List<Programme> programmes = getPreferenceFromJson();
        Programme programme = programmes.get(id);
        programme.setNom("toto");

        when(programmeService.exist(programme.getId())).thenReturn(true);
        when(programmeService.update(programme)).thenReturn(programme.getId());

        RequestBuilder requestBuilder
                = put("/programmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(programme));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for update programme controller done *******************");

    }

    private static String objectToJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Programme> getPreferenceFromJson() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "programme.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Programme>>() {
        }.getType();
        List<Programme> programmes = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return programmes;
    }

    public File getFile(String relativePath) throws Exception {
        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());
        if (!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }
        return file;
    }
}
