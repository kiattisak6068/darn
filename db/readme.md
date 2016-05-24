# README for DB #

## [Requirement] run the webapp to create table with evolution first 
    activator run

## How to run?
    java -cp h2-1.4.191.jar org.h2.tools.Shell -url jdbc:h2:./provincedb

## How to import from csv?
    #java -cp h2-1.4.191.jar org.h2.tools.Shell -url jdbc:h2:./provincedb
	  sql> INSERT INTO province SELECT * FROM CSVREAD('romanized_province.csv');
	  sql> INSERT INTO amphoe SELECT * FROM CSVREAD('romanized_amphoe.csv');


