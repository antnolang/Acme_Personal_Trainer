
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BoxRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class BoxService {

	// Managed repository --------------------------
	@Autowired
	private BoxRepository	boxRepository;

	// Other supporting services -------------------
	@Autowired
	private ActorService	actorService;


	// Constructor --------------------------------
	public BoxService() {
		super();
	}

	// Simple CRUD methods ------------------------
	public Box findOne(final int boxId) {
		Box result;

		result = this.boxRepository.findOne(boxId);
		Assert.notNull(result);

		return result;
	}

	public Box findOneToDisplay(final int boxId) {
		Box result;

		result = this.findOne(boxId);
		this.checkByPrincipal(result);

		return result;
	}

	public Box findOneToEdit(final int boxId) {
		Box result;

		result = this.findOneToDisplay(boxId);
		Assert.isTrue(!result.getIsSystemBox());

		return result;
	}

	public Collection<Box> findAll() {
		Collection<Box> results;

		results = this.boxRepository.findAll();

		return results;
	}

	public Box create() {
		Box result;
		final Actor principal;

		principal = this.actorService.findPrincipal();

		result = new Box();
		result.setActor(principal);
		result.setMessages(Collections.<Message> emptySet());

		return result;
	}

	public Box save(final Box box) {
		Assert.notNull(box);
		Assert.isTrue(!box.getIsSystemBox());
		this.checkByPrincipal(box);
		this.checkName(box);

		Box result;

		if (this.boxRepository.exists(box.getId()) && box.getParent() != null)
			this.checkParent(box);

		result = this.boxRepository.save(box);

		return result;
	}

	public void delete(final Box box) {
		Assert.notNull(box);
		Assert.isTrue(this.boxRepository.exists(box.getId()));
		Assert.isTrue(!box.getIsSystemBox());
		this.checkByPrincipal(box);

		Actor principal;
		Box trashBox;
		Collection<Message> messages;
		Collection<Box> descendantBoxes;

		principal = this.actorService.findPrincipal();
		trashBox = this.findTrashBoxFromActor(principal.getId());

		// If this box contains messages, we must move those messages to
		// trash box.
		messages = box.getMessages();
		if (!messages.isEmpty())
			this.addMessages(trashBox, messages);

		// If this box has descendant boxes, we must delete them and move the messages of those
		// descendant boxes to trash box.
		descendantBoxes = this.descendantBoxes(box);
		if (descendantBoxes != null && !descendantBoxes.isEmpty())
			for (final Box descendant : descendantBoxes) {
				messages = descendant.getMessages();

				if (!messages.isEmpty())
					this.addMessages(trashBox, messages);

				this.boxRepository.delete(descendant);
			}

		this.boxRepository.delete(box);
	}

	// This method is called when a user wants to remove this data from the system
	public void deleteBoxesFromActor(final Actor actor) {
		Collection<Box> boxes;

		boxes = this.findBoxesByActor(actor.getId());

		this.boxRepository.delete(boxes);
	}

	public Collection<Box> findRootBoxesByActor(final int actorId) {
		Collection<Box> results;

		results = this.boxRepository.findRootBoxesByActor(actorId);

		return results;
	}

	public Collection<Box> findChildBoxesByBox(final int boxId) {
		Collection<Box> results;

		results = this.boxRepository.findChildBoxesByBox(boxId);

		return results;
	}

	public Collection<Box> findBoxesByActor(final int actorId) {
		Collection<Box> results;

		results = this.boxRepository.findBoxesByActor(actorId);

		return results;
	}


	@Autowired
	private Validator	validator;


	public Box reconstruct(final Box box, final BindingResult binding) {
		Box result, storedBox;

		if (box.getId() == 0)
			result = this.create();
		else {
			storedBox = this.findOne(box.getId());

			result = new Box();
			result.setId(storedBox.getId());
			result.setVersion(storedBox.getVersion());
			result.setIsSystemBox(storedBox.getIsSystemBox());
			result.setActor(storedBox.getActor());
			result.setMessages(storedBox.getMessages());
		}

		result.setName(box.getName().trim());
		result.setParent(box.getParent());

		this.validator.validate(result, binding);

		return result;
	}

	public Collection<Box> posibleParentBoxes(final Box box, final int actorId) {
		Collection<Box> results, descendantBoxes;

		descendantBoxes = this.descendantBoxes(box);
		results = this.findBoxesByActor(actorId);
		results.removeAll(descendantBoxes);

		return results;
	}

	// Protected methods --------------------------
	protected void createSystemBoxes(final Actor actor) {
		Assert.notNull(actor);

		final String inBox = "in box";
		final String outBox = "out box";
		final String notificationBox = "notification box";
		final String trashBox = "trash box";
		final String spamBox = "spam box";

		this.createSystemBox(actor, inBox);
		this.createSystemBox(actor, outBox);
		this.createSystemBox(actor, spamBox);
		this.createSystemBox(actor, notificationBox);
		this.createSystemBox(actor, trashBox);
	}

	protected Box findInBoxFromActor(final int actorId) {
		Box result;

		result = this.boxRepository.findInBoxFromActor(actorId);

		return result;
	}

	protected Box findOutBoxFromActor(final int actorId) {
		Box result;

		result = this.boxRepository.findOutBoxFromActor(actorId);

		return result;
	}

	protected Box findSpamBoxFromActor(final int actorId) {
		Box result;

		result = this.boxRepository.findSpamBoxFromActor(actorId);

		return result;
	}

	protected Box findTrashBoxFromActor(final int actorId) {
		Box result;

		result = this.boxRepository.findTrashBoxFromActor(actorId);

		return result;
	}

	protected Box findNotificationBoxFromActor(final int actorId) {
		Box result;

		result = this.boxRepository.findNotificationBoxFromActor(actorId);

		return result;
	}

	protected Collection<Box> findBoxesFromActorThatContaintsAMessage(final int actorId, final int messageId) {
		Collection<Box> results;

		results = this.boxRepository.findBoxesFromActorThatContaintsAMessage(actorId, messageId);

		return results;
	}

	protected Integer numberOfBoxesThatContaintAMessage(final int messageId) {
		Integer result;

		result = this.boxRepository.numberOfBoxesThatContaintAMessage(messageId);

		return result;
	}

	protected void addMessage(final Box box, final Message message) {
		box.getMessages().add(message);
	}

	protected void addMessages(final Box box, final Collection<Message> messages) {
		box.getMessages().addAll(messages);
	}

	protected void removeMessage(final Box box, final Message message) {
		box.getMessages().remove(message);
	}

	protected void checkByPrincipal(final Box box) {
		Actor principal;

		principal = this.actorService.findPrincipal();

		Assert.isTrue(box.getActor().equals(principal));
	}

	protected Collection<Box> descendantBoxes(final Box box) {
		List<Box> results;
		Collection<Box> childBoxes;

		childBoxes = this.findChildBoxesByBox(box.getId());

		results = new ArrayList<Box>();
		if (!childBoxes.isEmpty()) {
			results.addAll(childBoxes);
			for (final Box b : childBoxes)
				results.addAll(this.descendantBoxes(b));
		}

		return results;
	}

	protected Collection<Box> findBoxesByMessage(final int messageId) {
		Collection<Box> results;

		results = this.boxRepository.findBoxesByMessage(messageId);

		return results;
	}

	protected void flush() {
		this.boxRepository.flush();
	}

	// Private methods ---------------------------
	private void checkName(final Box box) {
		boolean validName;

		validName = box.getName().equals("in box") || box.getName().equals("out box") || box.getName().equals("notification box") || box.getName().equals("trash box") || box.getName().equals("spam box");

		Assert.isTrue(!validName, "Invalid name");
	}

	// Check that box doesn't create a cycle.
	private void checkParent(final Box box) {
		Box parent;
		Collection<Box> descendantBoxes;

		parent = box.getParent();
		descendantBoxes = this.descendantBoxes(box);

		Assert.isTrue(!descendantBoxes.contains(parent));
	}

	private void createSystemBox(final Actor actor, final String name) {
		Box box;

		box = new Box();
		box.setActor(actor);
		box.setMessages(Collections.<Message> emptySet());
		box.setIsSystemBox(true);
		box.setName(name);

		this.boxRepository.save(box);
	}

}
