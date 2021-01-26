# inz
![alt text](https://github.com/krysztok/inz/blob/main/screen-gui1.png)
![alt text](https://github.com/krysztok/inz/blob/main/screen-gui2.png)

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


# Krótki opis klas
klasa Calculations<br/>
przechowuje informacje o wszystkich przeprowadzonych iteracjach(współrzędne cząsteczek oraz najlepsze rozwiązanie znalezione do danej iteracji)

klasa OpFunction<br/>
na razie na sztywno jedna funkcja, późnie zmienię na interfejs, wtedy każda optymalizowana funkcja będzie musiała zaimplementować 3 funkcję:<br/>
- getvalue - obliczenie wartości funkcji dla danej cząsteczki<br/>
- isBetter - metoda porównująca dwie wartości funkcji; np jeśli funkcja ma dążyć do minimum, ma zwracać true dla mniejszej wartości<br/>
- getBestSolutionString - potrzebne do wyświetlenia w gui; pokazuje najlepszą możliwą wartość optymalizowanej funkcji

klasa Particle<br/>
przechowuje właściwości każedej cząśteczki, czyli<br/>
- aktualne położenie<br/>
- aktualną prędkość<br/>
- położenie najlepszego znalezionego rozwiązania przez tę cząsteczkę<br/>
- wartość najlepszego znalezionego rozwiązania przez tę cząsteczkę

klasa POS<br/>
klasa implementująca alogrytm <br/>
posiada<br/>
- współczynnik bezwładonści<br/>
- współczynnik dążenia do najlepszego lokalnego rozwiązania<br/>
- współczynnik dążenia do najlepszego globalnego rozwiązania<br/>
- liczbę cząsteczek<br/>
- liczbę iteracji<br/>
- listę cząsteczek<br/>
- informacje o wszystkich poprzednich przeprowadzonch iteracjach<br/>
- zakres funkcji (min, max0)<br/>
- współczynnik uczenia<br/>
- optymalizowaną funkcję

metody:<br/>
- initialize - losuje początkowe wartości(położenie i prędkość każdej cząśteczki) oraz znajduje najlepsze globalne rozwiązanie<br/>
- runIteration - przeprowadza jedną iterację algorytmu<br/>

klasa Vector<br/>
przechowuje współrzędne cząsteczki<br/>

klasa Controller<br/>
klasa odpowiadająca za działanie GUI<br/>
metody:<br/>
- initialize - inicjalizuje wszystkie pola(wartości początkowe oraz sprawdzanie poprawności wpisywanych danych) oraz zachowanie suwaka<br/>
- startCalcualtions - ustawia wszystkie wpisane parametry z GUI do klasy POS, a następnie wywołuje określoną ilość iteracji<br/>

# ToDo<br/>
- dodanie funkcji do optymalizacji(na razie jest tylko jedna na sztywno)<br/>
- poprawienie wyświetlania punktów<br/>
- dodanie zapisywania do txt i jsona<br/>
- uporządkowanie kodu