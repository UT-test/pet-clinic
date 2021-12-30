Feature: Pet Service Feature

  Background: Sample General Preconditions Explanation
    Given There is some predefined pet types like "dog"

  Scenario: Owner is found using owner id
    Given There is a pet owner with id 2
    When Owner is looked up in pet service
    Then The owner is returned successfully
