package com.PlayTrackerWebApp.playtracker.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.PlayTrackerWebApp.playtracker"})
@EntityScan(basePackages = {"com.PlayTrackerWebApp.playtracker.model"})
public class StatsApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(StatsApplication.class, args);
	}
}
