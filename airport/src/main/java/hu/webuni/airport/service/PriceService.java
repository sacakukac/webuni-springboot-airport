package hu.webuni.airport.service;

import org.springframework.stereotype.Service;

@Service
public class PriceService {

	//@Autowired is lehetne
	private DiscountService discountService;

	//create construktor
	public PriceService(DiscountService discountService) {
		this.discountService = discountService;
	}

	public int getFinalPrice(int price) {
		return (int)(price * (100.0 - discountService.getDiscountPercent(price)) / 100.0);
	}
	
	
}
