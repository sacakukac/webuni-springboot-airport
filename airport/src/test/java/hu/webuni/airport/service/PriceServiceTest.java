package hu.webuni.airport.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {

	@InjectMocks    // <= ebbe akarunk mock-ot injektálni, ezeket éldányosítja
	PriceService priceService;
	
	@Mock			// <= ez az injektált mock, ebből sok lehet
	DiscountService discountService;
	
	@Test
	void testGetFinalPrice() throws Exception {
		int newPrice = new PriceService(p -> 5).getFinalPrice(100);
		//assertEquals(95, newPrice);  //fordított logikai sorrend
		assertThat(newPrice).isEqualTo(95);
	}
	
	@Test
	void testGetFinalPrice2() throws Exception {
		when(discountService.getDiscountPercent(100)).thenReturn(5);
		int newPrice = priceService.getFinalPrice(100); 
		assertThat(newPrice).isEqualTo(95);
	}
}
