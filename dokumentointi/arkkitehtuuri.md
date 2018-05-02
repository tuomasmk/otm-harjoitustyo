# Arkkitehtuuri

## Rakenne
Sovelluksessa käytetään kolmiportaista rakennetta. Käyttölittymä toimii sovelluslogiikan ja käyttäjän välissä ja dao hoitaa tiedon tallennuksen.

## Käyttöliittymä
Käyttölittymä hyvin pelkistetty. Käyttöliittymä sisältää pelialueen, seuraavan palan näyttämisen ja pistelaskun. Käyttöliittymä ohjaa käyttäjältä tulevat käskyt oikealle luokalle (palojen liikuttaminen ja kääntäminen).

## Sovelluslogiikka
Tetriksen toiminnallisuuden hoitaa sovelluslogiikka, joka pitää huolta tippuvasta palasta, sekä jo paikoilleen jääneistä paloista ja niiden osista. Logiikka poistaa täydet rivit ja liikuttaa niiden päällä olevia rivejä alaspäin tarvittaessa. Sovelluslogiikka myös vastaa pelin etenemisestä tärkeimmän metodinsa advance() avulla. 

Sovelluslogiikan riippuvuuksia on havainnollistettu luokkakaavion avulla:
## Class diagram
![class diagram](https://github.com/tuomasmk/otm-harjoitustyo/blob/master/dokumentointi/Tetris_classdiagram.png "Tetris class diagram")


## Tiedon pysyväistallennus
Tiedot pisteistä pelin päättyessä tallennetaan tietokantaa Score-olioina Data Access Object -suunnittelumallin avulla. Tallennettavia tietoja ovat pelaajan nimi, sekä pelaajaan saamat pisteet. Peliä aloitettaessa tietokannasta haetaan kaikkien aikojen parhaat pisteet, sekä kyseisen pelaajan parhaat pisteet. 

## Päätoiminnallisuudet
Tetriksen päätoiminnallisuutena on tarjota pelikokemus, joka aiheuttaa reaktion "vielä yksi". Testaajien keskuudessa tämä on osoittautunut varsin onnistuneeksi. 

Pelin toiminnallisuutta havainnollistetaan sekvenssikaaviolla, jossa pelaaja on painanut välilyötiä eli pudottanut palan kerralla alas. Pala tarvitsee esimerkissä pudotakseen vain kaksi siirtoa alaspäin.

## Sequence diagram - Space pressed
![sequence diagram - space pressed](https://github.com/tuomasmk/otm-harjoitustyo/blob/master/dokumentointi/tetris_sequence_diagram_spacePressed.png "Tetris sequence diagram")

## Ohjelman kehityskohteita
Palan kääntäminen saattaa tietyissä tilanteissa aiheuttaa palan epäintuitiivista käyttäytymistä, vaikka pala sinänsä kääntyykin aivan oikein.
