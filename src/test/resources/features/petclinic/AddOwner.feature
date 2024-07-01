@cucumber
Feature: Add Owner

  @owner
  Scenario Outline: Add new owner
    Given User goes to Find Owners
    When User clicks Add Owner Button
    Then User types first name <fname>
    Then User types last name <lname>
    Then User types address <address>
    Then User types city <city>
    Then User types telephone <telephone>
    When User clicks Add Owner
    Then New Owner appears in owner list

    Examples: 
      | fname    | lname    | address  | city     | telephone  |
      | "name1"  | "name11" | "spain"  | "madrid" | 1234567890 |
      | "name2"  | "name12" | "uk"     | "london" | 1234567890 |
 