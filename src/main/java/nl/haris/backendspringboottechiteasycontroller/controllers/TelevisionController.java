package nl.haris.backendspringboottechiteasycontroller.controllers;

import nl.haris.backendspringboottechiteasycontroller.Television;
import nl.haris.backendspringboottechiteasycontroller.exceptions.NameTooLongException;
import nl.haris.backendspringboottechiteasycontroller.exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/televisions")
public class TelevisionController {

    private List<Television> televisionDataBase = new ArrayList<>();
    private Long currentId = 1L;

    // CREATE: Voeg een nieuwe television toe
    @PostMapping
    public ResponseEntity<Television> createTelevision(@RequestBody Television televison) {
        televison.setId(currentId++);
        if (televison.getBrand().length() > 20) {
            throw new NameTooLongException("the name is too big, should be less than 20 characters");
        }

        televisionDataBase.add(televison);
        return ResponseEntity.status(HttpStatus.CREATED).body(televison);


    }
    // READ: Haal een specifieke television op op basis van ID
    @GetMapping("/{id}")
    public ResponseEntity<Television> getTelevision(@PathVariable Long id) {
        var optionalTelevision = findTelevisionById(id);
        if (optionalTelevision== null) {
            throw new RecordNotFoundException("the ID is too big");
        }
        return ResponseEntity.ok(optionalTelevision);
    }

    // READ: Haal alle television's op
    @GetMapping
    public ResponseEntity<List<Television>> getAllCars(@RequestParam(name = "brand",
            required = false) String brand) {
        if (brand != null) {
            List<Television> filteredTelevisions = getFilteredTelevision(brand);
            return ResponseEntity.ok(filteredTelevisions);
        } else {
            return ResponseEntity.ok(televisionDataBase);
        }
    }

    private List<Television> getFilteredTelevision(String brand) {
        List<Television> filteredCars = new ArrayList<>();
        for (Television television : televisionDataBase) {
            if (television.getBrand().equalsIgnoreCase(brand)) {
                filteredCars.add(television);
            }

        }
        return filteredCars;
    }

    // UPDATE: Werk een bestaande television bij
    @PutMapping("/{id}")
    public ResponseEntity<Television> updateCar(@PathVariable Long id, @RequestBody
    Television television) {
        var existingTelevision = findTelevisionById(id);
        if (existingTelevision == null) {
            return ResponseEntity.notFound().build();
        }
        existingTelevision.setBrand(television.getBrand());
        return ResponseEntity.ok(existingTelevision);
    }

    // DELETE: Verwijder een television
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTelevision(@PathVariable Long id) {
        var optionalTelevision = findTelevisionById(id);
        if (optionalTelevision == null) {
            throw new RecordNotFoundException("ID cannot be found");
        }
        televisionDataBase.remove(optionalTelevision);
        return ResponseEntity.ok().build();
    }

    private Television findTelevisionById(Long id) {
        for (Television television : televisionDataBase) {
            if (television.getId().equals(id)) {
                return television;
            }
        }
        return null;
    }

}
