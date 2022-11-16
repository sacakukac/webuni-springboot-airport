package hu.webuni.airport.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.airport.dto.AirportDto;
import hu.webuni.airport.service.NonUniqueIataException;

@RestController  //torzsbe serializál minden response-t
@RequestMapping("/api/airports")  //gyoker url minden mappingre 
public class AirportController {

	private Map<Long, AirportDto> airports = new HashMap<>();
	
	{
		airports.put(1L, new AirportDto(1, "lisztFeri", "BUD"));
		airports.put(2L, new AirportDto(2, "DebiFeri", "DEB"));
	}
	
	@GetMapping
	public List<AirportDto> getAll(){
		return new ArrayList<>(airports.values());
	}
	
	@GetMapping("/{id}")
	public AirportDto getById(@PathVariable long id) {
		AirportDto airportDto = airports.get(id);
//		if (airportDto != null) {
//			return ResponseEntity.ok(airportDto);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
		if (airportDto != null) {
			return airportDto;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public AirportDto createAirport(@RequestBody @Valid AirportDto airportDto/*, BindingResult errors*/) {  //ha a BindingResults benne van, akkor nekünk kell kivételt dobni/lekezelni a validációs hibát
//		if(errors.hasErrors()) {
//			//valami kivételdobás
//		}
		checkUniqueIata(airportDto.getIata());
		airports.put(airportDto.getId(), airportDto);
		return airportDto;
	} 
	
	@PutMapping("/{id}")  //modify
	public  ResponseEntity<AirportDto>  modifyAirport(@PathVariable long id, @RequestBody AirportDto airportDto) { 
		if (!airports.containsKey(id)) {
			return ResponseEntity.notFound().build();			
		} else {
			checkUniqueIata(airportDto.getIata());
			airportDto.setId(id);
			airports.put(airportDto.getId(), airportDto);
			return ResponseEntity.ok(airportDto);
		}
	}
	
	private void checkUniqueIata(String iata) {
		Optional<AirportDto> airportWithSameIata = airports.values().stream().filter(a -> a.getIata().equals(iata)).findAny();
		if (airportWithSameIata.isPresent()) {
			throw new NonUniqueIataException(iata);
		}
	}

	@DeleteMapping("/{id}")
	public void deleteAirport(@PathVariable long id) {
		airports.remove(id);
	}
}
