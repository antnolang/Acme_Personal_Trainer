
package services;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.CustomerRepository;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	// Other services and repositories ----------------------------------------

	@Autowired
	private CustomerRepository	customerRepository;

	// Tests ------------------------------------------------------------------

	//	/* TODO: Test funcional query 11.4.8
	//	 * A: An actor who is authenticated as an administrator must be able to
	//	 * display a dashboard with the following information:
	//	 * The listing of customers who have got accepted at least 10% more 
	//	 * applications than the average, ordered by number of applications.
	//	 * 
	//	 * B: Positive test
	//	 * 
	//	 * C: 100% of sentence coverage.
	//	 * 
	//	 * D: 100% of data coverage.
	//	 */
	//	@Test
	//	public void testFindUsualCustomers() {
	//		Collection<Customer> customers;
	//		Customer c1, c2;
	//		int c1Id, c2Id, numberCustomers;
	//
	//		customers = this.curriculumService.findUsualCustomers();
	//		c1Id = super.getEntityId("customer1");
	//		c2Id = super.getEntityId("customer2");
	//		c1 = this.customerRepository.findOne(c1Id);
	//		c2 = this.customerRepository.findOne(c2Id);
	//		numberCustomers = 2;
	//
	//		Assert.isTrue(customers.contains(c1) && customers.contains(c2));
	//		Assert.isTrue(customers.size() == numberCustomers);
	//	}

	//	/* TODO: Test funcional query 37.5.3
	//	 * A: An actor who is authenticated as an administrator must be able to
	//	 * display a dashboard with the following information:
	//	 * The top-three customers that write more endorsement.
	//	 * 
	//	 * B: Positive test
	//	 * 
	//	 * C: 100% of sentence coverage.
	//	 * 
	//	 * D: 100% of data coverage.
	//	 */
	//	@Test
	//	public void testCustomerWriteMostEndorsement() {
	//		Collection<Customer> customers;
	//		Customer c1, c2, c3;
	//		int c1Id, c2Id, c3Id;
	//
	//		customers = this.curriculumService.findCustomerWriteMostEndorsement();
	//		c1Id = super.getEntityId("customer1");
	//		c2Id = super.getEntityId("customer2");
	//		c3Id = super.getEntityId("customer3");
	//		c1 = this.customerRepository.findOne(c1Id);
	//		c2 = this.customerRepository.findOne(c2Id);
	//		c3 = this.customerRepository.findOne(c3Id);
	//
	//		Assert.isTrue(customers.contains(c1) && customers.contains(c2) && customers.contains(c3));
	//		Assert.isTrue(customers.size() == 3);
	//	}

	// Ancillary methods ------------------------------------------------------
}
