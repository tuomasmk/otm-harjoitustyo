# otm-harjoitustyö - Tetris

Harjoitustyön aiheena on perinteinen tetris. Tetriksessä paloja tippuu pelialueen yläreunasta ja ne tulee järjestää täysiin riveihin. Täydet rivit poistuvat tuoden pisteitä ja tehden tilaa uusille.

Ladattava versio: [tetris_v1.5.jar](https://github.com/tuomasmk/otm-harjoitustyo/releases/tag/v1.5)

## Dokumentaatio

[käyttöohje](https://github.com/tuomasmk/otm-harjoitustyo/blob/master/dokumentointi/kayttoohje.md)

[arkkitehtuuri](https://github.com/tuomasmk/otm-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[vaatimusmäärittely](https://github.com/tuomasmk/otm-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)

[testausdokumentti](https://github.com/tuomasmk/otm-harjoitustyo/blob/master/dokumentointi/testausdokumentti.md)

[työaikakirjanpito](https://github.com/tuomasmk/otm-harjoitustyo/blob/master/dokumentointi/tuntikirjanpito.md)

## Komentorivitoiminnot
### Testaus
Testit suoritetaan komennolla

`mvn test`

Testikattavuusraportti luodaan komennolla

`mvn test jacoco:report`
### Suoritettava versio
Ohjelmasta luodaan suoritettava versio komennolla

`mvn package`

### Checkstyle
Chekckstyle tarkstuksen voi suorittaa komennolla

`mvn checkstyle:checkstyle`
### Javadoc
JavaDoc luodaan komennolla

`mvn javadoc:javadoc`
