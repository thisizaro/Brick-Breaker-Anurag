# Brick Breaker Game

Welcome to the **Brick Breaker Game**! This is a classic arcade game where you control a paddle to bounce a ball and break bricks. Keep the ball from falling, and clear all the bricks to win!

## Features

- Classic brick-breaking gameplay
- Smooth and responsive controls
- Score tracking system
- Game over and win conditions
- Increasing difficulty levels (Upcoming)

## How to Play

1. Use the **Left** and **Right Arrow Keys** to move the paddle.
2. Bounce the ball using the paddle to break the bricks.
3. Prevent the ball from falling off the screen, or you lose.
4. Break all the bricks to win the game.

## Installation

### Prerequisites

- **Java 17** or higher
- **Gradle** (included in the repository)

### Steps

1. Clone this repository:
   ```bash
   git clone https://github.com/Anurag-2312/Brick-Breaker.git
   ```
2. Navigate to the project directory:
   ```bash
   cd Brick-Breaker
   ```
3. Build the project using Gradle:
   ```bash
   ./gradlew build   # For Linux/macOS
   gradlew.bat build # For Windows
   ```
4. Run the game:
   ```bash
   ./gradlew run   # For Linux/macOS
   gradlew.bat run # For Windows
   ```

## Project Structure

```
Brick-Breaker/
│── app/
│   ├── src/main/java/brickBreaker/   # Java source files
│   ├── src/main/resources/           # Game assets
│   ├── build.gradle.kts              # Gradle build script
│── gradlew.bat                       # Windows Gradle wrapper
│── gradlew                           # Unix Gradle wrapper
│── settings.gradle.kts               # Gradle settings
│── README.md                         # This file
```

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.
2. Create a new branch with your changes.
3. Submit a pull request with a clear description.

Happy coding! 🎮
