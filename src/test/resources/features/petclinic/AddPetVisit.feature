Feature: Add Pet Visit

  @pet
  Scenario Outline: Add new visit to a pet
    Given User goes to Find Owners
    When User clicks Find Owner
    Then User clicks first owner
    Then User clicks Add Visit in first pet
    Then User types a description
    Then User clicks Add Visit
    Then New Visit appears in visit list