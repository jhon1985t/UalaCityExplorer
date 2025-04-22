# UalaCityExplorer

Aplicación desarrollada en Kotlin con Jetpack Compose como parte del desafío técnico de Ualá. Permite buscar entre más de 200,000 ciudades usando un filtro por prefijo optimizado, marcar favoritas y visualizarlas en un mapa interactivo.

---

## Características

- Búsqueda de ciudades por prefijo (case-insensitive)
- Carga de datos desde archivo JSON remoto
- Marcar y filtrar ciudades favoritas
- Visualización de ciudades en Google Maps
- UI adaptable a orientación (portrait vs landscape)
- Búsqueda optimizada para rendimiento en tiempo real
- Persistencia de favoritos usando DataStore
- Pruebas unitarias y de interfaz (UI Test)

---

## Arquitectura

El proyecto sigue **Clean Architecture** con separación en 3 capas:

presentation/ → Jetpack Compose + ViewModels (MVVM) 
domain/ → UseCases + Modelos puros 
data/ → DataSources, Repositorios, DTOs, Retrofit, DataStore


Además, se utiliza:

- **Jetpack Compose** para UI reactiva
- **Hilt** para inyección de dependencias
- **DataStore** para persistencia de favoritos
- **Retrofit + Gson** para carga de datos remotos
- **Google Maps Compose** para mapas interactivos

---

## Búsqueda por prefijo optimizada

La búsqueda está optimizada mediante **índice en memoria (`Map<Char, List<City>>`)**, el cual se construye al inicio. Esto permite responder con alta eficiencia a filtros por inicial, evitando recorrer la lista completa (~200k ciudades).

Además, los resultados están ordenados alfabéticamente (`city.name.lowercase()`).

---

## UI Adaptativa

- **Portrait** → Pantallas separadas para lista, mapa y detalle
- **Landscape** → Se puede mostrar lista y mapa en la misma pantalla (optimizable con `BoxWithConstraints`)

---

## Pruebas

- `CityViewModelTest.kt` valida:
    - Filtro por prefijo
    - Agregado/eliminación de favoritos
    - Visualización de solo favoritos
- `CityListScreenTest.kt` (UI Test):
    - Entrada en el campo de búsqueda
    - Comprobación de resultados
    - Comportamiento del ícono de favoritos

Se utilizó:

- `MockK` para mocks de UseCases
- `kotlinx.coroutines.test` para control del `Dispatchers.Main`

---

## Instalación y ejecución

1. Clona el repo
```bash
git clone https://github.com/tu-usuario/UalaCityExplorer.git


