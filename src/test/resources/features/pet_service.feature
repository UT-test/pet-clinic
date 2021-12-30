@pet_service
Feature: Pet Service Feature

  Background: Sample General Preconditions Explanation
    Given There is some predefined pet types like "dog"

  Scenario: Owner is found using owner id
    Given There is a pet owner with id 2
    When Owner is looked up in pet service
    Then The owner is returned successfully


  Scenario: New pet is added to owner
    Given There is a pet owner with id 2
    When Request for a new pet arrives
    Then The new pet is returned successfully
    And The new pet is saved in owners pets correctly


  Scenario: Pet is found using pet id
    Given There is a pet with id 11
    And The pet has owner with id 2
    Then The pet is returned successfully

  Scenario: Pet is found using pet id
    Given There is a pet owner with id 2
    And There is a pet with id 11
    When Owner performs save pet service to add a pet to his list
    Then The pet is saved into pet cache successfully
