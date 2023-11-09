# Cats app

This app loads a constant stream of cat images. There is a refresh button in the top right to reload the cat images.

Used in this project:
*   User Interface built with **[Jetpack Compose](https://developer.android.com/jetpack/compose)**
*   A single-activity MVVM architecture, using **[Navigation Compose](https://developer.android.com/jetpack/compose/navigation)**.
*   A presentation layer that contains a Compose screen (View) and a **ViewModel**.
*   Reactive UIs using **[Flow](https://developer.android.com/kotlin/flow)** and **[coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** for asynchronous operations.
*   Network interaction using [Retrofit](https://square.github.io/retrofit/)
*   A pager which uses a remote mediator to load data from a remote data source (https://thecatapi.com/) and caches it in a local Room database.
*   Dependency injection using [Hilt](https://developer.android.com/training/dependency-injection/hilt-android).

## Screenshots:

<img src="https://i.imgur.com/4bPTCLQ.jpeg" width="300">

<img src="https://i.imgur.com/yN2M9i0.jpg" width="300">

<img src="https://i.imgur.com/vnXkGZ3.jpg" width="300">
