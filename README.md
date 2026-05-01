## Descripción
El jugador controla a un héroe dentro de un campo de batalla. El objetivo es llegar al Tesoro Final (Corona Mágica) 
ubicado en la esquina inferior derecha del mapa, evitando enemigos y destruyendo obstáculos en el camino.

## Diagrama de clases simplificado
Interfaces:
  * Inventariable  -  Arma, Recompensa
  * Destruible   -  Personaje, Obstaculo
  * ElementoDinamico - Personaje, Utileria

## Herencia:
  * Personaje  - Jugador
  * Personaje  - Enemigo
  * Recompensa - Tesoro

## Como ejecutar
Clonar o descargar el repositorio
Abrir la carpeta del proyecto en IntelliJ IDEA
Esperar a que Gradle sincronice las dependencias
Abrir Main.java y presionar Run

## Controles
Flechas direccionales para mover al personaje
Dos flechas simultaneas para movimiento diagonal
ESPACIO para destruir un obstaculo adyacente
I para listar el inventario en consola

## Clases del proyecto
El proyecto esta organizado en interfaces, clases base y subclases.
Las interfaces son Inventariable, Destruible y ElementoDinamico. Las 
clases base son Personaje, Recompensa, Arma, Obstaculo, Utileria, CheckPoint, 
Inventario y Nivel. Las subclases que extienden las clases base son Jugador (extiende Personaje), 
Enemigo (extiende Personaje) y Tesoro (extiende Recompensa).

## Link Javadoc
https://effulgent-medovik-958906.netlify.app/
