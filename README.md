# CPP Mobile Students App

A JavaFX 21 application implementing a mobile-like Students screen for CPP Mobile.

## Features

- **Mobile-like UI**: 380×780px window with clean, modern design
- **Three Sections**: Favorites, Academics, Campus Life
- **2×2 Tile Grids**: Each section contains 4 interactive tiles
- **Loading Feedback**: Progress indicators and status updates
- **Navigation**: Back button to Login screen
- **Responsive Design**: Scrollable content with hover effects

## Design Principles Demonstrated

- **Reduced short-term memory load**: Simplified sections, no accordions
- **Consistency & clear signifiers**: Uniform tile styling across all sections
- **Reversal of actions & internal locus**: Back goes to previous screen, not system logout
- **Visibility of system status**: Loading spinner + status text during tile clicks

## Technology Stack

- **Java 17**
- **JavaFX 21**
- **Gradle 8.5**

## Running the Application

```bash
# Set Java environment (if needed)
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-17.0.16.8-hotspot"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Run the app
.\gradlew.bat run
```

## Project Structure

```
src/main/java/cppmobile/
├── CPPMobileApp.java      # Main application class
├── LoginScene.java        # Simple login placeholder
└── StudentsScene.java      # Main Students screen
```

## Interactive Features

- Click any tile → Loading spinner + status update
- Hover over tiles → Visual feedback
- Back button → Navigate to Login screen
- Login button → Return to Students screen

## Requirements Met

✅ Mobile-like window (380×780px)  
✅ Header with Back button + Students title  
✅ Three section cards with 2×2 tile grids  
✅ Consistent tile styling and hover effects  
✅ Loading overlay with progress indicator  
✅ Status text updates  
✅ Back button navigation  
✅ Scrollable content  
✅ Clean, readable code with helper methods
