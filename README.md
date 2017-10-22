# INPAS-Demo

### Описание

Представьте, что есть входящий массив, описывающий ландшафт некой 2D стены.

Теперь представьте, что прошел сильный дождь и все возможные ямы в стене заполнены водой.

Программа на вход получает массив int, описывающий
ландшафт, а на выходе возвращает значение int с объемом воды с ямах.

Пример:<br>
Входыне данные: 3,2,4,1,2<br>
Результат: 2<br>
Графическое изображение:

O - пустота<br>
M - стена<br>
W - вода после дождя

OOMOM - - - - - - - - - - - - - - - - - - - - - - - -> OOMOM<br>
MOMOO - - - - - - - - - - - - - - - - - - - - - - - -> MWMOO<br>
MMMOM - - - - - - - - - - - - - - - - - - - - - - - -> MMMWM<br>
MMMMM - - - - - - - - - - - - - - - - - - - - - - - -> MMMMM<br>

### Cборка
mvn install
