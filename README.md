# Adyen Android Assignment

This repository contains the coding challenge for candidates applying for an Android role at Adyen.
Please create a local Git repository and include it in the final zip while keeping your Git history 
intact as we are interested in your development process as well as the end result.
Feel free to make changes to any code in this repository.

Your task is to create an app using the NASA Astronomy Picture Of the Day (APOD) API.
We have set up some basic networking code; you still need to register for an [API key](https://api.nasa.gov/) 
and add it to `app/local.gradle` - see `local.gradle.example` for details. 
You can verify your API key works by running the `PlanetaryServiceTest`.

## Features:
- [ ] Create a list screen of APODs ![](screens/List Screen.png):
  * Sanitise list to ONLY show images - NOT videos.
- [ ] Implement ordering for APODs ![](screens/Reorder Dialog.png):
  * Order by title - ascending.
  * Order by date - descending.
- [ ] Show a details screen with more info about a single APOD ![](screens/Detail Screen.png).
- [ ] Implement error screens:
  * Api error ![](screens/Error screen.png).

n.b The resources folder has been updated with the appropriate colors and icons.
  
## Development Tasks:
Tips for candidates to think about.

- [ ] App to function in both portrait and landscape orientations:
   * Is the UI still functional - is state persisted?
   * Ensure unnecessary network calls are not made.
- [ ] Unit tests.

## Grading:
We are interested in:
* How the features work - user functionality.
* How they are implemented - architecture & styling.
* And why implementation decisions were taken.

**OPTIONAL**
* Allow user to save/persist APODs that they like and pin them to the top of the summary list screen.
* The UI/UX is not a strict requirement, feel free to make it pixel perfect with animations if you please.



## Notes:
- I employed the Model-View-ViewModel (MVVM) architecture to separate concerns and enhance testability.
- The data flow is unidirectional, making sure that the UI observes the ViewModel for changes in state.
- I used Coroutines and Flow for asynchronous operations, enabling smooth background
    operations and real-time data updates without blocking the UI.
- Hilt was used for dependency injection to simplify the management of dependencies.
- I integrated Room as the local database for persisting data.
- I used MockK library to create mocks of classes for testing.

Possible Enhancements:
- In my current implementation of APODViewModel, I am launching an asynchronous operation in the
  init block of the viewmodel. This is not the most ideal solution as it can lead to issues if the call
  tries to update the object before it is fully initialized, but was adopted due to time constraints
  of the task. If I had more time I would device a more effective approach to trigger the data
  fetching operation.
- If I had more time, I would increase unit tests coverage and implement UI tests and instrumentation
    tests for the database to ensure comprehensive testing coverage and reliability of the app.