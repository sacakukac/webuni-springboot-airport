package hu.webuni.airport.service;

public class NonUniqueIataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NonUniqueIataException(String iata) {
		super("Existing IATA:" + iata);
		// TODO Auto-generated constructor stub
	}


	
}
