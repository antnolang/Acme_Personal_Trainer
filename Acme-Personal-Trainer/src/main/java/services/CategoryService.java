
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	// Managed repository -------------------------------
	@Autowired
	private CategoryRepository	categoryRepository;

	// Supporting services -------------------------------
	@Autowired
	private UtilityService		utilityService;


	// Constructors --------------------------------------
	public CategoryService() {
		super();
	}

	// Simple CRUD methods -------------------------------
	public Category findOne(final int categoryId) {
		Category result;

		result = this.categoryRepository.findOne(categoryId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Category> findAll() {
		Collection<Category> results;

		results = this.categoryRepository.findAll();

		return results;
	}

	public Category create() {
		Category result;

		result = new Category();

		return result;
	}

	public Category save(final Category category) {
		Assert.notNull(category);

		Category result;
		String name, e;
		String[] elements;

		name = category.getName();
		elements = name.split(",");

		// As the system must be available in English and Spanish, that is, 2
		// languages, the list must contain 2 elements.
		Assert.isTrue(elements.length == 2);

		for (final String element : elements) {
			e = element.trim();
			Assert.isTrue(e != null && e != "");
		}

		result = this.categoryRepository.save(category);

		return result;
	}

	public void delete(final Category category) {
		Assert.notNull(category);
		Assert.isTrue(this.categoryRepository.exists(category.getId()));

		Integer count;

		count = this.numberOfWorkingOutsByCategory(category.getId());

		// We must check that this category doesn't have any reference
		// to working outs.
		Assert.isTrue(count == 0);

		this.categoryRepository.delete(category);
	}

	// Other business methods -----------------------------
	public Map<Integer, String> categoriesByLanguage(final Collection<Category> categories, final String language) {
		Map<Integer, String> results;
		Map<String, Integer> references;
		List<String> elements;
		int w_language;

		results = new HashMap<Integer, String>();

		references = this.referenceByLanguage();

		w_language = references.get(language);

		for (final Category c : categories) {
			elements = this.utilityService.ListByString(c.getName());
			results.put(c.getId(), elements.get(w_language));
		}

		return results;
	}

	public Map<Integer, String> categoriesByLanguage(final String language) {
		Map<Integer, String> results;

		results = this.categoriesByLanguage(this.findAll(), language);

		return results;
	}

	public Integer numberOfWorkingOutsByCategory(final int categoryId) {
		Integer result;

		result = this.categoryRepository.numberOfWorkingOutsByCategory(categoryId);

		return result;
	}

	protected void flush() {
		this.categoryRepository.flush();
	}

	// Private methods ------------------------------------
	private Map<String, Integer> referenceByLanguage() {
		Map<String, Integer> results;

		results = new HashMap<String, Integer>();
		results.put("en", 0);
		results.put("es", 1);

		return results;
	}

}
