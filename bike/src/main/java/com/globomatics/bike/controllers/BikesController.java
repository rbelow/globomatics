package com.globomatics.bike.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.globomatics.bike.models.Bike;
import com.globomatics.bike.repositories.BikeRepository;

@RestController
@RequestMapping("/api/v1/bikes")
public class BikesController {
	@Autowired
	private BikeRepository bikeRepository;

	@GetMapping // forward get requests from /api/v1/bikes/ to list method
	public List<Bike> list() {
//		List<Bike> bikes = new ArrayList<>();
//		return bikes;
		return bikeRepository.findAll(); // spring jpa-repository method
	}

	// post request to create a bike
	@PostMapping
	@ResponseStatus(HttpStatus.OK) // customize response status
	// TODO:
	// [] send 201 created response
	// [] if the email is already in use do not create the resource
	public void create(@RequestBody Bike bike) {
		bikeRepository.save(bike);
	}

	// get a bike by id
	@GetMapping("/{id}") // add get to base path
	public Bike get(@PathVariable("id") long id) {
//		return new Bike();
		return bikeRepository.getOne(id);
	}
}
