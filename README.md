# Cats app

This app loads a constant stream of cat images. There is a refresh button in the top right to reload the cat images.

Used in this project:
*   User Interface built with **[Jetpack Compose](https://developer.android.com/jetpack/compose)**
*   A single-activity MVVM architecture, using **[Navigation Compose](https://developer.android.com/jetpack/compose/navigation)**.
*   A presentation layer that contains a Compose screen (View) and a **ViewModel**.
*   Reactive UIs using **[Flow](https://developer.android.com/kotlin/flow)** and **[coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** for asynchronous operations.
*   Network interaction using [Retrofit](https://square.github.io/retrofit/)
*   A pager which uses a remote mediator to load data from a remote data source (https://thecatapi.com/) and caches it in a local [Room](https://developer.android.com/jetpack/androidx/releases/room) database.
*   Dependency injection using [Hilt](https://developer.android.com/training/dependency-injection/hilt-android).

## Screenshots:

<img src="https://i.ibb.co/jH5SYZG/cats-screenshot-1.jpg" width="300">

<img src="https://i.ibb.co/d4fcrvd/cats-screenshot-2.jpg" width="300">

<img src="https://i.ibb.co/NZWR011/cats-screenshot-3.jpg" width="300">