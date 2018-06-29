# 2017_S2_Group_40

Wanneer je op de hoofdpagina bent heb je de keuze om op 5 knoppen te drukken, Play, Highscore, Theme, Help en About. 
Hieronder worden deze onderdelen meer in detail besproken.

# Start #
Make sure to change the endpoint in the bridge.js file so it corresponds with the URL the site launches at.

# Play #
Wanneer je op play klikt wordt je doorgestuurd naar een settings pagina. Deze pagina geeft je de optie om een type sudoku te kiezen.
De keuzes zijn 4x4, 6x6, 9x9 en 12x12. Vervolgens een optie om de moeilijkheidsgraad in te stellen. 
Als je op play now klikt kom je op de sudoku pagina terecht. 
Met het pijltje linksboven op het scherm kan je terug keren naar de hoofdpagina.

Op de sudokupagina wordt een sudoku gegenereerd,
wanneer je op een leeg vakje klikt kan je aan de hand van de knoppenbalk  een cijfer invoeren in de sudoku.
Met de gom functie kan je ingevulde vakjes wissen. 
Met de potlood functie kan je notities maken op de sudoku om zo de mogelijke waarden op tijdelijk op te schrijven.
Op deze pagina wordt ook de tijd en moeilijkheidsgraad afgebeeld.
Wanneer je de sudoku oplost krijg je een scherm te zien de je een voor en achternaam laat in vullen en een link naar de highscore page.

Vervolgens zijn er nog 5 mogelijke functies op deze pagina, home, pauze, herstarten, hints en een game setup.

De homeknop laat je terugkeren naar de hoofdpagina. 

Wanneer je op de pauzeknop klikt stopt de tijd met lopen en kan je de sudoku niet meer zien.
Het pauzemenu geeft je twee opties, het spel hervatten of help. Als je op help klikt krijg je de help pagina te zien in een pop-up.
De herstarten knop genereerd een volledig nieuwe sudoku.

De hintsknop geeft je 4 mogelijkheden. De eerste mogelijkheid is om de mogelijke waarden van het aangeklikte veld te tonen.
Deze worden afgebeeld boven de sudoku. Als tweede een optie om alle in  cellen alle mogelijke waarden in te vullen als notitie.
Als derde optie een functie die de huidige sudoku oplost. Uiteraard krijg je geen score als je deze functie gebruikt.
Als laatste een optie om het volgende lege vakje in de sudoku op te lossen en in te vullen.

De game setuppagina geeft je 4 keuzes. Validate sudoku bekijkt alle ingevulde velden en controleert als de waarden juist ingevuld zijn.
Inden je een fout gemaakt hebt krijg je de keuze om te zien waar je fout zit.
Reset sudoku wist alle ingevulde velden en laat je opnieuw beginnen met dezelfde sudooku.
Set selection color laat je de het kleur veranderen van de geselecteerde cel aan de hand van een color picker.
De rij, kolom en blok waarin de geselecteerde cel zich bevindt worden ook aangeduid. Dit kleur is standaard groen.
De reset color selection stelt het selectiekleur terug in op groen. 

# Highscore #
Op de highscorepagina krijg je de scores te zien van de mensen die al eens gespeeld hebben op hetzelfde apparaat.
De highscores worden onderverdeeld in 3 categorieën, easy, normal en hard.
De score wordt berekend aan de hand van deze moeilijkheidsgraad en de grootte van de sudoku.
Daarna wordt deze opgeslaan in de local storage. Wanneer je op het pijltje linksboven klikt keer je terug naar de hoofdpagina.

# Theme #
Op de themepagina kan je zelf een thema kiezen voor de sudoku.
Er zijn 5 verschillende thema’s, normal, 8-bit, sand, stonehenge en stones.
Wanneer je op een van deze thema’s drukt zal de achtergrond veranderen.
Het pijltje linksboven laat je terugkeren naar de hoofdpagina.

# Help #
De helppagina toont de regels van sudoku en een beknopte handleiding van hoe je best start aan een sudoku.
Het pijltje linksboven laat je terugkeren naar de hoofdpagina.

# About # 
De aboutpagina geeft een beetje uitleg over wie we zijn . Het pijltje linksboven laat je terugkeren naar de hoofdpagina.
