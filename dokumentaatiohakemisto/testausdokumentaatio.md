#Testausdokumentaatio

Game-luokan testit ovat puutteelliset, koska aika loppui kesken.
Muutenkin Game-luokan metodi, jossa tarkistetaan törmääkö pallo rakentumassa olevaan seinään ja toimitaan sen mukaan, on erittäin ruma.
Kuitenkin peliä on testattu monta kertaa manuaalisesti, ja tällä hetkellä se toimii hyvin.


## Havaittuja bugeja
 - Peliä manuaalisesti testattaessa ilmeni pari kertaa bugi, jossa rakentunut seinä ei muuttunutkaan seinäksi. En ehtinyt testata Wallbuilderia niin perinpohjaisesti, että osaisin sanoa mistä se johtuu.

 - Ball-luokalle kirjoitetut testit, joissa testataan pallon törmäämistä sisänurkkaan, eivät mene läpi. Tätä on debuggattu erittäin perusteellisesti, ja syy on se, että CollisionDetectorin getAllCollisionsAlongTrace palauttaa jostain syystä tyhjän listan.

 - Koulun koneilla ajassa käyttöliittymän Sidebar saattaa näyttää erilaiselta ja osa tekstistä saattaa leikkautua pois.
