Algorytm optymalizacji za pomocą roju cząstek<br/>
Każda cząsteczka posiada położenie oraz wektor określający kierunek w jakim się porusza. Dodatkowo zapamiętywane jest najlepsze rozwiązanie znalezione przez każdą z cząstek oraz najlepsze rozwiązanie z całego roju. Na ich podstawie oraz na podstawie prędkości w poprzednim kroku obliczany jest nowy wektor przemieszczenia. 

do wyznaczenia nowej prędkości wykorzystuje wzór:<br/>
v <- iR * v + lR * rl * (l-x) + gR * rg * (g-x)<br/>
Gdzie:<br/>
	v - prędkość cząstki<br/>
	iR - współczynnik bezwładności<br/>
	lR - współczynnik dążenia do najlepszego lokalnego rozwiązania<br/>
	gR - współczynnik dążenia do najlepszego globalnego rozwiązania<br/>
	l - położenie najlepszego lokalnego rozwiązania<br/>
	g - położenie najlepszego globalnego rozwiązania<br/>
	x - położenie cząstki<br/>
	rl, rg - losowe wartości z przedziału <0,1><br/>

następnie, nowe położenie każdej cząsteczki jest obliczane ze wzoru:<br/>
x <- x + Lr * v<br/>
Gdzie:<br/>
	x - położenie cząstki <br/>
	Lr – współczynnik uczenia<br/>
	v – wektor przesunięcia(prędkość cząsteczki)<br/>

# GUI
![gui1](https://github.com/krysztok/inz/blob/main/screen-gui1.png)
![gui2](https://github.com/krysztok/inz/blob/main/screen-gui2.png)
![gui3](https://github.com/krysztok/inz/blob/main/screen-gui3.png)

Program pozwala ustawić oraz sprawdza poprawność wprowadzonych parametrów:
- zakres funkcji (liczba zmiennoprzecinkowa maksymalnie 5-cio cyfrowa z 4-ema miejscami po przecinku, jeśli min > max to wartości zamienią się miejscami)
- liczba cząsteczek (dodatnia liczba całkowita maksymalnie 5-cio cyfrowa)
- liczba iteracji (dodatnia liczba całkowita maksymalnie 5-cio cyfrowa)
- współczynniki (liczby zmiennoprzecinkowe z maksymalnie 6-oma miejscami po przecinku, w zakresie <0,1>)

Po wciśnięciu przycisku "licz" program przeprowadza symulację algorytmu. Następnie, przy pomocy suwaka na dole ekranu, można wybrać daną iterację, po czym na wykresie można obserwować położenie cząsteczek, a po prawej stronie można odczytać współrzędne i wartość każdej cząsteczki oraz najlepsze znalezione rozwiązanie do danej iteracji. Przyciski zapisania (zapisz jako txt i zapisz jako json) na razie nie działają.

# Krótki opis klas
**Klasa Calculations**<br/>
Przechowuje informacje o wszystkich przeprowadzonych iteracjach(współrzędne cząsteczek oraz najlepsze rozwiązanie znalezione do danej iteracji)

**Klasa OpFunction**<br/>
Na razie na sztywno jedna funkcja, później zmienię na interfejs, wtedy każda optymalizowana funkcja będzie musiała zaimplementować 3 funkcje:<br/>
- getvalue - obliczenie wartości funkcji dla danej cząsteczki<br/>
- isBetter - metoda porównująca dwie wartości funkcji; np. jeśli funkcja ma dążyć do minimum, ma zwracać true dla mniejszej wartości<br/>
- getBestSolutionString - potrzebne do wyświetlenia w gui; pokazuje najlepszą możliwą wartość optymalizowanej funkcji

**Klasa Particle**<br/>
Przechowuje właściwości każedej cząśteczki, czyli<br/>
- aktualne położenie<br/>
- aktualną prędkość<br/>
- położenie najlepszego znalezionego rozwiązania przez tę cząsteczkę<br/>
- wartość najlepszego znalezionego rozwiązania przez tę cząsteczkę

**Klasa POS**<br/>
Klasa implementująca alogrytm <br/>
posiada:<br/>
- współczynnik bezwładonści<br/>
- współczynnik dążenia do najlepszego lokalnego rozwiązania<br/>
- współczynnik dążenia do najlepszego globalnego rozwiązania<br/>
- liczbę cząsteczek<br/>
- liczbę iteracji<br/>
- listę cząsteczek<br/>
- informacje o wszystkich poprzednich przeprowadzonch iteracjach<br/>
- zakres funkcji (min, max)<br/>
- współczynnik uczenia<br/>
- optymalizowaną funkcję

metody:<br/>
- initialize - losuje początkowe wartości(położenie i prędkość każdej cząśteczki) oraz znajduje wśród nich najlepsze rozwiązanie<br/>
- runIteration - przeprowadza jedną iterację algorytmu<br/>

**Klasa Vector**<br/>
przechowuje współrzędne cząsteczki<br/>

**Klasa Controller**<br/>
Klasa odpowiadająca za działanie GUI<br/>
metody:<br/>
- initialize - inicjalizuje wszystkie pola(wartości początkowe oraz sprawdzanie poprawności wpisywanych danych) oraz zachowanie suwaka<br/>
- startCalcualtions - ustawia wszystkie wpisane parametry z GUI do klasy POS, a następnie wywołuje określoną ilość iteracji<br/>
