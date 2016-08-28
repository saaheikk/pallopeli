Aihe: 2D-tietokonepeli, jossa on tarkoituksena vangita seinistä poukkoileva pallo (tai joissain versioissa/tasoilla ehkä usempi pallo) seinien sisään, niin että pallo ei pääse enää liikkumaan. Seiniä luodaan hiirtä klikkaamalla. Luotu seinä laajenee pala palalta klikkauskohdasta ylös ja alas tai vasemmalle ja oikealle (pelaajan valinnan mukaan), kunnes törmää pelialueen seiniin tai palloon. Jos luotu seinä pääsee laajenemaan pelialueen seiniin asti, niin alue/alueet joiden sisälle ei jää palloa poukkoilemaan täyttyvät "seinämassalla". Tavoite on siis täyttää koko pelialue tällä "seinämassalla" ja saartaa pallo(t). Jos klikkaa vahingossa palloa, niin peli päättyy.

Pelissä on (tarkoitus olla) eri tasoja, ja vaikeammilla tasoilla pallon nopeus on suurempi ja/tai palloja on monta. Pelissä on käytännössä vain yksi toiminto, eli seinän rakentaminen hiirtä klikkaamalla. Lisäksi pelaaja valitsee mihin suuntaan seinä lähtee rakentumaan.

Päivitys: Pelaajalla on alussa esim. 3 elämää, jotka pelaaja yksi kerrallaan menettää, jos tekee virheitä. 
Päivitys2: Pelaajalla on alussa vain yksi elämä, ts. peli loppuu heti ensimmäisestä virheestä.

Käyttäjät: Kuka tahansa, joka haluaa pelata hauskaa peliä.

Toiminnot:
* Rakennussuunnan valinta (pysty/vaaka)
* Seinän rakentaminen valittuun suuntaan
	* Ei onnistu jos klikkaa vahingossa palaa, jossa pallo parhaillaan on (vain osittainkin), tällöin pelaaja menettää yhden "elämän"
	* Jos seinä pääsee rakentumaan loppuun asti, ilman palloa jäänyt alue täyttyy seinämassalla
	* Päivitys: jos seinä ei pääse rakentumaan loppuun asti, niin pelaaja menettää yhden "elämän"
	* Päivitys: jos pallo koskee yhtäkin rakentumassa olevaa seinäpalaa, niin koko rakentumassa oleva seinäosuus häviää ja pelaaja menettää "elämän" 
* Pelin voittaminen/häviäminen
	* Voittaminen kun tietty prosenttiosuus pelialueesta on muuttunut seinäksi (esim. 75%)
	* Häviäminen kun "elämät" loppuvat

Mallipeli: http://www.freeonlinepcgames.net/play/bounce-build-walls-to-capture-balls-cover-75-of/flash-game/

![Määrittelyvaiheen luokkakaavio](https://github.com/saaheikk/pallopeli/tree/master/dokumentaatiohakemisto/maarittelyvaiheenluokkakaavio.png)
