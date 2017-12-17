package Service;

import com.bootcamp.application.Application;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.crud.ProgrammeCRUD;
import com.bootcamp.entities.Programme;
import com.bootcamp.services.ProgrammeService;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;

//import org.junit.Test;
//import org.testng.annotations.Test;

/**
 * Created by darextossa on 12/9/17.
 */

@RunWith(PowerMockRunner.class)
@WebMvcTest(value = ProgrammeService.class, secure = false)
@ContextConfiguration(classes = {Application.class})
@PrepareForTest(ProgrammeCRUD.class)
@PowerMockRunnerDelegate(SpringRunner.class)
public class ProgrammeServiceTest {

    @InjectMocks
    private ProgrammeService programmeService;


    @Test
    public void create() throws Exception{
        List<Programme> programmes = loadDataProgrammeFromJsonFile();
        for (Programme programme : programmes) {
//            preference = preferences.get(1);
             PowerMockito.mockStatic(ProgrammeCRUD.class);
        Mockito.
                when(ProgrammeCRUD.create(programme)).thenReturn(true);
        }
             
    }

    @Test
    public void delete() throws Exception{
                List<Programme> programmes = loadDataProgrammeFromJsonFile();
        Programme programme = programmes.get(1);

        PowerMockito.mockStatic(ProgrammeCRUD.class);
        Mockito.
                when(ProgrammeCRUD.delete(programme)).thenReturn(true);
    }

    @Test
    public void update() throws Exception{
        List<Programme> programmes = loadDataProgrammeFromJsonFile();
        Programme programme = programmes.get(1);

        PowerMockito.mockStatic(ProgrammeCRUD.class);
        Mockito.
                when(ProgrammeCRUD.update(programme)).thenReturn(true);
    }


    public File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if (!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
    }

    public List<Programme> loadDataProgrammeFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "programme.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Programme>>() {
        }.getType();
        List<Programme> programmes = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return programmes;
    }
    
        @Test
    public void getAllProgramme() throws Exception {
        List<Programme> programmes = loadDataProgrammeFromJsonFile();
        PowerMockito.mockStatic(ProgrammeCRUD.class);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.
                when(ProgrammeCRUD.read()).thenReturn(programmes);
        List<Programme> resultProgramme = programmeService.findAll(mockRequest);
        Assert.assertNotNull(resultProgramme);
    }

}