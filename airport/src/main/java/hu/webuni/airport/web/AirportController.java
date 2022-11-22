package hu.webuni.airport.web;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import hu.webuni.airport.mapper.AirportMapper;
import hu.webuni.airport.model.Airport;
import hu.webuni.airport.service.AirportService;

@RestController  //torzsbe serializál minden response-t
@RequestMapping("/api/airports")  //gyoker url minden mappingre 
public class AirportController {

	@Autowired
	AirportService airportService;
	
	@Autowired
	AirportMapper airportMapper;

	@GetMapping
	public List<AirportDto> getAll(){
		return airportMapper.airportsToDtos(airportService.findAll());
	}
	
	@GetMapping("/{id}")
	public AirportDto getById(@PathVariable long id) {
		Airport airport = airportService.findById(id);
		
		if (airport != null) {
			return airportMapper.airportToDto(airport);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public AirportDto createAirport(@RequestBody @Valid AirportDto airportDto) {  
		Airport airport = airportService.save(airportMapper.dtoToAirport(airportDto));
		return airportMapper.airportToDto(airport);
	} 
	
	@PutMapping("/{id}")  //modify
	public  ResponseEntity<AirportDto>  modifyAirport(@PathVariable long id, @RequestBody AirportDto airportDto) { 
		Airport airport = airportMapper.dtoToAirport(airportDto);
		airport.setId(id);
		try {
			AirportDto savedAirportDto = airportMapper.airportToDto(airportService.update(airport));
			return ResponseEntity.ok(savedAirportDto);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}


	@DeleteMapping("/{id}")
	public void deleteAirport(@PathVariable long id) {
		airportService.delete(id);
	}

//	@GetMapping
//	public List<AirportDto> getAll(){
//		return new ArrayList<>(airports.values());
//	}
//	
//	@GetMapping("/{id}")
//	public AirportDto getById(@PathVariable long id) {
//		AirportDto airportDto = airports.get(id);
////		if (airportDto != null) {
////			return ResponseEntity.ok(airportDto);
////		} else {
////			return ResponseEntity.notFound().build();
////		}
//		if (airportDto != null) {
//			return airportDto;
//		} else {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//		}
//	}
//	
//	@PostMapping
//	public AirportDto createAirport(@RequestBody @Valid AirportDto airportDto/*, BindingResult errors*/) {  //ha a BindingResults benne van, akkor nekünk kell kivételt dobni/lekezelni a validációs hibát
////		if(errors.hasErrors()) {
////			//valami kivételdobás
////		}
//		checkUniqueIata(airportDto.getIata());
//		airports.put(airportDto.getId(), airportDto);
//		return airportDto;
//	} 
//	
//	@PutMapping("/{id}")  //modify
//	public  ResponseEntity<AirportDto>  modifyAirport(@PathVariable long id, @RequestBody AirportDto airportDto) { 
//		if (!airports.containsKey(id)) {
//			return ResponseEntity.notFound().build();			
//		} else {
//			checkUniqueIata(airportDto.getIata());
//			airportDto.setId(id);
//			airports.put(airportDto.getId(), airportDto);
//			return ResponseEntity.ok(airportDto);
//		}
//	}
//	
//	private void checkUniqueIata(String iata) {
//		Optional<AirportDto> airportWithSameIata = airports.values().stream().filter(a -> a.getIata().equals(iata)).findAny();
//		if (airportWithSameIata.isPresent()) {
//			throw new NonUniqueIataException(iata);
//		}
//	}
}
