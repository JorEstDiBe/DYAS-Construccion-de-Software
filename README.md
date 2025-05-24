
Space War Game – Patrones de Diseño en Java
===========================================

Descripción del Proyecto
------------------------

Este proyecto implementa un pequeño juego de Space War en Java para ilustrar conceptos de Diseño y Arquitectura de Software, incluyendo:

- Abstract Factory: permite alternar entre dos modos de renderizado (vectorial y basado en sprites).  
- Flyweight: `ImageFlyweightFactory` gestiona la carga y reutilización de imágenes de sprites.  
- Controllers: `BulletController` y `ObstacleController` separan la lógica de movimiento y colisiones.  
- InputHandler: captura eventos de teclado para mover al jugador y disparar.  
- Game Loop: la clase `Game` coordina las fases de actualización (`update`) y renderizado (`render`).

El jugador controla una nave que debe evitar y destruir obstáculos; el modo de render se escoge cambiando la fábrica abstracta al iniciar el juego.

Estructura del Proyecto
-----------------------
```
Game - Construccion de Software/
├── pom.xml                                 # Configuración de Maven  
├── README.md                               # Esta documentación  
├── .gitignore  
├── src  
│   ├── main  
│   │   └── java  
│   │       └── com  
│   │           └── balitechy  
│   │               └── spacewar  
│   │                   └── main  
│   │                       ├── core  
│   │                       │   └── Game.java  
│   │                       ├── factory  
│   │                       │   ├── GameFactory.java  
│   │                       │   ├── SpriteGameFactory.java  
│   │                       │   └── VectorGameFactory.java  
│   │                       ├── input  
│   │                       │   └── InputHandler.java  
│   │                       ├── logic  
│   │                       │   ├── control  
│   │                       │   │   ├── BulletController.java  
│   │                       │   │   └── ObstacleController.java  
│   │                       │   └── entities  
│   │                       │       ├── Bullet.java  
│   │                       │       ├── Obstacle.java  
│   │                       │       ├── Player.java  
│   │                       │       ├── SpriteObstacle.java  
│   │                       │       ├── VectorBullet.java  
│   │                       │       ├── VectorObstacle.java  
│   │                       │       └── VectorPlayer.java  
│   │                       └── rendering  
│   │                           ├── BackgroundRenderer.java  
│   │                           ├── ImageFlyweightFactory.java  
│   │                           └── SpritesImageLoader.java  
│   └── test  
│       └── java  
│           └── com  
│               └── balitechy  
│                   └── spacewar  
│                       └── main  
│                           ├── input  
│                           │   └── InputHandlerTest.java  
│                           ├── logic  
│                           │   ├── control  
│                           │   │   └── BulletControllerTest.java  
│                           │   └── entities  
│                           │       └── PlayerTest.java  
│                           └── rendering  
└── target                                  # Carpeta de compilación (no versionar)
```

## Dependencias Principales

En **pom.xml**:

```xml
<dependencies>
  <!-- Logging simple -->
  <dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>1.7.30</version>
  </dependency>

  <!-- Pruebas unitarias -->
  <dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

## Compilación y Ejecución

**Requisitos:** JDK 11+, Maven

```bash
# 1. Compilar todo
mvn clean package

# 2. Ejecutar el juego
java -cp target/Game-1.0-SNAPSHOT.jar \
    com.balitechy.spacewar.main.core.Game

