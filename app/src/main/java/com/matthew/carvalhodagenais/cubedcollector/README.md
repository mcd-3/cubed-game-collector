# Directory Layout
If you are viewing the code for the first time or need some quick documentation, read below to see what each directory is for and how the code is organised

### adapters
Adapters for RecyclerViews
### data
All things related to inner storage using Room
- dao: Data Access Objects to interact with database tables
- entities: Models mapped to database tables
- repositories: Contains DAO functions for its respective models
### databinders
Used in binding data and modifying data for layouts
- viewactions: bindings for OnClick and similar methods
### factories
Factory classes, mostly to create ViewModels
### helpers
Classes to help out with specific tasks, like storing images
- generators: generates onClicks/adapters/watchers/listeners for views
### ui
Fragments for each page in the app
### viewmodels
ViewModels linked to each UI fragment (1 fragment <-> 1 viewmodel)
### MainActivity.kt
Not a directory, but the parent holder for all UI fragments. Contains session specific code and things shared between fragments
