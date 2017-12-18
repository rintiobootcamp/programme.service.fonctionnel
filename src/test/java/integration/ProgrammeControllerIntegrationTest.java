package integration;

import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.entities.*;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

/**
 * <h2> The integration test for Programme controller</h2>
 * <p>
 * In this test class,
 * the methods :
 * <ul>
 * <li>create a programme </li>
 * <li>get one programme by it's id</li>
 * <li>get all programme</li>
 * <li>And update a programme have been implemented</li>
 * </ul>
 * before  getting started , make sure , the programme fonctionnel service is deploy and running as well.
 * you can also test it will the online ruuning service
 * As this test interact directly with the local database, make sure that the specific database has been created
 * and all it's tables.
 * If you have data in the table,make sure that before creating a data with it's id, do not use
 * an existing id.
 * </p>
 */

public class ProgrammeControllerIntegrationTest {
    private static Logger logger = LogManager.getLogger(ProgrammeControllerIntegrationTest.class);

    /**
     *The Base URI of programme fonctionnal service,
     * it can be change with the online URIof this service.
     */
    private String BASE_URI = "http://localhost:8091/programme";

    /**
     * The path of the Programme controller, according to this controller implementation
     */
    private String PROGRAMME_PATH ="/programmes";

    /**
     * This ID is initialize for create , getById, and update method,
     * you have to change it if you have a save data on this ID otherwise
     * a error or conflit will be note by your test.
     */
    private int programmeId = 0;

    /**
     * A entity of type:
     * <ul>
     *     <li>
     *         PROJET
     *     </li>
     *     <li>
     *         SECTEUR
     *     </li>
     *     <li>
     *         PILIER
     *     </li>
     *     <li>
     *         AXE
     *     </li>
     *     <li>
     *         PROJET
     *     </li>
     * </ul>
     *
     */
    private String entityType = "PROJET";

    /**
     *The given entity type ID
     * you have to specify this ID according to record in your data
     */
    private int entityId = 2;


    /**
     * This method create a new programme with the given id
     * @see Programme#id
     * <b>you have to chenge the name of
     * the programme if this name already exists in the database
     * @see Programme#getNom()
     * else, the programme  will be created but not wiht the given ID.
     * and this will accure an error in the getById and update method</b>
     * Note that this method will be the first to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 0, groups = {"ProgrammeTest"})
    public void createProgrammeTest() throws Exception{
        String createURI = BASE_URI+PROGRAMME_PATH;
        Programme programme = getProgrammeById( 1 );
        programme.setId( programmeId );
        programme.setNom( "Any name for the programme" );
        Gson gson = new Gson();
        String programmeData = gson.toJson( programme );
        Response response = given()
                .log().all()
                .contentType("application/json")
                .body(programmeData)
                .expect()
                .when()
                .post(createURI);

        programmeId = gson.fromJson( response.getBody().print(),Programme.class ).getId();

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;



    }

    /**
     * This method get a programme with the given id
     * @see Programme#id
     * <b>
     *     If the given ID doesn't exist it will log an error
     * </b>
     * Note that this method will be the second to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 1, groups = {"ProgrammeTest"})
    public void getProgrammeByIdTest() throws Exception{

        String getProgrammeById = BASE_URI+PROGRAMME_PATH+"/"+programmeId;

        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getProgrammeById);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;


    }

    /**
     * Update a programme with the given ID
     * <b>
     *     the programme must exist in the database
     *     </b>
     * Note that this method will be the third to execute
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 2, groups = {"ProgrammeTest"})
    public void updateProgrammeTest() throws Exception{
        String updateURI = BASE_URI+PROGRAMME_PATH;
        Programme programme = getProgrammeById( 1 );
        programme.setId( programmeId );
        programme.setNom( "update programme during test" );
        Gson gson = new Gson();
        String programmeData = gson.toJson( programme );
        Response response = given()
                .log().all()
                .contentType("application/json")
                .body(programmeData)
                .expect()
                .when()
                .put(updateURI);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;



    }

    /**
     * Get All the programmes in the database
     * If every done , it will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 3, groups = {"ProgrammeTest"})
    public void getAllProgrammesTest()throws Exception{
        String getAllProgrammeURI = BASE_URI+PROGRAMME_PATH;
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getAllProgrammeURI);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200) ;

    }



    /**
     * Delete a secteur for the given ID
     * will return a 200 httpStatus code if OK
     * @throws Exception
     */
    @Test(priority = 4, groups = {"SecteurTest"})
    public void deleteSecteurTest() throws Exception{

        String deleteSecteurUI = BASE_URI+PROGRAMME_PATH+"/"+programmeId;

        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .delete(deleteSecteurUI);

        Assert.assertEquals(response.statusCode(), 200) ;


    }

    /**
     * Convert a relative path file into a File Object type
     * @param relativePath
     * @return  File
     * @throws Exception
     */
    public File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if (!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
    }

    

    /**
     * Convert a programmes json data to a secteur objet list
     * this json file is in resources
     * @return a list of programme in this json file
     * @throws Exception
     */
    public List<Programme> loadDataProgrammeFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "programme.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Programme>>() {
        }.getType();
        List<Programme> programmes = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return programmes;
    }

    private Programme getProgrammeById(int id) throws Exception {
        List<Programme> programmes = loadDataProgrammeFromJsonFile();
        Programme programme = programmes.stream().filter(item -> item.getId() == id).findFirst().get();

        return programme;
    }

}
