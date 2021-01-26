# inz
![alt text](https://github.com/krysztok/inz/blob/main/screen-gui1.png)
![alt text](https://github.com/krysztok/inz/blob/main/screen-gui2.png)

Algorytm optymalizacji za pomocą roju cząstek
Każda cząsteczka posiada położenie oraz wektor określający kierunek w jakim się porusza. Dodatkowo zapamiętywane jest najlepsze rozwiązanie znalezione przez każdą z cząstek oraz najlepsze rozwiązanie z całego roju. Na ich podstawie oraz na podstawie prędkości w poprzednim kroku obliczany jest nowy wektor przemieszczenia. 

do wyznaczenia nowej prędkości wykorzystuje wzór:
v <- iR * v + lR * rl * (l-x) + gR * rg * (g-x)
Gdzie:
	v - prędkość cząstki
	iR - współczynnik bezwładności
	lR - współczynnik dążenia do najlepszego lokalnego rozwiązania
	gR - współczynnik dążenia do najlepszego globalnego rozwiązania
	l - położenie najlepszego lokalnego rozwiązania
	g - położenie najlepszego globalnego rozwiązania
	x - położenie cząstki
	rl, rg - losowe wartości z przedziału <0,1>

następnie, nowe położenie każdej cząsteczki jest obliczane ze wzoru:
x <- x + Lr * v
Gdzie:
	x - położenie cząstki 
	Lr – współczynnik uczenia
	v – wektor przesunięcia(prędkość cząsteczki)


#Krótki opis klas
klasa Calculations
przechowuje informacje o wszystkich przeprowadzonych iteracjach(współrzędne cząsteczek oraz najlepsze rozwiązanie znalezione do danej iteracji)

klasa OpFunction
na razie na sztywno jedna funkcja, późnie zmienię na interfejs, wtedy każda optymalizowana funkcja będzie musiała zaimplementować 3 funkcję:
-getvalue - obliczenie wartości funkcji dla danej cząsteczki
-isBetter - metoda porównująca dwie wartości funkcji; np jeśli funkcja ma dążyć do minimum, ma zwracać true dla mniejszej wartości
-getBestSolutionString - potrzebne do wyświetlenia w gui; pokazuje najlepszą możliwą wartość optymalizowanej funkcji

klasa Particle
przechowuje właściwości każedej cząśteczki, czyli
-aktualne położenie
-aktualną prędkość
-położenie najlepszego znalezionego rozwiązania przez tę cząsteczkę
-wartość najlepszego znalezionego rozwiązania przez tę cząsteczkę

klasa POS
klasa implementująca alogrytm 
posiada
-współczynnik bezwładonści
-współczynnik dążenia do najlepszego lokalnego rozwiązania
-współczynnik dążenia do najlepszego globalnego rozwiązania
-liczbę cząsteczek
-liczbę iteracji
-listę cząsteczek
-informacje o wszystkich poprzednich przeprowadzonch iteracjach
-zakres funkcji (min, max0)
-współczynnik uczenia
-optymalizowaną funkcję

metody:
-initialize - losuje początkowe wartości(położenie i prędkość każdej cząśteczki) oraz znajduje najlepsze globalne rozwiązanie
-runIteration - przeprowadza jedną iterację algorytmu

klasa Vector
przechowuje współrzędne cząsteczki

klasa Controller
klasa odpowiadająca za działanie GUI
metody
-initialize - inicjalizuje wszystkie pola(wartości początkowe oraz sprawdzanie poprawności wpisywanych danych) oraz zachowanie suwaka
-startCalcualtions - ustawia wszystkie wpisane parametry z GUI do klasy POS, a następnie wywołuje określoną ilość iteracji

#ToDo
-dodanie funkcji do optymalizacji(na razie jest tylko jedna na sztywno)
-poprawienie wyświetlania punktów
-dodanie zapisywania do txt i jsona
-uporządkowanie kodu