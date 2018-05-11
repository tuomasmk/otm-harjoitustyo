# Testausdokumentti
Ohjelmaa on testattu automatisoidusti yksikkö- ja integraatiotestein JUnitin avulla. Lisäksi ohjelmaa on testattu manuaalisesti käyttämällä sen ominaisuuksia mahdollisimman laajasti.

## Yksikkö- ja integraatiotestaus
### Sovelluslogiikka ja komponentit
Testauksen pääosassa on ollut sovelluslogiikan testaaminen, joka luonteensa vuoksi oli lähinnä integraatiotestausta.Tätä ennen yksittäisten komponenttien oikeaa toimintaa oli tarkasteltu yksikkötestein. 

### Dao
Tiedon tallentamisesta huolehtivaa DAO-luokkaa testattiin itsenäisesti. Testaus toteutettiin erillisessä testaustietokannassa.

### Testauskattavuus
Käyttöliittymää lukuunottamatta sovelluksen testauksen rivikattavuus on 84 % ja haarautumakattavauus 61 %. Haarautumakattavuuden matalaa arvoa selittää manuaalisen testaamisen kohdistuminen tässä testaamatta jääneisiin osiin.

![testauskattavuus](https://github.com/tuomasmk/otm-harjoitustyo/blob/master/dokumentointi/testauskattavuus.png "Testauskattavuus")

## Järjestelmätestaus
Sovellusta on testattu Linux- ja Windows-ympäristöissä käynnistämällä ohjelma kuvakkeesta (Windows) tai kuvakkeesta ja komentoriviltä (Linux). Ohjelma on joutunut ensimmäisellä suorituskerralla luomaan tarvitsemansa tietokannan ja sen jälkeen tietokanta on ollut olemassa. Kaikkia käyttöohjeessa kuvattuja toiminnallisuuksia on testattu.

Järjestelmätestauksessa on kiinnitetty erityistä huomiota ohjelman riveihin, joita on ollut vaikea testata yksikkö- ja integraatiotesteillä. Näitä ovat olleet muun muassa:
- Palojen kääntäminen paikoissa, joissa ne eivät mahdu kääntymään. 
- Palojen kääntäminen seinän tai toisten palojen läheisyydessä, jolloin niitä täytyy liikuttaa. 
- Pelin lopetusehdon (uusi pala ei mahdu pelialueelle) testaaminen.

## Sovellukseen jääneet laatuongelmat
Sovellus ei aina piirrä uutta palaa, mikäli se ei enää mahtuisi pelialueelle.
