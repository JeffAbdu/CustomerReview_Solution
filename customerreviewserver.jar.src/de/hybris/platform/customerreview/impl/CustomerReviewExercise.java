package de.hybris.platform.customerreview.impl;

import java.util.Arrays;
import java.util.List;

import de.hybris.platform.customerreview.CustomerReviewService;
import de.hybris.platform.customerreview.exception.CurseWordException;
import de.hybris.platform.customerreview.exception.RatingLessThanZeroReviewException;
import de.hybris.platform.customerreview.exception.RatingRangeException;
import de.hybris.platform.customerreview.jalo.CustomerReview;
import de.hybris.platform.customerreview.jalo.CustomerReviewManager;

import org.springframework.beans.factory.annotation.Required; // Spring framework must be added to the library or its dependency must be added within pom.xml in case maven-based project 

import de.hybris.platform.customerreview.CustomerReviewExerciseInterface;

public class CustomerReviewExercise extends AbstractBusinessService implements CustomerReviewExerciseInterface {

	private CustomerReviewService customerReviewService; // Type from the
															// package
															// de.hybris.platform.customerreview.impl
															// -> class
															// DefaultCustomerReviewService 
	                                                        // (added to customerreview-spring.xml)
	private String[] curseWords;

	@Required
	public void setcurseWords(String[] curseWords) {
		this.curseWords = curseWords;
	}

	public String[] getcurseWords() {
		return curseWords;
	}

	/*
	 * Note: ProductModel type (package: "de.hybris.platform.core.model") is
	 * missing from the provided code.
	 */
	
    // Product's total number of customer reviews within a given range:
	public Integer getNumOfReviewsInRangeForProduct(ProductModel productModel, Double minRatingValue, Double maxRatingValue) {

		// Checking the range values:
		if ((minRatingValue == null) || (maxRatingValue == null) || (maxRatingValue.doubleValue() < minRatingValue.doubleValue())) {
			throw new RatingRangeException("Wrong rating range!");
		}

		/*
		 * Note: Product type (class: "de.hybris.platform.jalo.product.Product")
		 * is missing from the provided code.
		 */
		// retrieve all the reviews:
		List<CustomerReview> reviews = CustomerReviewManager.getInstance()
				.getAllReviews((Product) getModelService().getSource(productModel));

		// Counting the reviews within the provided range:
		int count = 0;
		for (CustomerReview review : reviews) {
			if ((review.getRating() <= maxRatingValue) && (review.getRating() >= minRatingValue)) {
				count++;
			}
		}
		return count;
	}
	
	
	/*
	 * Note: The provided code misses the flowing classes:
	 * "CustomerReviewModel", "UserModel", and "ProductModel". However, I am
	 * using the method createCustomerReview(...) from the class
	 * DefaultCustomerReviewService which returns type "CustomerReviewModel" and
	 * accepts arguments of the types: "UserModel", and "ProductModel".
	 */

	// Creating a customer review after checking the required conditions:
	public CustomerReviewModel createCustomerReview(Double rating, String headline, String comment, UserModel user,
			ProductModel product) {

		if (comment != null) {
			// Check if comment contains any of the curse words. compare
			// uppercase only
			String CommentLowerCase = comment.toLowerCase();
			if (Arrays.asList(curseWords).stream().anyMatch(curseword -> CommentLowerCase.contains(curseword))) {
				throw new CurseWordException("Curse words are not allowed to be in the comment");
			}
		}

		// Checking case  rating < 0
		if (rating == null || rating.doubleValue() < 0) {
			throw new RatingLessThanZeroReviewException("Rating value must be less than zero");
		}

		// Go ahead and create a customer review: 
		return customerReviewService.createCustomerReview(rating, headline, comment, user, product);
	}

}