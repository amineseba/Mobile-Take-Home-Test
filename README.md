## Android App using Rick and Morty API in pagination manner. 

- Overview
This android application displays a list of characters from the Rick and Morty API, featuring pagination, search functionality, and a details view. Built using Kotlin, 
it leverages modern Android architecture components and follows best practices for scalability and maintainability.

## Instructions for Building and Running the application 

### Prerequisites

- Android Studio (latest stable version recommended).
- Android SDK with API level 21+.
- Internet connection for initial API calls.

### Steps
1. **Clone the Repository**:
    git clone (https://github.com/amineseba/Mobile-Take-Home-Test.git)
   cd Take-Home
2. **Open in Android Studio**:
- Select `File > Open` and choose the cloned directory.
3. **Sync Project**:
- Wait for Gradle to sync (click "Sync Project with Gradle Files" if needed).
4. **Build the App**:
- Select `Build > Make Project` to compile.
5. **Run the App**:
- Connect an Android device or use an emulator.
- Click `Run > Run 'app'` to launch.
6. **Usage**:
- Scroll to load more characters (20 per page as it mentionned in the Rick and Morty API documentation and automatically paginated from the Server side),
- Scroll and the characters list will be Appended by each 20 characters automatically, and you can scroll up and down.
- you can turn off the internet and scroll down after first usage with internet (data cached).
- Use the search bar (press Enter to submit) to filter by name.
- Click a character to view details.

### Architectural Choices
- **MVVM Architecture**: Used `ViewModel` with `Flow` and `PagingData` to separate UI from business logic.
- **Paging 3**: Implemented for efficient data loading with `RemoteMediator` and Room caching, supporting offline use.
- **Retrofit**: Chosen for API calls with Gson for JSON parsing.
- **Room Database**: Used for local caching to enable offline functionality.
- **Material Design**: Integrated `SearchBar` and `SearchView` for a modern UI.

### Assumptions and Decisions
- **API**: Assumed the Rick and Morty API<a href="https://rickandmortyapi.com/api/character/" target="_blank" rel="noopener noreferrer nofollow"></a> provides 826 characters across 42 pages, with filtering by `name`.
- **Offline**: Cached all data for offline scrolling to the last character (826), as partial caching wasn’t specified.
- **Search**: Limited to `name` filtering based on UI design; multi-filter (e.g., species) left as a potential enhancement.
- **Pagination**: Set to 20 items per page per API docs, with prefetchDistance of 5 for smooth scrolling.
- **Unit Tests**: Added basic tests for `ApiService` and `ViewModel`, with plans for expansion (e.g., `PagingSource` mocking).

### Known Limitations and Improvements
- Search triggers only on Enter key; real-time search could be added.
- Offline limit not restricted (loads all 826 characters); configurable if needed.
- The app handles network errors and HTTP exceptions by displaying generic error messages to the user. This approach provides basic feedback but lacks detailed information or actionable steps, Improve it by Dialogs for better UX.
