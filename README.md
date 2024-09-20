# BookExplorer App

The **BookExplorer** app allows users to search for and explore books from a large collection. It provides detailed information about each book, including the author, publish date, and a brief description. The app is designed using modern Android development practices, following the **Model-View-Intent (MVI)** architecture pattern for better separation of concerns and maintainability.


https://github.com/user-attachments/assets/e29cec54-68a9-414c-b0b4-7dc906ced730


## Features

- **Book Search**: Users can search for books by title or author name.
- **Book Details**: View detailed information about books, including title, author, publication year, cover image, and a brief description.
- **User Interface**: Built entirely with [Jetpack Compose](https://developer.android.com/jetpack/compose) to deliver a modern and responsive UI.
- **State Management**: Follows the **MVI** architecture pattern for managing the app's state effectively.
- **Sorting Options**: Allows users to sort books by publish date or author name.
- **Scroll Position Preservation**: Maintains scroll position when navigating between screens, providing a smooth user experience.
- **Testing**: Comprehensive UI tests using **Jetpack Compose** to ensure app stability and correctness.

## Technologies Used

- **Jetpack Compose**: For building a modern, declarative UI.
- **Retrofit**: For making network requests to fetch book data from an API.
- **Coil**: For efficient image loading and caching of book covers.
- **MVI Architecture**: Ensures clean separation of state, view, and business logic for maintainable and testable code.
- **Coroutines & Flow**: For handling asynchronous operations and managing data streams.
- **UI Testing**: Written tests using Compose’s testing framework to verify UI behavior.

## Repository

The repository follows **clean code** principles and is structured to be easily extendable and scalable for future enhancements.
