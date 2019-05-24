
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.WorkingOut;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// The SUT ---------------------------
	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private WorkingOutService	workingOutService;

	@Autowired
	private CreditCardService	creditCardService;


	//Test ------------------------------------------------

	@Test
	public void driverCreate() {
		final Object testingData[][] = {
			/*
			 * A: Req.10.3 Crear una solicitud
			 * B: Test positivo
			 * C: 100%. 77/77 Recorre 77 de las 77 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"customer1", "workingOut7", "creditCard2", null
			},
			/*
			 * A: Req.10.1 Crear una solicitud
			 * B: Test negativo: La creditCard no es del customer
			 * C: 32%. 25/77 Recorre 25 de las 77 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"customer1", "workingOut7", "creditCard5", IllegalArgumentException.class
			},
			/*
			 * A: Req.10.1 Crear una solicitud
			 * B: Test negativo: El workingOut no está en modo final
			 * C: 38.9%. 30/77 Recorre 30 de las 77 líneas de código totales
			 * D: Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"customer1", "workingOut6", "creditCard2", IllegalArgumentException.class
			},
			/*
			 * A: Req.10.1 Crear una solicitud
			 * B: Test negativo: Ya tiene una solicitud en dicho WorkingOut
			 * C: 11.6%. 9/77 Recorre 9 de las 77 líneas totales
			 * D:Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"customer3", "workingOut2", "creditCard4", IllegalArgumentException.class
			},
			/*
			 * A: Req.10.1 Crear una solicitud
			 * B: Test negativo: Dicho workingOut ya tiene una solicitud aceptada
			 * C: 12.9%. 10/77 Recorre 10 de las 77 líneas totales
			 * D:Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"customer3", "workingOut1", "creditCard4", IllegalArgumentException.class
			},
			/*
			 * A: Req.10.1 Crear una solicitud
			 * B: Test negativo: El workingOut ya está caducado
			 * C: 27.2%. 21/77 Recorre 21 de las 77 líneas totales
			 * D:Intencionadamente en blanco. No se comprueban datos
			 */
			{
				"customer3", "workingOut8", "creditCard4", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreate((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), super.getEntityId((String) testingData[i][2]), (Class<?>) testingData[i][3]);

	}

	protected void templateCreate(final String username, final int workingOutId, final int creditCardId, final Class<?> expected) {
		Class<?> caught;
		final Application application, applicationSaved;
		final WorkingOut workingOut;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate(username);
			workingOut = this.workingOutService.findOne(workingOutId);

			application = this.applicationService.create(workingOut);

			application.setCreditCard(this.creditCardService.findOne(creditCardId));
			applicationSaved = this.applicationService.save(application);
			this.applicationService.flush();

			Assert.notNull(applicationSaved);
			Assert.isTrue(applicationSaved.getId() != 0);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	/*
	 * A: Req.9.2 Cambiar estado de application a "ACCEPTED"
	 * C: 96.7% 28/28 Recorre 28 de las 28 líneas totales
	 * D: Intencionadamente en blanco. No se comprueban datos
	 */
	@Test
	public void acceptedApplication_test() {
		super.authenticate("trainer3");

		int applicationId;
		Application application;

		applicationId = super.getEntityId("application4");
		application = this.applicationService.findOneToTrainer(applicationId);

		this.applicationService.acceptedApplication(application);

		super.unauthenticate();
	}

	/*
	 * A: Req.9.2 Cambiar estado de application a "REJECTED"
	 * C: 96.7% 28/28 Recorre 28 de las 28 líneas totales
	 * D: Intencionadamente en blanco. No se comprueban datos
	 */
	@Test
	public void rejectedApplication_test() {
		super.authenticate("trainer3");

		int applicationId;
		Application application;

		applicationId = super.getEntityId("application4");
		application = this.applicationService.findOneToTrainer(applicationId);

		this.applicationService.rejectedApplication(application);

		super.unauthenticate();
	}

	/*
	 * A: Req.9.2 Cambiar estado de application de otro company a "ACCEPTED"
	 * C:96.7% 18/28 Recorre 18 de las 28 líneas totales
	 * D: Intencionadamente en blanco. No se comprueban datos
	 */
	@Test(expected = IllegalArgumentException.class)
	public void acceptedApplication_negative_test() {
		super.authenticate("trainer2");

		int applicationId;
		Application application;

		applicationId = super.getEntityId("application4");
		application = this.applicationService.findOneToTrainer(applicationId);

		this.applicationService.acceptedApplication(application);

		super.unauthenticate();
	}

	/*
	 * A: Req.9.2 Cambiar estado de application de otro company a "REJECTED"
	 * C:96.7% 18/28 Recorre 18 de las 28 líneas totales
	 * D: Intencionadamente en blanco. No se comprueban datos
	 */
	@Test(expected = IllegalArgumentException.class)
	public void rejectedApplication_negative_test() {
		super.authenticate("trainer2");

		int applicationId;
		Application application;

		applicationId = super.getEntityId("application4");
		application = this.applicationService.findOneToTrainer(applicationId);

		this.applicationService.rejectedApplication(application);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 11.4: The ratio of the pending, accepted, rejected applications
	 * C: Analysis of sentence coverage: 3/3 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void findRatioPendingApplications_positiveTest() {
		Double dataRejected;
		Double dataPending;
		Double dataAccepted;
		Double suma;

		dataAccepted = this.applicationService.findRatioAcceptedApplications();
		dataPending = this.applicationService.findRatioPendingApplications();
		dataRejected = this.applicationService.findRatioRejectedApplications();

		suma = dataRejected + dataPending + dataAccepted;
		Assert.isTrue(suma == 1.00001);
	}

}
