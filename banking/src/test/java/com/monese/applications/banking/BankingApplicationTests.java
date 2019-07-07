package com.monese.applications.banking;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List; 

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monese.applications.banking.controller.BankingWebController;
import com.monese.applications.banking.model.Account;
import com.monese.applications.banking.model.Transactions;
import com.monese.applications.banking.repository.AccountsRepository;
import com.monese.applications.banking.repository.TransactionsRespository;
import com.monese.applications.banking.requests.AccountsTransferRequest;
import com.monese.applications.banking.responses.AccountDetailsResponse;
import com.monese.applications.banking.responses.TransactionsResponse;
import com.monese.applications.banking.services.AccountDetailsService;
import com.monese.applications.banking.services.TransactionsService;
import com.monese.applications.banking.services.impl.TransactionsServicesImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=BankingWebController.class)
@WebMvcTest(BankingWebController.class)
@TestPropertySource(
		  locations = "classpath:application-test.properties")
@EnableAutoConfiguration
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
public class BankingApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
    private TestEntityManager entityManager;
 
	@Autowired
	private AccountsRepository accountRepository;
	
	@Autowired
	private TransactionsRespository transactionsRepository;
	
	@MockBean
	private AccountDetailsService accountService;
	
	@MockBean
	private TransactionsService transactionsService;
	
	@TestConfiguration
    static class TransactionsServiceImplTestContextConfiguration {
		@Bean
		public TransactionsService transactionService() {
			return new TransactionsServicesImpl();
    	}
    }
	
	@Before
	public void setUp(){
	  mockMvc = MockMvcBuilders.webAppContextSetup(context)
	      .build();
	  objectMapper = new Jackson2ObjectMapperBuilder().failOnUnknownProperties(true).build();
	}
	
	/*@Autowired
	public TransactionsService transactionService;
	
	@Autowired
	private AccountDetailsService accountService;*/
	
	public static final Long FROM_ACCOUNT_NUMBER = new Long(11811811);
	public static final Long TO_ACCOUNT_NUMBER = new Long(98989898);
	
	public static final Long ACCOUNT_NUMBER = new Long(56565656);
	public static final Long GEN_ID = new Long(1111111111);
		
	
	private void accountTestDataSetup() {
		Account fromAccount = addTestAccount("Current","XYZ","X",FROM_ACCOUNT_NUMBER,"S1",new Long(909090),new BigDecimal(1000.00));
		Account toAccount = addTestAccount("Current","PQR","P",TO_ACCOUNT_NUMBER,"S1",new Long(505050),new BigDecimal(2000.00));
		entityManager.persist(fromAccount);
		entityManager.persist(toAccount);
		entityManager.flush();
	}
	
	private Account fromAccountTestData() {
		Account fromAccount = addTestAccount("Current","XYZ","X",ACCOUNT_NUMBER,"S1",new Long(909090),new BigDecimal(900.00));
		entityManager.persist(fromAccount);
		entityManager.flush();
		return fromAccount;
	}
	
	private List<Transactions> transactionTestDataSetup() {
		
		accountTestDataSetup();
		
		Transactions transaction = addTransaction(FROM_ACCOUNT_NUMBER, TO_ACCOUNT_NUMBER, "S", "E2E test", new BigDecimal(100.00), "WIRE");
		entityManager.persistAndFlush(transaction);
		
		List<Transactions> transList = new ArrayList<Transactions>();
		transList.add(transaction);
		return transList;
	}
	
	@Test
	public void whenFindAccountNumber_returnAccount() {
		Account testAccount = addTestAccount("Current","ABC","A",ACCOUNT_NUMBER,"S1",new Long(303030),new BigDecimal(100));
		entityManager.persist(testAccount);
		entityManager.flush();
		
		Account dbAccount = accountRepository.findByAccountNumber(ACCOUNT_NUMBER);
		assertEquals(testAccount.getAccountNumber(),dbAccount.getAccountNumber());
		
		entityManager.remove(testAccount);
		entityManager.flush();
		entityManager.clear();
	}

	@Test
	public void testListTransactions200ResponseWhenProcessedSuccessfully() throws Exception {
		List<Transactions> transList = transactionTestDataSetup();
		TransactionsResponse response = new TransactionsResponse();
		response.setListTransactions(transList);
		
		Mockito.when(transactionsService.listAllTransactions(FROM_ACCOUNT_NUMBER)).thenReturn(response);
		
	    String jsonResponse =  objectMapper.writeValueAsString(response);
	    
	    mockMvc.perform(buildListAllTransactionsRequest(FROM_ACCOUNT_NUMBER))
	        .andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
	    
	}
	
	/**
	 * Integration Test to create end to end flow
	 * Data setup: 2 accounts setup with default data
	 * 
	 * Calls 3 services in a sequence:
	 * a. Post Transaction API to transfer data from account to another account
	 * b. List all transactions from Transactions master table
	 * c. Get account details API to retrieve from account details and verify the balance.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTransferBetweenAccounts200ResponseWhenProcessedSuccessfully() throws Exception {
		
		List<Transactions> transList = transactionTestDataSetup();
		TransactionsResponse response = new TransactionsResponse();
		response.setListTransactions(transList);		
		
		AccountDetailsResponse accResponse = new AccountDetailsResponse();
		accResponse.setAccount(fromAccountTestData());
		
		accountTestDataSetup();
		
		// Transfer 100 amount from one account to another account
		AccountsTransferRequest request = new AccountsTransferRequest(TO_ACCOUNT_NUMBER, new BigDecimal(100.00), "WIRE", "E2E test");
		String jsonRequest =  objectMapper.writeValueAsString(request);
	
		// Calls postTransactions API to post a transaction
	    mockMvc.perform(buildPostTransactionsRequest(ACCOUNT_NUMBER, jsonRequest))
		.andExpect(MockMvcResultMatchers.status().isCreated());
	    
	    Mockito.when(transactionsService.listAllTransactions(ACCOUNT_NUMBER)).thenReturn(response);
	    Mockito.when(accountService.getAccountDetails(ACCOUNT_NUMBER)).thenReturn(accResponse);
		
	    String jsonTransResponse =  objectMapper.writeValueAsString(response);
	    String jsonAccResponse  =  objectMapper.writeValueAsString(accResponse);
	    System.out.println("Account Response :"+jsonAccResponse);
	    System.out.println("Transactions Response: "+jsonTransResponse);
	    
	    // Calls ListTransactions API to list all transactions from Transactions Master DB
	    mockMvc.perform(buildListAllTransactionsRequest(ACCOUNT_NUMBER))
	        .andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(MockMvcResultMatchers.content().json(jsonTransResponse));	    
	    
	    //Calls GetAccountDetails API to retrieve details of FROM Account and verify the balance reduced by 100
	    mockMvc.perform(buildGetAccountDetails(ACCOUNT_NUMBER))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(jsonAccResponse))
        .andExpect(jsonPath("$.account.accountBalance").value(900));
	}
	
	private MockHttpServletRequestBuilder buildListAllTransactionsRequest(Long accountNumber) {
	    String url = "/v1/accounts/"+accountNumber+"/transactions";
	    System.out.println("calling List Transactions endpoint: "+ url);
	    return MockMvcRequestBuilders.get(url);
	}
	
	private MockHttpServletRequestBuilder buildGetAccountDetails(Long accountNumber) {
	    String url = "/v1/accounts/"+accountNumber;
	    System.out.println("calling GET Account Details endpoint: "+ url);
	    return MockMvcRequestBuilders.get(url);
	}
	
	private MockHttpServletRequestBuilder buildPostTransactionsRequest(Long accountNumber, String body) {
	    String url = "/v1/accounts/"+accountNumber+"/transactions";
	    System.out.println("post transaction to endpoint: "+ url);
	    return MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(body);
	}

	private Account addTestAccount(String accountType, String accountName, String accountDisplayName, Long accountNumber, String society, Long sortcode, BigDecimal amount) {
		Account testAccount = new Account();
		testAccount.setAccountType(accountType);
		testAccount.setAccountName(accountName);
		testAccount.setAccountDisplayName(accountDisplayName);
		testAccount.setAccountNumber(accountNumber);
		testAccount.setAccountSociety(society);
		testAccount.setAccountSortCode(sortcode);
		testAccount.setAccountBalance(amount);
		return testAccount;
	}
	
	private Transactions addTransaction(Long transFromAccNumber, Long transToAccNumber, String transStatus, String transReference, BigDecimal transAmount, String transType) {
		Transactions testTransaction = new Transactions();
		testTransaction.setTransType(transType);
		testTransaction.setTransToAccNumber(transToAccNumber);
		testTransaction.setTransStatus(transStatus);
		testTransaction.setTransReference(transReference);
		testTransaction.setTransAmount(transAmount);
		testTransaction.setTransFromAccNumber(transFromAccNumber);
		return testTransaction;
	}
	
	@After
	public void transactionTestDataTearDown() {
		this.entityManager.clear();
	}

}

