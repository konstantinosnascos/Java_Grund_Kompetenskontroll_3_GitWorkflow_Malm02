package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PricingService {
    private static final Logger logger = LoggerFactory.getLogger(PricingService.class);
    private final double besiktningPris = 550;

    public double calculateServicePrice(int year)
    {
        double price ;

        if (year>2020)
        {
            price = 1500;
        } else if ( year >= 2015 && year <= 2019)
        {
            price = 1800;
        } else if ( year >= 2010 && year <= 2014)
        {
            price = 2000;
        } else if ( year>= 2005 && year <=2009)
        {
            price = 2300;
        } else
        {
            price = 2800;
        }

        logger.info("Service-pris beräknat för årsmodell {}: {} kr", year, price);
        return price;
    }

    public double getBesiktningPris() {
        logger.info("Besiktningspris hämtat: {} kr", besiktningPris);
        return besiktningPris;
    }

    public double setReparationPrice (double customPrice)
    {
        if (customPrice < 0)
        {
            logger.warn("Ogiltigt pris för reparation: {}", customPrice);
            throw new IllegalArgumentException("Priset kan inte vara negativt");
        }
        logger.info("Reparationspris satt till: {} kr", customPrice);
        return customPrice;
    }
}
