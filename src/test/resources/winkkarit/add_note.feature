Feature: Käyttäjä voi lisätä uuden vinkin

    Scenario: Käyttäjä voi lisätä uuden vinkin
        Given   Käyttäjä on sivulla josta vinkki lisätään
        When    Kirjoittaja "Robert Martin" ja otsikko "Clean Code: A Handbook of Agile Software Craftmanship"
        Then    Uusi vinkki luodaan
