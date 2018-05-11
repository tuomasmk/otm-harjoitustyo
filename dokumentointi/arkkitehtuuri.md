# Arkkitehtuuri

## Rakenne
Sovelluksessa käytetään kolmiportaista rakennetta, jossa käyttöliittymä toimii sovelluslogiikan ja käyttäjän välissä ja dao hoitaa tiedon tallennuksen.

## Käyttöliittymä
Käyttöliittymä on hyvin pelkistetty. Käyttöliittymä sisältää pelialueen, seuraavan palan näyttämisen ja pistelaskun. Käyttöliittymä ohjaa käyttäjältä tulevat käskyt oikealle luokalle (palojen liikuttaminen ja kääntäminen). Käyttöliittymää täydentävät valikko ja valintaikkunat.

## Sovelluslogiikka
Tetriksen toiminnallisuuden hoitaa sovelluslogiikka, joka pitää huolta putoavasta palasta, sekä jo paikoilleen jääneistä paloista ja niiden osista. Logiikka poistaa täydet rivit ja liikuttaa niiden päällä olevia rivejä alaspäin tarvittaessa. Sovelluslogiikka myös vastaa pelin etenemisestä tärkeimmän metodinsa advance() avulla. 

Sovelluslogiikan riippuvuuksia on havainnollistettu luokkakaavion avulla:
## Luokkakaavio
![class diagram](https://github.com/tuomasmk/otm-harjoitustyo/blob/master/dokumentointi/class_diagram2.png "Tetris class diagram")


## Tiedon pysyväistallennus
Tiedot pisteistä pelin päättyessä tallennetaan tietokantaa Score-olioina Data Access Object -suunnittelumallia noudattaen. Tallennettavia tietoja ovat pelaajan nimi sekä pelaajaan saamat pisteet. Peliä aloitettaessa tietokannasta haetaan kaikkien aikojen parhaat pisteet, sekä kyseisen pelaajan parhaat pisteet. Ohjelma luo tarvittaessa tietokannan suoritushakemiston juureen.

## Päätoiminnallisuudet
Tetriksen päätoiminnallisuutena on tarjota pelikokemus, joka aiheuttaa reaktion "vielä yksi". Testaajien keskuudessa tämä on osoittautunut varsin onnistuneeksi. 

Pelin toiminnallisuutta havainnollistetaan sekvenssikaaviolla, jossa pelaaja on painanut välilyötiä eli pudottanut palan kerralla alas. Pala tarvitsee esimerkissä pudotakseen vain kaksi siirtoa alaspäin.

## Sequence diagram - Space pressed
![sequence diagram - space pressed](https://github.com/tuomasmk/otm-harjoitustyo/blob/master/dokumentointi/tetris_sequence_diagram_spacePressed.png "Tetris sequence diagram")

## Ohjelman kehityskohteita
Käyttöliittymä on toteutettu täysin yhdessä luokassa. Luokan sisällä on kuitenkin käytetty metodeja hoitamaan pienempiä tehtäviä. Käyttölittymää voisi yrittää jakaa useampaan luokkaan, toisaalta käyttöliittymä muodostuu vain yhdestä ikkunasta ja kahdesta dialogista.

Myös pelin logiikka on kokonaisuudessaan yhdessä suurehkossa tiedostossa. Tiedosto on kuitenkin jaettu pieniin metodeihin, joista jokainen hoitaa oman tehtävänsä. Isoin metodi on pelin etenemisestä vastaava advance(), joka sekin kutsuu muita metodeja. Kokonaisuutta voisi yrittä jakaa useampaan luokkaan.

DAO-rajapinta tuntuu turhalta, koska käytössä on vain yksi sitä käyttävä luokka.
