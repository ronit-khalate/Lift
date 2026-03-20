# Lift Log

Lift Log is a modern Android application designed to help fitness enthusiasts track their workouts, routines, and body progress. Built with the latest Android technologies, it provides a seamless and efficient user experience.

## Project Documentation

We use Notion to discuss features, track progress, and manage the development roadmap. You can find our workspace here:
- [Lift Log Notion Workspace](https://www.notion.so/Weekendlabs-329698c02eae80018e6fff59b4aa8814?source=copy_link) *(Authorized access only)*

## Features

- **Workout Tracking:** Log exercises, sets, and reps with ease.
- **Routine Management:** Create and manage custom workout routines.
- **Body Weight Tracking:** Monitor your body weight progress over time.
- **Exercise Library:** Comprehensive list of exercises with detailed instructions and muscle group information.
- **Offline Support:** Powered by Realm for fast, local-first data storage.

## Tech Stack

- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) for a modern, declarative UI.
- **Dependency Injection:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for clean and scalable architecture.
- **Database:** [Realm Kotlin](https://www.mongodb.com/docs/realm/sdk/kotlin/) for reactive and efficient local data persistence.
- **Networking:** [Retrofit](https://square.github.io/retrofit/) and [Gson](https://github.com/google/gson) for API interactions.
- **Image Loading:** [Coil](https://coil-kt.github.io/coil/) for fast and lightweight image loading.
- **Local Preferences:** [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) for storing user preferences.

## Getting Started

### Prerequisites

- Android Studio Iguana or newer.
- JDK 17.
- Android device or emulator running API 26 (Android 8.0) or higher.

### Installation (For Authorized Users)

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/ZLift.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the `app` module on your device/emulator.

## Architecture

The project follows modern Android development practices and Clean Architecture principles, leveraging:
- MVVM (Model-View-ViewModel) pattern.
- Repository pattern for data abstraction.
- Kotlin Coroutines and Flow for asynchronous programming.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
