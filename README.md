Name: Ryan Yi
Computing Id: sbr8wr

HW 0:
Notes:
- Wanted to go to office hours instead of using AI, but the office listed (RICE 430) is a CS Computing Lab for CS Graduate Students and could not enter. Also tried looking for office hours in Room 330, and Room 442, both of which were not the right location.

AI Usage:
- Used AI to help set up and deal with issues with Github.
- Specifically had issues making a repo, linking with Android Studio
- Used AI to try to fix issue with Github pulling from laptop to PC. Issue with gradle, fixed when restarting entirely.

HW 1:
Notes:
- Tried to make app look nicer using Theme, Color, and Type only. But it didn't really work. I think I would need to change the Screens, but am unsure. Did not want to use AI for this as it might've changed the code too much and I would not be able to explain.

AI Usage:
- Used it to help with java.time.localDate.
- Also used it to help with Color.kt, and Type.kt
- Type.kt (Change font to Roboto (Sansserif)
- Color.kt (Hex Values for Color)

HW 2:
Notes:
- For the week selector, I decided to use a Dropdown Menu, as I thought it would be the best way to choose a desired week. It would lessen the amount of taps needed compared to an incrementor, and would allow you to go to the desired week directly.
- Ran into a few issues during setup, and a few issues later on.

Issue?:
- Found issue with data display, weeks 1-6, 8,9 are all the same. Only weeks 7, and 10+ are different / accurate. Not sure if issue with my code, or something else.

AI Usage:
- Mainly to aid in set-up and fixing some unknown issues.
- Main issue that constituted most AI usage was Room Dependency setup. Ran into an issue, where ksp kept getting flagged as an unsolved reference.
- Other usages:
- Upon finishing first iteration, app kept crashing on launch, asked AI, and told me reason was due to database issues, and suggested updating version=1 to 2, and adding exportschema=false.
- Also suggested adding .fallbackToDestructiveMigration() to prevent unknown crashes.
- Also had issues, where app would take long time to launch and would get thread dump error. AI didn't really help.
- Had it help with UI.Theme(Color, Theme, Type) (Giving color palette for blue and green theme, etc.)
- Had issues with app taking long time to launch, update, and would freeze. AI brought up that I had forgotten to separate by division and week.
- Also helped with github, I decided to use a branch for HW2 and had issues merging it back into main.
- After reviewing code, I came to conclusion that the issue did not lied with the View model and Activity. Had AI help look through and fix that code. Led to the AI making some redesigns. It wanted to also add a circular button to refresh, but it felt redundant with the pull to refresh and the formatting was weird so I removed it.

HW 3:
Notes:
- Tested the app on my old phone (Samsung S22).

Issues (?):
- If I held the phone completely still while walking in place, it wouldn't count the steps. And conversely, if I swung my arm back and forth while remaining still it counted steps, with out without StepCounter permission.
- I think this is due to how I handled step counting.

AI Usage:
- Mainly used to aid in setup of app and debugging.
- Is listed in the files in comments everywhere AI was used. However, the area is also highly explained in comments as well.

REFLECTION:

Sampling Rate:
- In MainActivity.kt, I used SensorManager.SENSOR_DELAY_UI. I choose this under the advice of AI as it explained that it was the best option for walking as it samples the accelerometer and step counter at a rate of around once every 60,000 microseconds. (0.6 seconds) which seems reasonable for walking as the average walking speed of a human is ~2 steps per second.
- Battery vs Needs. While apparently SENSOR_DELAY_FASTEST polls data rapidly, it also uses up a lot of CPU and battery as it keeps the sampling running more frequently. This isn't the most optimal solution as walking isn't a rapid case scenario.
- In the viewmodel, for the accelerometer, I had the delay be 0.25 seconds. This is different from the SensorManager.SENSOR_DELAY_UI. As explained in a comment, I used this to prevent possible double counting from reverberations during steps. And to possible account for sprinting, or speed walking as the average speed for that ups to ~3 steps per second. For the force required I used 11.5 as there should be a passive 9.81 from gravity. And slight movements could easily push it to ~11. This seemed to work well, as putting my phone down, or typing next to it didn't raise the step counter. The way I calculated the force was taking the magnitude (physics formula sqrt(x^2 + y^2 + z^2)) of the direction vectors.

Ethics:
- High frequency logging is useful in making sure it collects every step taken by the user, but it could also be used to infer the user's method of transport, daily habits, specific important locations (home, work), and health status.
  - Method of transport: Maybe they take a few steps in the morning, and then there is like a 30 minute break, before they start walking again (infer: they got in a car and drove somewhere)
  - Daily Habits: They start walking at 6 am, and stop walking past 10 pm. (Infer: They sleep during that time.)
  - Specific Important locations: Can estimate distance based on how long they walk. (Infer: They live close to their work place, as they walk for 15 minutes between a certain time period)
  - Health Status: They walk significantly less than usual (Infer: They got sick or injured.)
- Data is stored locally. The app runs using a Room Database which is stored locally and not connected to any internet backup. So the data isn't being shared with anyone or stored outside of your control.
- The app requests access to Step Counter, but can run using the accelerometer instead. However, the information is displayed on the app's 'Live Sensor Screen' right above the step counter.
