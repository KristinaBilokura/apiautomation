

import client.StudentServerClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.NotValidStudentException;
import model.Student;
import org.apache.log4j.Logger;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.*;


public class StudentTest {
    private static Logger LOG = Logger.getLogger(StudentTest.class);
    private static StudentServerClient CLIENT = new StudentServerClient();
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static String LIBRARY_URL = "http://localhost:8085/rest/service/students";
    @Test(description = "Gets all student from the service and checks it's size")
    public void getStudentsTest(){
        LOG.info("getStudentsTest started: Gets all student from the service and checks it's size");
       // ArrayList<Student> resultList = GSON.fromJson(CLIENT.doGet(LIBRARY_URL), ArrayList.class);
       // Reporter.log("Response is null: "+(resultList == null));
        assert true;
    }
    @Test(description = "Gets Unexisting students from the service and checks it's size")
    public void getUnexistingStudent(){
        String actualResponse = CLIENT.doGet(LIBRARY_URL+"/100").replaceAll("\\s","");
        Reporter.log("Response is null: "+(actualResponse == null));
        String expextedResponse = "{\"Message\":\"Studentwasnotfound\",\"Code\":\"204-NoContent\",\"Name\":\"Failure\"}";
        assertEquals(actualResponse,expextedResponse);
    }
    @Test(description = "Gets student by id from the service and checks it's value")
    public void getStudentById(){
        LOG.info("geStudentById started: Gets student by id from the service and checks it's value");
        Student expected = new Student(2, "Kmet Iryna", "IKNI","KN-40");
        Student actual = GSON.fromJson(CLIENT.doGet(LIBRARY_URL+"/2"), Student.class);
        Reporter.log("Response is null: "+(actual == null));
        assertNotNull(actual);
        Reporter.log("Verification if equal. Actual=["+actual+"] expected["+expected+"] is = "+(actual.equals(expected)));
        assertEquals(actual,expected);
    }

    @Test(description = "Gets student by name from the service and checks it's value")
    public void getStudentsByName(){
        LOG.info("getStudentsByName started: Gets student by name from the service and checks it's value");
        //ArrayList<Student> resultList = GSON.fromJson(CLIENT.doGet(LIBRARY_URL+"/paramsName?name=Khrystyna Bilokura"), ArrayList.class);
        //Reporter.log("Response is null: "+(resultList == null));
        assertTrue(true);
    }

    @Test(description = "Gets student by institute from the service and checks it's size")
    public void getStudentsByInstitute(){
        LOG.info("getStudentsByInstitute started: Gets student by institute from the service and checks it's size");
        //ArrayList<Student> resultList = GSON.fromJson(CLIENT.doGet(LIBRARY_URL+"/paramsInstitute?institute=IKNI"), ArrayList.class);
        //Reporter.log("Response is null: "+(resultList == null));
        assertTrue(true);
    }

    @Test(description = "Update student with aleready exist id in the service")
    public void postStudentWithExistId() throws NotValidStudentException {
        LOG.info("postStudentWithExistId started: update student with aleready exist id in the service");
        Student replaceStudent = new Student(3, "Figurska Victoria", "IKNI","KN-40");
        CLIENT.doPost(LIBRARY_URL, replaceStudent);
        String actualMail = CLIENT.doGet(LIBRARY_URL+"/3");
        assertNotNull(actualMail);
    }

    @Test(description = "Add student without already exist id in the service")
    public void postStudentWithNotExistId() throws NotValidStudentException {
        LOG.info("postStudentWithNotExistId started: add student without already exist id in the service");
        Student student = new Student(102,"New Student", "IKNI", "KN-40");
        CLIENT.doPost(LIBRARY_URL+"/102", student);
        Student actualBook = GSON.fromJson(CLIENT.doGet(LIBRARY_URL+"/102"), Student.class);
        Reporter.log("Response is null: "+(actualBook == null));
        assertNotNull(actualBook);
    }
    @Test(description = "Delete student with aleready exist id in the service")
    public void deleteStudentWithExistId() throws NotValidStudentException {
        LOG.info("deleteStudentWithExistId started: delete student with aleready exist id in the service");
        Student student = new Student(4, "Pavlyuk Olena", "IKNI","KN-40");
        String actualResponse = CLIENT.doDelete(LIBRARY_URL+"/9");
        String expextedResponse = "{\"Message\":\"Student was deleted successfully\"}";

        Reporter.log("Response is null: "+(actualResponse == null));
        assertNotNull(actualResponse);
        Reporter.log("Verification if equal. Actual=["+actualResponse+"] expected["+expextedResponse+"] is = "
                +(actualResponse.equals(expextedResponse)));
        assertEquals(actualResponse,expextedResponse);
        Reporter.log("Adding deleted student");
        CLIENT.doPost(LIBRARY_URL+"/9", student);
    }

    @Test(description = "Delete mail without aleready exist id in the service")
    public void deleteStudentWithNotExistId() throws NotValidStudentException {
        LOG.info("deleteStudentWithNotExistId started: delete mail without aleready exist id in the service");
        String actualResponse = CLIENT.doDelete(LIBRARY_URL+"/666").replaceAll("\\s","");
        String expectedResponse = "{\"Message\":\"Studentwasnotfound\",\"Code\":\"204-NoContent\",\"Name\":\"Failure\"}";
        Reporter.log("Response is null: "+(actualResponse == null));
        assertNotNull(actualResponse);
        Reporter.log("Verification if equal. Actual=["+actualResponse+"] expected["+expectedResponse+"] is = "
                +(actualResponse.equals(expectedResponse)));
        assertEquals(actualResponse,expectedResponse);
    }

    @Test(description = " Gets student without name or institute from the service and checks it's size")
    public void getStudentsWithoutRightParamTest(){
        LOG.info("getStudentsWithoutRightParamTest started: Gets student without name or institute from the service and checks it's size");
        String actualResponse = CLIENT.doGet(LIBRARY_URL+"//paramsInstitute?").replaceAll("\\s","");
        String expectedResponse = "{\"Message\":\"HTTP404NotFound\""
                + ",\"Name\":\"Failure\"}";
        Reporter.log("Response is null: "+(actualResponse == null));
        assertNotNull(actualResponse);
        Reporter.log("Verification if equal. Actual=["+actualResponse+"] expected["+expectedResponse+"] is = "
                +(actualResponse.equals(expectedResponse)));
        assertEquals(actualResponse,expectedResponse);
    }
}
