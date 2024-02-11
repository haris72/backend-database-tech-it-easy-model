package nl.haris.backendspringboottechiteasymodel.controllers;

import nl.haris.backendspringboottechiteasymodel.models.Television;
import nl.haris.backendspringboottechiteasymodel.exceptions.NameTooLongException;
import nl.haris.backendspringboottechiteasymodel.exceptions.RecordNotFoundException;
import nl.haris.backendspringboottechiteasymodel.repositories.TelevisionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/televisions")
public class TelevisionController {


    private TelevisionRepository televisionRepository;

    public TelevisionController(TelevisionRepository televisionRepository) {
        this.televisionRepository = televisionRepository;
    }

    // CREATE: Voeg een nieuwe television toe
    @PostMapping
    public ResponseEntity<Television> createTelevision(@RequestBody Television television) {

        if (television.getBrand().length() > 20) {
            throw new NameTooLongException("the name is too big, should be less than 20 characters");
        }

        televisionRepository.save(television);
        return ResponseEntity.status(HttpStatus.CREATED).body(television);

        //in postman {"brand": "samsung1", "refreshRate":0}

    }

    // READ: Haal een specifieke television op op basis van ID
    @GetMapping("/{id}")
    public ResponseEntity<List<Television>> getTelevision(@PathVariable Long id) {
        var optionalTelevision = televisionRepository.findTelevisionById(id);
        if (optionalTelevision== null) {
            throw new RecordNotFoundException("the ID is too big");
        }
        return ResponseEntity.ok(optionalTelevision);
    }

    // READ: Haal alle television's op
    @GetMapping
    public ResponseEntity<List<Television>> getAllTelevisions(@RequestParam(name = "brand",
            required = false) String brand) {

        List<Television> filteredTelevisions;
        List<Television> allTelevisions;
        if (brand != null) {

            filteredTelevisions = televisionRepository.findByBrand(brand);

            return ResponseEntity.ok(filteredTelevisions);
        } else {
            allTelevisions = televisionRepository.findAll();
            return ResponseEntity.ok(allTelevisions);
        }
    }


     //UPDATE: Werk een bestaande television bij
    @PutMapping("/{id}")
    public ResponseEntity<Television> updateTelevision(@PathVariable Long id, @RequestBody
    Television televisionDetails){

        Optional<Television> television = televisionRepository.findById(id);

        if (television.isEmpty()){

            throw new RecordNotFoundException("No television found with id: " + id);

        }else {

            Television television1 = television.get();
            television1.setAmbiLight(televisionDetails.getAmbiLight());
            television1.setAvailableSize(televisionDetails.getAvailableSize());
            television1.setAmbiLight(televisionDetails.getAmbiLight());
            television1.setBluetooth(televisionDetails.getBluetooth());
            television1.setBrand(televisionDetails.getBrand());
            television1.setHdr(televisionDetails.getHdr());
            television1.setName(televisionDetails.getName());
            television1.setOriginalStock(televisionDetails.getOriginalStock());
            television1.setPrice(televisionDetails.getPrice());
            //television1.setRefreshRate(televisionDetails.getRefreshRate());
            television1.setScreenQuality(televisionDetails.getScreenQuality());
            television1.setScreenType(televisionDetails.getScreenType());
            television1.setSmartTv(televisionDetails.getSmartTv());
            television1.setSold(televisionDetails.getSold());
            television1.setType(televisionDetails.getType());
            television1.setVoiceControl(televisionDetails.getVoiceControl());
            television1.setWifi(televisionDetails.getWifi());

            Television returnTelevision = televisionRepository.save(television1);

            return ResponseEntity.ok().body(returnTelevision);
        }
}



    // DELETE: Verwijder een television
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id){
        if(televisionRepository.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        televisionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}


//        //var existingTelevision = televisionRepository.findTelevisionById(id);
//        return televisionRepository.findTelevisionById(id)
//                .map(television->{
//                    television.setBrand(televisionDetails.getBrand());
//                    //television.setModel(televisionDetails.getModel());
//                    Television updateTelevision=televisionRepository.save(television);
//                    return ResponseEntity.ok(updateTelevision);
//                }).orElseGet(()->ResponseEntity.notFound().build());