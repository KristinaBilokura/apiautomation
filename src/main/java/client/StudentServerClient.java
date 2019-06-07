package client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import model.NotValidStudentException;
import model.Student;
import org.testng.Reporter;

import javax.ws.rs.core.MediaType;

public class StudentServerClient {
    private Client client;
    private WebResource webResource;
    private ClientResponse response;

    public StudentServerClient() {
        client = Client.create();
    }

    public String doGet(String url){
        Reporter.log("doGet method started");
        webResource = client.resource(url);
        Reporter.log("Setting accept 'application/json'");
        response = webResource.accept("application/json")
                .get(ClientResponse.class);
        Reporter.log("Response: "+response);
        return response.getEntity(String.class);
    }

    public String doPost(String url, Student student) throws NotValidStudentException {
        Reporter.log("doPost method started");
        if(!isValid(student)){
            Reporter.log("Student is NOT VALID: "+ student);
            throw new NotValidStudentException();
        }
        webResource = client.resource(url);
        Reporter.log("Setting accept 'application/json'");
        response = webResource.accept("application/json")
                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .post(ClientResponse.class);
        Reporter.log("Response: "+response);
        return response.getEntity(String.class);
    }

    public String doDelete(String url){
        Reporter.log("doDelete method started");
        webResource = client.resource(url);
        Reporter.log("Setting accept 'application/json'");
        response = webResource.accept("application/json")
                .delete(ClientResponse.class);
        Reporter.log("Response: "+response);
        return response.getEntity(String.class);
    }

    private String transformToUrlUncodedType(Student student){
        return  "id="+ student.getId()+"email="	+ student.getName()+"&"+
                "subject="+ student.getInstitute()+"&"+
                "body="+ student.getGroup()+"&"
                ;
    }

    private boolean isValid(Student student){
        boolean isValid;
        if(student.getId()>0 && student.getName() != null && student.getInstitute() != null && student.getGroup() != null){
            isValid = true;
        }else{
            isValid = false;
        }
        return isValid;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public WebResource getWebResource() {
        return webResource;
    }

    public void setWebResource(WebResource webResource) {
        this.webResource = webResource;
    }

    public ClientResponse getResponse() {
        return response;
    }

    public void setResponse(ClientResponse response) {
        this.response = response;
    }



}
