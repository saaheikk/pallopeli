#Sovelluslogiikka

Game-luokka on pelin sovelluslogiikan keskus. Pohjana on pelilooppi, jonka pyörimsestä vastaa Gamen yliluokka Timer ja sen actionPerformed-metodi.
Game sisältää pelin peruspalaset, eli pallon (Ball) ja laudan (Board). Board sisältää edelleen paloja (Piece), jotka on tallennettu 2-ulotteiseksi taulukoksi.
Pelissä on kaksi perustoiminnallisuutta, seinän rakentuminen ja pallon liikkuminen. Pallon liikkuminen hoidetaan CollisionDetector-luokan avulla. Ball sisältää yhden CollisionDetectorin, joka palauttaa Collision-olioita, ja pallo osaa liikkua Collisionin antamien tietojen mukaan. 
Seinien rakentamisesta vastaa Game-luokalla oleva Wallbuilder-olio. Wallbuilder sisältää pelilaudan (Board), johon se rakentaa seiniä. Kun seinän rakentuminen loppuu (tavalla tai toisella), niin Wallbuilder "refreshaa" kaikki attribuuttinsa, Boardia lukuunottamatta.
Seinien rakentumisen ja pallon liikkumisen välisestä kommunkaatioista vastaa Game-luokka. Joka kerta kun Wallbuilder rakentaa yhden palan seinää, niin Gamessa tarkistetaan, että osuuko pallo rakentuneeseen palaan ja toimitaan sen mukaan.


#Käyttöliittymä

Käyttöliittymän  ulkoasun keskus on UserInterface-luokka. UserInterface sisältää Swingin JFrame-olion, joka huolehtii peli-ikkunan luomisesta.
UserInterfacella on kaksi päivitettävää (Updateable-rajapinta) attribuuttia: PaintingCanvas ja Sidebar. 
PaintingCanvas piirtää pelin grafiikat (palat, niiden muutokset, sekä pallon liikkuminen). Piirtäminen hoidetaan Drawable-rajapinnan kautta. Ideana on, että pelin peruskomponenteilla (pallo, pelilauta, pallo) on vastaavat Drawable-rajapinnan toteuttavat luokat, esim. Pieceä vastaa DrawablePiece.
Sidebar on palkki peli-ikkunan oikeassa laidassa, ja siihen päivitetään pelitilanne, elämien määrä, sekä rakennussuunta.
Kummatkin päivitettävät luokat sisältävät viitteen Gameen, jolta ne saavat tiedon miten päivittyä.

Varsinaisen käyttäjän kanssa kommunikoinnin hoitavat luokat GameKeyListener ja GameMouseListener. Nimensä mukaisesti ne kuuntelevat hiirtä ja näppäimistöä. Molemmat sisältävät viitteen Gameen, joten ne muokkaavat suoraan Gamen paramtereja (jos muokkaaminen on sillä hetkellä sallittua).
