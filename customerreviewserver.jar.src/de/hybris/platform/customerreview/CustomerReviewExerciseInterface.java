package de.hybris.platform.customerreview;

import java.util.List;

public abstract interface CustomerReviewExerciseInterface 
{
	
	// Product's total number of customer reviews within a given range: 
    public abstract Integer getNumOfReviewsInRangeForProduct(ProductModel productModel, Double minRatingValue, Double maxRatingValue);
	
    // Creating a customer review after checking the required conditions:
    public abstract CustomerReviewModel createCustomerReview(Double rating, String headline, String comment, UserModel user,
			ProductModel product);


}
