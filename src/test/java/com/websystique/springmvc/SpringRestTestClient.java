package com.websystique.springmvc;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.websystique.springmvc.model.User;
import sun.misc.BASE64Encoder;

public class SpringRestTestClient {

	private static String contextPath = "/"; //"Spring4MVCCRUDRestService";
	public static final String REST_SERVICE_URI = "http://localhost:4566" + contextPath;

	/* GET */
	@SuppressWarnings("unchecked")
	private static void listAllUsers(){
		System.out.println("Testing listAllUsers API-----------");
		
		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI+"/user/", List.class);
		
		if(usersMap!=null){
			for(LinkedHashMap<String, Object> map : usersMap){
	            System.out.println("User : id="+map.get("id")+", Name="+map.get("name")+", Age="+map.get("age")+", Salary="+map.get("salary"));;
	        }
		}else{
			System.out.println("No user exist----------");
		}
	}


	private static HttpEntity<String> request() {
		String name = "user1";
		String password = "Test12345";
		String authString = name + ":" + password;
		String authStringEnc = new BASE64Encoder().encode(authString.getBytes());
		System.out.println("Base64 encoded auth string: " + authStringEnc);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + authStringEnc);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		return request;
	}

	private static void getUserBasicAuth(String id) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI+"/user/"+id, HttpMethod.GET, request(), User.class);
		User user = response.getBody();
		System.out.println("User using BasicAuth : " + user);
	}
	
	/* GET */
	private static void getUser(String id){
		System.out.println("Testing getUser API----------");
		RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject(REST_SERVICE_URI+"/user/"+id, User.class);
        System.out.println(user);
	}
	
	/* POST */
    private static void createUser(String name, int age, double salary) {
		System.out.println("Testing create User API----------");
    	RestTemplate restTemplate = new RestTemplate();
		User user = new User(-1, name, age, salary);
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/", user, User.class);
        System.out.println("Location : "+uri.toASCIIString());
    }

    /* PUT */
    private static void updateUser(long id, String name, int age, double salary) {
		System.out.println("Testing update User API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user  = new User(id, name, age, salary);
        restTemplate.put(REST_SERVICE_URI+"/user/1", user);
        System.out.println(user);
    }

    /* DELETE */
    private static void deleteUser(String id) {
		System.out.println("Testing delete User API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/user/"+id);
    }


    /* DELETE */
    private static void deleteAllUsers() {
		System.out.println("Testing all delete Users API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/user/");
    }

    public static void main(String args[]){
	/*	listAllUsers();

		try {
			getUser("1");
		}   catch (HttpClientErrorException e) {
			System.err.println("Error while getting user : " + e);
		}

		try {
			createUser("Sarah",51,134);
			listAllUsers();
		}   catch (HttpClientErrorException e) {
			System.err.println("Error while creating user : " + e);
		}

		try {
			updateUser(1,"Tomy",33, 70000);
			listAllUsers();
		}   catch (HttpClientErrorException e) {
			System.err.println("Error while updating user : " + e);
		}

		try {
			deleteUser("5");
			listAllUsers();
		}   catch (HttpClientErrorException e) {
			System.err.println("Error while deleting user : " + e);
		}
		//deleteAllUsers();
		//listAllUsers();

*/
		getUserBasicAuth("2");

    }
}