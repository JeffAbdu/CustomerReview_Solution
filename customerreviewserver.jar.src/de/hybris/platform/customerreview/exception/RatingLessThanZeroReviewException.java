package de.hybris.platform.customerreview.exception;

public class RatingLessThanZeroReviewException extends Exception{
	
    public RatingLessThanZeroReviewException() {}
    
    public RatingLessThanZeroReviewException(String message)
    {
       super(message);
    }
}
