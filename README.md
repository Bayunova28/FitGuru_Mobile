# FitGuru Mobile Application

## Table of Contents
* [Background](#background)
* [Requirement](#requirement)
* [Feature](#feature)
* [Creator](#creator)

## Background
<p align="center">
<img src="https://github.com/Bayunova28/FitGuru_Mobile_Application/blob/master/fitguru-cover.png" height="750" width="700">
<p>
  
<p align="justify">FitGuru mobile application is a mobile application that brings new innovations in the world of fitness by bringing features that provide many benefits 
for users to track their health and physical fitness patterns. FitGuru is designed to help users achieve their desired goals and bring understanding of the importance of 
healthy living through unique ways. That way, it indirectly provides a special attraction for millennials to join as members of FitGuru. FitGuru was founded in 2021 with 
the aim of being a model healthcare service digital technology that aims to provide modern health services and education to user. FitGuru is passionate about providing 
health benefits to users and ready to be a pioneer in the advancement of the health and sports sector in information and technology.<p>  

## Requirement
### Database
<img src="https://github.com/Bayunova28/FitGuru_Mobile_Application/blob/master/erd-database.png" height="500" width="900">
  
```mysql
-- create database FitGuru
CREATE DATABASE FitGuru;
  
-- create table calories
CREATE TABLE Calories (
  CaloriesID INT NOT NULL PRIMARY KEY,
  Total_Calories INT NOT NULL);

-- create table product
CREATE TABLE Product (
  ProductID INT NOT NULL PRIMARY KEY,
  Name VARCHAR(50) NOT NULL,
  Category VARCHAR(20) NOT NULL,
  Description TEXT NOT NULL);
  
-- create table rating
CREATE TABLE Rating (
  RatingID INT NOT NULL PRIMARY KEY,
  Rate CHAR(1) NOT NULL,
  Comment TEXT NOT NULL);
 
-- create table users
CREATE TABLE Inuvoice (
  UserID INT NOT NULL PRIMARY KEY,
  FullName VARCHAR(50) NOT NULL,
  Username VARCHAR(20) NOT NULL,
  Password CHAR(20) NOT NULL,
  Email VARCHAR(20) NOT NULL,
  Phone INT NOT NULL,
  Address VARCHAR(100) NOT NULL,
  Weight INT NOT NULL,
  FOREIGN KEY (RatingID) REFERENCES Rating(RatingID));
  
-- create table login information
CREATE TABLE Login_Information (
  LoginID INT NOT NULL PRIMARY KEY,
  Username VARCHAR(20) NOT NULL,
  FOREIGN KEY (UserID) REFERENCES Users(UserID));
```
  
## Feature
* Login & Logout
* Signup
* CRUD Database (Create, Read, Update & Delete)
* FitProduct
* FitWorkout
* Rating
* FitProfile
* Visit Website
* FitFood
  
## Creator
* Andre Billy
* Christofer Miko Lee
* Jonathan Juliano Wibowo
* Nicholas Tjayadi
* Thendy Rhenaldy
* Willibrordus Bayu
