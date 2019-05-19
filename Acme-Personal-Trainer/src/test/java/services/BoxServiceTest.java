
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Box;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class BoxServiceTest extends AbstractTest {

	// Service under testing -----------------
	@Autowired
	private BoxService	boxService;


	// Other services ------------------------

	// Suite test ----------------------------

	/*
	 * A: Requirement 8.4 (An authenticated user can list his or her boxes).
	 * C: Analysis of sentence coverage: 3/3 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void list_positiveTest() {
		super.authenticate("auditor1");

		int actorId;
		List<Box> boxes;

		actorId = super.getEntityId("auditor1");
		boxes = new ArrayList<>(this.boxService.findRootBoxesByActor(actorId));

		Assert.notNull(boxes);
		Assert.isTrue(boxes.size() >= 5);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 8.4 (An authenticated user can display his or her boxes).
	 * C: Analysis of sentence coverage: 20/20 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void display_positiveTest() {
		super.authenticate("customer1");

		int boxId;
		Box outBox;

		// Out box of customer 1
		boxId = super.getEntityId("box81");
		outBox = this.boxService.findOneToDisplay(boxId);

		Assert.notNull(outBox);
		Assert.notNull(outBox.getName());
		Assert.isTrue(outBox.getIsSystemBox());
		Assert.notNull(outBox.getActor());
		Assert.isTrue(outBox.getMessages().size() == 1);
		Assert.isNull(outBox.getParent());

		super.unauthenticate();
	}

	/*
	 * A: Requirement 8.4 (An authenticated user can display his or her boxes).
	 * B: A user try to display a box that belongs to other actor.
	 * C: Analysis of sentence coverage: 19/20 -> 95.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void display_negativeTest() {
		super.authenticate("auditor1");

		int boxId;
		Box outBox;

		// Out box of customer 1
		boxId = super.getEntityId("box81");
		outBox = this.boxService.findOneToDisplay(boxId);

		Assert.isNull(outBox);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 8.4 (An authenticated user can create a box).
	 * C: Analysis of sentence coverage: 16/16 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void create_positiveTest() {
		super.authenticate("admin1");

		Box box;
		box = this.boxService.create();

		Assert.notNull(box);
		Assert.notNull(box.getActor());
		Assert.notNull(box.getMessages());
		Assert.isNull(box.getName());
		Assert.isTrue(!box.getIsSystemBox());
		Assert.isNull(box.getParent());

		super.unauthenticate();
	}

	/*
	 * A: Requirement 8.4 (An authenticated user can edit his or her boxes).
	 * B: A user try to edit a system box.
	 * C: Analysis of sentence coverage: 2/41 -> 4.88% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negative_saveTest_uno() {
		super.authenticate("customer1");

		final int actorId = super.getEntityId("customer1");
		Box saved, outBox;

		outBox = this.boxService.findOutBoxFromActor(actorId);
		outBox.setName("OUT BOX");

		saved = this.boxService.save(outBox);

		Assert.isNull(saved);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 8.4 (An authenticated user can edit his or her boxes).
	 * B: A user try to edit a box that belongs to another actor.
	 * C: Analysis of sentence coverage: 15/41 -> 36.58% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negative_saveTest_dos() {
		super.authenticate("customer1");

		final int boxId = super.getEntityId("box35");
		Box saved, box;

		// This box belongs to auditor1
		box = this.boxService.findOne(boxId);
		box.setName("Hacked by customer1");

		saved = this.boxService.save(box);

		Assert.isNull(saved);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 8.4 (An authenticated user can edit his or her boxes).
	 * C: Analysis of sentence coverage: 41/41 -> 100.00% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test
	public void positive_saveTest() {
		super.authenticate("auditor1");

		final int boxId = super.getEntityId("box35");
		Box saved, box;

		box = this.boxService.findOne(boxId);
		box.setName("private box");

		saved = this.boxService.save(box);
		this.boxService.flush();

		Assert.notNull(saved);

		super.unauthenticate();
	}

	@Test
	public void driverSave() {
		final Object testingData[][] = {
			/*
			 * A: Requirement 8.5 (An authenticated user can save a box)
			 * B: Invalid data in box::name.
			 * C: Analysis of sentence coverage: 23/41 -> 56.97% executed code lines.
			 * D: Analysis of data coverage: Box::name is null => 1/9 -> 11.11%.
			 */
			//TODO:
			//			{
			//				null, "box35", ConstraintViolationException.class
			//			},

			/*
			 * A: Requirement 8.5 (An authenticated user can save a box)
			 * B: Invalid data in box::name.
			 * C: Analysis of sentence coverage: 23/41 -> 56.97% executed code lines.
			 * D: Analysis of data coverage: Box::name is empty string => 1/9 -> 11.11%.
			 */
			{
				"", "box35", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 8.5 (An authenticated user can save a box)
			 * B: Invalid data in box::name.
			 * C: Analysis of sentence coverage: 23/41 -> 56.97% executed code lines.
			 * D: Analysis of data coverage: Box::name is a malicious script => 1/9 -> 11.11%.
			 */
			{
				"<script> Alert('Hacked');</script>", "box35", ConstraintViolationException.class
			},
			/*
			 * A: Requirement 8.5 (An authenticated user can save a box)
			 * C: Analysis of sentence coverage: 23/41 -> 56.97% executed code lines.
			 * D: Analysis of data coverage: Every attribute has a valid value => 1/9 -> 11.11%.
			 */
			{
				"Betis box", "box35", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSave((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateSave(final String name, final String parentBean, final Class<?> expected) {
		Class<?> caught;
		Box box, saved, parent;
		int boxId;

		this.startTransaction();

		caught = null;
		try {
			super.authenticate("auditor1");

			boxId = this.getEntityId(parentBean);
			parent = this.boxService.findOne(boxId);

			box = this.boxService.create();
			box.setName(name);
			box.setParent(parent);

			saved = this.boxService.save(box);
			this.boxService.flush();

			Assert.notNull(saved);
			Assert.isTrue(saved.getId() != 0);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
		}

		this.rollbackTransaction();

		super.checkExceptions(expected, caught);
	}

	/*
	 * A: Requirement 8.4 (An authenticated user can delete his or her boxes).
	 * B: A user try to delete a system box.
	 * C: Analysis of sentence coverage: 3/47 -> 6.38% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negative_deleteTest_uno() {
		super.authenticate("auditor1");

		final int actorId = super.getEntityId("auditor1");
		Box found, outBox;

		outBox = this.boxService.findOutBoxFromActor(actorId);

		this.boxService.delete(outBox);

		found = this.boxService.findOne(outBox.getId());
		Assert.notNull(found);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 8.4 (An authenticated user can edit his or her boxes).
	 * B: A user try to delete a box that belongs to another actor.
	 * C: Analysis of sentence coverage: 16/47 -> 34.04% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negative_deleteTest_dos() {
		super.authenticate("customer1");

		Box found, box;
		int boxId;

		boxId = super.getEntityId("box35");
		box = this.boxService.findOne(boxId);

		this.boxService.delete(box);

		found = this.boxService.findOne(box.getId());
		Assert.notNull(found);

		super.unauthenticate();
	}

	/*
	 * A: Requirement 8.4 (An authenticated user can edit his or her boxes).
	 * C: Analysis of sentence coverage: 44/47 -> 93.61% of executed lines codes .
	 * D: Analysis of data coverage: intentionally blank.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void positive_deleteTest() {
		super.authenticate("auditor1");

		Box found, box;
		int boxId;

		boxId = super.getEntityId("box35");
		box = this.boxService.findOne(boxId);

		this.boxService.delete(box);

		found = this.boxService.findOne(box.getId());
		Assert.isNull(found);

		super.unauthenticate();
	}

}