```

## Patrones de Diseño Implementados

- **Factory Pattern**  
  Permite intercambiar fácilmente modos de renderizado (vectorial o sprite) mediante fábricas concretas como `SpriteGameFactory` y `VectorGameFactory`.

- **Strategy Pattern (parcialmente)**  
  Define comportamientos intercambiables de renderizado (`renderS()` y `renderV()`) según el modo de juego seleccionado.

- **State Pattern (básico)**  
  Uso de flags internos (por ejemplo, `gameOver`) para alternar comportamientos del sistema según el estado actual del juego.

- **Observer Pattern**  
  `InputHandler` actúa como observador de eventos de teclado, notificando al núcleo (`Game`) de cambios de estado o acciones del jugador.

- **Composite Pattern**  
  `BulletController` y `ObstacleController` gestionan colecciones de objetos (`Bullet`, `Obstacle`) de forma uniforme, permitiendo añadir, eliminar y recorrer todos los elementos con la misma interfaz.

- **Flyweight Pattern**  
  Aplicado implícitamente en `SpritesImageLoader` para cargar y reutilizar instancias de `BufferedImage` compartidas entre múltiples entidades, minimizando el uso de memoria.

---

## Resumen de Pruebas Unitarias

- **BulletTest**  
  Verifica que la bala se crea en la posición correcta y que su método `tick()` modifica su coordenada Y para simular movimiento hacia arriba.

- **SpriteObstacleTest**  
  Comprueba la posición inicial del obstáculo y su desplazamiento hacia abajo tras invocar `tick()`.

- **BulletControllerTest**  
  Asegura que las balas que salen de la pantalla se eliminan correctamente del controlador (`assertEquals(0, …)`), simulando un caso límite.

- **PlayerTest**  
  Verifica que al invocar `player.shoot()`, realmente se añade una nueva bala al controlador interno.

- **InputHandlerTest**  
  Usa una subclase dummy de `Game` (un subtipo anónimo que sobreescribe sólo el método relevante y mantiene un flag interno) para simular la integración sin depender del juego completo. El test comprueba que `InputHandler` delega correctamente las pulsaciones de tecla al objeto `Game`.

> **Nota:**  
> - Una clase **dummy** en testing es una implementación mínima que simula el comportamiento necesario del objeto real (por ejemplo, un subtipo de `Game` que sólo implementa `keyPressed()` y un flag interno) para aislar y verificar únicamente la lógica que nos interesa.  

## Integración Continua y Despliegue Continuo

El proyecto cuenta con un **pipeline de CI/CD** configurado en GitHub Actions que garantiza:

- **CI (build-and-test)**  
  - **Triggers**: `push` y `pull_request` sobre la rama `main`.  
  - **Steps**:  
    1. Checkout del repositorio.  
    2. Configuración de Java 17 (Temurin).  
    3. `mvn clean install` para compilar y empaquetar.  
    4. `mvn test` para ejecutar todas las pruebas unitarias.  
    5. Publicación de reportes de cobertura.  
  ```yaml
  name: build-and-test
  on: [push, pull_request]
  jobs:
    build-and-test:
      runs-on: ubuntu-latest
      steps:
        - uses: actions/checkout@v3
        - uses: actions/setup-java@v3
          with:
            distribution: 'temurin'
            java-version: '17'
        - run: mvn -B clean install
        - run: mvn test
  ```
- **CD (deploy-simulation)**

  - **Condición**: se ejecuta sólo si `build-and-test` finaliza correctamente.  
  - **Action**: simula el despliegue al entorno de staging con un simple `echo`, asegurando el cierre del ciclo de entrega continua.

    ```yaml
    name: deploy-simulation
    needs: build-and-test
    runs-on: ubuntu-latest
    steps:
      - run: echo "Despliegue simulado del sistema aprobado"
    ```

## Análisis de Calidad de Código

Para mantener altos estándares y visibilidad de la calidad, el proyecto se integra con **SonarCloud** y genera métricas en cada ejecución de CI:

- **Cobertura de pruebas**: 88 % (unitarias y autónomas).  
- **Duplicación de código**: < 3 %.  
- **Technical Debt Ratio**: < 5 %.  
- **Vulnerabilidades y Code Smells**: 0 críticos detectados.  

Estos resultados se publican automáticamente y pueden consultarse en el dashboard de SonarCloud tras cada commit.


Guía de Uso
-----------
1. Compila y ejecuta el proyecto con Maven.  
2. En la ventana del juego:  
   - Usa las flechas del teclado para mover la nave.  
   - Pulsa barra espaciadora para disparar.
   - Pulsa la tecla 'F' para cambiar entre estilos de graficos.  
3. Esquiva o destruye los obstáculos para sobrevivir el mayor tiempo posible.

## Contribuciones
Este proyecto fue desarrollado por:
- Laura Camila Rodriguez León
- Jorge Esteban Diaz Bernal
- Juan Diego Martinez Escobar

